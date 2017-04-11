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


  public void load() throws SQLException;

  void save();

  public ArrayList<Point> getNamedPoints();

  public ArrayList<Point> getPoints();

  public void setPoints(ArrayList<Point> points);

  public ArrayList<Physician> getPhysicians();

  public void setPhysicians(ArrayList<Physician> physicians);

}
