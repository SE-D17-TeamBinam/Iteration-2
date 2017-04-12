package org;

import Database.FakePhysician;
import Database.FakePoint;
import Definitions.Physician;
import java.util.ArrayList;

/**
 * @author ajanagal and aramirez2
 * @since 1.0
 */

/**
 * The purpose of this class is to store the values from the database,
 * and to make pathfinding easier.
 */
public class Point {

  int xCoord;    //X coordinate
  int yCoord;    //Y coordinate
  String name;  //Name of the room
  int id;      //Unique Identifier
  int floor;
  public ArrayList<Point> neighbors = new ArrayList<>();
  //Attributes For A* only below.
  Point parent;
  int cost;

  //Constructor
  public Point(double xCoord, double yCoord, String name) {
    this.xCoord = (int) xCoord;
    this.yCoord = (int) yCoord;
    this.name = name;
  }

  public Point(double xCoord, double yCoord, int floor) {
    this.xCoord = (int) xCoord;
    this.yCoord = (int) yCoord;
    this.floor = floor;
  }

  public Point(int xCoord, int yCoord, String name, int id, ArrayList<Point> new_neighbors,
      int floor) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.name = name;
    this.id = id;
    this.parent = null;
    this.neighbors = new_neighbors;
    this.cost = 0;
    this.floor = floor;
  }

  //Methods
  public void connectTo(Point node) {
    node.getNeighbors().add(this);
    this.neighbors.add(node);
  }

  public void severFrom(Point point) {
    if (this.neighbors.contains(point)) {
      point.getNeighbors().remove(this);
      this.neighbors.remove(point);
    }
  }

  public ArrayList<Point> getNeighbors() {
    return neighbors;
  }

  public void addParent(Point padre) {
    this.parent = padre;
  }

  public void setName(String newName) {
    name = newName;
  }

  public String getName() {
    return name;
  }

  public void setFloor(int floor) {
    this.floor = floor;
  }

  public int getId() {
    return id;
  }

  public int getCost() {
    return cost;
  }

  public Point getParent() {
    return parent;
  }

  public int getFloor() {
    return floor;
  }

  public void setXCoord(double xCoord) {
    this.xCoord = (int) xCoord;
  }

  public int getXCoord() {
    return this.xCoord;
  }

  public int getYCoord() {
    return this.yCoord;
  }

  public void setYCoord(double yCoord) {
    this.yCoord = (int) yCoord;
  }

  /**Heurstic will give the manhattan straight line distance from one point to another
   * <p>
   *   it does the distance formula dist =difference of x  and difference of y
   * </p>
   * @param End
   * @return int The return will be the manhattan line distance
   */
  public int Heuristic(Point End) {
    int x = Math.abs(this.xCoord - End.xCoord);
    int y = Math.abs(this.yCoord - End.yCoord);
    return x + y;
  }

  /**
   * Distance will find the straight line distance from one point to another
   * <p>
   *   it does the distance formula dist = sqrt(diffence of x squared and difference of y squared)
   * </p>
   * @param End
   * @return int The return will be the straight line distance
   */
  public int Distance(Point End) {//Straight Line Distance
    double x = End.xCoord - this.xCoord;
    double y = End.yCoord - this.yCoord;
    return (int) Math.sqrt(x * x + y * y);
  }

  public void setID(int ID) {
    this.id = ID;
  }

  public boolean isElevator() {
    return false;
  }

  public boolean isStair() {
    return false;
  }

  public boolean compareTo(Point p2) {
    if (this.getName().equals(p2.getName()) && this.getCost() == p2.getCost() &&
        this.getFloor() == p2.getFloor() && this.getId() == p2.getId() &&
        this.getXCoord() == p2.getXCoord() && this.getYCoord() == p2.getYCoord()) {

      FakePoint p3 = new FakePoint(this);
      FakePoint p4 = new FakePoint(p2);
      for (int k = 0; k < p3.getNeighbors().size(); k++) {
        if (!(p4.getNeighbors().contains(p3.getNeighbors().get(k)))) {
          System.out.println("Neighbor " + p4.getNeighbors().get(k) + " not in the other");
          return false;
        }
      }
    } else {
      System.out.println("verification failed a field is different-point");
      return false;
    }

    return true;
  }

}

