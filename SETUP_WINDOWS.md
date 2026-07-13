# Reprise propre sous Windows / Android Studio

Ce dépôt contient une configuration Android volontairement stable.

Versions retenues :

- Android Gradle Plugin : 8.7.3
- Kotlin Android : 1.9.25
- Compose compiler extension : 1.5.15
- compileSdk : 35

## Fichiers importants

A la racine du projet :

- `settings.gradle.kts` doit déclarer `google()`, `mavenCentral()` et `gradlePluginPortal()` dans `pluginManagement`.
- `build.gradle.kts` doit utiliser `com.android.application` en version `8.7.3`.
- `gradle.properties` doit contenir `android.useAndroidX=true`.

## Commande de remise au propre

Depuis le dossier local du projet :

```cmd
git fetch origin
git reset --hard origin/main
rmdir /s /q .gradle
rmdir /s /q build
rmdir /s /q app\build
```

Puis ouvrir le dossier dans Android Studio et lancer :

```text
File > Sync Project with Gradle Files
```

Refuser temporairement les propositions de mise a jour automatique du plugin Gradle/AGP tant que le premier build n'est pas passe.

## Build local

Si Gradle est installe sur le poste :

```cmd
gradle assembleDebug --stacktrace
```

Si le wrapper Gradle local est incomplet, ne pas utiliser `gradlew.bat` avant de l'avoir regenere.

## APK produit

L'APK debug se trouve apres compilation dans :

```text
app\build\outputs\apk\debug\app-debug.apk
```
