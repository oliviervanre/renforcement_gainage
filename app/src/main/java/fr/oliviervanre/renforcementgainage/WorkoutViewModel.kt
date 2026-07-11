package fr.oliviervanre.renforcementgainage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel : ViewModel() {

    private val routine = WorkoutRepository.defaultRoutine()

    private val _uiState = MutableStateFlow(
        WorkoutUiState(
            currentStep = routine.firstOrNull(),
            nextStep = routine.getOrNull(1),
            remainingSeconds = routine.firstOrNull()?.durationSeconds ?: 0
        )
    )
    val uiState: StateFlow<WorkoutUiState> = _uiState.asStateFlow()

    private var job: Job? = null
    private var speechManager: SpeechManager? = null

    fun attachSpeechManager(manager: SpeechManager) {
        speechManager = manager
    }

    fun start() {
        if (_uiState.value.isRunning) return

        job?.cancel()
        _uiState.value = WorkoutUiState(
            currentStepIndex = 0,
            currentStep = routine.firstOrNull(),
            nextStep = routine.getOrNull(1),
            remainingSeconds = routine.firstOrNull()?.durationSeconds ?: 0,
            isRunning = true
        )

        job = viewModelScope.launch {
            runRoutine()
        }
    }

    fun pause() {
        if (!_uiState.value.isRunning || _uiState.value.isPaused) return
        _uiState.value = _uiState.value.copy(isPaused = true)
        speechManager?.speak("Pause.")
    }

    fun resume() {
        if (!_uiState.value.isPaused) return
        _uiState.value = _uiState.value.copy(isPaused = false)
        speechManager?.speak("Reprise.")
    }

    fun stop() {
        job?.cancel()
        speechManager?.stop()
        _uiState.value = WorkoutUiState(
            currentStep = routine.firstOrNull(),
            nextStep = routine.getOrNull(1),
            remainingSeconds = routine.firstOrNull()?.durationSeconds ?: 0
        )
    }

    private suspend fun runRoutine() {
        routine.forEachIndexed { index, step ->
            _uiState.value = _uiState.value.copy(
                currentStepIndex = index,
                currentStep = step,
                nextStep = routine.getOrNull(index + 1),
                remainingSeconds = step.durationSeconds,
                isRunning = true,
                isPaused = false,
                isFinished = false
            )

            speechManager?.speak(step.spokenInstruction)

            var remaining = step.durationSeconds

            while (remaining > 0) {
                while (_uiState.value.isPaused) {
                    delay(250)
                }

                delay(1000)
                remaining--

                val shouldAnnounce =
                    step.announceEverySeconds != null &&
                        remaining > 0 &&
                        remaining % step.announceEverySeconds == 0

                if (shouldAnnounce) {
                    speechManager?.speak("$remaining secondes restantes")
                }

                _uiState.value = _uiState.value.copy(remainingSeconds = remaining)
            }

            routine.getOrNull(index + 1)?.let { next ->
                speechManager?.speak("Prochain exercice : ${next.title}.")
                delay(800)
            }
        }

        _uiState.value = _uiState.value.copy(
            isRunning = false,
            isPaused = false,
            isFinished = true,
            remainingSeconds = 0
        )
        speechManager?.speak("Séance terminée.")
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        speechManager?.shutdown()
    }
}
