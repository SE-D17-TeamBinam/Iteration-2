package UIControllers;

import CredentialManager.CredentialManager;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.Dictionary;
import org.Point;
import org.Session;


public class CentralUIController {
  // TODO Add method to create/update header images on every page, could mean making another class
  /* Type of map to show when mapView is displayed
    1 for interactive map
    2 for directory map
    3 for admin map
   */
  protected static int mapViewFlag = 0;
  public static AnchorPane anchorPane;
  public static ArrayList<Point> globalPoints = new ArrayList<Point>(); // TODO Fix
  protected static Session currSession;
  protected static CredentialManager credentialManager;
  protected static Dictionary dictionary;


  public void setSession (Session s) {
    this.currSession = s;
    this.credentialManager = s.credentialManager;
    this.dictionary = s.dictionary;
  }
  /**
   * Set the stage to the initial scene (main menu)
   * @parameter primaryStage: The main stage of the application
   */
  public void restartUI(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
    primaryStage.setScene(new Scene(root, 1300, 750));
    primaryStage.setTitle("Faulkner Hospital Kiosk");
    primaryStage.show();
  }
  /**
   * @parameter primaryStage: The main stage of the application
   * @parameter fxmlpath: the file path of the fxml file to be loaded
   * Set the stage to a scene by an fxml file
   */
  public void loadScene (Stage primaryStage, String fxmlpath, int width, int height) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource(fxmlpath));
    primaryStage.setScene(new Scene(root, width, height));
    primaryStage.show();
  }

}
