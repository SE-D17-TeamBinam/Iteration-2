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

  public DatabaseDriver(String _driver, String _url) {
    this.url = _url;
    this.driver = _driver;
    try {
      this.registerDriver();
      this.connect();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("SQL Exception Occurred, check that the url is correct");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.out.println(
          "Class Not Found Exception Occurred, check that the driver is correct and installed");
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
            System.out.println("Query Error!");
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

  private boolean connect() throws SQLException {
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
