package UIControllers;

import Definitions.Physician;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * Created by Leon Zhang on 2017/4/1.
 */
public class SearchMenuController extends CentralUIController implements Initializable {
  // define all ui elements
  @FXML
  private ListView SearchDirectory;
  @FXML
  private TextField SearchField;
  @FXML
  private Button SearchDone;
  @FXML
  private Button SearchClear;
  @FXML
  private Pane SearchPane;

  @FXML
  private ListView DropMenu;
  @FXML
  private Button SearchMap;
  @FXML
  private Button SearchInfo;
  @FXML
  private Button SearchBack;

  @FXML
  private AnchorPane anchorPane;

  public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    addResolutionListener(anchorPane);
    setBackground(anchorPane);

    /* apply language configs */
    SearchMap.setText(dictionary.getString("Show on Map", currSession.getLanguage()));
    SearchInfo.setText(dictionary.getString("View Info", currSession.getLanguage()));
    SearchBack.setText(dictionary.getString("Back", currSession.getLanguage()));
    SearchField.setPromptText(dictionary.getString("Search Directory", currSession.getLanguage()));
    /* Tests */
    ArrayList<Physician> docs = database.getPhysicians();
    updateDirectory(docs);
  }

  @Override
  public void customListenerX () {
    SearchPane.setLayoutX(x_res/2 - 325);
    SearchField.setLayoutX(x_res/2 - 325);
    SearchClear.setLayoutX(x_res/2 + 250);
    SearchDone.setLayoutX(x_res - 155);
  }
  @Override
  public void customListenerY () {
    SearchPane.setLayoutY(5*(y_res/11)-20);
    SearchField.setLayoutY(4*(y_res/11)-50);
    SearchClear.setLayoutY(4*(y_res/11)-50);
  }

  public void updateDirectory (List<Physician> HCs){
    ArrayList<String> list = new ArrayList<>();
    for (Physician doctor : HCs) {
      String newDoc = doctor.getLastName() + ", " + doctor.getFirstName() + ", " + doctor.getTitle()
          + "\nLocations: ";
      for (int i = 0; i < doctor.getLocations().size(); i++) {
        newDoc = newDoc + doctor.getLocations().get(i).getName();
        if (i < doctor.getLocations().size() - 1){
          newDoc = newDoc + ", ";
        }
      }
      list.add(newDoc);
    }
    ObservableList<String> listHC = FXCollections.observableList(list);
    SearchDirectory.setItems(listHC);
  }

  public void back () {
    Stage primaryStage = (Stage) SearchDirectory.getScene().getWindow();
    try {
      loadScene(primaryStage, "/MainMenu.fxml");
    } catch (Exception e) {
      System.out.println("Cannot load main menu");
      e.printStackTrace();
    }
  }

  public void clear () {
    SearchField.clear();
  }
}