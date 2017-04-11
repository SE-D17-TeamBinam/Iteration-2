import java.util.ArrayList;
import org.ElevatorPoint;
import org.ListPoints;
import org.NoPathException;
import org.Point;
import org.StairPoint;
import org.VerticalPoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Alberto on 4/8/2017.
 */
/**
 * The purpose of this class is to take test functions in the listPoints class
 *
 */
public class ListPointsTest { //tests Astar for one floor, all floor, and error case- not on floor
  ListPoints test = new ListPoints(new ArrayList<Point>());
  ArrayList<ArrayList<Point>> floor = new ArrayList<ArrayList<Point>>();


  @Test
  void AstarMultifloorElevatorTest(){ // tests to see if it doesnt touch any other floors
    ArrayList<ArrayList<Point>> floor = test.grid3dCreate(5,5,3);
    Point node1 = floor.get(0).get(4);
    Point node2 = floor.get(1).get(4);
    Point node3 = floor.get(2).get(4);
    ElevatorPoint elevator1 = new ElevatorPoint(node1.getXCoord(), node1.getYCoord(), node1.getName(),node1.getId(),
        node1.neighbors,node1.getFloor());
    ElevatorPoint elevator2 = new ElevatorPoint(node2.getXCoord(), node2.getYCoord(), node2.getName(),node2.getId(),
        node2.neighbors,node2.getFloor());
    ElevatorPoint elevator3 = new ElevatorPoint(node3.getXCoord(), node3.getYCoord(), node3.getName(),node3.getId(),
        node3.neighbors,node3.getFloor());


    elevator1.neighbors.remove(node2);
    elevator2.neighbors.remove(node1);
    elevator2.neighbors.remove(node3);
    elevator3.neighbors.remove(node2);
    elevator1.neighbors.add(elevator2);
    elevator2.neighbors.add(elevator3);
    elevator2.neighbors.add(elevator1);
    elevator3.neighbors.add(elevator2);


    floor.get(0).remove(4);
    floor.get(1).remove(4);
    floor.get(2).remove(4);

    floor.get(0).add(4,elevator1);
    floor.get(1).add(4,elevator2);
    floor.get(2).add(4,elevator3);

    floor.get(0).get(3).neighbors.remove(node1);
    floor.get(0).get(9).neighbors.remove(node1);
    floor.get(1).get(3).neighbors.remove(node2);
    floor.get(1).get(9).neighbors.remove(node2);
    floor.get(2).get(3).neighbors.remove(node3);
    floor.get(2).get(9).neighbors.remove(node3);

    floor.get(0).get(3).neighbors.add(elevator1);
    floor.get(0).get(9).neighbors.add(elevator1);
    floor.get(1).get(3).neighbors.add(elevator2);
    floor.get(1).get(9).neighbors.add(elevator2);
    floor.get(2).get(3).neighbors.add(elevator3);
    floor.get(2).get(9).neighbors.add(elevator3);


    Point start = floor.get(0).get(0); //Chose start and goal points and floors
    Point goal = floor.get(2).get(0);

    ArrayList<Point> path = new ArrayList<Point>();

    try {
      path = test.Astar(start,goal);
    } catch (Exception e) {
      assertTrue(false);
      e.printStackTrace();
    }

    for(int i = 0; i < path.size(); i++){
      if(path.get(i).getFloor() != start.getFloor() && path.get(i).getFloor() != goal.getFloor()){
        if (path.get(i) instanceof ElevatorPoint){

        }else {
          assertTrue(false);
        }
      }
    }
    assertTrue(true);
  }

