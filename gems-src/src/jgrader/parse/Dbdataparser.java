package jgrader.parse;

import java.sql.*;

public class Dbdataparser {
  private String[] ogerrors;
  private String[] eerrors;

  public Dbdataparser() {
  }

  public void setStringArrays() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      Connection con = DriverManager.getConnection (
      "jdbc:mysql://gems-db-instance-1-rds.cxg9j6dtitgh.us-east-2.rds.amazonaws.com:3306/errors","admin","gemsTest123"
      );
      //here sonoo is database name, root is username and password
      System.out.println(con);
      Statement stmt = con.createStatement();
      ResultSet rs;
      int count = 0;
      rs = stmt.executeQuery("SELECT * FROM eMessages");
      while (rs.next()) {
        count++;
      }
      int i = 0;
      ogerrors = new String[count];
      eerrors = new String[count];
      rs = stmt.executeQuery("SELECT * FROM eMessages");
      while (rs.next()) {
        ogerrors[i] = rs.getString("ogerrors");
        eerrors[i] = rs.getString("eerrors");
        i++;
      }
      rs.close();
      stmt.close();
      con.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public String[] getOgErrorArr() {
    return ogerrors;
  }

  public String[] getEnhErrorArr() {
    return eerrors;
  }
}
