package fr.oliviervanre.renforcementgainage

object WorkoutRepository {

    fun defaultRoutine(): List<WorkoutStep> = listOf(
        WorkoutStep(
            title = "Mobilité épaules",
            spokenInstruction = "Mobilité des épaules. Trente secondes.",
            type = StepType.TIMED,
            durationSeconds = 30
        ),
        WorkoutStep(
            title = "Squats lents",
            spokenInstruction = "Squats lents. Trente secondes.",
            type = StepType.TIMED,
            durationSeconds = 30
        ),
        WorkoutStep(
            title = "Pompes",
            spokenInstruction = "Faire quinze pompes.",
            type = StepType.REPS,
            durationSeconds = 25
        ),
        WorkoutStep(
            title = "Pause",
            spokenInstruction = "Pause. Trente secondes.",
            type = StepType.REST,
            durationSeconds = 30
        ),
        WorkoutStep(
            title = "Fentes",
            spokenInstruction = "Faire dix fentes par jambe.",
            type = StepType.REPS,
            durationSeconds = 35
        ),
        WorkoutStep(
            title = "Pause",
            spokenInstruction = "Pause. Trente secondes.",
            type = StepType.REST,
            durationSeconds = 30
        ),
        WorkoutStep(
            title = "Gainage frontal",
            spokenInstruction = "Gainage frontal. Quarante-cinq secondes.",
            type = StepType.TIMED,
            durationSeconds = 45,
            announceEverySeconds = 10
        ),
        WorkoutStep(
            title = "Pause",
            spokenInstruction = "Pause. Trente secondes.",
            type = StepType.REST,
            durationSeconds = 30
        ),
        WorkoutStep(
            title = "Gainage gauche",
            spokenInstruction = "Gainage latéral gauche. Trente secondes.",
            type = StepType.TIMED,
            durationSeconds = 30,
            announceEverySeconds = 10
        ),
        WorkoutStep(
            title = "Pause",
            spokenInstruction = "Pause. Quinze secondes.",
            type = StepType.REST,
            durationSeconds = 15
        ),
        WorkoutStep(
            title = "Gainage droit",
            spokenInstruction = "Gainage latéral droit. Trente secondes.",
            type = StepType.TIMED,
            durationSeconds = 30,
            announceEverySeconds = 10
        ),
        WorkoutStep(
            title = "Étirement final",
            spokenInstruction = "Routine étirement. Une minute.",
            type = StepType.TIMED,
            durationSeconds = 60,
            announceEverySeconds = 15
        )
    )
}
