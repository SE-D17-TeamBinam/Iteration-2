package UIControllers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.CentralController;
import org.Language;
import org.Session;

/**
 * Created by Leon Zhang on 2017/4/1.
 */

public class MainMenuController extends CentralUIController implements Initializable {
  // define all ui elements
  @FXML
  private Pane MainMenu;
  @FXML
  private ChoiceBox langBox;
  @FXML
  private AnchorPane anchorPane;
  @FXML
  private ImageView MainKey;

  @FXML
  private Button MapButton;
  @FXML
  private Button SearchButton;

  public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    chooseLang();
    addResolutionListener(anchorPane);
    setBackground(anchorPane);
  }

  @Override
  public void customListenerX () {
    MapButton.setLayoutX(5*(x_res/7) - 175);
    SearchButton.setLayoutX(2*(x_res/7) - 175);
    MainKey.setLayoutX(x_res - 150);

  }
  @Override
  public void customListenerY () {
    MainKey.setLayoutY(y_res - 57);
    MapButton.setLayoutY(6*(y_res/11) - 160);
    SearchButton.setLayoutY(6*(y_res/11) - 160);
    langBox.setLayoutY(y_res - 50);
  }

  public void gotoMap () {
    Stage primaryStage = (Stage) MainMenu.getScene().getWindow();
    try {
      mapViewFlag = 2;
      loadScene(primaryStage, "/MapScene.fxml");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void gotoSearch () {
    Stage primaryStage = (Stage) MainMenu.getScene().getWindow();
    try {
      loadScene(primaryStage, "/SearchMenu.fxml");
    } catch (Exception e) {
    }
  }

  public void gotoAdmin () {
    Stage primaryStage = (Stage) MainMenu.getScene().getWindow();
    try {
      loadScene(primaryStage, "/AdminLogin.fxml");
    } catch (Exception e) {
    }
  }

  public void chooseLang() {
    langBox.getItems().add("ENGLISH");
    langBox.getItems().add("SPANISH");
    langBox.getItems().add("PORTUGUESE");

    langBox.getSelectionModel().select(0);
    langBox.getSelectionModel().selectedIndexProperty().addListener(
        new ChangeListener<Number>() {
          public void changed(ObservableValue ov, Number old_value, Number new_value) {
            // Change the language that's being displayed when the input changes\
            if ("ENGLISH".equals((String) langBox.getItems().get((int) new_value))) {
              currSession.setLanguage(Language.ENGLISH);
            } else if ("SPANISH".equals((String) langBox.getItems().get((int) new_value))) {
              currSession.setLanguage(Language.SPANISH);
            } else if ("PORTUGUESE".equals((String) langBox.getItems().get((int) new_value))) {
              currSession.setLanguage(Language.PORTUGESE);
            }
            System.out.println(currSession.getLanguage());
          }
        });
  }


}
