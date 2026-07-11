package fr.oliviervanre.renforcementgainage

enum class StepType {
    TIMED,
    REPS,
    REST
}

data class WorkoutStep(
    val title: String,
    val spokenInstruction: String,
    val type: StepType,
    val durationSeconds: Int,
    val announceEverySeconds: Int? = null
)

data class WorkoutUiState(
    val currentStepIndex: Int = 0,
    val currentStep: WorkoutStep? = null,
    val nextStep: WorkoutStep? = null,
    val remainingSeconds: Int = 0,
    val isRunning: Boolean = false,
    val isPaused: Boolean = false,
    val isFinished: Boolean = false
)
