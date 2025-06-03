#!/bin/bash

# Modifier ce chemin vers ton dossier JavaFX
JAVAFX="/usr/share/openjfx/lib/"

javac --module-path $JAVAFX --add-modules javafx.controls,javafx.fxml -d pendu_pour_etu/bin pendu_pour_etu/src/*.java
java --module-path $JAVAFX --add-modules javafx.controls,javafx.fxml -cp pendu_pour_etu/bin Pendu