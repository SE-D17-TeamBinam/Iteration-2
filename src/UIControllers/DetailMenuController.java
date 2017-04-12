package UIControllers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Leon Zhang on 2017/4/1.
 */

public class DetailMenuController extends CentralUIController implements Initializable {
  // define all ui elements
  @FXML
  private Pane DetailMenu; // Value injected by FXMLLoader
  @FXML
  private AnchorPane anchorPane;
  @FXML
  private Label DetailTitle;
  @FXML
  private Label DetailRoomName;
  @FXML
  private Label DetailRoomFloor;
  @FXML
  private Label DetailRoomHCPs;


  private Stage primaryStage = (Stage) DetailMenu.getScene().getWindow();

  @Override
  public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    /* apply language configs */
    DetailTitle.setText(dictionary.getString("Room Details", currSession.getLanguage()));
    DetailRoomName.setText(dictionary.getString("Room Name", currSession.getLanguage()));
    DetailRoomFloor.setText(dictionary.getString("Floor", currSession.getLanguage()));
    DetailRoomHCPs.setText(dictionary.getString("Healthcare Providers", currSession.getLanguage()));

    addResolutionListener(anchorPane);
    setBackground(anchorPane);
  }

  public void quit () {
    try {
      restartUI(primaryStage);
    } catch (Exception e) {
      System.out.println("Cannot load main menu");
      e.printStackTrace();
    }
  }

  public void back () {
    try {
      loadScene(primaryStage, "/SearchMenu.fxml");
    } catch (Exception e) {
      System.out.println("Cannot load search menu");
      e.printStackTrace();
    }
  }

  public void gotoMap () {
    try {
      loadScene(primaryStage, "/MapScene.fxml");
    } catch (Exception e) {
      System.out.println("Cannot load map view");
      e.printStackTrace();
    }
  }

}

