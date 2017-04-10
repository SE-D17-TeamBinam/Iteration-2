package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Definitions.*;
import org.Point;

/**
 * Created by evan on 3/25/17.
 * This Object will add, remove and edit our hospital database
 */
public class DatabaseEditor implements DatabaseInterface{

  DatabaseController dbc = null;

  public DatabaseEditor(DatabaseController _dbc) {
    this.dbc = _dbc;
  }

  ///////////////////////////
  /////// Physician /////////
  ///////////////////////////

  public boolean removePhysician(String first_name, String last_name, String title) {
    dbc.send_Command(
        "delete from physician (first_name, last_name, title) values ('" + first_name + "','"
            + last_name + "','" + title + "')");
    return true;
  }

  public boolean addPhysician(int PID, String first_name, String last_name, String title,
      ArrayList<FakePoint> array_points) {
    dbc.send_Command(
        "insert into physician (pid,first_name, last_name, title) values (" + PID + ",'"
            + first_name + "','"
            + last_name + "','" + title + "')");

    int i;
    for (i = 0; i < array_points.size(); i++) {
      this.addPhysicianLocation(PID, array_points.get(i).id);
    }
    return true;
  }

  public Physician get_physician(int pid) {
    ResultSet res = dbc.send_Command("select * from physician where pid = " + pid).get(0);
    int c = 0;
    Physician my_p = null;
    c++;
    try {
      while (res.next()) {
        if (c > 1) {
          System.out.println("was not supposed to happen");
          break;
        }
        String first_name = res.getString("FIRST_NAME");
        String last_name = res.getString("LAST_NAME");
        String title = res.getString("TITLE");
        int new_pid = res.getInt("PID");

        my_p = new Physician(first_name, last_name, title, pid, new ArrayList<FakePoint>());
        //physicians.add(p);

      }

      res.close();

      if (my_p == null) {
        System.out.println("no physician found");
        return null;
      }

      ResultSet res2 = dbc.send_Command("select * from physician_location where pid_ph = " + pid)
          .get(0);

      ArrayList<FakePoint> my_locs = new ArrayList<FakePoint>();
      while (res2.next()) {
        int new_pid2 = res2.getInt("PID_po");
        my_locs.add(get_point(new_pid2));

      }
      res2.close();
      my_p.setLocations(my_locs);


    } catch (SQLException e) {
      e.printStackTrace();
    }

    return my_p;


  }


  public ArrayList<Physician> getAllPhysicians() throws SQLException {
    ArrayList<Physician> physicians = new ArrayList<Physician>();
    ResultSet res = dbc.send_Command("select pid from physician").get(0);
    while (res.next()) {
      int pid = res.getInt("PID");

      Physician p = get_physician(pid);
      physicians.add(p);
    }

    return physicians;

  }


  public boolean updatePhysicians(ArrayList<Physician> ap) throws SQLException {
    dbc.send_Command("truncate table Physician; truncate table Physician_location;");
    int i;
    for (i = 0; i < ap.size(); i++) {
      this.addPhysician(ap.get(i).getID(), ap.get(i).getFirstName(), ap.get(i).getLastName(),
          ap.get(i).getTitle(), ap.get(i).getLocations());
    }

    return true;
  }

  ///////////////////////////
  //// Location - Service ///
  ///////////////////////////

  public boolean addServiceLocation(String service_name, String md_related, String location_name) {
    dbc.send_Command(
        "insert into ServiceLocation (lid,sid) select lid,sid from service_location, service where location.name = '"
            + location_name + "', select sid from service where"
            + " service.name = '" + service_name + "')\n ");
    return true;
  }

  public boolean removeServiceLocation(String service_name, String location_name) {
    dbc.send_Command(
        "delete from ServiceLocation  where sid = (select sid from service where name = '"
            + service_name + "') and  lid = (select lid from location where"
            + " name = '" + location_name + "')\n ");
    return true;
  }

  ///////////////////////////
  /// Location - Physician //
  ///////////////////////////

