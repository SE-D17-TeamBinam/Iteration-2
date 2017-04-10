package UIControllers;

import Definitions.Physician;
import javafx.scene.layout.AnchorPane;
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
    /* Tests */
    ArrayList<Point> rooms = new ArrayList<Point>();
    ArrayList<Physician> docs = new ArrayList<Physician>();
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
    updateDirectory(docs);
  }

  public void updateDirectory (List<Physician> HCs){
    ArrayList<String> list = new ArrayList<String>();
    for (Physician doctor : HCs) {
      String newDoc = doctor.getLastName() + ", " + doctor.getFirstName() + ", " + doctor.getTitle()
          + "\nLocations: " + doctor.getLocations().get(0).getName()
          + ", " + doctor.getLocations().get(1).getName()
          + ", " + doctor.getLocations().get(2).getName()
          + ", " + doctor.getLocations().get(3).getName()
          + ", " + doctor.getLocations().get(4).getName()
          + ", " + doctor.getLocations().get(5).getName();
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