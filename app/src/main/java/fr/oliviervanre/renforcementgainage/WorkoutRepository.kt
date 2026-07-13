package fr.oliviervanre.renforcementgainage

object WorkoutRepository {

    fun defaultRoutine(): List<WorkoutStep> {
        val steps = mutableListOf<WorkoutStep>()

        steps += WorkoutStep(
            title = "Préparation",
            spokenInstruction = "Prenez quelques secondes pour vous placer. Relâchez les épaules. Début du circuit.",
            type = StepType.TIMED,
            durationSeconds = 15,
            objective = "Se placer",
            guidance = "Respirez calmement. Préparez le premier exercice.",
            tourLabel = "Préparation",
            announceAtSeconds = setOf(5)
        )

        addCircuitTour(steps, tourNumber = 1, totalTours = 2)

        steps += WorkoutStep(
            title = "Pause entre les tours",
            spokenInstruction = "Pause entre les deux tours. Soixante secondes. Marchez un peu, respirez calmement.",
            type = StepType.REST,
            durationSeconds = 60,
            objective = "Récupération",
            guidance = "Ne vous asseyez pas forcément. Respiration calme.",
            tourLabel = "Inter-tour",
            announceAtSeconds = setOf(30, 10)
        )

        addCircuitTour(steps, tourNumber = 2, totalTours = 2)

        steps += WorkoutStep(
            title = "Fin de séance",
            spokenInstruction = "Séance terminée. Respirez calmement. Bon travail.",
            type = StepType.TIMED,
            durationSeconds = 20,
            objective = "Retour au calme libre",
            guidance = "Relâchez les épaules. Marchez quelques pas si besoin.",
            tourLabel = "Terminé",
            announceAtSeconds = emptySet()
        )

        return steps
    }

    private fun addCircuitTour(
        steps: MutableList<WorkoutStep>,
        tourNumber: Int,
        totalTours: Int
    ) {
        val tour = "Tour $tourNumber / $totalTours"

        steps += repsStep(
            title = "Pompes",
            spokenInstruction = "Pompes inclinées ou classiques. Objectif : dix répétitions propres. Vous avez quarante-cinq secondes. Pas de précipitation.",
            durationSeconds = 45,
            objective = "10 répétitions propres",
            guidance = "Corps gainé. Amplitude propre. Variante inclinée si nécessaire.",
            tourLabel = tour
        )
        steps += restStep(tour)

        steps += repsStep(
            title = "Squats",
            spokenInstruction = "Squats. Quinze répétitions propres. Descente contrôlée, genoux dans l'axe. Vous avez cinquante secondes.",
            durationSeconds = 50,
            objective = "15 répétitions",
            guidance = "Dos tenu. Appui sur tout le pied. Remontez sans vous précipiter.",
            tourLabel = tour
        )
        steps += restStep(tour)

        steps += repsStep(
            title = "Fentes",
            spokenInstruction = "Fentes. Huit à dix par jambe. Buste droit. Vous avez soixante-quinze secondes.",
            durationSeconds = 75,
            objective = "8 à 10 par jambe",
            guidance = "Pas trop long. Contrôle à la descente. Faites moins si la technique se dégrade.",
            tourLabel = tour,
            announceAtSeconds = setOf(30, 15)
        )
        steps += restStep(tour)

        steps += timedStep(
            title = "Gainage frontal",
            spokenInstruction = "Gainage frontal. Trente-cinq secondes. Abdos serrés, fessiers actifs. Ne creusez pas le dos.",
            durationSeconds = 35,
            objective = "35 secondes",
            guidance = "Respirez. Bassin stable. Arrêtez si douleur anormale.",
            tourLabel = tour,
            announceAtSeconds = setOf(20, 10)
        )
        steps += restStep(tour)

        steps += timedStep(
            title = "Gainage gauche",
            spokenInstruction = "Gainage latéral gauche. Vingt-cinq secondes. Bassin haut, nuque neutre.",
            durationSeconds = 25,
            objective = "25 secondes",
            guidance = "Appui confortable. Variante genoux au sol si besoin.",
            tourLabel = tour,
            announceAtSeconds = setOf(10)
        )
        steps += restStep(tour)

        steps += timedStep(
            title = "Gainage droit",
            spokenInstruction = "Gainage latéral droit. Vingt-cinq secondes. Bassin haut, respiration continue.",
            durationSeconds = 25,
            objective = "25 secondes",
            guidance = "Même consigne. Qualité avant durée.",
            tourLabel = tour,
            announceAtSeconds = setOf(10)
        )
        steps += restStep(tour)

        steps += repsStep(
            title = "Rowing",
            spokenInstruction = "Rowing élastique ou haltères. Douze répétitions propres. Tirez les coudes vers l'arrière. Vous avez quarante-cinq secondes.",
            durationSeconds = 45,
            objective = "12 répétitions",
            guidance = "Rapprochez les omoplates. Épaules basses. Mouvement contrôlé.",
            tourLabel = tour
        )
        steps += restStep(tour)

        steps += repsStep(
            title = "Épaules",
            spokenInstruction = "Développé épaules léger. Dix répétitions propres. Pas de cambrure excessive. Vous avez quarante-cinq secondes.",
            durationSeconds = 45,
            objective = "10 répétitions",
            guidance = "Charge modérée. Abdos tenus. Arrêtez si gêne articulaire.",
            tourLabel = tour
        )
    }

    private fun repsStep(
        title: String,
        spokenInstruction: String,
        durationSeconds: Int,
        objective: String,
        guidance: String,
        tourLabel: String,
        announceAtSeconds: Set<Int> = setOf(15)
    ) = WorkoutStep(
        title = title,
        spokenInstruction = spokenInstruction,
        type = StepType.REPS,
        durationSeconds = durationSeconds,
        objective = objective,
        guidance = guidance,
        tourLabel = tourLabel,
        announceAtSeconds = announceAtSeconds
    )

    private fun timedStep(
        title: String,
        spokenInstruction: String,
        durationSeconds: Int,
        objective: String,
        guidance: String,
        tourLabel: String,
        announceAtSeconds: Set<Int>
    ) = WorkoutStep(
        title = title,
        spokenInstruction = spokenInstruction,
        type = StepType.TIMED,
        durationSeconds = durationSeconds,
        objective = objective,
        guidance = guidance,
        tourLabel = tourLabel,
        announceAtSeconds = announceAtSeconds
    )

    private fun restStep(tourLabel: String) = WorkoutStep(
        title = "Récupération",
        spokenInstruction = "Récupération. Vingt-cinq secondes.",
        type = StepType.REST,
        durationSeconds = 25,
        objective = "25 secondes",
        guidance = "Respirez. Préparez l'exercice suivant.",
        tourLabel = tourLabel,
        announceAtSeconds = setOf(10)
    )
}
