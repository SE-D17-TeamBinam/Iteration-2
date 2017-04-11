package org;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author  ajanagal and aramirez2
 *
 * @since 1.0
 */

/**
 * This class will store lists of Points to deliver to the controller.
 */
public class ListPoints {
  // Used for generation unique IDS
  private ArrayList<Point> points;  //List of Nodes that relate to each other

  //Constructor
  public ListPoints(ArrayList<Point> points){
    this.points = points;
  }

  //Methods
  private void sort(){}

  //Helpers
  public ArrayList<Point> getPoints(){
    return this.points;
  } //Getter

  public void include(Point n){
    this.points.add(n);  //adds node to ArrayList Nodes
  }

  /**
   * Generates a clone of this ListPoints with no identical references
   * Will be used for copying points so that pasting does not break the program
   * Does not duplicate neighbors that are not selected
   * @return a clone of this ListPoints with no identical references
   */
  public ListPoints deepClone(){
    HashMap<Point, Point> newPoints = new HashMap<Point, Point>();
    for(Point p : points){
      newPoints.put(p, new Point(p.getXCoord(), p.getYCoord(), p.getName(), generateUniqueID(), new ArrayList<Point>(), p.getFloor()));
    }
    for(Point p : points){
      Point p2 = newPoints.get(p);
      for(Point pN : p.getNeighbors()){
        Point newNeighbor = newPoints.get(pN);
        if(newNeighbor != null) {
          p2.connectTo(newPoints.get(pN));
        }
      }
    }
    ArrayList<Point> out = new ArrayList<Point>();
    for(Point p : newPoints.values()){
      out.add(p);
    }
    return new ListPoints(out);
  }

  /**
   * Attempts to generate a unique ID
   * This method should probably be rewritten more reliably, as this could theoretically repeat
   * @return a unique ID as an integer
   */
  public int generateUniqueID(){
    return (int) Math.random()*Integer.MAX_VALUE;
  }

  /**
   * Searches through this ListPoints and returns any points on the given floor
   * @param floor the floor to search for
   * @return a ListPoints of the points on the requested floor
   */
  public ListPoints getFloor(int floor){
    ArrayList<Point> out = new ArrayList<Point>();
    for(Point p : this.getPoints()){
      if(p.getFloor() == floor){
        out.add(p);
      }else{
        for(Point p2 : out){
//          p2.severFrom(p);
        }
      }
    }
    return new ListPoints(out);
  }


  /**
   * gridCreate creates a grid based on width and height
   *
   * <p>
   *   gridCreate creates a grid based on a size of width and height, all of the adjacent nodes are
   *   connected to each other ( the 4 neighbors).
   * </p>
   * @param width
   * @param height
   * @param floor
   * @return
   */

  public ArrayList<Point> gridCreate(int width, int height, int floor){
    int x = 0;
    int y = 0;
    ArrayList<Point> p = new ArrayList<Point>();

    for(int i = 0; i < width*height; i++) {//creates Points
      x = i % width;
      y = i / width;

      Point c = new Point(x, y, "", i, new ArrayList<Point>(), floor);
      p.add(i, c);
    }

    for (int i = 0; i < width*height; i++){
      Point p1, p2, p3, p4 = null;
      x = i % width;
      y = i / width;

      if(y > 0){
        p1 = p.get(i-width);
        p.get(i).neighbors.add(p1);
      }
      if(x < (width-1)){
        p2 = p.get(i+1);
        p.get(i).neighbors.add(p2);
      }
      if(y < (height-1)){
        p3 = p.get(i+width);
        p.get(i).neighbors.add(p3);
      }
      if(x > 0){
        p4 = p.get(i-1);
        p.get(i).neighbors.add(p4);
      }
    }
    return p;
  }

  /**
   *  ListPath will create a path from the A* algorithm
   *  <p>
   *    It creates a ListPoints classs which will hold the arraylist of the path from
   *    one direction to another.
   *  </p>
   *
   * @param destination     This is usually the goal from A*
   * @param begin           This is the start of A*
   * @return                A list of points that represent a path from goal to start
   */

  public ArrayList<Point> ListPath(Point destination, Point begin){    //ceate a path list by reading parents
    ArrayList<Point> order = new ArrayList<Point>();


    while(destination != begin){  //while destination is not start,
      // since start should not have a parent
      order.add(destination);
      destination = destination.parent; //set the parent as new destination and try again.
      //Add destination to path list
    }
    order.add(destination);  //add "start" to listpath
    return order;
  }

