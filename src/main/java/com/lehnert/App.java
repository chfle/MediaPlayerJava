package com.lehnert;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {
        // get file from a user
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choose a Media File");

        // set Extension Filter
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All FILES", "*.*"),
                new FileChooser.ExtensionFilter("MP4", "*.mp4")
        );

        File file = fileChooser.showOpenDialog(primaryStage);

        if (file == null) {
            System.err.println("No file selected");
            System.exit(1);
        }


        try {
            Media media = new Media(file.toURI().toURL().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            VBox box = new VBox();

            MediaView mediaView = new MediaView(mediaPlayer);


            mediaPlayer.setOnReady (()-> {
                MediaBar mediaBar = new MediaBar(mediaPlayer);

                box.getChildren().add(mediaView);
                box.getChildren().add(mediaBar);

                Scene scene = new Scene(new Pane(box), media.getWidth(), media.getHeight() + 40);

                primaryStage.setTitle("Media Player");
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.show();
            });


            mediaPlayer.play();
        } catch (Exception ignore) {
            Label label = new Label("Url does not Work...");
            Button button = new Button("OK");
            label.setTextFill(Color.BLACK);
            label.setFont(Font.font("verdana", 15));
            VBox box = new VBox(20, label, button);
            box.setAlignment(Pos.CENTER);

            button.setOnAction(e -> primaryStage.close());


            primaryStage.setTitle("Error");
            primaryStage.setScene(new Scene(box, 200, 100));
            primaryStage.show();
        }

    }
}
