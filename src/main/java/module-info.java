module MediaPlayer {

    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;

    opens com.lehnert;
    opens com.lehnert.widgets.mediabar;
    opens com.lehnert.widgets.slider;
    opens com.lehnert.widgets.menubar;
    opens com.lehnert.widgets.dialog;
}