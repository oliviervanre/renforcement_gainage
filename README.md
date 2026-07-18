# Renforcement Gainage

Version actuelle : **0.2.0**

Application Android Kotlin locale, volontairement simple, destinée à lancer une routine courte de renforcement musculaire et de gainage avec guidage vocal.

L'objectif n'est pas de produire une application sportive générique, mais une application d'adhérence : poser le téléphone, appuyer sur **Démarrer**, suivre les consignes, terminer la séance sans friction.

## Objectif fonctionnel

L'application guide une séance de renforcement adaptée à un usage personnel :

- circuit principal uniquement, sans échauffement long imposé ;
- deux tours par défaut ;
- exercices à répétitions guidés par objectif + fenêtre de temps ;
- exercices de gainage guidés au chrono ;
- exercices unilatéraux avec haltère en fin de circuit ;
- récupérations automatiques ;
- consignes vocales en français ;
- écran maintenu allumé pendant la séance ;
- boutons simples : **Démarrer**, **Pause**, **Reprendre**, **Stop** ;
- bouton **Passer** uniquement sur les exercices nécessitant du matériel ;
- numéro de version affiché discrètement en bas de l'écran.

## Principes de conception

- Pas de compte.
- Pas de cloud.
- Pas de backend.
- Pas d'analytics.
- Pas de gamification.
- Pas de statistiques.
- Pas de synchronisation externe.
- Routine codée en dur dans un premier temps.
- Interface minimale, lisible au sol pendant l'exercice.

L'application privilégie la régularité et l'exécution propre plutôt que l'optimisation sportive théorique.

## Logique sportive retenue

Deux catégories d'exercices sont distinguées :

1. **Exercices chronométrés** : gainage frontal, gainage latéral, récupérations.
   L'application pilote strictement le temps et annonce les repères utiles.

2. **Exercices à répétitions dans une fenêtre de temps** : pompes, squats, fentes, rowing, développé épaules.
   L'application annonce un objectif qualitatif et laisse une fenêtre de temps confortable.
   Elle ne compte pas les répétitions vocalement, afin de préserver la qualité du geste.

Exemple de logique :

```text
Pompes
Objectif : 10 répétitions propres
Fenêtre : 30 secondes
Consigne : corps gainé, amplitude propre, variante inclinée si nécessaire
```

Les exercices avec haltère sont désormais traités séparément par côté, pour éviter un changement droite/gauche trop précipité.

## Routine actuelle

La routine est définie dans `WorkoutRepository.kt`.

Déroulé global :

```text
Préparation courte
Tour 1 / 2
Pause entre les tours
Tour 2 / 2
Fin de séance
```

Circuit d'un tour :

```text
Pompes
Récupération
Squats
Récupération
Fentes
Récupération
Gainage frontal
Récupération
Gainage gauche
Récupération
Gainage droit
Récupération
Rowing droit
Changement de côté
Rowing gauche
Récupération
Épaule droite
Changement de côté
Épaule gauche
```

Les exercices `Rowing droit`, `Rowing gauche`, `Épaule droite` et `Épaule gauche` sont marqués comme optionnels avec `canSkip = true`, car ils nécessitent une haltère ou une charge adaptée.

## Réglage sportif de la version 0.2.0

Ajustements issus du premier test en conditions réelles :

```text
Rowing droit : 25 secondes
Changement de côté : 15 secondes
Rowing gauche : 25 secondes
Récupération : 25 secondes

Épaule droite : 25 secondes
Changement de côté : 15 secondes
Épaule gauche : 25 secondes
```

Consignes intégrées :

```text
Rowing : appui main + genou opposés, dos plat, coude vers la hanche.
Épaule : haltère à l'épaule, pousser au-dessus de la tête sans cambrer.
```

Le choix retenu est de privilégier le geste propre et contrôlé, surtout en fin de circuit, plutôt que d'augmenter trop vite l'intensité.

## Guidage vocal

Le guidage vocal utilise le moteur Android `TextToSpeech` en français.

Point important : le démarrage du chrono est séquencé sur la fin réelle de la synthèse vocale. L'application n'utilise plus une estimation approximative de durée de parole.

Séquence d'un exercice :

```text
1. annonce complète de l'exercice
2. attente de fin réelle du TextToSpeech
3. courte latence d'environ 900 ms
4. annonce "Attention. Top."
5. attente de fin réelle du TextToSpeech
6. démarrage du chrono
```

Cela évite les coupures du type :

```text
"Vous avez quar... Attention. Top."
```

La courte latence avant `Attention. Top.` laisse le temps de se placer physiquement avant le départ du chrono.

## Bouton Passer et remarques vocales

Certains exercices peuvent être sautés lorsqu'ils nécessitent du matériel.

Actuellement :

```text
Rowing droit
Rowing gauche
Épaule droite
Épaule gauche
```

Quand l'utilisateur appuie sur **Passer**, l'application :

1. arrête l'exercice courant ;
2. prononce une remarque aléatoire ;
3. attend la fin réelle de la synthèse vocale ;
4. reprend à l'étape suivante.

Les remarques sont stockées dans `PersiflageRepository.kt` sous forme encodée en Base64. Ce n'est pas une protection cryptographique ; c'est seulement destiné à éviter de lire directement les phrases dans le code.

## Version affichée dans l'application

Le numéro de version est défini dans `app/build.gradle.kts` :

```kotlin
versionCode = 2
versionName = "0.2.0"
```

L'écran principal affiche discrètement :

```text
v0.2.0
```

Ce repère permet de vérifier rapidement quelle APK est installée sur le téléphone lors des tests.

## Structure technique

