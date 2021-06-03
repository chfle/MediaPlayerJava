package com.lehnert.widgets.dialog;

import javafx.scene.control.Alert;

public class ErrorDialog extends Alert {
    public ErrorDialog(String msg) {
        super(AlertType.ERROR);
        this.setTitle("Error");
        this.setHeaderText(msg);

        this.showAndWait();
    }
}
