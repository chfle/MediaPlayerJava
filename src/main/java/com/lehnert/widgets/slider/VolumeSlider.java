package com.lehnert.widgets.slider;

import javafx.scene.control.Slider;

import java.util.function.Consumer;

public class VolumeSlider extends Slider{
    private final Slider volumeSlider = new Slider();

    public VolumeSlider() {}

    public void setOnVolumeChanged(Consumer<Double> volConsume) {
        volumeSlider.valueProperty().addListener((ov) -> {
            if(volumeSlider.isPressed()) {
                volConsume.accept(volumeSlider.getValue());
            }
        });
    }
}
