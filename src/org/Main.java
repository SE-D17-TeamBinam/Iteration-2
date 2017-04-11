package org;

import static javafx.application.Application.launch;

import Database.DatabaseDriver;
import Database.DatabaseController;
import Database.DatabaseInterface;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

  static DatabaseDriver dbc;
  static DatabaseInterface dbe;


  @Override
  public void start(Stage primaryStage) throws Exception {
    CentralController cc = new CentralController();
    cc.startUI(primaryStage);
  }

  public static void main(String[] args) {
    try {
      dbc = new DatabaseDriver("org.apache.derby.jdbc.EmbeddedDriver",
          "jdbc:derby:testDB;create=true");
    }
    catch (Exception e){
      e.printStackTrace();
    }
    dbe = new DatabaseController(dbc);
    launch(args);
  }
}
