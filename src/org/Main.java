package org;

import static javafx.application.Application.launch;

import Database.DatabaseDriver;
import Database.DatabaseController;
import Database.DatabaseInterface;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

  DatabaseDriver dbc = new DatabaseDriver("org.apache.derby.jdbc.EmbeddedDriver",
      "jdbc:derby:testDB;create=true");
  DatabaseInterface dbe = new DatabaseController(dbc);


  @Override
  public void start(Stage primaryStage) throws Exception {
    CentralController cc = new CentralController();
    cc.startUI(primaryStage);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