  public boolean addPhysicianLocation(int pid_ph, int pid_po) {
    dbc.send_Command(
        "insert into Physician_Location (pid_po,pid_ph) values(" + pid_po + "," + pid_ph
            + ");\n");
    return true;
  }

  public boolean removePhysicianLocation(int pid_ph, int pid_po) {
    dbc.send_Command(
        "delete from Physician_Location where pid_ph = " + pid_ph + " and pid_po ='" + pid_po
            + ");\n");
    return true;
  }

  ////////////////////////
  /////////Point/////////
  //////////////////////

  public boolean addPoint(Point realpoint) {
    FakePoint point = new FakePoint(realpoint);
    int cost = point.getCost();
    int x = point.getXCoord();
    int y = point.getYCoord();
    int id = point.getId();
    int floor = point.getFloor();
    String name = point.getName();
    ArrayList<Integer> neighbors = point.getNeighbors();

    dbc.send_Command(
        "insert into Point (x,y,cost,pid,floor,name) values (" + x + ","
            + y + "," + cost + "," + id + "," + floor + ",'" + name + "'); \n");
    return true;
  }
  public boolean addPoint(FakePoint point) {
    int cost = point.getCost();
    int x = point.getXCoord();
    int y = point.getYCoord();
    int id = point.getId();
    int floor = point.getFloor();
    String name = point.getName();
    ArrayList<Integer> neighbors = point.getNeighbors();

    dbc.send_Command(
        "insert into Point (x,y,cost,pid,floor,name) values (" + x + ","
            + y + "," + cost + "," + id + "," + floor + ",'" + name + "'); \n");
    return true;
  }


  public boolean removePoint(Point realpoint) {
    FakePoint point = new FakePoint(realpoint);
    int cost = point.getCost();
    int x = point.getXCoord();
    int y = point.getYCoord();
    int id = point.getId();
    int floor = point.getFloor();
    String name = point.getName();

    dbc.send_Command(
        "delete from Point where pid = " + id + ";");
    return true;
  }


  public boolean update_points(ArrayList<Point> rpal) {
    ArrayList<FakePoint> al = new ArrayList<FakePoint>();
    for (int q = 0; q < rpal.size(); q++){
      al.add(new FakePoint(rpal.get(q)));
    }
    dbc.send_Command("truncate table Point;");
    int i;
    for (i = 0; i < al.size(); i++) {
      this.addPoint(al.get(i));
    }
    //int i;

    int k, l;
    for (k = 0; k < al.size(); k++) {
      //this.addPoint(al.get(i));
      FakePoint point = al.get(k);
      ArrayList<Integer> neighbor_ids = point.getNeighbors();
      for (l = 0; l < neighbor_ids.size(); l++) {
        this.addNeighbor(point.getId(), neighbor_ids.get(l));
        //this.addNeighboring(pl.get(i).id,point.id);
      }
    }

    return true;
  }

