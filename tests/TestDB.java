//import Definitions.Physician;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import org.DatabaseController;
//import org.DatabaseEditor;
//import org.FakePoint;
//import org.Point;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
///**
// * Created by Evan on 4/8/2017.
// */
//
//
//public class TestDB {
//
//  DatabaseController dbc = new DatabaseController("org.apache.derby.jdbc.EmbeddedDriver",
//      "jdbc:derby:testDB;create=true");
//  DatabaseEditor dbe = new DatabaseEditor(dbc);
//
//  @Test
//  void testMakeDB() {
//    dbc.send_Command(
//        "CREATE TABLE Physician(PID int, First_Name VARCHAR(25) NOT NULL, Last_Name VARCHAR(25) NOT NULL,Title VARCHAR(25) NOT NULL,CONSTRAINT pk_physician PRIMARY KEY (PID));CREATE TABLE Physician_Location(PLID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), PID_po int,PID_ph int, CONSTRAINT pk_physicianl PRIMARY KEY (PLID),CONSTRAINT u_physicianl UNIQUE(PID_po,PID_ph));CREATE TABLE Point(pid int NOT NULL, x int NOT NULL,y int NOT NULL,floor int NOT NULL, Cost int NOT NULL,name varchar(50) ,CONSTRAINT pk_ppoint1 PRIMARY KEY (pid));CREATE TABLE Neighbor(\tnid int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\tPid1 int NOT NULL,\tPid2 int NOT NULL,\tCONSTRAINT pk_neighbors  PRIMARY KEY (nid),CONSTRAINT u_neighbors UNIQUE(Pid1,Pid2));");
//  }
//
//  FakePoint p1 = new FakePoint(1024, 2048, "", 1, new ArrayList<>(Arrays.asList(2, 3, 5, 4)), 4);
//  FakePoint p2 = new FakePoint(2024, 1048, "", 2, new ArrayList<>(Arrays.asList(1, 3, 5)), 4);
//  FakePoint p3 = new FakePoint(1024, 1024, "", 3, new ArrayList<>(Arrays.asList(2, 1, 4)), 4);
//  FakePoint p4 = new FakePoint(2345, 1048, "", 4, new ArrayList<>(Arrays.asList(1, 3)), 4);
//  FakePoint p5 = new FakePoint(2334, 1024, "", 5, new ArrayList<>(Arrays.asList(2, 1)), 4);
//
//  @Test
//  void testDB1() {
//
//    dbe.addPoint(p1);
//    dbe.addPoint(p2);
//    dbe.addPoint(p3);
//    dbe.addPoint(p4);
//    dbe.addPoint(p5);
//  }
//
//  @Test
//  void testDB2() {
//    Physician physician1 = new Physician("Evan", "Duffy", "Doctor Professor", 1,
//        new ArrayList<FakePoint>(Arrays.asList(p1, p2)));
//    Physician physician2 = new Physician("Tom", "White", "Mister Doctor Professor", 2,
//        new ArrayList<FakePoint>(Arrays.asList(p3)));
//    Physician physician3 = new Physician("Spyros", "Antonatos", "Doctor Professor", 3,
//        new ArrayList<FakePoint>(Arrays.asList(p3, p4, p5)));
//
//    dbe.addPhysician(physician1.getID(), physician1.getFirstName(), physician1.getLastName(),
//        physician1.getTitle(), physician1.getLocations());
//    dbe.addPhysician(physician2.getID(), physician2.getFirstName(), physician2.getLastName(),
//        physician2.getTitle(), physician2.getLocations());
//    dbe.addPhysician(physician3.getID(), physician3.getFirstName(), physician3.getLastName(),
//        physician3.getTitle(), physician3.getLocations());
//  }
//
//  @Test
//  void testGetFromDB1() {
//    Physician physician1 = new Physician("Evan", "Duffy", "Doctor Professor", 1,
//        new ArrayList<FakePoint>(Arrays.asList(p1, p2)));
//    Assertions.assertEquals(physician1.getFirstName(), dbe.get_physician(1).getFirstName());
//  }
//
//  @Test
//  void testGetFromDB2() {
//    Physician physician1 = new Physician("Evan", "Duffy", "Doctor Professor", 1,
//        new ArrayList<FakePoint>(Arrays.asList(p1, p2)));
//    Assertions
//        .assertEquals(physician1.getLocations().get(1).getYCoord(), dbe.get_physician(1).getLocations().get(1).getYCoord());
//  }
//
//  @Test
//  void testGetPoint1(){
//    FakePoint p5 = new FakePoint(2334, 1024, "", 5, new ArrayList<Integer>(Arrays.asList(2, 1)), 4);
//    Assertions.assertEquals(p5.getCost(), dbe.get_point(5).getCost());
//  }
//
//  @Test
//  void testGetPoint2(){
//    FakePoint p5 = new FakePoint(2334, 1024, "", 5, new ArrayList<Integer>(Arrays.asList(2, 1)), 4);
//    Assertions.assertEquals(p5.getName(), dbe.get_point(5).getName());
//  }
//
//
//  @Test
//  void testUpdateAllPoints(){
//    Point p6 = new Point(1025, 2044, "6", 6, new ArrayList<Point>(), 4);
//    Point p7 = new Point(2024, 1047, "7", 7, new ArrayList<Point>(), 4);
//    Point p8 = new Point(1024, 1029, "8", 8, new ArrayList<Point>(), 4);
//
//    ArrayList<Point> ap = new ArrayList<Point>(Arrays.asList(p6,p7,p8));
//
//    dbe.update_points(ap);
//    Assertions.assertEquals(ap.get(1).getName(), dbe.get_point(7).getName());
//
//  }
//
//  @Test
//  void testGetAllPoints() throws SQLException{
//    Point p1 = new Point(1024, 2048, "", 1, new ArrayList<Point>(), 4);
//    Point p2 = new Point(2024, 1048, "", 2, new ArrayList<Point>(), 4);
//    Point p3 = new Point(1024, 1024, "", 3, new ArrayList<Point>(), 4);
//    Point p4 = new Point(2345, 1048, "", 4, new ArrayList<Point>(), 4);
//    Point p5 = new Point(2334, 1024, "", 5, new ArrayList<Point>(), 4);
//    Point p6 = new Point(1025, 2044, "6", 6, new ArrayList<Point>(), 4);
//    Point p7 = new Point(2024, 1047, "7", 7, new ArrayList<Point>(), 4);
//    Point p8 = new Point(1024, 1029, "8", 8, new ArrayList<Point>(), 4);
//
//    p1.neighbors = new ArrayList<Point>(Arrays.asList(p2, p3, p5, p4));
//    p2.neighbors = new ArrayList<>(Arrays.asList(p1,p3,p5));
//    p3.neighbors = new ArrayList<>(Arrays.asList(p2,p1,p4));
//    p4.neighbors = new ArrayList<>(Arrays.asList(p1,p3));
//    p5.neighbors = new ArrayList<>(Arrays.asList(p2,p1));
//    p6.neighbors = new ArrayList<>(Arrays.asList(p2,p3,p7));
//    p7.neighbors = new ArrayList<>(Arrays.asList(p1,p8,p6));
//    p8.neighbors = new ArrayList<>(Arrays.asList(p2,p1,p7));
//
//
//    int i;
//    int index = 0;
//    ArrayList<Point> points = dbe.getAllPoints();
//    for(i = 0;i < points.size();i++){
//      if(points.get(i).getId() == p7.getId()){
//        index = i;
//        System.out.println("index = " + index +  ",point + " + points.get(index).getId());
//        break;
//      }
//    }
//    Assertions.assertEquals(points.get(index).getId(), p7.getId());
//    Assertions.assertEquals(new FakePoint(points.get(index)).getNeighbors().contains(8), true);
//    Assertions.assertEquals(points.size(), 8);
//
//  }
//
//
//
//  @Test
//  void testUpdateAllPhysicians() throws  SQLException{
//    Physician physician4 = new Physician("Evan2", "Duffy2", "Doctor Professor", 4,
//        new ArrayList<FakePoint>(Arrays.asList(p1, p2)));
//    Physician physician5 = new Physician("Tom2", "White2", "Mister Doctor Professor", 5,
//        new ArrayList<FakePoint>(Arrays.asList(p3)));
//    Physician physician6 = new Physician("Spyros2", "Antonatos2", "Doctor Professor", 6,
//        new ArrayList<FakePoint>(Arrays.asList(p3, p4, p5)));
//
//    ArrayList<Physician> ap = new ArrayList<Physician>(Arrays.asList(physician4,physician5,physician6));
//
//    dbe.updatePhysicians(ap);
//    Assertions.assertEquals(ap.get(1).getTitle(), dbe.get_physician(5).getTitle());
//    System.out.println("HEY THIS IS A THING: " + dbe.get_physician(6).getID());
//    Assertions.assertEquals(dbe.get_physician(6).getLocations().get(1).getId(), 4);
//    Assertions.assertEquals(dbe.getAllPhysicians().size(),3);
//
//
//  }
//
//  @Test
//  void testGetAllPhysicians() throws SQLException{
//    Physician physician5 = new Physician("Tom2", "White2", "Mister Doctor Professor", 5,
//        new ArrayList<FakePoint>(Arrays.asList(p3)));
//
//    int i;
//    int index = 0;
//    ArrayList<Physician> physicians = dbe.getAllPhysicians();
//    for(i = 0;i < physicians.size();i++){
//      if(physicians.get(i).getID() == physician5.getID()){
//        index = i;
//        break;
//      }
//    }
//    Assertions.assertEquals(physicians.get(index).getID(), physician5.getID());
//    Assertions.assertEquals(physicians.get(index).getLocations().get(0).getId() == 3, true);
//    Assertions.assertEquals(physicians.size(), 3);
//
//  }
//
//}
