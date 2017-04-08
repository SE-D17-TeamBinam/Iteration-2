package Definitions;

import java.util.ArrayList;
import org.Point;

/**
 * Created by Leon Zhang on 2017/4/3.
 */
public class Physician {
  private String firstName;
  private String lastName;
  private String title;
  private long PID;
  private ArrayList<Point> locations;

  public Physician(String firstName, String lastName, String title, long PID, ArrayList<Point> locations) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.title = title;
    this.PID = PID;
    this.locations = locations;
  }

  public String getFirstName () {
    return this.firstName;
  }

  public String getLastName () {
    return this.lastName;
  }

  public String getTitle() {
    return this.title;
  }

  public ArrayList<Point> getLocations () {
    return this.locations;
  }

  public void setFirstName (String newFirstName) {
    this.firstName = newFirstName;
  }

  public void setLastName (String newLastName) {
    this.lastName = newLastName;
  }

  public void setTitle (String newTitle) {
    this.title = newTitle;
  }

  public void setLocations (ArrayList<Point> newLocations) {
    this.locations = newLocations;
  }

  public long getID () {
    return this.PID;
  }
}
