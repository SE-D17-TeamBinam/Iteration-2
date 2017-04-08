package UIControllers;

import javafx.event.Event;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.Dictionary;
import CredentialManager.CredentialManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Leon Zhang on 2017/4/1.
 */


public class AdminLoginController extends CentralUIController implements Initializable {

  /* define all ui elements */
  @FXML
  private Pane AdminLogin;
  @FXML
  private javafx.scene.control.TextField AdminNameField;
  @FXML
  private PasswordField AdminPassField;

  /* language fields */
  @FXML
  private Button AdminBack;
  @FXML
  private Label AdminNameLabel;
  @FXML
  private Label AdminPassLabel;
  @FXML
  private Button AdminLoginButton;
  @FXML
  private Label LoginError;

  @Override
  public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    /* apply language configs */
    System.out.println(currLang);
    Dictionary d = new Dictionary();
    AdminBack.setText(d.getString("Back", currLang));
    AdminNameLabel.setText(d.getString("Username", currLang));
  }


  // Detects if a key is pressed when the username, password, or login button are highlighted
  // If the key pressed is the ENTER key, then it attempts to login with the current input
  @FXML
  private void tryLogin(KeyEvent e){
    if(e.getCode().toString().equals("ENTER")){
      login();
    }
  }

  /**
   * checks login credential. If pass, log into directory editor; if fail, show an error message
   * TODO: throw an exception in the future.
   */
  public void login () {

    Stage primaryStage = (Stage) AdminLogin.getScene().getWindow();
    String enteredName = AdminNameField.getText();
    String enteredPass = AdminPassField.getText();
    CredentialManager cm = new CredentialManager();
    if (cm.userIsAdmin(enteredName, enteredPass)) {
      LoginError.setVisible(false);
      try {
        loadScene(primaryStage, "/DirectEdit.fxml");
      } catch (Exception e) {
        System.out.println("Cannot load directory editor");
        e.printStackTrace();
      }
    } else {
      LoginError.setVisible(true);
    }
  }

  /**
   * go back to the main menu
   */
  public void back () {
    Stage primaryStage = (Stage) AdminLogin.getScene().getWindow();
    try {
      restartUI(primaryStage);
    } catch (Exception e) {
      System.out.println("Cannot load main menu");
      e.printStackTrace();
    }
  }

}