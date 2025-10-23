package com.example.typinggame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ScoreScreenController {

    public Button restartButton;
    @FXML
    private Text scoreText;
    @FXML
    private Text messageText;
    public void setScoreText(String score) {
        scoreText.setText("Your score is: " + score);
        int scoreValue = Integer.parseInt(score);

        if (scoreValue > 5) {
            messageText.setText("Congratulations!");
        } else {
            messageText.setText("");
        }
    }

    public void restartGame(ActionEvent actionEvent) {

        TypingGame.resetGame();

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("start_screen.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root, 400, 300);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
