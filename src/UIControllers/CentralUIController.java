package UIControllers;

import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.Point;


public class CentralUIController {
  /* Type of map to show when mapView is displayed
    1 for interactive map
    2 for directory map
    3 for admin map
   */
  protected static int mapViewFlag = 0;
  protected static String currLang = "ENGLISH";
  public static ArrayList<Point> globalPoints = new ArrayList<Point>();
  /**
   * Set the stage to the initial scene (main menu)
   * @parameter primaryStage: The main stage of the application
   */
  public void restartUI(Stage primaryStage) throws Exception {
    loadScene(primaryStage, "/MainMenu.fxml");
    primaryStage.setTitle("Faulkner Hospital Kiosk");
  }
  /**
   * @parameter primaryStage: The main stage of the application
   * @parameter fxmlpath: the file path of the fxml file to be loaded
   * Set the stage to a scene by an fxml file
   */
  public void loadScene (Stage primaryStage, String fxmlpath) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource(fxmlpath));
    primaryStage.setScene(new Scene(root, 1300, 750));
    primaryStage.show();
  }

}
