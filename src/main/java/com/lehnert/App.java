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

    /** Opens a FileChooser to choose something to play
     *
     */
    private File getFile(Stage primaryStage) {
        // get file from a user
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choose a Media File");

        // set Extension Filter
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All FILES", "*.*"),
                new FileChooser.ExtensionFilter("MP4", "*.mp4")
        );

        return fileChooser.showOpenDialog(primaryStage);
    }

    private boolean playerRunning = false;
    private MediaPlayer mediaPlayer = null;

    public void start(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();
        primaryStage.setTitle("Media Player");

        Runnable player = () -> {
            // ask user for a file
            File file = getFile(primaryStage);


            // check if a player is running
            if (playerRunning && file != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();

                playerRunning = false;
            }

            // No file selected
            if (file == null && !playerRunning) {
                Label label = new Label("No file selected");
                VBox box = new VBox(menuBar, label);

                Scene scene = new Scene(box, 200, 100);
                primaryStage.setScene(scene);
                primaryStage.show();
                return;
            } else if (playerRunning) {
                return;
            }


            try {

                Media media = new Media(file.toURI().toURL().toString());
                mediaPlayer = new MediaPlayer(media);
                VBox box = new VBox();

                MediaView mediaView = new MediaView(mediaPlayer);
                MediaBar mediaBar = new MediaBar(mediaPlayer);
                MusicBar musicBar = new MusicBar(mediaPlayer);

                // create a player with window
                mediaPlayer.setOnReady(() -> {

                    // check if the media is music or video
                    String extension = Objects.requireNonNull(getFileExtension(file.toString())).toLowerCase();
                    box.getChildren().add(menuBar);
                    Scene scene;

                    // supported video formats
                    if (extension.equals("mp3") || extension.equals("wav")) {
                        // got music there
                        Label musicTitle = new Label("Audio - " + extension);
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

                    // media player is running
                    playerRunning = true;

                    // setup window
                    primaryStage.setTitle("Media Player");
                    primaryStage.setScene(scene);
                    primaryStage.setResizable(false);
                    primaryStage.show();

                    // play the media
                    mediaPlayer.play();
                });
                // create a window to notify the user for a bad url...
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
        };

        // menubar handles...
        menuBar.setOnAbout("Christian Lehnert");
        menuBar.setOnOpen(player);
        menuBar.setOnClose(primaryStage::close);

        // start a player
        player.run();

    }
}
