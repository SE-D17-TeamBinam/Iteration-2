package org;

import UIControllers.CentralUIController;
import javafx.stage.Stage;

/**
 * Created by Tom on 4/2/2017.
 */
public class CentralController {

  private FileController fController;
  private Controller dController;
  //private CredentialManager credManager;
  static Session currSession = new Session();
  private CentralUIController uiController;
  public CentralController(){
  }
  public Language getCurrLang () {
    return this.currSession.currLang;
  }
  public void setCurrLang (Language L) {
    this.currSession.currLang = L;
  }
  public void startUI (Stage primaryStage) throws Exception {
    this.uiController = new CentralUIController();
    uiController.restartUI(primaryStage);
  }
}
