package UIControllers;

import Definitions.Coordinate;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import org.Point;

/**
 * Created by Leon Zhang on 2017/4/1.
 */
public class MapViewController extends CentralUIController implements Initializable {

  // define all ui elements
  @FXML
  private ImageView mapImage;
  @FXML
  private Pane mapViewPane;

  @FXML
  private ChoiceBox floorChoiceBox;
  @FXML
  private ImageView bannerImage;
  @FXML
  private AnchorPane anchorPane;
  @FXML
  private Rectangle leftBar;

  @FXML
  private ImageView titleBanner;

  @FXML
  private Pane infoPane;
  @FXML
  private Rectangle infoPaneRectangle;
  @FXML
  private Pane adminPane;
  @FXML
  private Rectangle adminPaneRectangle;

  @FXML
  private TextField xCoordField;
  @FXML
  private TextField yCoordField;
  @FXML
  private TextField floorField;
  @FXML
  private TextField nameField;

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
  private Pane zoomPane;


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
  private double current_zoom_scale = 1;

  // Map Image positioning boundaries
  // the furthest to the right that the left side of the map image may move
  private double map_x_min = 145;
  // the furthest to the left that the right side of the map image may move
  private double map_x_max = 1300;
  // the furthest down that the top of the map image may move
  private double map_y_min = 150;
  // the furthest up that the bottom of the map image may move
  private double map_y_max = 750;

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

  // ArrayList of Points to maintain in memory
  private ArrayList<Point> points = new ArrayList<org.Point>();
  // ArrayList of Edges to help track for drawing
  private ArrayList<Connection> connections = new ArrayList<Connection>();

  // The currently selected point
//  private Point pointFocus = null;


  // For drawing the points
  private final double NODE_RADIUS = 15;
  private final Color NODE_COLOR = new Color(1, 0, 0,1);
  // For drawing connections between points
  private final double LINE_FILL = 2;
  private final Color LINE_COLOR = new Color(0,0,0,1);

  // The radius that a user needs to click within in order to select a node
  private final double CLICK_SELECTION_RADIUS = 15;

  // Tracks whether or not the mouse has been dragged since being pressed down
  private boolean mouseDragged = false;

  // The circles and lines that are currently drawn
  private HashMap<Point, Circle> circles = new HashMap<Point, Circle>();
  private HashMap<Connection, Line> lines = new HashMap<Connection, Line>();

  private class Connection{
    private Point start;
    private Point end;

    public Connection(Point start, Point end){
      this.start = start;
      this.end = end;
    }

    public Point getStart(){
      return start;
    }

    public Point getEnd(){
      return end;
    }

    public boolean equals(Object o){
      if(o.getClass() != this.getClass()){
        return false;
      }else{
        Connection c = (Connection) o;
        return c.getStart().equals(this.getStart()) ||
            c.getStart().equals(this.getEnd()) ||
            c.getEnd().equals(this.getStart()) ||
            c.getStart().equals(this.getEnd());
      }
    }


  }


  //////////////////
  // FXML Methods //
  //////////////////