  @Test
  void AstarMultifloorElevator2Test(){ // tests to see if it doesnt touch any other floors
    ArrayList<ArrayList<Point>> floor = test.grid3dCreate(5,5,3);
    Point node1 = floor.get(0).get(4);
    Point node2 = floor.get(1).get(4);
    Point node3 = floor.get(2).get(4);
    Point node4 = floor.get(0).get(3); //second elevator
    Point node5 = floor.get(1).get(3);

    ElevatorPoint elevator1 = new ElevatorPoint(node1.getXCoord(), node1.getYCoord(), node1.getName(),node1.getId(),
        node1.neighbors,node1.getFloor());
    ElevatorPoint elevator2 = new ElevatorPoint(node2.getXCoord(), node2.getYCoord(), node2.getName(),node2.getId(),
        node2.neighbors,node2.getFloor());
    ElevatorPoint elevator3 = new ElevatorPoint(node3.getXCoord(), node3.getYCoord(), node3.getName(),node3.getId(),
        node3.neighbors,node3.getFloor());
    ElevatorPoint elevator4 = new ElevatorPoint(node4.getXCoord(), node4.getYCoord(), node4.getName(),node4.getId(),
        node4.neighbors,node4.getFloor());
    ElevatorPoint elevator5 = new ElevatorPoint(node5.getXCoord(), node5.getYCoord(), node5.getName(),node5.getId(),
        node5.neighbors,node5.getFloor());

    elevator1.neighbors.remove(node2);
    elevator2.neighbors.remove(node1);
    elevator2.neighbors.remove(node3);
    elevator3.neighbors.remove(node2);
    elevator1.neighbors.add(elevator2);
    elevator2.neighbors.add(elevator3);
    elevator2.neighbors.add(elevator1);
    elevator3.neighbors.add(elevator2);
    elevator4.neighbors.remove(node5);
    elevator5.neighbors.remove(node4);
    elevator4.neighbors.add(elevator5);
    elevator5.neighbors.add(elevator4);


    floor.get(0).remove(4);
    floor.get(1).remove(4);
    floor.get(2).remove(4);
    floor.get(0).remove(3);
    floor.get(1).remove(3);

    floor.get(0).add(4,elevator1);
    floor.get(1).add(4,elevator2);
    floor.get(2).add(4,elevator3);
    floor.get(0).add(3,elevator4);
    floor.get(1).add(4,elevator5);

    floor.get(0).get(3).neighbors.remove(node1);
    floor.get(0).get(9).neighbors.remove(node1);
    floor.get(1).get(3).neighbors.remove(node2);
    floor.get(1).get(9).neighbors.remove(node2);
    floor.get(2).get(3).neighbors.remove(node3);
    floor.get(2).get(9).neighbors.remove(node3);
    floor.get(0).get(2).neighbors.remove(node4);
    floor.get(0).get(4).neighbors.remove(node4);
    floor.get(0).get(7).neighbors.remove(node4);
    floor.get(1).get(2).neighbors.remove(node5);
    floor.get(1).get(2).neighbors.remove(node5);
    floor.get(1).get(2).neighbors.remove(node5);

    floor.get(0).get(3).neighbors.add(elevator1);
    floor.get(0).get(9).neighbors.add(elevator1);
    floor.get(1).get(3).neighbors.add(elevator2);
    floor.get(1).get(9).neighbors.add(elevator2);
    floor.get(2).get(3).neighbors.add(elevator3);
    floor.get(2).get(9).neighbors.add(elevator3);
    floor.get(0).get(2).neighbors.add(elevator4);
    floor.get(0).get(4).neighbors.add(elevator4);
    floor.get(0).get(7).neighbors.add(elevator4);
    floor.get(1).get(2).neighbors.add(elevator5);
    floor.get(1).get(4).neighbors.add(elevator5);
    floor.get(1).get(7).neighbors.add(elevator5);


    Point start = floor.get(0).get(0); //Chose start and goal points and floors
    Point goal = floor.get(2).get(0);

    ArrayList<Point> path = new ArrayList<Point>();

    try {
      path = test.Astar(start,goal);
    } catch (Exception e) {
      assertTrue(false);
      e.printStackTrace();
    }

    for(int i = 0; i < path.size(); i++){
      if(path.get(i).getFloor() != start.getFloor() && path.get(i).getFloor() != goal.getFloor()){
        if (path.get(i) instanceof ElevatorPoint){

        }else {
          assertTrue(false);
        }
      }
    }
    assertTrue(true);
  }

