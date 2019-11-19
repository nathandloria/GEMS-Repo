package jgrader.parse;

import java.sql.*;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class Dbdataparser {
  private String[] ogerrors;
  private String[] eerrors;
  static int lport;
  static String rhost;
  static int rport;

  public Dbdataparser() {
  }

  static void go() {
    String user = "gems";
    String password = "gemsdb";
    String host = "141.195.6.18";
    int port = 22;
    try {
      JSch jsch = new JSch();
      Session session = jsch.getSession(user, host, port);
      lport = 4321;
      rhost = "localhost";
      rport = 3306;
      session.setPassword(password);
      session.setConfig("StrictHostKeyChecking", "no");
      session.connect();
      session.setPortForwardingL(lport, rhost, rport);
    }
    catch(Exception e) {}
  }

  public void setStringArrays() {
    try {
      go();
    } catch(Exception ex){
      ex.printStackTrace();
    }
    Connection conn = null;
    try {
      String driverName = "com.mysql.jdbc.Driver";
      String url = "jdbc:mysql://" + rhost + ":" + lport + "/gems";
      String dbUser = "gems";
      String dbPasswd = "gemsdb2019";
      Class.forName(driverName).getDeclaredConstructor().newInstance();
      conn = DriverManager.getConnection(url, dbUser, dbPasswd);
      Statement stat = conn.createStatement();
      ResultSet rest = null;
      rest = stat.executeQuery("SELECT * FROM gems_error_messages");
      int count = 0;
      while (rest.next()) {
        count++;
      }
      int i = 0;
      ogerrors = new String[count];
      eerrors = new String[count];
      rest = stat.executeQuery("SELECT * FROM gems_error_messages");
      while (rest.next()) {
        ogerrors[i] = rest.getString("ogerrors");
        eerrors[i] = rest.getString("eerrors");
        i++;
      }
      rest.close();
      stat.close();
      conn.close();
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
