package com.lehnert;

import com.lehnert.widgets.dialog.ErrorDialog;
import com.lehnert.widgets.mediabar.AudioBar;
import com.lehnert.widgets.mediabar.MediaBar;

import com.lehnert.widgets.menubar.MenuBar;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.Optional;

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

    private Optional<MediaPlayer> mediaPlayerWrapper = Optional.empty();

    public void start(Stage primaryStage) {
        com.lehnert.widgets.menubar.MenuBar menuBar = new MenuBar();
        primaryStage.setTitle("Media Player");

        Runnable noFile = () -> {
            Label label = new Label("No file selected");
            VBox box = new VBox(menuBar, label);

            Scene scene = new Scene(box, 200, 100);
            primaryStage.setScene(scene);
            primaryStage.show();
        };

        Runnable player = () -> {
            // ask user for a file
            Optional<File> file = Optional.ofNullable(getFile(primaryStage));

            // check if a player is running
            if (file.isPresent()) {
                mediaPlayerWrapper.ifPresent((mediaPlayer) -> {
                    mediaPlayer.stop();
                    mediaPlayer.dispose();
                });


                mediaPlayerWrapper = Optional.empty();
            }

            // No file selected
            if (file.isEmpty() && mediaPlayerWrapper.isEmpty()) {
                noFile.run();
                return;
            } else if (mediaPlayerWrapper.isPresent()) {
                return;
            }


            try {

                Media media = new Media(file.get().toURI().toURL().toString());
                mediaPlayerWrapper = Optional.of(new MediaPlayer(media));
                VBox box = new VBox();

                mediaPlayerWrapper.ifPresent((mediaPlayer) -> {
                    MediaView mediaView = new MediaView(mediaPlayer);
                    MediaBar mediaBar = new MediaBar(mediaPlayer);
                    AudioBar musicBar = new AudioBar(mediaPlayer);

                    // create a player with window
                    mediaPlayer.setOnReady(() -> {

                        // check if the media is music or video
                        String extension = Objects.requireNonNull(getFileExtension(file.get().toString())).toLowerCase();
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

                        // setup window
                        primaryStage.setTitle("Media Player");
                        primaryStage.setScene(scene);
                        primaryStage.setResizable(false);
                        primaryStage.show();

                        // play the media
                        mediaPlayer.play();
                    });
                });

                if (mediaPlayerWrapper.isEmpty()) {
                    throw new Exception("Media not working");
                }
                // create a window to notify the user for a bad url...
            } catch (Exception ignore) {
                noFile.run();

                new ErrorDialog("Media not found");
            }
        };

        // menubar handles...
        menuBar.setOnAbout("Christian Lehnert");
        menuBar.setOnOpen(player);
        menuBar.setOnClose(primaryStage::close);
        menuBar.setOnReportBug(() -> {
            try {
                getHostServices().showDocument("mailto:christian.lehnert.dev@gmail.com");
            } catch (Exception ignored) {
                new ErrorDialog("No Email Client found!");
            }
        });

        // start a player
        player.run();

    }
}
