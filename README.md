# Renforcement Gainage

Application Android Kotlin locale, ultra simple, destinée à lancer une routine de renforcement et de gainage avec consignes vocales.

## Objectif

Poser le téléphone, appuyer sur **Démarrer**, puis suivre les annonces vocales :

- échauffement ;
- exercices en répétitions ;
- exercices chronométrés ;
- pauses automatiques ;
- annonces toutes les 10 secondes pour le gainage ;
- routine d'étirement final.

## Principes

- Pas de compte.
- Pas de cloud.
- Pas de backend.
- Pas d'analytics.
- Pas de gamification.
- Routine codée en dur dans un premier temps.
- Interface minimale.

## Structure

```text
app/
 └── src/main/java/fr/oliviervanre/renforcementgainage/
     ├── MainActivity.kt
     ├── SpeechManager.kt
     ├── WorkoutModels.kt
     ├── WorkoutRepository.kt
     ├── WorkoutViewModel.kt
     └── WorkoutScreen.kt
```

## Reprise dans Android Studio

1. Cloner le dépôt.
2. Ouvrir le dossier `renforcement_gainage` avec Android Studio.
3. Laisser Gradle synchroniser.
4. Lancer sur le Samsung S21 ou un émulateur.
5. Ajuster les durées dans `WorkoutRepository.kt`.

## MVP

- Jetpack Compose.
- TextToSpeech Android en français.
- Boutons Démarrer / Pause / Reprendre / Stop.
- Écran maintenu allumé pendant la séance.
- Routine codée en dur.
- Commandes vocales non incluses au départ.