  @Test
  void AstarMultifloorStairTest(){ // tests to see if it doesnt touch any other floors
    ArrayList<ArrayList<Point>> floor = test.grid3dCreate(5,5,3);
    Point node1 = floor.get(0).get(4);
    Point node2 = floor.get(1).get(4);
    Point node3 = floor.get(2).get(4);
    StairPoint stair1 = new StairPoint(node1.getXCoord(), node1.getYCoord(), node1.getName(),node1.getId(),
        node1.neighbors,node1.getFloor());
    StairPoint stair2 = new StairPoint(node2.getXCoord(), node2.getYCoord(), node2.getName(),node2.getId(),
        node2.neighbors,node2.getFloor());
    StairPoint stair3 = new StairPoint(node3.getXCoord(), node3.getYCoord(), node3.getName(),node3.getId(),
        node3.neighbors,node3.getFloor());


    stair1.neighbors.remove(node2);
    stair2.neighbors.remove(node1);
    stair2.neighbors.remove(node3);
    stair3.neighbors.remove(node2);
    stair1.neighbors.add(stair2);
    stair2.neighbors.add(stair3);
    stair2.neighbors.add(stair1);
    stair3.neighbors.add(stair2);


    floor.get(0).remove(4);
    floor.get(1).remove(4);
    floor.get(2).remove(4);

    floor.get(0).add(4,stair1);
    floor.get(1).add(4,stair2);
    floor.get(2).add(4,stair3);

    floor.get(0).get(3).neighbors.remove(node1);
    floor.get(0).get(9).neighbors.remove(node1);
    floor.get(1).get(3).neighbors.remove(node2);
    floor.get(1).get(9).neighbors.remove(node2);
    floor.get(2).get(3).neighbors.remove(node3);
    floor.get(2).get(9).neighbors.remove(node3);

    floor.get(0).get(3).neighbors.add(stair1);
    floor.get(0).get(9).neighbors.add(stair1);
    floor.get(1).get(3).neighbors.add(stair2);
    floor.get(1).get(9).neighbors.add(stair2);
    floor.get(2).get(3).neighbors.add(stair3);
    floor.get(2).get(9).neighbors.add(stair3);


    Point start = floor.get(0).get(8); //Chose start and goal points and floors
    Point goal = floor.get(2).get(8);

    ArrayList<Point> path = new ArrayList<Point>();

    try {
      path = test.Astar(start,goal);
    } catch (Exception e) {
      assertTrue(false);
      e.printStackTrace();
    }

    for(int i = 0; i < path.size(); i++){
      if(path.get(i).getFloor() != start.getFloor() && path.get(i).getFloor() != goal.getFloor()){
        if (path.get(i) instanceof StairPoint){

        }else {
          assertTrue(false);
        }
      }
    }
    assertTrue(true);
  }

