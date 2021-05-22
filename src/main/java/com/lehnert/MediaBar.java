package com.lehnert;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

import java.io.File;

public class MediaBar extends HBox {
    private Slider time = new Slider();
    private Slider vol = new Slider();
    private Button PlayButton = new Button("||");
    private Label volume = new Label("Volume: ");
    private MediaPlayer player;

    public MediaBar(MediaPlayer play) {
        this.player = play;

        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 10, 5, 10));
        vol.setPrefWidth(70);
        vol.setMinWidth(30);
        vol.setValue(100);
        HBox.setHgrow(time, Priority.ALWAYS);
        PlayButton.setPrefWidth(30);

        getChildren().add(PlayButton);
        getChildren().add(time);
        getChildren().add(volume);
        getChildren().add(vol);

        PlayButton.setText("||");


        PlayButton.setOnAction(e -> {
            Status status = player.getStatus(); // To get the status of Player
            if (status == Status.PLAYING) {

                // If the status is Video playing
                if (player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())) {

                    player.seek(player.getStartTime());
                    player.play();
                }
                else {
                    player.pause();

                    PlayButton.setText(">");
                }
            }

            if (status == Status.HALTED || status == Status.STOPPED || status == Status.PAUSED) {
                player.play(); // Start the video
                PlayButton.setText("||");
            }
        });

        // Providing functionality to time slider
        player.currentTimeProperty().addListener(ov -> updatesValues());

        // Inorder to jump to the certain part of video
        time.valueProperty().addListener(ov -> {
            if (time.isPressed()) {
                player.seek(player.getMedia().getDuration().multiply(time.getValue() / 100));
            }
        });


        vol.valueProperty().addListener(ov -> {
            if (vol.isPressed()) {
                player.setVolume(vol.getValue() / 100);
            }
        });
    }

    protected void updatesValues() {
        Platform.runLater(() -> {
            // This will move the slider while the video is running
            time.setValue(player.getCurrentTime().toMillis() /
                    player.getTotalDuration()
                            .toMillis()
                            * 100);
        });
    }
}