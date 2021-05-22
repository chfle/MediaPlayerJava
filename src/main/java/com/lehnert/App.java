package com.lehnert;

import javafx.application.Application;
import javafx.geometry.Insets;
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
import java.util.Locale;
import java.util.Objects;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private String getFileExtension(String path) {
        int i = path.lastIndexOf('.');
        if (i > 0) {
            return path.substring(i+1);
        } else {
            return null;
        }
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

        try {
            Media media = new Media(file.toURI().toURL().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            VBox box = new VBox();

            MediaView mediaView = new MediaView(mediaPlayer);
            MediaBar mediaBar = new MediaBar(mediaPlayer);
            MusicBar musicBar = new MusicBar(mediaPlayer);
            MenuBar menuBar = new MenuBar();

            mediaPlayer.setOnReady (()-> {
                // check if the media is music or video
                String extension = Objects.requireNonNull(getFileExtension(file.toString())).toLowerCase();
                box.getChildren().add(menuBar);
                Scene scene = null;

                if (extension.equals("mp3") || extension.equals("wav")) {
                    // got music there
                    Label musicTitle = new Label("Music - " + extension);
                    musicTitle.setPadding(new Insets(5, 10, 5, 10));

                    musicTitle.setMaxWidth(Double.MAX_VALUE);
                    musicTitle.setAlignment(Pos.CENTER);

                    box.getChildren().add(musicTitle);
                    box.getChildren().add(musicBar);

                    // Music Scene
                    scene = new Scene(new Pane(box), 300, 100);
                } else {
                    // got a video there
                    box.getChildren().add(mediaView);
                    box.getChildren().add(mediaBar);

                    scene = new Scene(new Pane(box), media.getWidth(),
                            media.getHeight() + 70);

                }

                // setup window
                primaryStage.setTitle("Media Player");
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.show();

                // play the media
                mediaPlayer.play();
            });
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
