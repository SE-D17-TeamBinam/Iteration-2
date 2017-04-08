package UIControllers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.CentralController;
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
  private Button MapButton;
  @FXML
  private Button SearchButton;

  public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    chooseLang();

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
            // Change the language that's being displayed when the input changes
            System.out.println(Integer.toString(langBox.getSelectionModel().getSelectedIndex()));

           currLang = (String) langBox.getItems().get((int) new_value);
           System.out.println(currLang);
          }
        });
  }


}
