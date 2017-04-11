package org;

import java.util.ArrayList;

/**
 * Created by Alberto on 4/8/2017.
 */


public abstract class VerticalPoint extends Point{

  VerticalPoint(int xCoord, int yCoord, String name, int id, ArrayList<Point> new_neighbors, int floor){
    super(xCoord, yCoord, name, id, new_neighbors, floor);
  }


}
