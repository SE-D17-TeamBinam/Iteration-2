package UIControllers;

import CredentialManager.CredentialManager;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
  protected static Image banner = new Image ("/icons/banner.png");
  protected static Image background = new Image ("/icons/background_shapte.png");
  protected static Image logo = new Image ("/icons/BWFH_logo_rgb.jpg");
  protected static ImageView bannerView = new ImageView();
  protected static ImageView backgroundView = new ImageView();
  protected static ImageView logoView = new ImageView();

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
        bannerView.setFitWidth(x_res);
        backgroundView.setFitWidth(x_res);
        logoView.setLayoutX(x_res/2 - 240);
        customListenerX();
      }
    });
    anchorPane.heightProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
        y_res = (double) newSceneHeight;
        backgroundView.setLayoutY(y_res/2.5);
        customListenerY();
      }
    });
  }

  public void customListenerX () {

  }

  public void customListenerY () {

  }

  public void setBackground (AnchorPane anchorPane) {
    bannerView.setImage(banner);
    bannerView.setFitWidth(x_res);
    backgroundView.setImage(background);
    backgroundView.setFitWidth(x_res);
    backgroundView.setPreserveRatio(true);
    backgroundView.setLayoutY(y_res/2.5);
    logoView.setImage(logo);
    logoView.setFitWidth(480);
    logoView.setPreserveRatio(true);
    logoView.setLayoutX(x_res/2 - 240);
    logoView.setLayoutY(-1);
    anchorPane.getChildren().add(bannerView);
    anchorPane.getChildren().add(backgroundView);
    anchorPane.getChildren().add(logoView);
    logoView.toBack();
    bannerView.toBack();
    backgroundView.toBack();
  }
}