  /**
   * This is the A* algorithm to find the most efficient path
   * <p>
   *   A* is designed so that it finds the most cost efficient path. Now with multiple floors based
   *   on elevators and stairs
   * </p>
   * @param   start  starting point for the A* algorithm
   * @param   goal   desired location
   * @return  ListPoint it returns a class that contains the ArrayList of nodes generated from A*
   */
  public ArrayList<Point> Astar (Point start, Point goal) throws NoPathException{
    boolean changeFloor = start.floor != goal.floor;

    Point next = new Point(500,500,"start",0, new ArrayList<Point>(),4);
    start.parent = start;
    start.cost = 0;
    ArrayList<Point> open = new ArrayList<Point>(); //List of nodes that are seen but not checked
    ArrayList<Point> close = new ArrayList<Point>(); //List of nodes that are seen and checked
    int finding_lowest = 0; // helps find lowest total cost in open
    open.add(start);      //adds to open

    while(!(open.isEmpty())){
      int total = 10000; // comparing to function.
      int i;
      for(i = 0; i < open.size(); i++){ //finds the lowest total in the open
        if(total > (open.get(i).cost + open.get(i).Distance(goal))) {
          total = open.get(i).cost + open.get(i).Distance(goal);
          finding_lowest = i;
        }
      }
      if(open.get(finding_lowest) == goal){ //found path
        return ListPath(open.get(finding_lowest), start);
      }
      next = open.get(finding_lowest); //stores next option as next
      open.remove(next);
      int currFloor = next.floor; //current floor

      if(next.neighbors.size() == 0){
        throw new NoPathException();
      }

      for (int j = 0; j < next.neighbors.size(); j++ ){ //searching through neighbors

        if(open.contains(next.neighbors.get(j))){ // visited but seen
          if (next.neighbors.get(j).cost < next.cost){ // successor cost <= current cost

            next.neighbors.get(j).cost = next.cost + 1; //update cost
            next.neighbors.get(j).parent = next;  //update parent
            break;
          }
        }else if(close.contains(next.neighbors.get(j))){ //visited and seen sucessor
          //Dont Update!!!!
        }else{// new to search
          if(next.neighbors.get(j) instanceof ElevatorPoint ){ //meets an elevator and changes node
            if(changeFloor) {
              next.neighbors.get(j).cost = next.cost + 1; //update cost
              next.neighbors.get(j).parent = next;  //update parent

              ElevatorPoint elevator = (ElevatorPoint) next.neighbors.get(j); //cast to elevator
              close.add(elevator);
              //check to see if it can make it to desired floor
              if (elevator.canAchieveFloor(goal.floor)) {
                int count = 0;
                int move = goal.floor - elevator.floor; //if it needs to go higher or lower
                while (elevator.floor != goal.floor) {
                  if (move < 0 && (elevator.neighbors.get(count).floor - elevator.floor
                      == -1)) { // goal is below current floor & neighbor is next elevator below
                    elevator.neighbors.get(count).cost = next.cost + 1; //update cost
                    elevator.neighbors.get(count).parent = elevator;  //update parent

                    elevator = (ElevatorPoint) elevator.neighbors.get(count);
                    count = 0;
                  } else if ((move > 0 && (elevator.neighbors.get(count).floor - elevator.floor
                      == 1))) {// goal is above and neighbor goes higher
                    elevator.neighbors.get(count).cost = next.cost + 1; //update cost
                    elevator.neighbors.get(count).parent = elevator;  //update parent

                    elevator = (ElevatorPoint) elevator.neighbors.get(count);
                    count = 0;
                  } else { //does nothing
                    count++;
                  }
                }
                open.add(elevator);
                changeFloor = false;

              }
            }
          }
          else if(next.neighbors.get(j) instanceof StairPoint){ //meets an elevator and changes node
            if(changeFloor) {
              next.neighbors.get(j).cost = next.cost + 1; //update cost
              next.neighbors.get(j).parent = next;  //update parent

              StairPoint stair = (StairPoint) next.neighbors.get(j); //cast to elevator
              //check to see if it can make it to desired floor
              close.add(stair);
              if (stair.canAchieveFloor(goal.floor)) {
                int count = 0;
                int move = goal.floor - stair.floor; //if it needs to go higher or lower
                while (stair.floor != goal.floor) {
                  if (move < 0 && (stair.neighbors.get(count).floor - stair.floor
                      == -1)) { // goal is below current floor & neighbor is next elevator below
                    stair.neighbors.get(count).cost = next.cost + 1; //update cost
                    stair.neighbors.get(count).parent = stair;  //update parent

                    stair = (StairPoint) stair.neighbors.get(count);
                    count = 0;
                  } else if ((move > 0 && (stair.neighbors.get(count).floor - stair.floor
                      == 1))) {// goal is above and neighbor goes higher
                    stair.neighbors.get(count).cost = next.cost + 1; //update cost
                    stair.neighbors.get(count).parent = stair;  //update parent

                    stair = (StairPoint) stair.neighbors.get(count);
                    count = 0;
                  } else { //does nothing
                    count++;
                  }
                }
                open.add(stair);
                changeFloor = false;
              }
            }
          }
//          else if (next.neighbors.get(j).floor != start.floor && next.neighbors.get(j).floor != goal.floor){
//            // if floor is neither on start or goal's floor
//
//            //dont do anything so the wrong floor points wont be added....
//          }
          else { // right floor(s).
            open.add(next.neighbors.get(j));    //add sucessor to open
            next.neighbors.get(j).cost = next.cost + 1; //update cost
            next.neighbors.get(j).parent = next;  //update parent
          }
        }
      }
      close.add(next);      //add current to close
    }

    throw new NoPathException();  //throw error
  }

  /**
   * gridCreate creates a grid based on width and height
   *
   * <p>
   *   gridCreate creates a grid based on a size of width and height, all of the adjacent nodes are
   *   connected to each other ( the 4 neighbors).
   * </p>
   * @param width
   * @param length
   * @param height
   * @return
   */
  public ArrayList<ArrayList<Point>> grid3dCreate(int width, int length, int height){
    ArrayList<ArrayList<Point>> p = new ArrayList< ArrayList<Point>>();

    for(int i = 1; i < height+1; i++) {//creates Points

      p.add(gridCreate(width,length,i));
    }
    return p;
  }
}
