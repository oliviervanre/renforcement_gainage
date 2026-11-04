package fr.oliviervanre.renforcementgainage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private data class ExerciseGuide(
    val name: String,
    val target: String,
    val setup: String,
    val movement: String,
    val vigilance: String,
    val adjustment: String
)

private val exerciseGuides = listOf(
    ExerciseGuide(
        "Pompes",
        "15 à 20 répétitions, 25 secondes",
        "Mains sous les épaules, pieds en arrière et corps aligné. Utilisez vos poignées si elles vous conviennent.",
        "Descendez sans affaisser le bassin, puis repoussez le sol. Gardez une amplitude régulière.",
        "Évitez de creuser les lombaires ou d'avancer la tête. Arrêtez avant de perdre l'alignement.",
        "Une pompe inclinée sur un support réellement stable permet de conserver une bonne technique si les dernières répétitions deviennent difficiles."
    ),
    ExerciseGuide(
        "Squats",
        "15 répétitions, 45 secondes",
        "Pieds environ à largeur d'épaules, pointes légèrement ouvertes selon votre confort.",
        "Reculez les hanches comme pour vous asseoir ; fléchissez les genoux, puis remontez en poussant dans tout le pied.",
        "Gardez les genoux dans l'axe des pieds. Ne cherchez pas une profondeur qui arrondit le dos ou fait décoller les talons.",
        "Pour le golf et la vie quotidienne, privilégiez le contrôle, la mobilité et la stabilité plutôt que la vitesse."
    ),
    ExerciseGuide(
        "Fentes",
        "10 répétitions par jambe, 35 secondes de chaque côté",
        "Pour la fente droite, pied droit devant et jambe gauche en arrière ; inversez pour la gauche.",
        "Fléchissez les jambes verticalement, buste droit ; poussez dans le pied avant pour remonter. Le genou arrière ne doit pas toucher le sol.",
        "Contrôlez l'équilibre et gardez le genou avant aligné avec le pied. Évitez une longueur de pas excessive.",
        "Les fentes arrière peuvent être plus faciles à contrôler. Cet exercice sert notamment la stabilité unilatérale utile au golf et pour monter dans un avion."
    ),
    ExerciseGuide(
        "Gainage frontal",
        "50 secondes",
        "Avant-bras et pointes de pieds au sol, coudes sous les épaules. Utilisez vos protections de coudes.",
        "Contractez légèrement abdominaux et fessiers ; respirez normalement, bassin stable.",
        "Ne creusez pas le bas du dos et ne bloquez pas la respiration.",
        "L'objectif est une tenue propre de 50 secondes, non un record. Si la posture se dégrade, interrompez la tenue."
    ),
    ExerciseGuide(
        "Gainage latéral",
        "25 secondes de chaque côté",
        "Coude sous l'épaule, avant-bras au sol, jambes allongées et pieds superposés ou décalés.",
        "Soulevez le bassin pour aligner épaules, hanches et pieds ; continuez à respirer.",
        "Évitez que le bassin descende ou que l'épaule d'appui s'affaisse.",
        "Gardez les 25 secondes actuelles. Variante genoux au sol en cas de fatigue : l'alignement compte plus que la durée."
    ),
    ExerciseGuide(
        "Rowing unilatéral",
        "15 répétitions par bras, 30 secondes de chaque côté ; charge actuelle : 12 kg",
        "Main et genou opposés à l'haltère en appui sur un support stable ; dos plat, nuque dans le prolongement du dos.",
        "Tirez le coude vers la hanche, puis redescendez lentement l'haltère.",
        "Évitez de tourner le buste, de donner de l'élan ou de soulever l'épaule vers l'oreille.",
        "À 12 kg, privilégiez 15 répétitions sans tricher. Sans haltère ni appui fiable, utilisez Passer l'étape ; une alternative sera étudiée séparément."
    ),
    ExerciseGuide(
        "Développé épaule unilatéral",
        "10 répétitions par bras, 30 secondes de chaque côté ; charge actuelle : 5 kg",
        "Debout, pieds stables, haltère près de l'épaule, abdominaux légèrement engagés.",
        "Poussez l'haltère vers le haut sans incliner le tronc, puis contrôlez la descente.",
        "Ne cambrez pas les lombaires et ne forcez pas une trajectoire douloureuse pour l'épaule.",
        "À 5 kg, la priorité est le contrôle sur les deux tours. Réduisez les répétitions ou passez l'étape si la technique se dégrade."
    )
)

@Composable
fun ExerciseGuideScreen(onBack: () -> Unit) {
    BackHandler(onBack = onBack)
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(start = 20.dp, end = 20.dp, top = 38.dp, bottom = 16.dp)
        ) {
            OutlinedButton(onClick = onBack) { Text("Retour à la séance") }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Fiches des exercices",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Conseils de positionnement et d'exécution. Consultation sans modifier la routine.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(exerciseGuides) { exercise ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(exercise.name, style = MaterialTheme.typography.titleLarge)
                            Text(exercise.target, style = MaterialTheme.typography.labelLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            GuideDetail("Position de départ", exercise.setup)
                            GuideDetail("Exécution", exercise.movement)
                            GuideDetail("À éviter", exercise.vigilance)
                            GuideDetail("Adaptation", exercise.adjustment)
                        }
                    }
                }
                item {
                    Text(
                        "En cas de douleur inhabituelle, interrompez l'exercice. La qualité du mouvement prime sur le nombre de répétitions.",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun GuideDetail(title: String, content: String) {
    Spacer(modifier = Modifier.height(6.dp))
    Text(title, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
    Text(content, style = MaterialTheme.typography.bodyMedium)
}
