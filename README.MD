# Notes App
Simple Notes app built using 100% shared UI code with Jetpack Compose, SQL Delight to store
data and Decompose + Essenty to manage navigation.

## Compose Template
Used the compose template from [Compose Multiplatform Wizard](https://terrakok.github.io/Compose-Multiplatform-Wizard/)
Thanks to them, project setup was considerably easier.
Also large help from xxFast's [Krouter library](https://github.com/xxfast/KRouter)

## Before running!
 - check your system with [KDoctor](https://github.com/Kotlin/kdoctor)
 - install JDK 8 on your machine
 - add `local.properties` file to the project root and set a path to Android SDK there
 - run `./gradlew podInstall` in the project root

### To build the SQL Delight Kotlin bindings
Don't rebuild the project
<br><br>
<image src="https://media.giphy.com/media/g79am6uuZJKSc/giphy.gif" />

Instead just run only the sqldelight tasks from the gradle tab 
 -  generateCommonMainNotesDBInterface
 - generateSqlDelightInterface
(Can also run the verify tasks for good measure.)

### Android
To run the application on android device/emulator:  
 - open project in Android Studio and run imported android run configuration

To build the application bundle:
 - run `./gradlew :composeApp:assembleDebug`
 - find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`

### Desktop
Run the desktop application: `./gradlew :composeApp:run`

### iOS
To run the application on iPhone device/simulator:
 - Add linker flags for SQL delight as `-lsqlite3` in Xcode under other linker flags.
 - Open `iosApp/iosApp.xcworkspace` in Xcode and run standard configuration.

## 🏗️️ Built with ❤️ using Kotlin
| What            | How                                                                       |
|-----------------|---------------------------------------------------------------------------|
| 🎭 All UI       | [Jetbrains Compose](https://github.com/JetBrains/compose-multiplatform)   |
| 💉 DI           | [Koin](https://insert-koin.io/)                                           |
| 🧭 Navigation   | [Decompose](https://github.com/arkivanov/Decompose)                       |
| ð Storage       | [SqlDelight](https://github.com/russhwolf/multiplatform-settings)         |
<p align="right">[<a href="#top">Back to top</a>]</p>


## Screenshots