  // Initialization
  @FXML
  public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    addRandomNodes(400);
    initializeScene();
    initializeChoiceBox();
    initializeMapImage();
    initializeVisualNodes();
    // Adds a circle to show where the mouse is on the map
    mapViewPane.getChildren().add(ca);
  }

  private void initializeVisualNodes(){
    // Load in some points
//    points = ...
    // Points are now in memory as arraylist
    // convert neighbors to connections

    // For every point, make its neighbors a connection if it doesn't already exist
    for(int i = 0; i < points.size(); i++){
      Point p = points.get(i);
      addVisualNodesForPoint(p);
    }
    // draw connections
    mapViewPane.getChildren().addAll(lines.values());
    // draw points
    mapViewPane.getChildren().addAll(circles.values());
  }

  private void addVisualNodesForPoint(Point p){
    // For every neighbor, turn it into a connection if it doesn't exist
    for(int j = 0; j < p.getNeighbors().size(); j++){
      Connection c = new Connection(p, p.getNeighbors().get(j));
      // If the connection doesn't exist, add it to the list
      if(!connections.contains(c)){
        connections.add(c);
        Coordinate start = new Coordinate(c.getStart().getXCoord(), c.getStart().getYCoord());
        Coordinate end = new Coordinate(c.getEnd().getXCoord(), c.getEnd().getYCoord());
        start = pixelToCoordinate(start);
        end = pixelToCoordinate(end);
        Line l = new Line(start.getX(), start.getY(), end.getX(), end.getY());
        l.setStroke(LINE_COLOR);
        l.setStrokeWidth(LINE_FILL*current_zoom_scale);
        lines.put(c, l);
      }
    }
    // Now, for every point, create a Circle
    Coordinate coord = pixelToCoordinate(new Coordinate(p.getXCoord(), p.getYCoord()));
    Circle c = new Circle(coord.getX(), coord.getY(), NODE_RADIUS*current_zoom_scale, NODE_COLOR);
    circles.put(p, c);
  }

  // Updates the points' and connections' to account for zoom
  private void updateVisualNodes(){
    ArrayList<Point> pts = new ArrayList<Point>();
    ArrayList<Connection> lns = new ArrayList<Connection>();
    for(Point p1 : circles.keySet()){
      pts.add(p1);
    }
    for(Connection c1 : lines.keySet()){
      lns.add(c1);
    }
    for(int i = 0; i < pts.size(); i++){
      Point p = pts.get(i);
      Coordinate coord = pixelToCoordinate(new Coordinate(p.getXCoord(), p.getYCoord()));
      Circle c = circles.get(p);
      c.setCenterX(coord.getX());
      c.setCenterY(coord.getY());
      c.setRadius(NODE_RADIUS*current_zoom_scale);
    }
    for(int i = 0; i < lns.size(); i++){
      Connection c = lns.get(i);
      Line l = lines.get(c);
      Point start = c.getStart();
      Point end = c.getEnd();
      Coordinate startCoord = pixelToCoordinate(new Coordinate(start.getXCoord(), start.getYCoord()));
      Coordinate endCoord = pixelToCoordinate(new Coordinate(end.getXCoord(), end.getYCoord()));
      l.setStartX(startCoord.getX());
      l.setStartY(startCoord.getY());
      l.setEndX(endCoord.getX());
      l.setEndY(endCoord.getY());
      l.setStrokeWidth(LINE_FILL*current_zoom_scale);
    }
  }

  // Add listeners for resizing the screen
  private void initializeScene() {
    ChangeListener<Number> windowHeightListener = new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
          Number newSceneWidth) {
        bannerImage.setFitWidth((double) newSceneWidth);
        map_x_max = (double) newSceneWidth - infoPaneRectangle.getWidth();
        infoPane.setLayoutX((double) newSceneWidth - infoPaneRectangle.getWidth());
        adminPane.setLayoutX(infoPane.getLayoutX());
        titleBanner.setLayoutX(((double) newSceneWidth - titleBanner.getFitWidth()) / 2);
        fixMapDisplayLocation();
        fixZoomPanePos();
      }
    };
    ChangeListener<Number> windowWidthListener = new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
          Number newSceneHeight) {
        leftBar.setHeight((double) newSceneHeight);

        map_y_max = (double) newSceneHeight;
        infoPaneRectangle.setHeight(((double) newSceneHeight - bannerImage.getFitHeight()) / 2);
        adminPaneRectangle.setHeight(infoPaneRectangle.getHeight());
        adminPane.setLayoutY(infoPane.getLayoutY() + infoPaneRectangle.getHeight());
        fixMapDisplayLocation();
        fixZoomPanePos();
      }
    };

    anchorPane.widthProperty().addListener(windowHeightListener);
    anchorPane.heightProperty().addListener(windowWidthListener);
    if (mapViewFlag != 3) {
      adminPane.setVisible(false);
    }else{
      fixZoomPanePos();
    }
  }

  // Fixes the location of the zoom buttons and label, vertically and horizontally
  private void fixZoomPanePos() {
      setZoomPaneY(
          anchorPane.getHeight() - zoomPane.getPrefHeight() - ZOOM_PANE_OFFSET_VERTICAL);
      setZoomPaneX(
          anchorPane.getWidth() - zoomPane.getPrefWidth() - ZOOM_PANE_OFFSET_HORIZONTAL
              - adminPaneRectangle.getWidth() * (adminPane.isVisible() ? 1 : 0));
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
      Point newPoint = new Point(xCoord, yCoord, 4);
      if(point == null){
      } else {
        newPoint.connectTo(point);
      }
      newPoint.setFloor(4);
      point = newPoint;
      points.add(newPoint);
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
//            setPointFocus(null);
          }
        });
    floorChoiceBox.setValue(4);
  }

  private void initializeMapImage() {
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
    System.out.println("New Point button is currently disabled");
    /*
    double x = Double.parseDouble(xCoordField.getText());
    double y = Double.parseDouble(yCoordField.getText());
    int floor = Integer.parseInt(floorField.getText());
    String name = nameField.getText();
    Point newPoint = new Point(x, y, floor);
    addPoint(x, y, floor);
    */
  }

  @FXML
  public void deletePoint() {
    System.out.println("Delete button disabled.");
    /*
    System.out.println(pointFocus.getNeighbors());
    ArrayList<Point> neighbors = (ArrayList<Point>) pointFocus.getNeighbors().clone();
    for (int i = 0; i < neighbors.size(); i++) {
      pointFocus.getNeighbors().remove(neighbors.get(i));
      neighbors.get(i).getNeighbors().remove(pointFocus);
    }
    points.remove(pointFocus);
    setPointFocus(null);
    */
  }

  @FXML
  public void updatePoint() {
    System.out.println("Update Point button is currently disabled.");
    /*
    double x = Double.parseDouble(xCoordField.getText());
    double y = Double.parseDouble(yCoordField.getText());
    int floor = Integer.parseInt(floorField.getText());
    String name = nameField.getText();
    pointFocus.setFloor(floor);
    pointFocus.setXCoord(x);
    pointFocus.setYCoord(y);
    pointFocus.setName(name);
    */
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
    mouseHovered(e); // TODO REMOVE ?
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
//        setPointFocus(getNearestPointWithinRadius(eventCoord, CLICK_SELECTION_RADIUS));
      }
    } else {
      // Drag, or initiate connection of a node
      mapPressedX = e.getSceneX();
      mapPressedY = e.getSceneY();
      difX = mapPressedX - mapViewPane.getLayoutX();
      difY = mapPressedY - mapViewPane.getLayoutY();
    }
  }

  @FXML
  private void mapReleased(MouseEvent e) {
    String buttonUsed = e.getButton().name();
    if(buttonUsed.equals("SECONDARY")){
      if(mapViewFlag == 3) {
        // Connect any node that qualifies
//        Point p = getNearestPointWithinRadius(new Coordinate(e.getSceneX(), e.getSceneY()),
//            CLICK_SELECTION_RADIUS);
//        if (p != null && pointFocus != null) {
//          p.connectTo(pointFocus);
//        }
      }
    }
    mapReleasedX = e.getSceneX();
    mapReleasedY = e.getSceneY();
  }

  @FXML
  public void zoomIn() {
    changeZoom(true);
  }

  @FXML
  public void zoomOut() {
    changeZoom(false);
  }

  @FXML
  private void clickMap(MouseEvent e) {
    String buttonUsed = e.getButton().name();
//    Coordinate c = new Coordinate(e.getX(), e.getY());
//    c = coordinateToPixel(c);
//    System.out.println("X: " + c.getX() + " Y: " + c.getY());
    if(!mouseDragged) {
      if(buttonUsed.equals("SECONDARY")){

      } else {
        if (mapViewFlag == 3) {

        }
      }
    }
  }


  private final Color pa = new Color(0.5,0.5,1,0.5);
  Circle ca = new Circle(0,0,25, pa);
  @FXML
  private void mouseHovered(MouseEvent e){
    ca.setCenterX(e.getX());
    ca.setCenterY(e.getY());
  }

  // "scrolled" means the scroll wheel. This method controls zooming with the scroll wheel.
  // It's also referenced for zooming with buttons.
  @FXML
  private void mapScrolled(ScrollEvent e) {
    changeZoom(e.getDeltaY() > 0);
    // Then update the tracking for cursor location vs image location
    // Prevents odd behavior when dragging and scrolling simultaneously
    if(mapViewPane.isPressed()){ // only if it's pressed to increase efficiency
      mapPressedX = e.getSceneX();
      mapPressedY = e.getSceneY();
      difX = mapPressedX - mapViewPane.getLayoutX();
      difY = mapPressedY - mapViewPane.getLayoutY();
    }
  }

  /*
  private void setPointFocus(Point newFocus){
    pointFocus = newFocus;
    if(newFocus != null) {
      xCoordField.setText("" + newFocus.getXCoord());
      yCoordField.setText("" + pointFocus.getYCoord());
      floorField.setText("" + newFocus.getFloor());
      nameField.setText(newFocus.getName());
    }
  }
*/
  @FXML
  ChoiceBox startNodeBox; // TODO (re?)move these
  @FXML
  ChoiceBox endNodeBox;

  @FXML
  public void drawPath(){
    System.out.println("Drawing the path is currently disabled.");
    /*
    DataController controller = new DataController(null); // TODO shouldn't be needed
    try{
      points = controller.Astar((Point) startNodeBox.getValue(), (Point) endNodeBox.getValue()).getPoints();
//      ObservableList<javafx.scene.Node> children = ((AnchorPane) mapImage.getParent()).getChildren();
//      children.removeAll(currentlyDrawnNodes);
    } catch (Exception e) {

    }
    */

  }



  ///////////////////////////
  // Scene Control Methods //
  ///////////////////////////

  // Changes the location of the map, such that it is within the maintained bounds
  private void moveMapImage(double x, double y) {
    mapViewPane.setLayoutX(x);
    mapViewPane.setLayoutY(y);
    fixMapDisplayLocation();
  }

  private void fixMapDisplayLocation(){
    // Make sure that the top of the map is above the minimum
    boolean isAbove = mapViewPane.getLayoutY() < map_y_min;
    // Make sure that the bottom of the map is below the maximum
    boolean isBelow = (mapViewPane.getLayoutY() + mapImage.getFitHeight()) > map_y_max;
    // Make sure that the left of the map is to the left of the minimum
    boolean isLeft = (mapViewPane.getLayoutX()) < map_x_min;
    // Make sure that the right of the map is to the right of the maximum
    boolean isRight = (mapViewPane.getLayoutX() + mapImage.getFitWidth()) > map_x_max;
    // Make the assertions, move the map
    if (isAbove && isBelow) {
     // mapViewPane.setLayoutY(mapViewPane.getLayoutY());
    } else if (!isAbove && isBelow) {
      mapViewPane.setLayoutY(map_y_min);
    } else if (isAbove && !isBelow) {
      mapViewPane.setLayoutY(map_y_max - mapImage.getFitHeight());
    } else {
      // The map is too small, not sure what to do.
    }
    if (isLeft && isRight) {
     // mapViewPane.setLayoutX(mapViewPane.getLayoutX());
    } else if (!isLeft && isRight) {
      mapViewPane.setLayoutX(map_x_min);
    } else if (isLeft && !isRight) {
      mapViewPane.setLayoutX(map_x_max - mapImage.getFitWidth());
    } else {
      // The map is too small, not sure what to do.
    }
  }

  // Takes a point in the scene and returns the pixel on the map that corresponds
  public Coordinate coordinateToPixel(Coordinate p) {
    double xRelToMapOrigin = p.getX();
    double yRelToMapOrigin = p.getY();
    return new Coordinate(xRelToMapOrigin/current_zoom_scale, yRelToMapOrigin/current_zoom_scale);

  }

  // Takes the pixel on the map image and returns the position in the scene where it corresponds to.
  // Useful for drawing
  public Coordinate pixelToCoordinate(Coordinate p) {
    double actualX = p.getX() * current_zoom_scale;
    double actualY = p.getY() * current_zoom_scale;
    return new Coordinate(actualX, actualY);
  }

  // Either zooms in or out
  // if delta is true, the zoom increases
  // if delta is false, the zoom decreases
  // Also distributes spacing around the image, 50% of the difference in size on each side
  private void changeZoom(boolean delta) {
    // Find the old dimensions, will be used for making the zooming look better
    double oldWidth = mapImage.getFitWidth();
    double oldHeight = mapImage.getFitHeight();

    // How to change zoom in one line
    current_zoom = (delta ?
        (current_zoom >= ZOOM_MAX ? ZOOM_MAX :
            current_zoom + 1 + ((int) (0 * (current_zoom_scale *=(1 + ZOOM_COEF))))) : // Zoom in
        (current_zoom <= ZOOM_MIN ? ZOOM_MIN :
            current_zoom - 1 + (int) (0 * (current_zoom_scale /= (1 + ZOOM_COEF))))); // Zoom out
    mapImage.setFitWidth(current_zoom_scale*mapImage.getImage().getWidth());
    mapImage.setFitHeight(current_zoom_scale*mapImage.getImage().getHeight());


    // Find the new dimensions
    double newWidth = mapImage.getFitWidth();
    double newHeight = mapImage.getFitHeight();
    // Move the image so that the zoom appears to be in the center of the image
    moveMapImage(mapViewPane.getLayoutX() - (newWidth - oldWidth) / 2,
        mapViewPane.getLayoutY() - (newHeight - oldHeight) / 2);
    fixMapDisplayLocation();
    updateVisualNodes();
  }




}