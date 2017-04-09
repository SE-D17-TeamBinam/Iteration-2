package UIControllers;

import Definitions.Coordinate;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
import javax.sound.midi.Soundbank;
import javax.swing.Action;
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

  // Admin Pane fields
  @FXML
  private TextField xCoordField;
  @FXML
  private TextField yCoordField;
  @FXML
  private TextField floorField;
  @FXML
  private TextField nameField;

  // User Pane fields
  @FXML
  private Label floorLabel;
  @FXML
  private Label xLabel;
  @FXML
  private Label yLabel;
  @FXML
  private Label nameLabel;

  // The pane with the + and - on it
  @FXML
  private Pane zoomPane;
  private final double ZOOM_PANE_OFFSET_HORIZONTAL = 20;
  private final double ZOOM_PANE_OFFSET_VERTICAL = 20;

  // Controls how much the zoom changes at a time.
  private final double ZOOM_COEF = 0.05;
  private final int ZOOM_MIN = -20;
  private final int ZOOM_MAX = 20;
  private int current_zoom = 0;
  private double current_zoom_scale = 1;

  // Map Image positioning boundaries
  // the furthest to the right that the left side of the map image may move
  private double map_x_min = 150;
  // the furthest to the left that the right side of the map image may move
  private double map_x_max = 1300;
  // the furthest down that the top of the map image may move
  private double map_y_min = 160;
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


  // TODO add default zoom/position for map, also make default floor = 1

  ////////////////////////
  // Administrator Data //
  ////////////////////////

  // ArrayList of Points to maintain in memory
  private ArrayList<Point> points = new ArrayList<org.Point>();
  // ArrayList of Edges to help track for drawing
  private ArrayList<Connection> connections = new ArrayList<Connection>();

  // The currently selected point
  private Point pointFocus = null;


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

    public String toString(){
      return start.toString() + "-" +  end.toString();
    }

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

    @Override
    public boolean equals(Object o){
      if(o.getClass() != this.getClass()){
        return false;
      }else{
        Connection c = (Connection) o;
        boolean b = ((c.getStart().equals(this.getStart()) && c.getEnd().equals(this.getEnd())) ||
            (c.getEnd().equals(this.getStart()) && c.getStart().equals(this.getEnd())));
        return b;
      }
    }

    // Turns out it takes 3 hours of debugging to figure out that hashmap uses hash codes
    // TODO
    @Override
    public int hashCode(){
      // I crie
      // Chances are this won't result in repeats.
      return start.hashCode() + end.hashCode();
    }


  }


  //////////////////
  // FXML Methods //
  //////////////////

  // Initialization
  @FXML
  public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    addRandomNodes(300);
    initializeScene();
    initializeChoiceBox();
    initializeMapImage();
    initializeVisualNodes();
    // Adds a circle to show where the mouse is on the map
  }

  private void initializeVisualNodes(){
    // TODO Load in some points
//    points = ...
    // Points are now in memory as arraylist
    // convert neighbors to connections

    // For every point, make its neighbors a connection if it doesn't already exist
    for(int i = 0; i < points.size(); i++){
      Point p = points.get(i);
      addVisualNodesForPoint(p);
    }

  }

  private void addVisualNodesForPoint(Point p){
    // For every neighbor, turn it into a connection if it doesn't exist
    for(int j = 0; j < p.getNeighbors().size(); j++){
      Connection c = new Connection(p, p.getNeighbors().get(j));
        addVisualConnection(c);

    }
    // Now, for every point, create a Circle
    Coordinate coord = pixelToCoordinate(new Coordinate(p.getXCoord(), p.getYCoord()));
    Circle c = new Circle(coord.getX(), coord.getY(), NODE_RADIUS*current_zoom_scale, NODE_COLOR);
    circles.put(p, c);
    mapViewPane.getChildren().add(c);
    addCircleListeners(c, p);
  }

  private void addVisualPoint(Point p){

  }

  private void addVisualConnection(Connection c){
    if(!connections.contains(c)) {
      connections.add(c);
      Line l = new Line();
      lines.put(c, l);
      updateLineForConnection(c);
      l.setMouseTransparent(true);
      // ensures that lines will always be drawn behind points
      mapViewPane.getChildren().add(1, l);
    }
  }

  // Updates the points' and connections' to account for zoom
  private void updateVisualNodes(){
    for(Point p1 : circles.keySet()){
      updateCircleForPoint(p1);
    }
    for(Connection c1 : lines.keySet()){
      updateLineForConnection(c1);
    }
  }

  private void updateLinesForPoint(Point p){
    ArrayList<Point> neighbors = p.getNeighbors();
    for(int i = 0; i < neighbors.size(); i++){
      Connection c = new Connection(p, neighbors.get(i));
      updateLineForConnection(c);
    }
  }

  private void updateLineForConnection(Connection c){
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

  private void updateCircleForPoint(Point p){
    Coordinate coord = pixelToCoordinate(new Coordinate(p.getXCoord(), p.getYCoord()));
    Circle c = circles.get(p);
    c.setCenterX(coord.getX());
    c.setCenterY(coord.getY());
    c.setRadius(NODE_RADIUS*current_zoom_scale);
  }

  private void removeVisualConnection(Connection c){
    mapViewPane.getChildren().remove(lines.get(c));
    connections.remove(c);
    lines.remove(c);
  }

  private void addCircleListeners(Circle c, Point p){
    c.setCursor(Cursor.HAND);
    c.setOnMouseClicked(e -> circleMouseClicked(e, p));
    c.setOnMouseDragged(e -> circleMouseDragged(e, p));
    c.setOnMousePressed(e -> circleMousePressed(e, p));
    c.setOnMouseReleased(e -> circleMouseReleased(e, p));
    c.setOnMouseDragReleased(e -> circleMouseDragReleased(e, p));

    c.setOnMouseEntered(e -> circleMouseEntered(e, p));
  }
  private void circleMouseEntered(MouseEvent e, Point p) {
//    System.out.println("Mouse entered");

  }
  private void circleMousePressed(MouseEvent e, Point p) {
//    System.out.println("Mouse entered");
    mouseDragged = false;
  }
  private void circleMouseClicked(MouseEvent e, Point p) {
    if(!mouseDragged) { // if it was dragged, then it's not a click
    String button = e.getButton().toString();
      if (button.equals("PRIMARY")) {
        setPointFocus(p);
      } else if (button.equals("SECONDARY")) {
        if (e.isShiftDown()) {
          if (mapViewFlag > 2) {
            // Ensure that it does not get connected to itself or a repeat connection
            if (!p.equals(pointFocus) && !p.getNeighbors().contains(pointFocus)) {
              p.connectTo(pointFocus);
              addVisualConnection(new Connection(p, pointFocus));
            }
          }
        }
      }
    }

  }
  private void circleMouseDragged(MouseEvent e, Point p) {
//    System.out.println("Mouse dragged");
    mouseDragged = true;
    String button = e.getButton().toString();
    if(button.equals("PRIMARY")){
    if(mapViewFlag > 2) {
      Coordinate c = coordinateToPixel(new Coordinate(e.getX(), e.getY()));
      movePoint(p, c);

    }
    }else if(button.equals("SECONDARY")){

    }
  }

  private void movePoint(Point p, Coordinate c){
    if(c.getX() > mapImage.getImage().getWidth()){
      c.setX(mapImage.getImage().getWidth());
    }else if(c.getX() < 0){
      c.setX(0);
    }
    if(c.getY() > mapImage.getImage().getHeight()){
      c.setY(mapImage.getImage().getHeight());
    }else if(c.getY() < 0){
      c.setY(0);
    }
    p.setXCoord(c.getX());
    p.setYCoord(c.getY());
    updateCircleForPoint(p);
    updateLinesForPoint(p);
  }

  private void circleMouseReleased(MouseEvent e, Point p) {
//    System.out.println("Mouse released");

  }
  private void circleMouseDragReleased(MouseEvent e, Point p) {
//    System.out.println("Mouse drag released");

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
      newPoint.setName("");
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
  }

  @FXML
  private void saveMapButtonClicked(){
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
  public void newButtonClicked() {
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
  private void xCoordFieldKeyTyped(KeyEvent e){
    if(!Character.isDigit(e.getCharacter().charAt(0))){
      e.consume(); // throws out the KeyEvent before it can reach the text field
    }
  }

  @FXML
  private void yCoordFieldKeyTyped(KeyEvent e){
    if(!Character.isDigit(e.getCharacter().charAt(0))){
      e.consume(); // throws out the KeyEvent before it can reach the text field
    }
  }

  @FXML
  private void floorFieldKeyTyped(KeyEvent e){
    if(!Character.isDigit(e.getCharacter().charAt(0))){
      e.consume(); // throws out the KeyEvent before it can reach the text field
    }
  }

  @FXML
  private void nameFieldKeyTyped(KeyEvent e){

  }

  @FXML
  public void deleteButtonClicked() {
    // Clone the neighbors so that data isn't lost when a neighbor is removed
    ArrayList<Point> neighbors = (ArrayList<Point>) pointFocus.getNeighbors().clone();
    for(Point p : neighbors){
      System.out.println("Neighbors' neighbors: " + p.getNeighbors());
      Connection c = new Connection(pointFocus, p);
      p.getNeighbors().remove(pointFocus);
      pointFocus.getNeighbors().remove(p);
      removeVisualConnection(c);
    }
    mapViewPane.getChildren().remove(circles.get(pointFocus));
    points.remove(pointFocus);
    circles.remove(pointFocus);
    setPointFocus(null);
  }



  @FXML
  public void updateSelectedButtonClicked() {
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
  public void backButtonClicked() {
    Stage primaryStage = (Stage) floorChoiceBox.getScene().getWindow();
    try {
      restartUI(primaryStage);
    } catch (Exception e) {
      System.out.println("Cannot load main menu");
      e.printStackTrace();
    }
  }


  @FXML
  private void mapMouseDragged(MouseEvent e) {
    mapMouseMoved(e); // TODO REMOVE ?
    String buttonUsed = e.getButton().name();
    mouseDragged = true;
    if (buttonUsed.equals("SECONDARY")) {

    } else {
      mapImage.setCursor(Cursor.CLOSED_HAND);
      double newX = e.getSceneX() - difX;
      double newY = e.getSceneY() - difY;
      moveMapImage(newX, newY);
    }
  }

  public Point getNearestPointWithinRadius(Coordinate coord, double radius){
    Point closestPoint = null;
    double closestDistance = Double.MAX_VALUE;
    for(int i = 0; i < points.size(); i++){
      Point curP = points.get(i);
      Coordinate curPos = new Coordinate(curP.getXCoord(), curP.getYCoord());
      double curDist = curPos.distanceTo(coord);
      if(curDist < closestDistance && curDist < radius){
        closestPoint = curP;
        closestDistance = curDist;
      }
    }
    return closestPoint;
  }

  @FXML
  private void mapMousePressed(MouseEvent e) {
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
      difX = mapPressedX - mapViewPane.getLayoutX();
      difY = mapPressedY - mapViewPane.getLayoutY();
    }
  }

  @FXML
  private void mapMouseReleased(MouseEvent e) {
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
    mapImage.setCursor(Cursor.DEFAULT);
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
  private void mapMouseClicked(MouseEvent e) {
    String buttonUsed = e.getButton().name();
//    Coordinate c = new Coordinate(e.getX(), e.getY());
//    c = coordinateToPixel(c);
//    System.out.println("X: " + c.getX() + " Y: " + c.getY());
    if(!mouseDragged) {
      if(buttonUsed.equals("SECONDARY")){

      } else {
        if (mapViewFlag == 3) {
          if(e.isShiftDown()) {
            Coordinate c = coordinateToPixel(new Coordinate(e.getX(), e.getY()));
            Point p = new Point(c.getX(), c.getY(), (int) floorChoiceBox.getValue());
            points.add(p);
            addVisualNodesForPoint(p);
            setPointFocus(p);
          }else{
            setPointFocus(null);
          }
        }
      }
    }
  }

  @FXML
  private void mapMouseMoved(MouseEvent e){
  }

  // "scrolled" means the scroll wheel. This method controls zooming with the scroll wheel.
  // It's also referenced for zooming with buttons.
  @FXML
  private void mapMouseScrolled(ScrollEvent e) {
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


  private void setPointFocus(Point newFocus){
    pointFocus = newFocus;
    String xText = "";
    String yText = "";
    String floorText = "";
    String nameText = "";
    if(newFocus != null) {
      xText = "" + pointFocus.getXCoord();
      yText = "" + pointFocus.getYCoord();
      floorText = "" + pointFocus.getFloor();
      nameText = pointFocus.getName();
    }else{
      mapViewPane.requestFocus();
    }
    if(mapViewFlag > 2) {
      xCoordField.setText("" + xText);
      yCoordField.setText("" + yText);
      floorField.setText("" + floorText);
      nameField.setText(nameText);
      if(nameText == "" && newFocus != null){
        nameField.requestFocus();
      }
    }
    xLabel.setText("X Pos: " + xText);
    yLabel.setText("Y Pos: " + yText);
    floorLabel.setText("Floor: " + floorText);
    nameLabel.setText("Room Name: " + nameText);
  }

  @FXML
  ChoiceBox startNodeBox; // TODO (re?)move these
  @FXML
  ChoiceBox endNodeBox;

  @FXML
  public void drawPathButtonClicked(){
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
    // ImageView
    // Image
    mapImage.getImage().getWidth();
    mapImage.getFitWidth();
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
    updateMapScale(delta);
    fixMapDisplayLocation();
    updateVisualNodes();
  }

  private void updateMapScale(boolean delta){
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
  }

  private void centerMapPixelAtCoordinate(Coordinate pixel, Coordinate target){
    moveMapImage(mapViewPane.getLayoutX() + target.getX() - pixel.getX(),
        mapViewPane.getLayoutY() + target.getY() - pixel.getY());
  }
}