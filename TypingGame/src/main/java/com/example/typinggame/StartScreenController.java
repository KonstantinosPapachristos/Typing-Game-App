package com.example.typinggame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartScreenController {

    public Button startButton;


    @FXML
    private void startGame() {

        TypingGame.startGame();
    }
}
