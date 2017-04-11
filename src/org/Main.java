package org;

import static javafx.application.Application.launch;

import Database.DatabaseDriver;
import Database.DatabaseController;
import Database.DatabaseInterface;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.xml.crypto.Data;

public class Main extends Application {

  static DatabaseDriver dbc;
  static DatabaseInterface dbe;


  @Override
  public void start(Stage primaryStage) throws Exception {
    CentralController cc = new CentralController();
    cc.startUI(primaryStage);
  }

  public static void main(String[] args) {
    dbc = new DatabaseDriver("org.apache.derby.jdbc.EmbeddedDriver",
        "jdbc:derby:testDB;create=true");
    dbe = new DatabaseController(dbc);
    launch(args);
    DataController.tearDownTTS();
  }
}
