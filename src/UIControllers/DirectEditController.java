package UIControllers;

import Definitions.Physician;
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
import org.Point;

/**
 * Created by Leon Zhang on 4/4/2017.
 */
public class DirectEditController extends CentralUIController implements Initializable {
  private int selectedHPIndex;
  private boolean locationShown;
  private Physician selectedHP = null;
  private ArrayList<Physician> docs;
  private ArrayList<Point> rooms;
  private ArrayList<String> roomNames;
  private ArrayList<String> docNames;

  @FXML
  private Pane DirectEdit;
  @FXML
  private TextField FirstName;
  @FXML
  private TextField LastName;
  @FXML
  private TextField Title;
  @FXML
  private ChoiceBox Location1;
  @FXML
  private ChoiceBox Location2;
  @FXML
  private ChoiceBox Location3;
  @FXML
  private ChoiceBox Location4;
  @FXML
  private ChoiceBox Location5;
  @FXML
  private ChoiceBox Location6;
  @FXML
  private ListView<String> Directory;

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
  public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

    rooms = new ArrayList<Point>();
    docs = new ArrayList<Physician>();
    roomNames = new ArrayList<String>();
    docNames = new ArrayList<String>();
    locationShown = false;
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

    roomNames.add("None");
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

            // fill location selectors
            if (!locationShown) {
              displayLoc();
              locationShown = true;
            }
            // set text field
            refreshInfo();

            // set location selectors
            refreshLoc();

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

  public void displayLoc () {
    Location1.setItems(FXCollections.observableList(roomNames));
    Location2.setItems(FXCollections.observableList(roomNames));
    Location3.setItems(FXCollections.observableList(roomNames));
    Location4.setItems(FXCollections.observableList(roomNames));
    Location5.setItems(FXCollections.observableList(roomNames));
    Location6.setItems(FXCollections.observableList(roomNames));
  }

  public void clearLoc () {
    Location1.setItems(FXCollections.observableList(new ArrayList<String>()));
    Location2.setItems(FXCollections.observableList(new ArrayList<String>()));
    Location3.setItems(FXCollections.observableList(new ArrayList<String>()));
    Location4.setItems(FXCollections.observableList(new ArrayList<String>()));
    Location5.setItems(FXCollections.observableList(new ArrayList<String>()));
    Location6.setItems(FXCollections.observableList(new ArrayList<String>()));
  }

  public void refreshDir () {
    docNames = new ArrayList<String>();
    for (Physician doc : docs) {
      String aDoc = doc.getFirstName() + ", " + doc.getLastName() + " " + Long.toString(doc.getID());
      docNames.add(aDoc);
    }
    // and fill the directory
    Directory.setItems(FXCollections.observableList(docNames));
  }

  public void refreshInfo () {
    LastName.setText(selectedHP.getLastName());
    FirstName.setText(selectedHP.getFirstName());
    Title.setText(selectedHP.getTitle());
  }

  public void clearInfo () {
    LastName.setText("");
    FirstName.setText("");
    Title.setText("");
  }

  public void refreshLoc () {
    Point temp;
    try {
      temp = selectedHP.getLocations().get(0);
      Location1.setValue(temp.getName());
    } catch (IndexOutOfBoundsException e) {
      Location1.setValue("None");
    }
    try {
      temp = selectedHP.getLocations().get(1);
      Location2.setValue(temp.getName());
    } catch (IndexOutOfBoundsException e) {
      Location2.setValue("None");
    }
    try {
      temp = selectedHP.getLocations().get(2);
      Location3.setValue(temp.getName());
    } catch (IndexOutOfBoundsException e) {
      Location3.setValue("None");
    }
    try {
      temp = selectedHP.getLocations().get(3);
      Location4.setValue(temp.getName());
    } catch (IndexOutOfBoundsException e) {
      Location4.setValue("None");
    }
    try {
      temp = selectedHP.getLocations().get(4);
      Location5.setValue(temp.getName());
    } catch (IndexOutOfBoundsException e) {
      Location5.setValue("None");
    }
    try {
      temp = selectedHP.getLocations().get(5);
      Location6.setValue(temp.getName());
    } catch (IndexOutOfBoundsException e) {
      Location6.setValue("None");
    }
  }


  public ArrayList<Point> finalLocs () {
    ArrayList<Point> ret = new ArrayList<Point>();
    addtoFinalLocs(ret, Location1);
    addtoFinalLocs(ret, Location2);
    addtoFinalLocs(ret, Location3);
    addtoFinalLocs(ret, Location4);
    addtoFinalLocs(ret, Location5);
    addtoFinalLocs(ret, Location6);
    return ret;
  }

  public void addtoFinalLocs(ArrayList<Point> ret, ChoiceBox L) {
    for (Point n : rooms) {
      if (L.getValue().toString().equals("None")) {
        break;
      }
      if (n.getName().equals(L.getValue().toString())){
        ret.add(n);
        break;
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
      System.out.println(Integer.toString(selectedHPIndex));
      if (selectedHPIndex >= docs.size()) {
        docs.add(selectedHP);
      } else {
        docs.set(selectedHPIndex, selectedHP);
      }
      // save docs to DB
      // refresh the page
      refreshInfo();
      refreshLoc();
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
    selectedHP = new Physician("", "", "", newPID, new ArrayList<Point>());
    selectedHPIndex = docs.size();
    refreshInfo();
    refreshLoc();
    if (!locationShown){
      displayLoc();
      locationShown = true;
    }
  }

  public void delete () {
    docs.remove(selectedHP);
    refreshDir();
    clearLoc();
    clearInfo();
    locationShown = false;
  }

  public void back () {
    Stage primaryStage = (Stage) DirectEdit.getScene().getWindow();
    try {
      loadScene(primaryStage, "/AdminLogin.fxml");
    } catch (Exception e) {
      System.out.println("Cannot load main menu");
      e.printStackTrace();
    }
  }

  public void logoff () {
    Stage primaryStage = (Stage) DirectEdit.getScene().getWindow();
    try {
      restartUI(primaryStage);
    } catch (Exception e) {
      System.out.println("Cannot load main menu");
      e.printStackTrace();
    }
  }
}
