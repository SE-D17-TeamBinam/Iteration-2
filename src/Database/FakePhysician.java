package Database;

/**
 * Created by Evan on 4/10/2017.
 */

import Definitions.Physician;
import java.util.ArrayList;
import org.Point;
import java.util.ArrayList;
import org.Point;

public class FakePhysician {
  private String firstName;
  private String lastName;
  private String title;
  private long PID;
  private ArrayList<Integer> locations;

  public FakePhysician(String firstName, String lastName, String title, long PID, ArrayList<Integer> locations) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.title = title;
    this.PID = PID;
    this.locations = locations;
  }

  public FakePhysician(Physician real_physician) {
    this.locations = new ArrayList<Integer>();
    this.firstName = real_physician.getFirstName();
    this.lastName = real_physician.getLastName();
    this.title = real_physician.getTitle();
    this.PID = real_physician.getID();
    for(int i= 0;i < real_physician.getLocations().size();i++){
      this.locations.add(real_physician.getLocations().get(i).getId());
    }
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

  public ArrayList<Integer> getLocations () {
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

  public void setLocations (ArrayList<Integer> newLocations) {
    this.locations = newLocations;
  }

  public Physician toRealPhysician(){
    return new Physician(this.firstName, this.lastName, this.title, this.PID, new ArrayList<Point>());
  }

  public long getID () {
    return this.PID;
  }
}

