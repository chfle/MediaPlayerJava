package com.lehnert;

import javafx.event.Event;
import javafx.scene.control.*;

public class MenuBar extends javafx.scene.control.MenuBar {
   public MenuBar() {
       // Help Menu
       Menu help = new Menu("Help");

       // Help items
       MenuItem about = new MenuItem("About");
       help.getItems().add(about);

       about.setOnAction(this::setAboutScene);


       // add all menus
       this.getMenus().add(help);
   }

   private void setAboutScene(Event e) {
       Dialog<String> dialog = new Dialog<>();

       dialog.setTitle("About");
       dialog.setContentText("Created by Christian Lehnert (C) 2021");

       ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

       dialog.getDialogPane().getButtonTypes().add(type);

       dialog.showAndWait();
   }
}
