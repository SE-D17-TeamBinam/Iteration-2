package org;

import Database.DatabaseInterface;
import UIControllers.CentralUIController;
import javafx.stage.Stage;

/**
 * Created by Tom on 4/2/2017.
 */
public class CentralController {

  private FileController fController;
  static Session currSession = new Session();
  private CentralUIController uiController;

  public CentralController(){
  }

  public void startUI (Stage primaryStage, DatabaseInterface dbe) throws Exception {
    this.uiController = new CentralUIController();
    uiController.setSession(currSession, dbe);
    uiController.restartUI(primaryStage);
  }

  public static Session getCurrSession(){
    return currSession;
  }

  public static void resetSession(){
    currSession = new Session();
  }
}
