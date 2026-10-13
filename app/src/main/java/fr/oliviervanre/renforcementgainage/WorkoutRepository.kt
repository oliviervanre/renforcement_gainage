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
            tourLabel = "Avant séance",
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
            spokenInstruction = "Pompes inclinées ou classiques. Objectif : quinze à vingt répétitions propres. Vous avez vingt-cinq secondes. Corps gainé, amplitude propre.",
            durationSeconds = 25,
            objective = "15 à 20 répétitions propres",
            guidance = "Corps gainé. Bonne cadence sans précipitation. Variante inclinée si nécessaire.",
            tourLabel = tour,
            announceAtSeconds = setOf(10),
            animationResId = R.drawable.pompes
        )
        steps += restStep(tour)

        steps += repsStep(
            title = "Squats",
            spokenInstruction = "Squats. Quinze répétitions propres. Descente contrôlée, genoux dans l'axe. Vous avez quarante-cinq secondes.",
            durationSeconds = 45,
            objective = "15 répétitions",
            guidance = "Dos tenu. Appui sur tout le pied. Remontez sans vous précipiter.",
            tourLabel = tour,
            announceAtSeconds = setOf(10)
        )
        steps += restStep(tour)

        steps += repsStep(
            title = "Fentes droite",
            spokenInstruction = "Fentes côté droit. Dix répétitions. Trente-cinq secondes. Pied droit devant, jambe gauche en arrière. Buste droit, genou dans l'axe.",
            durationSeconds = 35,
            objective = "10 répétitions côté droit",
            guidance = "Pied droit devant. Descente contrôlée. Remontez en poussant dans le pied droit.",
            tourLabel = tour,
            announceAtSeconds = setOf(15)
        )
        steps += changeSideStep(tour, durationSeconds = 10)

        steps += repsStep(
            title = "Fentes gauche",
            spokenInstruction = "Fentes côté gauche. Dix répétitions. Trente-cinq secondes. Pied gauche devant, jambe droite en arrière. Même contrôle.",
            durationSeconds = 35,
            objective = "10 répétitions côté gauche",
            guidance = "Pied gauche devant. Gardez le buste droit et le genou dans l'axe.",
            tourLabel = tour,
            announceAtSeconds = setOf(15)
        )
        steps += restStep(tour)

        steps += timedStep(
            title = "Gainage frontal",
            spokenInstruction = "Gainage frontal. Cinquante secondes. Abdos serrés, fessiers actifs. Ne creusez pas le dos.",
            durationSeconds = 50,
            objective = "50 secondes",
            guidance = "Respirez. Bassin stable. Arrêtez si douleur anormale.",
            tourLabel = tour,
            announceAtSeconds = setOf(40, 30, 20, 10)
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
            title = "Rowing droit",
            spokenInstruction = "Rowing côté droit. Objectif : quinze répétitions. Main gauche et genou gauche en appui, dos plat. Haltère dans la main droite. Tirez le coude droit vers la hanche. Trente secondes.",
            durationSeconds = 30,
            objective = "15 répétitions côté droit",
            guidance = "Dos plat. Le coude recule vers la hanche. Pas d'élan, pas de tirage de biceps.",
            tourLabel = tour,
            announceAtSeconds = setOf(10),
            canSkip = true
        )
        steps += shortRestStep(tour)

        steps += repsStep(
            title = "Rowing gauche",
            spokenInstruction = "Rowing côté gauche. Objectif : quinze répétitions. Main droite et genou droit en appui, dos plat. Haltère dans la main gauche. Tirez le coude gauche vers la hanche. Trente secondes.",
            durationSeconds = 30,
            objective = "15 répétitions côté gauche",
            guidance = "Même consigne. Dos stable. Tirage contrôlé, coude vers la hanche.",
            tourLabel = tour,
            announceAtSeconds = setOf(10),
            canSkip = true
        )
        steps += restStep(tour)

        steps += repsStep(
            title = "Épaule droite",
            spokenInstruction = "Développé épaule droite. Objectif : dix répétitions. Haltère à l'épaule, poussez au-dessus de la tête sans cambrer. Trente secondes. Mouvement propre et contrôlé.",
            durationSeconds = 30,
            objective = "10 répétitions côté droit",
            guidance = "Abdos serrés. Ne cambrez pas. Contrôlez la descente jusqu'à l'épaule.",
            tourLabel = tour,
            announceAtSeconds = setOf(10),
            canSkip = true
        )
        steps += shortRestStep(tour)

        steps += repsStep(
            title = "Épaule gauche",
            spokenInstruction = "Développé épaule gauche. Objectif : dix répétitions. Haltère à l'épaule, poussez au-dessus de la tête sans cambrer. Trente secondes. Gardez le buste stable.",
            durationSeconds = 30,
            objective = "10 répétitions côté gauche",
            guidance = "Même consigne. Buste stable, pas de compensation avec le dos.",
            tourLabel = tour,
            announceAtSeconds = setOf(10),
            canSkip = true
        )
    }

    private fun repsStep(
        title: String,
        spokenInstruction: String,
        durationSeconds: Int,
        objective: String,
        guidance: String,
        tourLabel: String,
        announceAtSeconds: Set<Int> = setOf(10),
        canSkip: Boolean = false,
        animationResId: Int? = null
    ) = WorkoutStep(
        title = title,
        spokenInstruction = spokenInstruction,
        type = StepType.REPS,
        durationSeconds = durationSeconds,
        objective = objective,
        guidance = guidance,
        tourLabel = tourLabel,
        announceAtSeconds = announceAtSeconds,
        canSkip = canSkip,
        animationResId = animationResId
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
        spokenInstruction = "Récupération. Vingt secondes.",
        type = StepType.REST,
        durationSeconds = 20,
        objective = "20 secondes",
        guidance = "Respirez. Préparez l'exercice suivant.",
        tourLabel = tourLabel,
        announceAtSeconds = setOf(10)
    )

    private fun shortRestStep(tourLabel: String) = WorkoutStep(
        title = "Changement de côté",
        spokenInstruction = "Changez de côté. Quinze secondes.",
        type = StepType.REST,
        durationSeconds = 15,
        objective = "15 secondes",
        guidance = "Replacez-vous proprement avant de repartir.",
        tourLabel = tourLabel,
        announceAtSeconds = setOf(5)
    )

    private fun changeSideStep(
        tourLabel: String,
        durationSeconds: Int
    ) = WorkoutStep(
        title = "Changement de côté",
        spokenInstruction = "Changez de côté. Dix secondes.",
        type = StepType.REST,
        durationSeconds = durationSeconds,
        objective = "$durationSeconds secondes",
        guidance = "Replacez-vous proprement avant de repartir.",
        tourLabel = tourLabel,
        announceAtSeconds = emptySet()
    )
}
