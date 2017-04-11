package Database;

import Definitions.Physician;
import java.sql.SQLException;
import java.util.ArrayList;
import org.Point;

/**
 * Interface to make the interaction with the Database Editor cleaner
 * Created by Evan on 4/9/2017.
 */
public interface DatabaseInterface {


  /**
   * Pulls data from the datababse and populates the local copies of the data
   * @throws SQLException If there was a problem communicating with the database
   */
  public void load() throws SQLException;

  /**
   * Package Protected Method to push the local copies into the database
   */
  void save();

  /**
   * Gets a sublist of the local copy of points that have a name (ie Destinations)
   * @return ArrayList of Points that are all the local points that have names
   */
  public ArrayList<Point> getNamedPoints();

  /**
   * Tries to pull data from the database into the local copies then returns the local copy of points
   * @return ArrayList of Points that is the local copy of points
   */
  public ArrayList<Point> getPoints();

  /**
   * Set the local copy of points and then save them to the database
   * @param points The ArrayList of Points to replace the local copy
   */
  public void setPoints(ArrayList<Point> points);

  /**
   * Tries to pull data from the database into the local copies then returns the local copy of physicians
   * @return The ArrayList of Physicians that is the local copy of physicians
   */
  public ArrayList<Physician> getPhysicians();

  /**
   * Set the local copy of points and then save them to the database
   * @param physicians The ArrayList of Points to replace the local copy
   */
  public void setPhysicians(ArrayList<Physician> physicians);

}
