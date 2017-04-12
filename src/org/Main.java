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
    dbc = new DatabaseDriver("org.apache.derby.jdbc.EmbeddedDriver",
        "jdbc:derby:testDB;create=true");
    dbc.send_Command(
        "CREATE TABLE Physician(PID int, First_Name VARCHAR(25) NOT NULL, Last_Name VARCHAR(25) NOT NULL,Title VARCHAR(25) NOT NULL,CONSTRAINT pk_physician PRIMARY KEY (PID));CREATE TABLE Physician_Location(PLID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), PID_po int,PID_ph int, CONSTRAINT pk_physicianl PRIMARY KEY (PLID),CONSTRAINT u_physicianl UNIQUE(PID_po,PID_ph));CREATE TABLE Point(pid int NOT NULL, x int NOT NULL,y int NOT NULL,floor int NOT NULL, Cost int NOT NULL,name varchar(50) ,CONSTRAINT pk_ppoint1 PRIMARY KEY (pid));CREATE TABLE Neighbor(\tnid int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\tPid1 int NOT NULL,\tPid2 int NOT NULL,\tCONSTRAINT pk_neighbors  PRIMARY KEY (nid),CONSTRAINT u_neighbors UNIQUE(Pid1,Pid2));"
    );
    dbe = new DatabaseController(dbc);
    CentralController cc = new CentralController();
    cc.startUI(primaryStage, dbe);
  }

  public static void main(String[] args) {
    launch(args);
    DataController.tearDownTTS();
  }
}
