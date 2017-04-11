package org;

import java.util.ArrayList;

/**
 * Created by Alberto on 4/9/2017.
 */
public class StairPoint extends VerticalPoint {

  public StairPoint(int xCoord, int yCoord, String name, int id, ArrayList<Point> new_neighbors, int floor){
    super(xCoord, yCoord, name, id, new_neighbors, floor);
  }
  public boolean isElevator(){
    return false;
  }
  public boolean isStair(){
    return true;
  }


  /**
   * canAchieveFloor will return a boolean stating if the elevator can reach the desired floor
   * <p>
   *   THIS method will use a recursive form to check to see if can reach the correct floor by searching
   *   the neighbors
   * </p>
   * @param desiredFloor  Goal's floor. stored as an integer
   * @return  boolean     true is when it can reach the destination's floor
   */
  public boolean canAchieveFloor(int desiredFloor){
    if(this.floor == desiredFloor){
      return true;
    }
    else{
      int move = desiredFloor - this.floor;
      for(int i = 0; i < this.neighbors.size(); i++){
        if (this.neighbors.get(i).floor - this.floor == 1 && move > 0 ){ // if neighbor floor is higher and suppose to move up
          StairPoint next = (StairPoint) this.neighbors.get(i);
          return next.canAchieveFloor(desiredFloor);
        }
        else if (this.neighbors.get(i).floor - this.floor == -1 && move < 0 ){ // if neighbor floor is lower and suppose to move down
          StairPoint next = (StairPoint) this.neighbors.get(i);
          return next.canAchieveFloor(desiredFloor);
        }
      }
      //reaches here if for loop doesnt work, so no more vertical points with desried floor
      return false;
    }
  }

}