  @Test
  void AstarMultifloorStair2Test(){ // tests to see if it doesnt touch any other floors
    ArrayList<ArrayList<Point>> floor = test.grid3dCreate(5,5,3);
    Point node1 = floor.get(0).get(4);
    Point node2 = floor.get(1).get(4);
    Point node3 = floor.get(2).get(4);
    Point node4 = floor.get(0).get(3); //second elevator
    Point node5 = floor.get(1).get(3);

    StairPoint stair1 = new StairPoint(node1.getXCoord(), node1.getYCoord(), node1.getName(),node1.getId(),
        node1.neighbors,node1.getFloor());
    StairPoint stair2 = new StairPoint(node2.getXCoord(), node2.getYCoord(), node2.getName(),node2.getId(),
        node2.neighbors,node2.getFloor());
    StairPoint stair3 = new StairPoint(node3.getXCoord(), node3.getYCoord(), node3.getName(),node3.getId(),
        node3.neighbors,node3.getFloor());
    StairPoint stair4 = new StairPoint(node4.getXCoord(), node4.getYCoord(), node4.getName(),node4.getId(),
        node4.neighbors,node4.getFloor());
    StairPoint stair5 = new StairPoint(node5.getXCoord(), node5.getYCoord(), node5.getName(),node5.getId(),
        node5.neighbors,node5.getFloor());


    stair1.neighbors.remove(node2);
    stair2.neighbors.remove(node1);
    stair2.neighbors.remove(node3);
    stair3.neighbors.remove(node2);
    stair1.neighbors.add(stair2);
    stair2.neighbors.add(stair3);
    stair2.neighbors.add(stair1);
    stair3.neighbors.add(stair2);
    stair4.neighbors.remove(node5);
    stair5.neighbors.remove(node4);
    stair4.neighbors.add(stair5);
    stair5.neighbors.add(stair4);


    floor.get(0).remove(4);
    floor.get(1).remove(4);
    floor.get(2).remove(4);
    floor.get(0).remove(3);
    floor.get(1).remove(3);

    floor.get(0).add(4,stair1);
    floor.get(1).add(4,stair2);
    floor.get(2).add(4,stair3);
    floor.get(0).add(3,stair4);
    floor.get(1).add(4,stair5);


    floor.get(0).get(3).neighbors.remove(node1);
    floor.get(0).get(9).neighbors.remove(node1);
    floor.get(1).get(3).neighbors.remove(node2);
    floor.get(1).get(9).neighbors.remove(node2);
    floor.get(2).get(3).neighbors.remove(node3);
    floor.get(2).get(9).neighbors.remove(node3);
    floor.get(0).get(2).neighbors.remove(node4);
    floor.get(0).get(4).neighbors.remove(node4);
    floor.get(0).get(7).neighbors.remove(node4);
    floor.get(1).get(2).neighbors.remove(node5);
    floor.get(1).get(2).neighbors.remove(node5);
    floor.get(1).get(2).neighbors.remove(node5);

    floor.get(0).get(3).neighbors.add(stair1);
    floor.get(0).get(9).neighbors.add(stair1);
    floor.get(1).get(3).neighbors.add(stair2);
    floor.get(1).get(9).neighbors.add(stair2);
    floor.get(2).get(3).neighbors.add(stair3);
    floor.get(2).get(9).neighbors.add(stair3);
    floor.get(0).get(2).neighbors.add(stair4);
    floor.get(0).get(4).neighbors.add(stair4);
    floor.get(0).get(7).neighbors.add(stair4);
    floor.get(1).get(2).neighbors.add(stair5);
    floor.get(1).get(4).neighbors.add(stair5);
    floor.get(1).get(7).neighbors.add(stair5);

    Point start = floor.get(0).get(0); //Chose start and goal points and floors
    Point goal = floor.get(2).get(8);

    ArrayList<Point> path = new ArrayList<Point>();

    try {
      path = test.Astar(start,goal);
    } catch (Exception e) {
      assertTrue(false);
      e.printStackTrace();
    }

    for(int i = 0; i < path.size(); i++){
      if(path.get(i).getFloor() != start.getFloor() && path.get(i).getFloor() != goal.getFloor()){
        if (path.get(i) instanceof StairPoint){

        }else {
          assertTrue(false);
        }
      }
    }
    assertTrue(true);
  }

