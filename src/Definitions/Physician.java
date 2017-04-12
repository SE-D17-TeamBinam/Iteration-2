package Definitions;

import Database.FakePhysician;
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

  public Physician(String firstName, String lastName, String title, long PID,
      ArrayList<Point> locations) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.title = title;
    this.PID = PID;
    this.locations = locations;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getTitle() {
    return this.title;
  }

  public ArrayList<Point> getLocations() {
    return this.locations;
  }

  public void setFirstName(String newFirstName) {
    this.firstName = newFirstName;
  }

  public void setLastName(String newLastName) {
    this.lastName = newLastName;
  }

  public void setTitle(String newTitle) {
    this.title = newTitle;
  }

  public void setLocations(ArrayList<Point> newLocations) {
    this.locations = newLocations;
  }

  public long getID() {
    return this.PID;
  }

  public boolean compareTo(Physician p2) {
    if (this.getTitle().equals(p2.getTitle()) && this.getFirstName().equals(p2.getFirstName()) &&
        this.getLastName().equals(p2.getLastName()) && this.getID() == p2.getID()) {

      FakePhysician p3 = new FakePhysician(this);
      FakePhysician p4 = new FakePhysician(p2);
      for (int k = 0; k < p4.getLocations().size(); k++) {
        if (!(p3.getLocations().contains(p4.getLocations().get(k)))) {
          System.out.println("Location " + p4.getLocations().get(k) + " not in the other");
          return false;
        }
      }

    } else {
      System.out.println("verification failed a field is different-physician");
      return false;
    }

    return true;
  }
}
