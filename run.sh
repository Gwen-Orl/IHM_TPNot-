#!/bin/bash

# Modifiez ce chemin vers votre dossier JavaFX si besoin
JAVAFX="/usr/share/openjfx/lib/"

javac --module-path $JAVAFX --add-modules javafx.controls,javafx.fxml -d pendu_pour_etu/bin pendu_pour_etu/src/*.java
java --module-path $JAVAFX --add-modules javafx.controls,javafx.fxml -cp pendu_pour_etu/bin Pendu