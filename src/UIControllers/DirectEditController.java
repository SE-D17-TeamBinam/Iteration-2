package UIControllers;

import Definitions.Physician;
import javafx.scene.layout.AnchorPane;
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
    DirectLogoff.setLayoutX(x_res - 155);
  }
  @Override
  public void customListenerY () {

  }

  @Override
  public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    addResolutionListener(anchorPane);
    setBackground(anchorPane);

    rooms = new ArrayList<>();
    docs = new ArrayList<>();
    roomNames = new ArrayList<>();
    docNames = new ArrayList<>();
    // get list of rooms
    Point a1 = new Point(0, 0, "a");
    Point a2 = new Point(0, 0, "b");
    Point a3 = new Point(0, 0, "c");
    Point a4 = new Point(0, 0, "d");
    Point a5 = new Point(0, 0, "e");
    Point a6 = new Point(0, 0, "f");
    rooms.add(a1);
    rooms.add(a2);
    rooms.add(a3);
    rooms.add(a4);
    rooms.add(a5);
    rooms.add(a6);
    Physician b1 = new Physician("A", "B", "Nurse", 0, rooms);
    Physician b2 = new Physician("C", "D", "Nurse", 1, rooms);
    Physician b3 = new Physician("E", "F", "Nurse", 2, rooms);
    Physician b4 = new Physician("G", "H", "Nurse", 3, rooms);
    docs.add(b1);
    docs.add(b2);
    docs.add(b3);
    docs.add(b4);

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
      String aDoc = doc.getFirstName() + ", " + doc.getLastName() + " " + Long.toString(doc.getID());
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
    long newPID = docs.get(docs.size() - 1).getID() + 1;
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