```text
app/
 └── src/main/java/fr/oliviervanre/renforcementgainage/
     ├── MainActivity.kt
     ├── PersiflageRepository.kt
     ├── SpeechManager.kt
     ├── WorkoutModels.kt
     ├── WorkoutRepository.kt
     ├── WorkoutScreen.kt
     └── WorkoutViewModel.kt
```

### `MainActivity.kt`

Point d'entrée Android.

Responsabilités :

- création de l'écran Compose ;
- attachement du `SpeechManager` au `WorkoutViewModel` ;
- maintien de l'écran allumé pendant la séance avec `FLAG_KEEP_SCREEN_ON`.

### `WorkoutModels.kt`

Contient les modèles métier de la séance.

Principaux éléments :

```kotlin
enum class StepType {
    TIMED,
    REPS,
    REST
}
```

`WorkoutStep` décrit une étape de séance :

- titre ;
- instruction vocale ;
- type d'exercice ;
- durée ;
- objectif affiché ;
- consigne affichée ;
- libellé du tour ;
- annonces intermédiaires ;
- possibilité ou non de passer l'exercice.

`WorkoutUiState` représente l'état affiché par l'écran :

- étape courante ;
- étape suivante ;
- temps restant ;
- état démarré / pause / terminé.

### `WorkoutRepository.kt`

Contient la routine codée en dur.

C'est le fichier à modifier pour ajuster :

- les exercices ;
- les durées ;
- les répétitions affichées ;
- les consignes vocales ;
- les récupérations ;
- les exercices optionnels.

Exemple :

```kotlin
durationSeconds = 30,
objective = "10 répétitions propres",
guidance = "Corps gainé. Amplitude propre. Variante inclinée si nécessaire.",
announceAtSeconds = setOf(10)
```

### `WorkoutViewModel.kt`

Pilote le déroulement de la séance.

Responsabilités :

- démarrer la routine ;
- gérer pause / reprise / stop ;
- gérer le passage d'une étape à l'autre ;
- décrémenter le chrono ;
- déclencher les annonces vocales ;
- attendre la fin réelle du TextToSpeech avant de démarrer le chrono ;
- ajouter une courte latence avant le signal de départ ;
- gérer le bouton **Passer**.

### `WorkoutScreen.kt`

Interface Jetpack Compose.

Affiche :

- titre de l'application ;
- tour courant ;
- exercice courant ;
- temps restant ;
- barre de progression ;
- objectif ;
- consigne courte ;
- exercice suivant ;
- boutons d'action ;
- version discrète de l'application.

Le bouton **Passer** n'apparaît que si l'étape courante a `canSkip = true`.

### `SpeechManager.kt`

Encapsule Android `TextToSpeech`.

Responsabilités :

- initialiser le moteur TTS ;
- définir la langue française ;
- parler immédiatement avec `speak()` ;
- parler en attendant la fin réelle avec `speakAndWait()` ;
- arrêter ou libérer le moteur TTS.

La méthode `speakAndWait()` repose sur `UtteranceProgressListener`, ce qui permet d'enchaîner les annonces sans les couper.

### `PersiflageRepository.kt`

Contient les remarques aléatoires prononcées lorsqu'un exercice optionnel est passé.

Les phrases sont encodées en Base64 et décodées au moment de l'exécution.

## Technologies

- Kotlin
- Android natif
- Jetpack Compose
- Material 3
- Android ViewModel
- Kotlin coroutines
- StateFlow
- Android TextToSpeech
- Gradle Kotlin DSL

## Configuration Android

Configuration principale :

```text
compileSdk = 35
minSdk = 26
targetSdk = 35
versionCode = 2
versionName = 0.2.0
```

Package Android :

```text
fr.oliviervanre.renforcementgainage
```

Nom de l'application :

```text
Renforcement Gainage
```

## Build local

Dans Android Studio :

```text
Build
→ Build Bundle(s) / APK(s)
→ Build APK(s)
```

APK généré :

```text
app/build/outputs/apk/debug/app-debug.apk
```

Sous Windows, chemin typique :

```text
C:\Users\Olivier\StudioProjects\renforcement_gainage\app\build\outputs\apk\debug\app-debug.apk
```

## Installation sur téléphone

Méthode simple sans câble :

1. générer `app-debug.apk` ;
2. envoyer l'APK sur Google Drive ;
3. ouvrir Google Drive sur le téléphone ;
4. télécharger ou ouvrir l'APK ;
5. autoriser temporairement l'installation depuis cette source si Android le demande ;
6. installer.

Tant que le package reste identique et que le `versionCode` augmente, Android traite les APK suivants comme des mises à jour.

## GitHub Actions

Le dépôt contient un workflow GitHub Actions :

```text
.github/workflows/android-build.yml
```

Il compile l'APK debug avec Gradle et publie l'artefact :

```text
renforcement-gainage-debug-apk
```

## Limites assumées du MVP

Non inclus volontairement à ce stade :

- compte utilisateur ;
- cloud ;
- backend ;
- statistiques ;
- historique de séances ;
- écran complet de réglages ;
- choix avancé de voix ;
- commandes vocales ;
- Play Store ;
- signature release.

## Pistes d'évolution

Évolutions envisagées, à garder simples :

- petit écran de réglages pour la durée des récupérations ;
- durée de pause entre les tours ;
- choix du nombre de tours : 1 / 2 / 3 ;
- mode Express / Standard / Complet ;
- réglage discret du style vocal des remarques ;
- icône d'application personnalisée ;
- historique minimal des versions testées.

Le principe directeur reste le même : ne pas transformer une application d'adhérence en cockpit.
