package Definitions;

/**
 * Created by John on 4/4/2017.
 */
public class Coordinate {

  private double x;
  private double y;

  public Coordinate(){
    x = 0;
    y = 0;
  }

  public Coordinate(double x, double y){
    this.x = x;
    this.y = y;
  }

  public double distanceTo(Coordinate to){
    return Math.sqrt(Math.pow(x - to.getX(), 2) + Math.pow(y - to.getY(), 2));
  }

  public void setX(double x){
    this.x = x;
  }

  public void setY(double y){
    this.y = y;
  }

  public double getX(){
    return x;
  }

  public double getY(){
    return y;
  }

}
