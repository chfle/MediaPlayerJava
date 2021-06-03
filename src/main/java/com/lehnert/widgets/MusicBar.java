package com.lehnert.widgets;

import com.lehnert.widgets.slider.VolumeSlider;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;

public class MusicBar extends HBox {
    private final VolumeSlider vol = new VolumeSlider();

    public MusicBar(MediaPlayer player) {

        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 10, 5, 10));
        setSpacing(10);

        vol.setPrefWidth(70);
        vol.setMinWidth(30);
        vol.setValue(100);

        Button playButton = new Button("Start");
        playButton.setPrefWidth(60);
        Button stopButton = new Button("Pause");
        stopButton.setPrefWidth(60);
        Button repeatButton = new Button("Repeat");
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

        vol.setOnVolumeChanged((v) -> player.setVolume(v / 100));
    }
}
