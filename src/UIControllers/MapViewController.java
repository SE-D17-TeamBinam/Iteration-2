package UIControllers;

import static com.sun.javafx.sg.prism.NGCanvas.LINE_WIDTH;

import Definitions.Coordinate;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.DataController;
import org.Point;

/**
 * Created by Leon Zhang on 2017/4/1.
 */
public class MapViewController extends CentralUIController implements Initializable {

  // define all ui elements
  @FXML
  ImageView mapImage;
  @FXML
  ChoiceBox floorChoiceBox;
  @FXML
  ImageView bannerImage;
  @FXML
  AnchorPane anchorPane;
  @FXML
  Rectangle leftBar;

  @FXML
  ImageView titleBanner;

  @FXML
  private Pane infoPane;
  @FXML
  private Rectangle infoPaneRectangle;
  @FXML
  private Pane adminPane;
  @FXML
  private Rectangle adminPaneRectangle;

  @FXML
  public TextField xCoordField;
  @FXML
  public TextField yCoordField;
  @FXML
  public TextField floorField;
  @FXML
  public TextField nameField;

  @FXML
  private Label MapFloor;
  @FXML
  private Button MapBack;
  @FXML
  private Label MapZoomLabel;
  @FXML
  private Button MapNewPoint;
  @FXML
  private Button MapDeletePoint;
  @FXML
  private Button MapUpdatePoint;
  @FXML
  private Text MapSelected;

  @FXML
  Pane zoomPane;
  private final double ZOOM_PANE_OFFSET_HORIZONTAL = 20;
  private final double ZOOM_PANE_OFFSET_VERTICAL = 20;

  int initialized = 0;

  // Used to maintain the map's ratio
  private double mapImageWtHRatio;

  // Controls how much the zoom changes at a time.
  private final double ZOOM_COEF = 0.05;
  private final int ZOOM_MIN = -20;
  private final int ZOOM_MAX = 20;
  private int current_zoom = 0;
  private double CURRENT_ZOOM_SCALE = 1;

  // Map Image positioning boundaries
  // the furthest to the right that the left side of the map image may move
  private double MAP_X_MIN = 145;
  // the furthest to the left that the right side of the map image may move
  private double MAP_X_MAX = 1300;
  // the furthest down that the top of the map image may move
  private double MAP_Y_MIN = 150;
  // the furthest up that the bottom of the map image may move
  private double MAP_Y_MAX = 750;

  // Difference in cursor location from map image location
  private double difX = 0;
  private double difY = 0;
  // Where the mouse drag was initiated
  private double mapPressedX;
  private double mapPressedY;
  // Where the mouse drag was released
  private double mapReleasedX;
  private double mapReleasedY;


  ////////////////////////
  // Administrator Data //
  ////////////////////////

  // ArrayList of Nodes to maintain in memory
  private ArrayList<Point> points = new ArrayList<Point>();

  // The currently selected node
  private org.Point pointFocus = null;
  private ArrayList<Point> nodes = new ArrayList<Point>();
  // List of points that are currently being displayed
  // Note: "org.Point" refers to a graphical object being displayed, not a room for this project
  private ArrayList<javafx.scene.Node> currentlyDrawnNodes = new ArrayList<javafx.scene.Node>();

  // Line to provide visual feedback when drawing points
  // Drawn between selected node and current mouse position, snaps to nearby points when close enough
//  Line connectionFeedbackLine = new Line();

  // For drawing the points
  private double NODE_RADIUS = 15;
  private Color NODE_COLOR = new Color(1, 0, 0,1);
  // For drawing connections between points
  private double LINE_FILL = 2;
  private Color LINE_COLOR = new Color(0,0,0,1);

  private final double CLICK_SELECTION_RADIUS = 25;

  // Tracks whether or not the mouse has been dragged since being pressed down
  private boolean mouseDragged = false;

  // TODO List
  // Add a feature to change privilege modes -> DONE
    // 1 -> just the map
    // 2 -> draw lines
    // 3 -> draw lines, draw paths, show admin panel, enable mouse shortcuts
  // Add feature to display points relative to map -> DONE
  // Add feature to display node connections -> DONE
  // Add scaling of UI when window resizes -> DONE
    // Add a feature to enable adding of points -> DONE
    // Add a feature to enable editing of points -> DONE
    // Add a feature to enable deleting of points -> DONE

