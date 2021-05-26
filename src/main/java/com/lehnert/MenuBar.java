package com.lehnert;

import javafx.scene.control.*;

import java.time.Year;

public class MenuBar extends javafx.scene.control.MenuBar {
    // help
    private final MenuItem about = new MenuItem("About");
    private final MenuItem reportBug = new MenuItem("Report bug");
   // file
    private final MenuItem open = new MenuItem("Open File...");
    private final MenuItem close = new MenuItem("Close Window");

   public MenuBar() {
       // Help Menu
       Menu help = new Menu("Help");

       help.getItems().add(about);
       help.getItems().add(reportBug);

       // File Menu
       Menu file = new Menu("File");

       file.getItems().add(open);
       file.getItems().add(new SeparatorMenuItem());
       file.getItems().add(close);

       // add all menus
       this.getMenus().add(file);
       this.getMenus().add(help);
   }

   public void setOnOpen(Runnable r) {
       open.setOnAction((e) -> r.run());
   }

   public void setOnClose(Runnable r) {
       close.setOnAction((e) -> r.run());
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

   public void setOnReportBug(Runnable r) {
       reportBug.setOnAction((e) -> r.run());
   }
}
