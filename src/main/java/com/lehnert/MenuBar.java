package com.lehnert;

import javafx.scene.control.*;

import java.time.Year;

public class MenuBar extends javafx.scene.control.MenuBar {
    // help
    private final MenuItem about = new MenuItem("About");
   // file
    private final MenuItem open = new MenuItem("Open");

   public MenuBar() {
       // Help Menu
       Menu help = new Menu("Help");

       help.getItems().add(about);

       // File Menu
       Menu file = new Menu("File");

       file.getItems().add(open);

       // add all menus
       this.getMenus().add(file);
       this.getMenus().add(help);
   }

   public void setOnOpen(Runnable r) {
       open.setOnAction((e) -> r.run());
   }

   public void setOnAbout(String author) {
       about.setOnAction(e -> {
           Dialog<String> dialog = new Dialog<>();

           dialog.setTitle("About");
           dialog.setContentText(String.format("Created by %s (C) %s", author, Year.now()));

           ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

           dialog.getDialogPane().getButtonTypes().add(type);

           dialog.showAndWait();
       });
   }
}
