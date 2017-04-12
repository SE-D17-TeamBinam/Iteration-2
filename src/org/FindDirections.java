package org;

import java.util.ArrayList;
import org.Point;

/**
 * Created by ajnag on 4/10/2017.
 */
/**
 * The purpose of this class is to take tha list of points from A*
 * and produce text directions.
 */
public class FindDirections {
  int pathValue;
  int Angle;
  String right = "Turn Right";
  String left = "Turn Left";
  String straight = "Go Straight until you reach";
  String reverse = "Turn Around";
  String finished = "You are at your Destination";
  String changeFloor = "Go to Floor";


  /**
   * This creates an empty lists that stores the strings of directions.
   */
  private ArrayList<String> directions = new ArrayList<>();  //List of directions that relate to each other4

  /**
   * This calculates the angles between points.
   */
  private float getAngle(Point destination, Point start){
    return (float) Math.toDegrees(Math.atan2((destination.getXCoord()- start.getXCoord()),( destination.getYCoord() - start.getYCoord())));
  }
  /**
   * This takes in the points from A* and produces text directions between points.
   */
  public ArrayList<String> getTextDirections(ArrayList<Point> reversePath){

    //reverses the path to make it from start to end
    ArrayList<Point> path = new ArrayList<Point>();
    for(int i=reversePath.size()-1;  i>=0; i--){
      path.add(reversePath.get(i));
    }
    int count = 0;
    Point destination = path.get(path.size()-1);

    while(destination !=path.get(count)){
      Point current = path.get(count);
      if(current.getFloor()==path.get(count+1).getFloor()){

        FloorDirections(getAngle(path.get(count+1), current), path.get(count+1));
        count++;
      } else {

        //indicates floor change in text direction
        directions.add(changeFloor + " "+ path.get(count+1).getFloor());
        count++;

      }

    }
    count=0;
    directions.add(finished);
    //start filtering
    while(count != directions.size()-1){
      if(directions.get(count).contains(changeFloor)){
        if(directions.get(count+1).contains(changeFloor)){
          directions.remove(count);
        }
        else {
          count++;
        }
      }
      count++;
    }
    return directions;

  }

  /**
   * This is a helper function that adds directions in terms of turning and walking to a destination.
   */
  private void FloorDirections(float Angle, Point next){
    if (Angle <= 45 && Angle >= -45) {
      directions.add(straight +" "+ next.getName());
    } else if (Angle < 135 && Angle > 45) {
      directions.add(right);
      directions.add(straight +" "+ next.getName());
    } else if (Angle >=135 || Angle<=-135){
      directions.add(reverse);
      directions.add(straight +" "+ next.getName());
    } else if (Angle >-135 && Angle<-45){
      directions.add(left);
      directions.add(straight +" "+ next.getName());
    }

  }
  /**
   * Takes in the directions and produces epeech.
   */
  public void sayDirections(ArrayList<String> directions){
    for(int i=0; i<directions.size()-1; i++){
      DataController.textToSpeech(directions.get(i));
    }
  }

}
