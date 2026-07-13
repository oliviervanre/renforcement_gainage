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
        launchRoutineFrom(0)
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

    fun skip() {
        val state = _uiState.value
        val step = state.currentStep ?: return
        if (!state.isRunning || state.isPaused || !step.canSkip) return

        val nextIndex = state.currentStepIndex + 1
        job?.cancel()

        job = viewModelScope.launch {
            speechManager?.speakAndWait(PersiflageRepository.randomRemark())
            runRoutineFrom(nextIndex)
        }
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

    private fun launchRoutineFrom(startIndex: Int) {
        job = viewModelScope.launch {
            runRoutineFrom(startIndex)
        }
    }

    private suspend fun runRoutineFrom(startIndex: Int) {
        if (startIndex !in routine.indices) {
            finishRoutine()
            return
        }

        for (index in startIndex..routine.lastIndex) {
            val step = routine[index]

            _uiState.value = _uiState.value.copy(
                currentStepIndex = index,
                currentStep = step,
                nextStep = routine.getOrNull(index + 1),
                remainingSeconds = step.durationSeconds,
                isRunning = true,
                isPaused = false,
                isFinished = false
            )

            speakInstructionThenStart(step)

            var remaining = step.durationSeconds

            while (remaining > 0) {
                while (_uiState.value.isPaused) {
                    delay(250)
                }

                delay(1000)
                remaining--

                val shouldAnnounceByInterval =
                    step.announceEverySeconds != null &&
                        remaining > 0 &&
                        remaining % step.announceEverySeconds == 0

                val shouldAnnounceByMarker = remaining in step.announceAtSeconds

                if (shouldAnnounceByInterval || shouldAnnounceByMarker) {
                    speechManager?.speak("Encore $remaining secondes.")
                }

                _uiState.value = _uiState.value.copy(remainingSeconds = remaining)
            }

            if (index < routine.lastIndex) {
                speechManager?.speakAndWait("Stop.")
            }
        }

        finishRoutine()
    }

    private suspend fun speakInstructionThenStart(step: WorkoutStep) {
        val speech = speechManager
        speech?.speakAndWait(step.spokenInstruction)

        if (step.type != StepType.REST && step.title != "Fin de séance") {
            speech?.speakAndWait("Attention. Top.")
        }
    }

    private fun finishRoutine() {
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
