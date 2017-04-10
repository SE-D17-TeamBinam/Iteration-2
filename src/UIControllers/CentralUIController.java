package UIControllers;

import CredentialManager.CredentialManager;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
  public static ArrayList<Point> globalPoints = new ArrayList<Point>(); // TODO Fix
  protected static Session currSession;
  protected static CredentialManager credentialManager;
  protected static Dictionary dictionary;
  protected static double x_res = 1300;
  protected static double y_res = 750;


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
    primaryStage.setScene(new Scene(root, x_res, y_res));
    primaryStage.setTitle("Faulkner Hospital Kiosk");
    primaryStage.show();
  }
  /**
   * @parameter primaryStage: The main stage of the application
   * @parameter fxmlpath: the file path of the fxml file to be loaded
   * Set the stage to a scene by an fxml file
   */
  public void loadScene (Stage primaryStage, String fxmlpath) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource(fxmlpath));
    primaryStage.setScene(new Scene(root, x_res, y_res));
    primaryStage.show();
  }

  public void addResolutionListener (AnchorPane anchorPane) {
    anchorPane.widthProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
        x_res = (double) newSceneWidth;
      }
    });
    anchorPane.heightProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
        y_res = (double) newSceneHeight;
      }
    });
  }
}
