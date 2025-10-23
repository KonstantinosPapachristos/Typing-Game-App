package com.example.typinggame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Collections;


public class TypingGame extends Application {

    private static Stage primaryStage;
    private static TypingGame instance;
    private static com.example.typinggame.ScoreScreenController ScoreScreenController;
    // UI του παιχνιδιού
    private static TextField inputField;
    private static Label wordLabel;
    private static Label timerLabel;
    private static Label scoreLabel;
    private static Label attemptsLeftLabel;
    private static int timerSeconds = 8;
    private static int failedAttempts = 0;
    private static int attemptsLeft = 3;
    private static int score = 0;
    private static List<String> wordList;
    private static Map<String, Boolean> wordMap;
    private static String currentWord;
    private static Timeline timeline;

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        TypingGame.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start_screen.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Typing Game");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }


    public static void startGame() {
        primaryStage.hide();
        setupGame();
    }


    public static void resetGame() {
        timerSeconds = 8;
        attemptsLeft = 3;
        score = 0;
        wordMap.clear();
    }


    private static void setupGame() {

        loadWordsFromFile("words.txt");
        Collections.shuffle(wordList);


        inputField = new TextField();
        wordLabel = new Label();
        timerLabel = new Label("Time: " + timerSeconds);
        scoreLabel = new Label("Score: " + score);
        attemptsLeftLabel = new Label("Attempts left: " + attemptsLeft);


        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timerSeconds--;
            timerLabel.setText("Time: " + timerSeconds);
            if (timerSeconds <= 0) {
                endGame();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        inputField.setOnAction(event -> {
            if (inputField.getText().trim().equalsIgnoreCase(currentWord)) {

                inputField.clear();
                currentWord = getNextWord();
                wordLabel.setText(currentWord);
                timerSeconds = 8;
                attemptsLeft = 3;
                attemptsLeftLabel.setText("Attempts left: " + attemptsLeft);
                score++;
                scoreLabel.setText("Score: " + score);
            } else {

                failedAttempts++;
                attemptsLeft--;
                attemptsLeftLabel.setText("Attempts left: " + attemptsLeft);
                if (attemptsLeft <= 0) {
                    endGame();
                } else {

                    inputField.clear();
                    timerSeconds = 8;
                }
            }
        });


        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(wordLabel, inputField, timerLabel, scoreLabel, attemptsLeftLabel);
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();


        currentWord = getNextWord();
        wordLabel.setText(currentWord);
    }


    private static void loadWordsFromFile(String filename) {
        wordList = new ArrayList<>();
        wordMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordList.add(line);
                wordMap.put(line, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String getNextWord() {
        for (String word : wordList) {
            if (!wordMap.get(word)) {
                wordMap.put(word, true);
                return word;
            }
        }

        resetWordMap();
        return getRandomWord();
    }


    private static void resetWordMap() {
        for (String word : wordList) {
            wordMap.put(word, false);
        }
    }


    private static String getRandomWord() {
        Random rand = new Random();
        return wordList.get(rand.nextInt(wordList.size()));
    }


    private static void endGame() {

        inputField.setDisable(true);
        wordLabel.setText("Game Over! Your score is: " + score);
        timeline.stop();


        try {
            FXMLLoader loader = new FXMLLoader(TypingGame.class.getResource("score_screen.fxml"));
            Parent root = loader.load();
            ScoreScreenController = loader.getController();
            ScoreScreenController.setScoreText(Integer.toString(score));


            Stage stage = new Stage();
            stage.setTitle("Score");


            Scene scene = new Scene(root, 400, 300);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