  @Test
  void AstarMultifloorStairAndElevatorTest(){ // tests to see if it doesnt touch any other floors
    ArrayList<ArrayList<Point>> floor = test.grid3dCreate(5,5,3);
    Point node1 = floor.get(0).get(4);
    Point node2 = floor.get(1).get(4);
    Point node3 = floor.get(2).get(4);
    StairPoint stair1 = new StairPoint(node1.getXCoord(), node1.getYCoord(), node1.getName(),node1.getId(),
        node1.neighbors,node1.getFloor());
    StairPoint stair2 = new StairPoint(node2.getXCoord(), node2.getYCoord(), node2.getName(),node2.getId(),
        node2.neighbors,node2.getFloor());
    StairPoint stair3 = new StairPoint(node3.getXCoord(), node3.getYCoord(), node3.getName(),node3.getId(),
        node3.neighbors,node3.getFloor());


    stair1.neighbors.remove(node2);
    stair2.neighbors.remove(node1);
    stair2.neighbors.remove(node3);
    stair3.neighbors.remove(node2);
    stair1.neighbors.add(stair2);
    stair2.neighbors.add(stair3);
    stair2.neighbors.add(stair1);
    stair3.neighbors.add(stair2);


    floor.get(0).remove(4);
    floor.get(1).remove(4);
    floor.get(2).remove(4);

    floor.get(0).add(4,stair1);
    floor.get(1).add(4,stair2);
    floor.get(2).add(4,stair3);

    floor.get(0).get(3).neighbors.remove(node1);
    floor.get(0).get(9).neighbors.remove(node1);
    floor.get(1).get(3).neighbors.remove(node2);
    floor.get(1).get(9).neighbors.remove(node2);
    floor.get(2).get(3).neighbors.remove(node3);
    floor.get(2).get(9).neighbors.remove(node3);

    floor.get(0).get(3).neighbors.add(stair1);
    floor.get(0).get(9).neighbors.add(stair1);
    floor.get(1).get(3).neighbors.add(stair2);
    floor.get(1).get(9).neighbors.add(stair2);
    floor.get(2).get(3).neighbors.add(stair3);
    floor.get(2).get(9).neighbors.add(stair3);
//Elevators
    Point node4 = floor.get(0).get(0);
    Point node5 = floor.get(1).get(0);
    Point node6 = floor.get(2).get(0);
    ElevatorPoint elevator1 = new ElevatorPoint(node4.getXCoord(), node4.getYCoord(), node4.getName(),node4.getId(),
        node4.neighbors,node4.getFloor());
    ElevatorPoint elevator2 = new ElevatorPoint(node5.getXCoord(), node5.getYCoord(), node5.getName(),node5.getId(),
        node5.neighbors,node5.getFloor());
    ElevatorPoint elevator3 = new ElevatorPoint(node6.getXCoord(), node6.getYCoord(), node6.getName(),node6.getId(),
        node6.neighbors,node6.getFloor());


    elevator1.neighbors.remove(node5);
    elevator2.neighbors.remove(node4);
    elevator2.neighbors.remove(node6);
    elevator3.neighbors.remove(node5);
    elevator1.neighbors.add(elevator2);
    elevator2.neighbors.add(elevator3);
    elevator2.neighbors.add(elevator1);
    elevator3.neighbors.add(elevator2);


    floor.get(0).remove(0);
    floor.get(1).remove(0);
    floor.get(2).remove(0);

    floor.get(0).add(0,elevator1);
    floor.get(1).add(0,elevator2);
    floor.get(2).add(0,elevator3);

    floor.get(0).get(5).neighbors.remove(node4);
    floor.get(0).get(1).neighbors.remove(node4);
    floor.get(1).get(5).neighbors.remove(node5);
    floor.get(1).get(1).neighbors.remove(node5);
    floor.get(2).get(5).neighbors.remove(node6);
    floor.get(2).get(1).neighbors.remove(node6);

    floor.get(0).get(5).neighbors.add(elevator1);
    floor.get(0).get(1).neighbors.add(elevator1);
    floor.get(1).get(5).neighbors.add(elevator2);
    floor.get(1).get(1).neighbors.add(elevator2);
    floor.get(2).get(5).neighbors.add(elevator3);
    floor.get(2).get(1).neighbors.add(elevator3);

    Point start = floor.get(0).get(0); //Chose start and goal points and floors
    Point goal = floor.get(2).get(12);

    ArrayList<Point> path = new ArrayList<Point>();

    try {
      path = test.Astar(start,goal);
    } catch (Exception e) {
      assertTrue(false);
      e.printStackTrace();
    }

    for(int i = 0; i < path.size(); i++){
      if(path.get(i).getFloor() != start.getFloor() && path.get(i).getFloor() != goal.getFloor()){
        if (path.get(i) instanceof StairPoint || path.get(i) instanceof ElevatorPoint){

        }else {
          assertTrue(false);
        }
      }
    }
    assertTrue(true);
  }

