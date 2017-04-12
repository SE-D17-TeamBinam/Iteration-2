package UIControllers;

import Definitions.Physician;
import javafx.scene.layout.AnchorPane;
import org.Language;
import org.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Leon Zhang on 4/4/2017.
 */
public class DirectEditController extends CentralUIController implements Initializable {
  private int selectedHPIndex;
  private Physician selectedHP = null;
  private ChoiceBox<String> selectedCB = null;
  private ArrayList<Physician> docs;
  private ArrayList<Point> rooms;
  private ArrayList<String> roomNames;
  private ArrayList<String> docNames;
  private ArrayList<ChoiceBox> locations;

  @FXML
  private Pane DirectEdit;
  @FXML
  private TextField FirstName;
  @FXML
  private TextField LastName;
  @FXML
  private TextField Title;
  @FXML
  private ListView<String> Directory;
  @FXML
  private ListView<ChoiceBox> Locations;
  @FXML
  private Button AddLocation;
  @FXML
  private Button RemoveLocation;
  @FXML
  private AnchorPane anchorPane;


  @FXML
  private Button DirectBack;
  @FXML
  private Button DirectEditMap;
  @FXML
  private Label DirectFirstName;
  @FXML
  private Label DirectLastName;
  @FXML
  private Label DirectTitle;
  @FXML
  private Label DirectLocations;
  @FXML
  private Button DirectCancel;
  @FXML
  private Button DirectSave;
  @FXML
  private Button DirectLogoff;
  @FXML
  private Button DirectCreate;
  @FXML
  private Button DirectDelete;

  @Override
  public void customListenerX () {
    DirectLogoff.setLayoutX(x_res - DirectLogoff.getPrefWidth() - 5);
  }
  @Override
  public void customListenerY () {

  }

  @Override
  public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    if (currSession.getLanguage() == Language.SPANISH) {
      DirectLogoff.setPrefWidth(200);
    } else if (currSession.getLanguage() == Language.PORTUGESE) {
      DirectLogoff.setPrefWidth(240);
    } else {
        DirectLogoff.setPrefWidth(150);
    }
    addResolutionListener(anchorPane);
    setBackground(anchorPane);

    /* apply language configs */
    DirectBack.setText(dictionary.getString("Back", currSession.getLanguage()));
    DirectFirstName.setText(dictionary.getString("First Name", currSession.getLanguage()));
    DirectLastName.setText(dictionary.getString("Last Name", currSession.getLanguage()));
    DirectTitle.setText(dictionary.getString("Title", currSession.getLanguage()));
    DirectCancel.setText(dictionary.getString("Cancel",currSession.getLanguage()));
    DirectCreate.setText(dictionary.getString("Create", currSession.getLanguage()));
    DirectDelete.setText(dictionary.getString("Delete", currSession.getLanguage()));
    DirectLocations.setText(dictionary.getString("Locations", currSession.getLanguage()));
    DirectSave.setText(dictionary.getString("Save", currSession.getLanguage()));
    DirectLogoff.setText(dictionary.getString("Log off", currSession.getLanguage()));
    DirectEditMap.setText(dictionary.getString("Edit Map", currSession.getLanguage()));


    rooms = database.getNamedPoints();
    docs = database.getPhysicians();
    roomNames = new ArrayList<>();
    docNames = new ArrayList<>();

    // load all docs
    refreshDir();

    for (Point n : rooms) {
      roomNames.add(n.getName());
    }

