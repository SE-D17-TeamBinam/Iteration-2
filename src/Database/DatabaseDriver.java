//import java.sql.*;
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Registers, Connects to, and sends commands to a database
 * Created by Evan on 4/3/2017.
 */
public class DatabaseDriver {

  private Connection conn = null;
  private Statement stmt = null;
  private String url = "";
  private String driver = "";

  public DatabaseDriver(String _driver, String _url){
    this.url = _url;
    this.driver = _driver;
    try {
      this.registerDriver();
      this.connect();
    }catch (SQLException e){
      System.out.println("Error Connecting To DB, Trying Again");
      try {
        this.connect();
      }catch (SQLException e2){
        System.out.println("Database Connection Error");
        System.out.println(e.getSQLState());
      }
    }
    catch (ClassNotFoundException e){
      System.out.println("\u001B[31m" + "Could not find Database Driver Jar File, make sure tou add it to the classpath!" + "\u001B[30m");
    }
  }

  public String[] Parser(String commands) {
    String[] SList;
    SList = commands.split(";");
    return SList;
  }

  public ArrayList<ResultSet> send_Command(String commands) {
    String command;
    String[] cList = this.Parser(commands);
    ArrayList<ResultSet> listrs = new ArrayList<ResultSet>();
    int i;
    ResultSet rs;
    for (i = 0; i < cList.length; i++) {
      command = cList[i];
      if (command.length() > 2) {
        System.out.println("command:" + command);
        try {
          stmt = conn.createStatement();
          rs = stmt.executeQuery(command);
          listrs.add(rs);
          //return rs;

        } catch (SQLException e) {
          System.out.println("Error Querying, Trying Execute...");
          try {
            stmt.execute(command);
            //listrs.add(rs);
            System.out.println("Executed Successfully");

          } catch (SQLException e2) {
            e.printStackTrace();
            System.out.println("Query Error: " + e.getSQLState());
          }
        }
      }

    }
    return listrs;
  }

  private boolean registerDriver() throws ClassNotFoundException {
    Class.forName(driver);
    return true;
  }

  boolean connect() throws SQLException {
    conn = DriverManager.getConnection(url);
    return true;
  }

  @Override
  public String toString() {
    if (url.length() > 0) {
      return "DatabaseDriver connected to " + url;
    }
    return "Unconnected DatabaseDriver";
  }

}