  @Test
  void AstarMultifloorStairAndElevator2Test(){ // tests to see if it doesnt touch any other floors
    ArrayList<ArrayList<Point>> floor = test.grid3dCreate(5,5,3);
    Point node1 = floor.get(0).get(4);
    Point node2 = floor.get(1).get(4);
    Point node3 = floor.get(2).get(4);
    Point node4 = floor.get(0).get(3); //second elevator
    Point node5 = floor.get(1).get(3);

    StairPoint stair1 = new StairPoint(node1.getXCoord(), node1.getYCoord(), node1.getName(),node1.getId(),
        node1.neighbors,node1.getFloor());
    StairPoint stair2 = new StairPoint(node2.getXCoord(), node2.getYCoord(), node2.getName(),node2.getId(),
        node2.neighbors,node2.getFloor());
    StairPoint stair3 = new StairPoint(node3.getXCoord(), node3.getYCoord(), node3.getName(),node3.getId(),
        node3.neighbors,node3.getFloor());
    StairPoint stair4 = new StairPoint(node4.getXCoord(), node4.getYCoord(), node4.getName(),node4.getId(),
        node4.neighbors,node4.getFloor());
    StairPoint stair5 = new StairPoint(node5.getXCoord(), node5.getYCoord(), node5.getName(),node5.getId(),
        node5.neighbors,node5.getFloor());


    stair1.neighbors.remove(node2);
    stair2.neighbors.remove(node1);
    stair2.neighbors.remove(node3);
    stair3.neighbors.remove(node2);
    stair1.neighbors.add(stair2);
    stair2.neighbors.add(stair3);
    stair2.neighbors.add(stair1);
    stair3.neighbors.add(stair2);
    stair4.neighbors.remove(node5);
    stair5.neighbors.remove(node4);
    stair4.neighbors.add(stair5);
    stair5.neighbors.add(stair4);


    floor.get(0).remove(4);
    floor.get(1).remove(4);
    floor.get(2).remove(4);
    floor.get(0).remove(3);
    floor.get(1).remove(3);

    floor.get(0).add(4,stair1);
    floor.get(1).add(4,stair2);
    floor.get(2).add(4,stair3);
    floor.get(0).add(3,stair4);
    floor.get(1).add(4,stair5);


    floor.get(0).get(3).neighbors.remove(node1);
    floor.get(0).get(9).neighbors.remove(node1);
    floor.get(1).get(3).neighbors.remove(node2);
    floor.get(1).get(9).neighbors.remove(node2);
    floor.get(2).get(3).neighbors.remove(node3);
    floor.get(2).get(9).neighbors.remove(node3);
    floor.get(0).get(2).neighbors.remove(node4);
    floor.get(0).get(4).neighbors.remove(node4);
    floor.get(0).get(7).neighbors.remove(node4);
    floor.get(1).get(2).neighbors.remove(node5);
    floor.get(1).get(2).neighbors.remove(node5);
    floor.get(1).get(2).neighbors.remove(node5);

    floor.get(0).get(3).neighbors.add(stair1);
    floor.get(0).get(9).neighbors.add(stair1);
    floor.get(1).get(3).neighbors.add(stair2);
    floor.get(1).get(9).neighbors.add(stair2);
    floor.get(2).get(3).neighbors.add(stair3);
    floor.get(2).get(9).neighbors.add(stair3);
    floor.get(0).get(2).neighbors.add(stair4);
    floor.get(0).get(4).neighbors.add(stair4);
    floor.get(0).get(7).neighbors.add(stair4);
    floor.get(1).get(2).neighbors.add(stair5);
    floor.get(1).get(4).neighbors.add(stair5);
    floor.get(1).get(7).neighbors.add(stair5);

//Elevators
    Point node7 = floor.get(0).get(0);
    Point node8 = floor.get(1).get(0);
    Point node9 = floor.get(2).get(0);
    Point node10 = floor.get(0).get(1);
    Point node11 = floor.get(1).get(1);
    ElevatorPoint elevator1 = new ElevatorPoint(node7.getXCoord(), node7.getYCoord(), node7.getName(),node7.getId(),
        node7.neighbors,node7.getFloor());
    ElevatorPoint elevator2 = new ElevatorPoint(node8.getXCoord(), node8.getYCoord(), node8.getName(),node8.getId(),
        node8.neighbors,node8.getFloor());
    ElevatorPoint elevator3 = new ElevatorPoint(node9.getXCoord(), node9.getYCoord(), node9.getName(),node9.getId(),
        node9.neighbors,node9.getFloor());
    ElevatorPoint elevator4 = new ElevatorPoint(node10.getXCoord(), node10.getYCoord(), node10.getName(),node10.getId(),
        node10.neighbors,node10.getFloor());
    ElevatorPoint elevator5 = new ElevatorPoint(node11.getXCoord(), node11.getYCoord(), node11.getName(),node11.getId(),
        node11.neighbors,node11.getFloor());


    elevator1.neighbors.remove(node8);
    elevator2.neighbors.remove(node7);
    elevator2.neighbors.remove(node9);
    elevator3.neighbors.remove(node8);
    elevator1.neighbors.add(elevator2);
    elevator2.neighbors.add(elevator3);
    elevator2.neighbors.add(elevator1);
    elevator3.neighbors.add(elevator2);
    elevator4.neighbors.remove(node11);
    elevator5.neighbors.remove(node10);
    elevator4.neighbors.add(elevator5);
    elevator5.neighbors.add(elevator4);


    floor.get(0).remove(0);
    floor.get(1).remove(0);
    floor.get(2).remove(0);
    floor.get(0).remove(1);
    floor.get(1).remove(1);

    floor.get(0).add(0,elevator1);
    floor.get(1).add(0,elevator2);
    floor.get(2).add(0,elevator3);
    floor.get(0).add(1,elevator4);
    floor.get(1).add(1,elevator5);

    floor.get(0).get(5).neighbors.remove(node7);
    floor.get(0).get(1).neighbors.remove(node7);
    floor.get(1).get(5).neighbors.remove(node8);
    floor.get(1).get(1).neighbors.remove(node8);
    floor.get(2).get(5).neighbors.remove(node9);
    floor.get(2).get(1).neighbors.remove(node9);
    floor.get(0).get(0).neighbors.remove(node10);
    floor.get(0).get(6).neighbors.remove(node10);
    floor.get(0).get(2).neighbors.remove(node10);
    floor.get(1).get(0).neighbors.remove(node11);
    floor.get(1).get(6).neighbors.remove(node11);
    floor.get(1).get(2).neighbors.remove(node11);

    floor.get(0).get(5).neighbors.add(elevator1);
    floor.get(0).get(1).neighbors.add(elevator1);
    floor.get(1).get(5).neighbors.add(elevator2);
    floor.get(1).get(1).neighbors.add(elevator2);
    floor.get(2).get(5).neighbors.add(elevator3);
    floor.get(2).get(1).neighbors.add(elevator3);
    floor.get(0).get(0).neighbors.add(elevator4);
    floor.get(0).get(0).neighbors.add(elevator4);
    floor.get(0).get(6).neighbors.add(elevator4);
    floor.get(0).get(2).neighbors.add(elevator4);
    floor.get(1).get(0).neighbors.add(elevator5);
    floor.get(1).get(6).neighbors.add(elevator5);
    floor.get(1).get(2).neighbors.add(elevator5);

    Point start = floor.get(0).get(2); //Chose start and goal points and floors
    Point goal = floor.get(2).get(12);

    ArrayList<Point> path = new ArrayList<Point>();

    try {
      path = test.Astar(start,goal);
    } catch (Exception e) {
      assertTrue(false);
      e.printStackTrace();
    }

    for(int i = 0; i < path.size(); i++){
      if(path.get(i).getFloor() != start.getFloor() && path.get(i).getFloor() != goal.getFloor()){
        if (path.get(i) instanceof StairPoint || path.get(i) instanceof ElevatorPoint){

        }else {
          assertTrue(false);
        }
      }
    }
    assertTrue(true);
  }

