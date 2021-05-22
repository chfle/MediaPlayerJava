package com.lehnert;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;

public class MusicBar extends HBox {
    private Slider vol = new Slider();
    private Button playButton = new Button("Start");
    private Button stopButton = new Button("Pause");
    private Button repeatButton = new Button("Repeat");
    private MediaPlayer player;


    public MusicBar(MediaPlayer player) {
        this.player = player;

        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 10, 5, 10));
        setSpacing(10);

        vol.setPrefWidth(70);
        vol.setMinWidth(30);
        vol.setValue(100);

        playButton.setPrefWidth(60);
        stopButton.setPrefWidth(60);
        repeatButton.setPrefWidth(60);

        getChildren().add(playButton);
        getChildren().add(stopButton);
        getChildren().add(repeatButton);
        getChildren().add(vol);

        // playButton
        playButton.setOnAction(e -> player.play());
        stopButton.setOnAction(e -> player.pause());
        repeatButton.setOnAction(e -> {
            player.stop();
            player.play();
        });

        vol.valueProperty().addListener(ov -> {
            if (vol.isPressed()) {
                player.setVolume(vol.getValue() / 100);
            }
        });
    }
}