  public FakePoint get_point(int my_pid) {
    FakePoint my_point = null;
    ResultSet res1 = dbc.send_Command("select * from point where pid = " + my_pid).get(0);
    int c = 0;
    try {
      while (res1.next()) {
        c++;
        if (c > 1) {
          System.out.println("that was not supposed to happen. ");
          break;
        }

        int floor = res1.getInt("floor");
        String name = res1.getString("NAME");
        int pid = res1.getInt("PID");
        int x = res1.getInt("x");
        int y = res1.getInt("y");
        int cost = res1.getInt("cost");

        my_point = new FakePoint(x, y, name, pid, new ArrayList<Integer>(), floor);

        ArrayList<Integer> neighbor_ids = new ArrayList<Integer>();
        ResultSet res4 = dbc.send_Command(
            "select pid1,pid2 from Neighbor where pid1 = " + pid + " OR pid2 = " + pid).get(0);
        while (res4.next()) {
          int pid1 = res4.getInt("Pid1");
          int pid2 = res4.getInt("Pid1");
          if (pid1 != my_pid) {
            neighbor_ids.add(pid1);
          } else {
            neighbor_ids.add(pid2);
          }

        }
        res4.close();

        my_point.setNeighbors(neighbor_ids);


      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return my_point;

  }


  public ArrayList<Point> getAllPoints() throws SQLException {
    ArrayList<FakePoint> fakepoints = new ArrayList<FakePoint>();
    ResultSet res = dbc.send_Command("select pid from point").get(0);
    FakePoint new_point;
    while (res.next()) {
      int pid = res.getInt("PID");
      new_point = get_point(pid);
      fakepoints.add(new_point);
    }
    res.close();
    //Now convert to real
    ArrayList<Point> ret = new ArrayList<Point>();
    for (int i = 0; i < fakepoints.size(); i++){
      ret.add(fakepoints.get(i).toRealPoint());
    }
    for (int i = 0; i < ret.size(); i++){
      ArrayList<Integer> currentNeighbors = findFakePoint(ret.get(i),fakepoints).getNeighbors();
      for (int j = 0; j < currentNeighbors.size(); j++){
        ret.get(i).neighbors.add(findRealPoint(currentNeighbors.get(j),ret));
      }

    }

    return ret;
  }


  private FakePoint findFakePoint(Point p, ArrayList<FakePoint> fps){
    for (int i = 0; i < fps.size(); i++){
      if (p.id == fps.get(i).getId()){
        return fps.get(i);
      }
    }
    return null;
  }
  private Point findRealPoint(int p, ArrayList<Point> pts){
    for (int i = 0; i < pts.size(); i++){
      if (p == pts.get(i).id){
        return pts.get(i);
      }
    }
    return null;
  }

  //////////////////////
///////Neighbor///////
//////////////////////

  public boolean addNeighbor(int pid1, int pid2) {

    dbc.send_Command(
        "insert into Neighbor (pid1,pid2) values (" + pid1 + "," + pid2 + "); \n");
    return true;
  }

  public boolean removeNeighbor(int pid1, int pid2) {

    dbc.send_Command(
        "delete from Neighbor where pid1 = " + pid1 + " or pid2 = " + pid2 + "); \n");
    return true;
  }

}

//OLD COMMENTED OUT CODE STARTS

///////////////////////
///////Neighboring///////
//////////////////////
/*
  boolean addNeighboring(int pid_n,int pid_p) {

    dbc.send_Command(
        "insert into Neighboring (pid_n,pid_p) values (" + pid_n + "," + pid_p +  " ); \n");
    return true;
  }

  boolean deleteNeighboring(int pid_n,int pid_p) {

    dbc.send_Command(
        "delete from Neighboring where pid_n = " + pid_n + " and pid_p = " + pid_p +  " ); \n");
    return true;
  }

}


*/

///////////////////////////
//////// Location /////////
///////////////////////////
/*
  boolean addLocation(String name, String isFloor, int floor) {
    dbc.send_Command(
        "insert into Location (name,isFloor,Floor) values (" + name + "," + isFloor + "," + floor
            + ")");
    return true;
  }

  boolean removeLocation(String name, String isFloor, String floor) {
    dbc.send_Command("delete from Location where name = '" + name + "')");
    return true;
  }
*/



  /*boolean update_nodes(ArrayList<Point> al){
      dbc.send_Command("truncate table Point");


      return true;
  }*/

///////////////////////
/////Location -Point///
///////////////////////
/*
  boolean addPointLocation(String location_name,int pid){
    dbc.send_Command(
        "insert into Point_Location (lid,pid) select lid,pid from location,point where location =  '"
            + location_name + "' and pid = " + pid +  ");\n");
    return true;
  }

  boolean removeLocationPoint(String location_name,int pid){

    dbc.send_Command(
        "delete from Point_Location where pid = " + pid +  " and lid = (select lid from location where name = '" + location_name +  "' ) ); \n");
    return true;

  }
*/