  @Test
  void AstarMultifloorErrorTest(){ // tests to see if it doesnt touch any other floors
    ArrayList<ArrayList<Point>> floor = test.grid3dCreate(5,5,3);
    Point node1 = floor.get(0).get(4);
    Point node2 = floor.get(1).get(4);
    Point node3 = floor.get(2).get(4);

    StairPoint stair1 = new StairPoint(node1.getXCoord(), node1.getYCoord(), node1.getName(),node1.getId(),
        node1.neighbors,node1.getFloor());
    StairPoint stair2 = new StairPoint(node2.getXCoord(), node2.getYCoord(), node2.getName(),node2.getId(),
        node2.neighbors,node2.getFloor());
    StairPoint stair3 = new StairPoint(node3.getXCoord(), node3.getYCoord(), node3.getName(),node3.getId(),
        node3.neighbors,node3.getFloor());


    stair1.neighbors.remove(node2);
    stair2.neighbors.remove(node1);
    stair2.neighbors.remove(node3);
    stair3.neighbors.remove(node2);
    stair1.neighbors.add(stair2);
    stair2.neighbors.add(stair3);
    stair2.neighbors.add(stair1);
    stair3.neighbors.add(stair2);


    floor.get(0).remove(4);
    floor.get(1).remove(4);
    floor.get(2).remove(4);

    floor.get(0).add(4,stair1);
    floor.get(1).add(4,stair2);
    floor.get(2).add(4,stair3);

    floor.get(0).get(3).neighbors.remove(node1);
    floor.get(0).get(9).neighbors.remove(node1);
    floor.get(1).get(3).neighbors.remove(node2);
    floor.get(1).get(9).neighbors.remove(node2);
    floor.get(2).get(3).neighbors.remove(node3);
    floor.get(2).get(9).neighbors.remove(node3);

    floor.get(0).get(3).neighbors.add(stair1);
    floor.get(0).get(9).neighbors.add(stair1);
    floor.get(1).get(3).neighbors.add(stair2);
    floor.get(1).get(9).neighbors.add(stair2);
    floor.get(2).get(3).neighbors.add(stair3);
    floor.get(2).get(9).neighbors.add(stair3);
//Elevators
    Point node4 = floor.get(0).get(0);
    Point node5 = floor.get(1).get(0);
    Point node6 = floor.get(2).get(0);

    ElevatorPoint elevator1 = new ElevatorPoint(node4.getXCoord(), node4.getYCoord(), node4.getName(),node4.getId(),
        node4.neighbors,node4.getFloor());
    ElevatorPoint elevator2 = new ElevatorPoint(node5.getXCoord(), node5.getYCoord(), node5.getName(),node5.getId(),
        node5.neighbors,node5.getFloor());
    ElevatorPoint elevator3 = new ElevatorPoint(node6.getXCoord(), node6.getYCoord(), node6.getName(),node6.getId(),
        node6.neighbors,node6.getFloor());


    elevator1.neighbors.remove(node5);
    elevator2.neighbors.remove(node4);
    elevator2.neighbors.remove(node6);
    elevator3.neighbors.remove(node5);
    elevator1.neighbors.add(elevator2);
    elevator2.neighbors.add(elevator3);
    elevator2.neighbors.add(elevator1);
    elevator3.neighbors.add(elevator2);


    floor.get(0).remove(0);
    floor.get(1).remove(0);
    floor.get(2).remove(0);

    floor.get(0).add(0,elevator1);
    floor.get(1).add(0,elevator2);
    floor.get(2).add(0,elevator3);

    floor.get(0).get(5).neighbors.remove(node4);
    floor.get(0).get(1).neighbors.remove(node4);
    floor.get(1).get(5).neighbors.remove(node5);
    floor.get(1).get(1).neighbors.remove(node5);
    floor.get(2).get(5).neighbors.remove(node6);
    floor.get(2).get(1).neighbors.remove(node6);

    floor.get(0).get(5).neighbors.add(elevator1);
    floor.get(0).get(1).neighbors.add(elevator1);
    floor.get(1).get(5).neighbors.add(elevator2);
    floor.get(1).get(1).neighbors.add(elevator2);
    floor.get(2).get(5).neighbors.add(elevator3);
    floor.get(2).get(1).neighbors.add(elevator3);

    Point start = floor.get(0).get(1); //Chose start and goal points and floors
    Point goal = new Point(4,4,"errorNode",3,new ArrayList<Point>(),5);

    ArrayList<Point> path = new ArrayList<Point>();

    try {
      path = test.Astar(start,goal);
    } catch (NoPathException e) {
      assertTrue(true);

    }
    //assertTrue(false);
  }
  @Test
  void SimpleErrorTest(){
    Point start = new Point(1,1,"", 3,new ArrayList<Point>(),5);
    Point goal = new Point(4,4,"errorNode",3,new ArrayList<Point>(),5);

    ArrayList<Point> path = new ArrayList<Point>();

    try {
      path = test.Astar(start,goal);
    } catch (NoPathException e) {
      assertTrue(true);

    }
    //assertTrue(false);
  }

  @Test
  void createGrid2Test() {
    ArrayList<Point> p = test.gridCreate(5, 5, 1);
    for (int i = 0; i < p.size(); i++) {
      if (p.get(i).neighbors.size() <= 4) {
      }
      else{
        assertTrue(false);
      }
    }
    assertTrue(true);
  }

  @Test
  void createGridTest(){
    ArrayList<ArrayList<Point>> p = new ArrayList<ArrayList<Point>>();
    p = test.grid3dCreate(5,5,5);
    for(int i = 0; i < 125; i++){
      if(p.get(i/25).get(i%25).neighbors.size() <= 4) {
      }
      else{
        System.out.print(i);
        assertTrue(false);
      }
    }
    assertTrue(true);
  }
}