  // Add a feature to allow right-click dragging to connect points
    // Add dropdown for starting path on search menu. contains directory entries with non-null names
  // Add padding to the logo on every menu so that wong doesn't get mad
  // Add a save button, includes generating uniqueIDs and updating database
  // Changing floors should query for points only on the floor
  // Fix the paintComponents method for efficiency
  // Do not allow disconnected Points
    // attempt to find paths to every node from one node
    // do not check if one or fewer nodes
  // Add feature to display directions from search menu

  //////////////////
  // FXML Methods //
  //////////////////

  // Initialization
  @FXML
  public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    // points = Editor.getAllPoints();
    System.out.println("Permissions: " + mapViewFlag);
    initializeScene();
    initializeChoiceBox();
    initializeMapImage();
    paintNodeComponents();
    fixZoomPanePos();
    getMap();
  }

  // Add listeners for resizing the screen
  private void initializeScene() {
    anchorPane.widthProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
          Number newSceneWidth) {
        bannerImage.setFitWidth((double) newSceneWidth);
        fixZoomPanePos();
        MAP_X_MAX = (double) newSceneWidth - infoPaneRectangle.getWidth();
        infoPane.setLayoutX((double) newSceneWidth - infoPaneRectangle.getWidth());
        adminPane.setLayoutX(infoPane.getLayoutX());
        moveMapImage(mapImage.getX() + mapImage.getLayoutX(),
            mapImage.getY() + mapImage.getLayoutY());
        titleBanner.setLayoutX(((double) newSceneWidth - titleBanner.getFitWidth()) / 2);
      }
    });
    anchorPane.heightProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
          Number newSceneHeight) {
        leftBar.setHeight((double) newSceneHeight);
        fixZoomPanePos();
        MAP_Y_MAX = (double) newSceneHeight;
        infoPaneRectangle.setHeight(((double) newSceneHeight - bannerImage.getFitHeight()) / 2);
        adminPaneRectangle.setHeight(infoPaneRectangle.getHeight());
        adminPane.setLayoutY(infoPane.getLayoutY() + infoPaneRectangle.getHeight());
        moveMapImage(mapImage.getX() + mapImage.getLayoutX(),
            mapImage.getY() + mapImage.getLayoutY());
      }
    });
    if (mapViewFlag != 3) {
      adminPane.setVisible(false);
    }
  }

  // Fixes the location of the zoom buttons and label, vertically and horizontally
  private void fixZoomPanePos() {
    if (initialized >= 3) {
      setZoomPaneY(
          (double) anchorPane.getHeight() - zoomPane.getHeight() - ZOOM_PANE_OFFSET_VERTICAL);
      setZoomPaneX(
          (double) anchorPane.getWidth() - zoomPane.getWidth() - ZOOM_PANE_OFFSET_HORIZONTAL
              - adminPane.getWidth() * (adminPane.isVisible() ? 1 : 0));
    } else {
      initialized++;
    }
  }

  // Change the zoom pane's horizontal location
  private void setZoomPaneX(double newX) {
    zoomPane.setLayoutX(newX);
  }

  // Change the zoom pane's vertical location
  private void setZoomPaneY(double newY) {
    zoomPane.setLayoutY(newY);
  }

  // Stress Test for displaying points.
  private void addRandomNodes(int count) {
    Point point = null;
    for(int i = 0; i < count; i++){
      double xCoord = Math.random()*5000;
      double yCoord = Math.random()*2500;
      org.Point newPoint = new org.Point(xCoord, yCoord, "");
      if(point == null){
      } else {
        newPoint.connectTo(point);
      }
      newPoint.setFloor(4);
      points.add(newPoint);
      point = newPoint;
    }
  }

  // Add values to the floor selector, add a listener, and set its default value
  private void initializeChoiceBox() {
    // Add options to change floors
    floorChoiceBox.getItems().add(1);
    floorChoiceBox.getItems().add(2);
    floorChoiceBox.getItems().add(3);
    floorChoiceBox.getItems().add(4);
    floorChoiceBox.getItems().add(5);
    floorChoiceBox.getItems().add(6);
    floorChoiceBox.getItems().add(7);
    // Add a ChangeListener to the floorChoiceBox
    floorChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
        new ChangeListener<Number>() {
          public void changed(ObservableValue ov, Number old_value, Number new_value) {
            // Change the image that's being displayed when the input changes
            Image new_img = new Image(
                "/floor_plans/" + (floorChoiceBox.getItems().get((int) new_value))
                    + "floor.png");
            mapImage.setImage(new_img);
            setPointFocus(null);
          }
        });
    floorChoiceBox.setValue(4);
  }

  private void initializeMapImage() {
    //mapImage.setTranslateZ(-5); // Push the map into the background
    mapImage.setFitHeight(mapImage.getImage().getHeight());
    mapImage.setFitWidth(mapImage.getImage().getWidth());
    mapImageWtHRatio = mapImage.getFitWidth() / mapImage.getFitHeight();
  }

  @FXML
  private void saveMap(){
    // send the current state of the map
    // update_nodes(points); // maybe Controller.update_nodes(points);
    globalPoints = points;
  }

  public void getMap(){
    points = globalPoints;
    startNodeBox.getItems().setAll(points);
    endNodeBox.getItems().setAll(points);
  }

  @FXML
  public void addPoint() {
    double x = Double.parseDouble(xCoordField.getText());
    double y = Double.parseDouble(yCoordField.getText());
    int floor = Integer.parseInt(floorField.getText());
    String name = nameField.getText();
    Point newPoint = new Point(x, y, floor);
    points.add(newPoint);
    setPointFocus(newPoint);
    zoomIn();
    zoomOut();
  }

  @FXML
  public void deletePoint() {
    System.out.println(pointFocus.getNeighbors());
    ArrayList<Point> neighbors = (ArrayList<Point>) pointFocus.getNeighbors().clone();
    for (int i = 0; i < neighbors.size(); i++) {
      pointFocus.getNeighbors().remove(neighbors.get(i));
      neighbors.get(i).getNeighbors().remove(pointFocus);
    }
    points.remove(pointFocus);
    setPointFocus(null);
    zoomIn();
    zoomOut();
  }

  @FXML
  public void updatePoint() {
    double x = Double.parseDouble(xCoordField.getText());
    double y = Double.parseDouble(yCoordField.getText());
    int floor = Integer.parseInt(floorField.getText());
    String name = nameField.getText();
    pointFocus.setFloor(floor);
    pointFocus.setXCoord(x);
    pointFocus.setYCoord(y);
    pointFocus.setName(name);
    zoomIn();
    zoomOut();
  }

  // Navigates back to the main menu
  @FXML
  public void back() {
    Stage primaryStage = (Stage) floorChoiceBox.getScene().getWindow();
    try {
      restartUI(primaryStage);
    } catch (Exception e) {
      System.out.println("Cannot load main menu");
      e.printStackTrace();
    }
  }


  @FXML
  private void dragMap(MouseEvent e) {
    String buttonUsed = e.getButton().name();
    mouseDragged = true;
    if (buttonUsed.equals("SECONDARY")) {

    } else {
      double newX = e.getSceneX() - difX;
      double newY = e.getSceneY() - difY;
      moveMapImage(newX, newY);
    }
  }

  @FXML
  private void mapPressed(MouseEvent e) {
    String buttonUsed = e.getButton().name();
    mouseDragged = false;
    if (buttonUsed.equals("SECONDARY")) {
      if(mapViewFlag == 3) {
        Coordinate eventCoord = new Coordinate(e.getSceneX(), e.getSceneY());
        setPointFocus(getNearestPointWithinRadius(eventCoord, CLICK_SELECTION_RADIUS));
      }
    } else {
      // Drag, or initiate connection of a node
      mapPressedX = e.getSceneX();
      mapPressedY = e.getSceneY();
      difX = mapPressedX - mapImage.getX();
      difY = mapPressedY - mapImage.getY();
    }
  }

  @FXML
  private void mapReleased(MouseEvent e) {
    String buttonUsed = e.getButton().name();
    if(buttonUsed.equals("SECONDARY")){
      if(mapViewFlag == 3) {
        // Connect any node that qualifies
        Point p = getNearestPointWithinRadius(new Coordinate(e.getSceneX(), e.getSceneY()),
            CLICK_SELECTION_RADIUS);
        if (p != null && pointFocus != null) {
          p.connectTo(pointFocus);
          zoomIn();
          zoomOut();
        }
      }
    }
    mapReleasedX = e.getSceneX();
    mapReleasedY = e.getSceneY();
  }

  @FXML
  public void zoomIn() {
    getMap();
    changeZoom(true);
  }

  @FXML
  public void zoomOut() {
    getMap();
    changeZoom(false);
  }

  @FXML
  private void clickMap(MouseEvent e) {
    String buttonUsed = e.getButton().name();
    if(!mouseDragged) {
      if(buttonUsed.equals("SECONDARY")){

      } else {
        if (mapViewFlag == 3) {
          setPointFocus(getNearestPointWithinRadius(new Coordinate(e.getSceneX(), e.getSceneY()),
              CLICK_SELECTION_RADIUS));
          if(pointFocus == null){
            Coordinate eventCoord = new Coordinate(e.getSceneX(), e.getSceneY());
            eventCoord = coordinateToPixel(eventCoord);
            Point newPoint = new Point(eventCoord.getX(), eventCoord.getY(), (int)floorChoiceBox.getValue());
            points.add(newPoint);
            setPointFocus(newPoint);
            zoomIn();
            zoomOut();
          }
        }
      }
    }
  }

  private Point getNearestPointWithinRadius(Coordinate nearestTo, double within) {
    Point closest = null;
    double closestDist = Double.MAX_VALUE;
    for (int i = 0; i < points.size(); i++) {
      Point p = points.get(i);
      Coordinate pScene = pixelToCoordinate(new Coordinate(p.getXCoord(), p.getYCoord()));
      double dist = nearestTo.distanceTo(pScene);
      if (dist < within && dist < closestDist) {
        closest = p;
        closestDist = dist;
      }
    }
    return closest;
  }

  // "scrolled" means the scroll wheel. This method controls zooming with the scroll wheel.
  // It's also referenced for zooming with buttons.
  @FXML
  private void mapScrolled(ScrollEvent e) {
    changeZoom(e.getDeltaY() > 0);
    // Then update the tracking for cursor location vs image location
    // Prevents odd behavior when dragging and scrolling simultaneously
    mapPressedX = e.getSceneX();
    mapPressedY = e.getSceneY();
    difX = mapPressedX - mapImage.getX();
    difY = mapPressedY - mapImage.getY();
  }

  private void setPointFocus(Point newFocus){
    pointFocus = newFocus;
    if(newFocus != null) {
      xCoordField.setText("" + newFocus.getXCoord());
      yCoordField.setText("" + pointFocus.getYCoord());
      floorField.setText("" + newFocus.getFloor());
      nameField.setText(newFocus.getName());
    }
  }

  @FXML
  ChoiceBox startNodeBox;
  @FXML
  ChoiceBox endNodeBox;

  @FXML
  public void drawPath(){
    DataController controller = new DataController(null);
    try{
      points = controller.Astar((Point) startNodeBox.getValue(), (Point) endNodeBox.getValue()).getPoints();
//      ObservableList<javafx.scene.Node> children = ((AnchorPane) mapImage.getParent()).getChildren();
//      children.removeAll(currentlyDrawnNodes);
    } catch (Exception e) {

    }

  }



  ///////////////////////////
  // Scene Control Methods //
  ///////////////////////////

  // Draws points and/or node connections on the map
  private void paintNodeComponents() {
    if (mapViewFlag == 1) {
      return;
    }
    ObservableList<javafx.scene.Node> children = ((AnchorPane) mapImage.getParent()).getChildren();

    children.removeAll(currentlyDrawnNodes);
    // Tracking these here so that I can make sure they get painted underneath points
    ArrayList<Line> lines = new ArrayList<Line>();
    for (int i = 0; i < points.size(); i++) {
      if (points.get(i).getFloor() != (int) floorChoiceBox.getValue()) {
        return;
      }
      // The position in the scene for the circle and/or the start of a line
      // Takes into account map size and offset
      Coordinate where2DrawI = pixelToCoordinate(new Coordinate(points.get(i).getXCoord(), points
          .get(i).getYCoord()));

      // Tracking the number of lines so that I can draw points on top of them
      int lineCount = 0;
      // Determine if lines should be drawn as well
      if (mapViewFlag > 1) {
        for (int j = 0; j < points.get(i).getNeighbors().size(); j++) {
          Coordinate where2DrawF = pixelToCoordinate(
              new Coordinate(points.get(i).getNeighbors().get(j).getXCoord(),
                  points.get(i).getNeighbors().get(j).getYCoord()));
          Line line = new Line(where2DrawI.getX(), where2DrawI.getY(), where2DrawF.getX(),
              where2DrawF.getY());
          // Avoids drawing the same line twice
          if (lineIsDrawn(line) == false) {
            line.setFill(LINE_COLOR);
            line.setStrokeWidth(LINE_WIDTH * CURRENT_ZOOM_SCALE);
            lines.add(line);
            line.setVisible(true);
            line.setMouseTransparent(true); // Don't steal events!
          }
        }
      }

      // creates the circle at the proper location.
      // size of the circle scales proportionally with the map zoom
      Circle c = new Circle(where2DrawI.getX(), where2DrawI.getY(),
          NODE_RADIUS * CURRENT_ZOOM_SCALE);
      c.setFill(NODE_COLOR);
      // By adding these Circles at this index, I am making them get painted in a certain order
      // The goal is to have them get painted right after the map image, but before the rest
      // of the components on the screen. That way they will appear on top of the map, but behind
      // the other visual components. 2 is the magic number.
      children.add(lineCount + 2, c);

      // Make sure that we track this object
      currentlyDrawnNodes.add(c);
      // Now display it
      c.setVisible(true);
      c.setMouseTransparent(true); // Don't steal events!
    }
    currentlyDrawnNodes.addAll(lines);
    children.addAll(2, lines);
  }

  // Determine if a circle is visible with the current orientation of the map
  private boolean isCircleVisible(Circle c) {
    // Check if the circle is within the map's display boundaries
    return true;

  }

  // Determine if the line is visible with the current orientation of the map
  private boolean isLineVisible(Line l) {
    // Check if the line intersects any of the map's vision boundaries
    Line top = new Line(MAP_X_MIN, MAP_Y_MIN, MAP_X_MAX, MAP_Y_MIN);
    Line right = new Line(MAP_X_MAX, MAP_Y_MIN, MAP_X_MAX, MAP_Y_MAX);
    Line bottom = new Line(MAP_X_MIN, MAP_Y_MAX, MAP_X_MAX, MAP_Y_MAX);
    Line left = new Line(MAP_X_MIN, MAP_Y_MIN, MAP_X_MIN, MAP_Y_MAX);
    return l.intersects(top.getBoundsInLocal()) ||
        l.intersects(right.getBoundsInLocal()) ||
        l.intersects(bottom.getBoundsInLocal()) ||
        l.intersects(left.getBoundsInLocal());
  }


  // Helper function to detect if line has been drawn in reverse already
  private boolean lineIsDrawn(Line line) {
    for (int i = 0; i < currentlyDrawnNodes.size(); i++) {
      if (currentlyDrawnNodes.get(i).getClass() == line.getClass()) {
        Line testAgainst = (Line) currentlyDrawnNodes.get(i);
        return (line.getEndX() == testAgainst.getStartX() && line.getEndY() == testAgainst
            .getStartY());
      }
    }
    return false;
  }

  // Changes the location of the map, such that it is within the maintained bounds
  private void moveMapImage(double x, double y) {
    // Make sure that the top of the map is above the minimum
    boolean isAbove = (y + mapImage.getLayoutY()) < MAP_Y_MIN;
    // Make sure that the bottom of the map is below the maximum
    boolean isBelow = (y + mapImage.getFitHeight() + mapImage.getLayoutY()) > MAP_Y_MAX;
    // Make sure that the left of the map is to the left of the minimum
    boolean isLeft = (x + mapImage.getLayoutX()) < MAP_X_MIN;
    // Make sure that the right of the map is to the right of the maximum
    boolean isRight = (x + mapImage.getFitWidth() + mapImage.getLayoutX()) > MAP_X_MAX;
    // Make the assertions, move the map
    if (isAbove && isBelow) {
      mapImage.setY(y);
    } else if (!isAbove && isBelow) {
      mapImage.setY(MAP_Y_MIN - mapImage.getLayoutY());
    } else if (isAbove && !isBelow) {
      mapImage.setY(MAP_Y_MAX - mapImage.getFitHeight() - mapImage.getLayoutY());
    } else {
      // The map is too small, not sure what to do.
    }
    if (isLeft && isRight) {
      mapImage.setX(x);
    } else if (!isLeft && isRight) {
      mapImage.setX(MAP_X_MIN - mapImage.getLayoutX());
    } else if (isLeft && !isRight) {
      mapImage.setX(MAP_X_MAX - mapImage.getFitWidth() - mapImage.getLayoutX());
    } else {
      // The map is too small, not sure what to do.
    }
    // If we have admin privileges, then we need to move the node displays as well
    if (mapViewFlag >= 2) {
      paintNodeComponents();
    }
  }

  // Takes a new width for the image to be resized to. Height is set based on the ratio.
  private void resizeImageByWidth(double width) {
    mapImage.setFitWidth(width);
    mapImage.setFitHeight(width / mapImageWtHRatio);
  }

  // Takes a new height for the image to be resized to. Width is set based on the ratio.
  private void resizeImageByHeight(double height) {
    mapImage.setFitHeight(height);
    mapImage.setFitWidth(height * mapImageWtHRatio);
  }

  // Takes a point in the scene and returns the pixel on the map that corresponds
  public Coordinate coordinateToPixel(Coordinate p) {
    double mapX = mapImage.getX() + mapImage.getLayoutX();
    double mapY = mapImage.getY() + mapImage.getLayoutY();
    double maxX = mapImage.getFitWidth() + mapX;
    double maxY = mapImage.getFitHeight() + mapY;
    if (p.getX() > mapX && p.getX() < maxX && p.getY() > mapY && p.getY() < maxY) {
      double ratioX = (p.getX() - mapX) / mapImage.getFitWidth();
      double ratioY = (p.getY() - mapY) / mapImage.getFitHeight();
      double pixelX = ratioX * mapImage.getImage().getWidth();
      double pixelY = ratioY * mapImage.getImage().getHeight();
      return new Coordinate((int) pixelX, (int) pixelY);
    } else {
      return null;
    }
  }

  // Takes the pixel on the map image and returns the position in the scene where it corresponds to.
  // Useful for drawing
  public Coordinate pixelToCoordinate(Coordinate p) {
    // Determine how the scaling is currently set (zoom)
    double mapScale = mapImage.getFitWidth() / mapImage.getImage().getWidth();
    double actualX = p.getX() * mapScale + mapImage.getX() + mapImage.getLayoutX();
    double actualY = p.getY() * mapScale + mapImage.getY() + mapImage.getLayoutY();
    Coordinate point = new Coordinate((int) actualX, (int) actualY);
    return point;
  }

  // Either zooms in or out
  // if delta is true, the zoom increases
  // if delta is false, the zoom decreases
  // Also distributes spacing around the image, 50% of the difference in size on each side
  private void changeZoom(boolean delta) {
    // Find the old dimensions, will be used for making the zooming look better
    double oldWidth = mapImage.getFitWidth();
    double oldHeight = mapImage.getFitHeight();
    // Resize the image
    if (delta) { // delta was positive
      if (current_zoom >= ZOOM_MAX) {
        // Can't zoom any more
      } else {
        current_zoom++;
        resizeImageByHeight(mapImage.getFitHeight() * (1 + ZOOM_COEF));
        CURRENT_ZOOM_SCALE = CURRENT_ZOOM_SCALE * (1 + ZOOM_COEF);
      }
    } else { // delta was negative
      if (current_zoom <= ZOOM_MIN) {
        // Can't zoom any more
      } else {
        current_zoom--;
        resizeImageByHeight(mapImage.getFitHeight() / (1 + ZOOM_COEF));
        CURRENT_ZOOM_SCALE = CURRENT_ZOOM_SCALE / (1 + ZOOM_COEF);
      }
    }
    // Find the new dimensions
    double newWidth = mapImage.getFitWidth();
    double newHeight = mapImage.getFitHeight();
    // Move the image so that the zoom appears to be in the center of the image
    moveMapImage(mapImage.getX() - (newWidth - oldWidth) / 2,
        mapImage.getY() - (newHeight - oldHeight) / 2);
  }
}