    // when select any doc
    Directory.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<String>() {
          public void changed(ObservableValue<? extends String> ov,
              String old_val, String new_val) {

            int clicked = Directory.getSelectionModel().getSelectedIndex();
            if (clicked >= 0) {
              selectedHPIndex = clicked;
              selectedHP = docs.get(selectedHPIndex);
            }
            refreshLoc();
            // set text field
            refreshInfo();
          }
        });
    Locations.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<ChoiceBox>() {
          @Override
          public void changed(ObservableValue<? extends ChoiceBox> observable, ChoiceBox oldValue,
              ChoiceBox newValue) {
            selectedCB = newValue;
          }
        });
  }

  @FXML
  public void editMap(){
    mapViewFlag = 3;
    Stage primaryStage = (Stage) DirectEdit.getScene().getWindow();
    try {
      loadScene(primaryStage, "/MapScene.fxml");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void refreshLoc () {
    int i = 0;
    locations = new ArrayList<>();
    for (Point p : selectedHP.getLocations()) {
      ChoiceBox<String> cb = new ChoiceBox<>();
      cb.setItems(FXCollections.observableList(roomNames));
      Point temp = selectedHP.getLocations().get(i);
      cb.setValue(temp.getName());
      locations.add(cb);
      i++;
    }
    Locations.setItems(FXCollections.observableList(locations));
  }


  private void clearLoc () {
    Locations.setItems(FXCollections.observableList(new ArrayList<>()));
  }

  public void addLocation () {
    ChoiceBox<String> cb = new ChoiceBox<>();
    cb.setItems(FXCollections.observableList(roomNames));
    locations.add(cb);
    Locations.setItems(FXCollections.observableList(locations));
  }

  public void removeLocation () {
    locations.remove(selectedCB);
    Locations.setItems(FXCollections.observableList(locations));
  }

  private void refreshDir () {
    docNames = new ArrayList<>();
    for (Physician doc : docs) {
      String aDoc = doc.getFirstName() + ", " + doc.getLastName() +
          ", Title: " + doc.getTitle() + " ID: " + Long.toString(doc.getID());
      docNames.add(aDoc);
    }
    // and fill the directory
    Directory.setItems(FXCollections.observableList(docNames));
  }

  private void refreshInfo () {
    LastName.setText(selectedHP.getLastName());
    FirstName.setText(selectedHP.getFirstName());
    Title.setText(selectedHP.getTitle());
  }

  private void clearInfo () {
    LastName.setText("");
    FirstName.setText("");
    Title.setText("");
  }

  private ArrayList<Point> finalLocs () {
    ArrayList<Point> ret = new ArrayList<>();
    for (ChoiceBox cb : locations) {
      addtoFinalLocs(ret, cb);
    }
    return ret;
  }

  private void addtoFinalLocs(ArrayList<Point> ret, ChoiceBox L) {
    for (Point n : rooms) {
      try {
        if (n.getName().equals(L.getValue().toString())) {
          ret.add(n);
        }
      } catch (NullPointerException e) {
        continue;
      }
    }
  }


  public void save () {
    try {
      selectedHP.setFirstName(FirstName.getText());
      selectedHP.setLastName(LastName.getText());
      selectedHP.setTitle(Title.getText());
      selectedHP.setLocations(finalLocs());
      // check if it's a new Physician
      if (selectedHPIndex >= docs.size()) {
        docs.add(selectedHP);
      } else {
        docs.set(selectedHPIndex, selectedHP);
      }
      // refresh the page
      refreshInfo();
      refreshDir();
      Directory.getSelectionModel().select(selectedHPIndex);
      database.setPhysicians(docs);
    } catch (NullPointerException e) {
      System.out.println("Nothing is selected");
    }
  }

  public void cancel () {
    try {
      refreshInfo();
      refreshLoc();
    } catch (NullPointerException e) {
      System.out.println("Nothing is selected");
    }
  }

  public void create () {
    Directory.getSelectionModel().select(-1);
    long newPID;
    try {
      newPID = docs.get(docs.size() - 1).getID() + 1;
    } catch (ArrayIndexOutOfBoundsException e) {
      newPID = 1;
    }
    selectedHP = new Physician("", "", "", newPID, new ArrayList<>());
    selectedHPIndex = docs.size();
    refreshInfo();
    refreshLoc();
  }

  public void delete () {
    docs.remove(selectedHP);
    refreshDir();
    clearLoc();
    clearInfo();
    selectedHP = null;
  }

  public void back () {
    Stage primaryStage = (Stage) DirectEdit.getScene().getWindow();
    try {
      loadScene(primaryStage, "/AdminLogin.fxml");
    } catch (Exception e) {
      System.out.println("Cannot load admin login menu");
      e.printStackTrace();
    }
  }

  public void logoff () {
    Stage primaryStage = (Stage) DirectEdit.getScene().getWindow();
    try {
      loadScene(primaryStage, "/MainMenu.fxml");
    } catch (Exception e) {
      System.out.println("Cannot load main menu");
      e.printStackTrace();
    }
  }
}
