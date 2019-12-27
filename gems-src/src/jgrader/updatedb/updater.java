package jgrader.updatedb;

import java.sql.*;
import java.util.Arrays;
import java.util.ArrayList;
import jgrader.parse.Dbdataparser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class updater {
  private String[] errorMessages;
  private int index = 0;
  private static int low;
  private static int high;
  private static int mid;
  String user = "gems";
  String password = "gemsdb";
  String host = "141.195.6.18";
  int port = 22;
  int lport = 4321;
  String rhost = "localhost";
  static int rport = 3306;

  public updater() {
  }

  public void update(ArrayList<String> emessages) {
    Connection conn = null;
    try {
      String driverName = "com.mysql.jdbc.Driver";
      String url = "jdbc:mysql://" + rhost + ":" + lport + "/gems";
      String dbUser = "gems";
      String dbPasswd = "gemsdb2019";

      try {
        Class.forName(driverName).getDeclaredConstructor().newInstance();
      } catch(Exception ex) {
        System.out.println(ex);
      }

      conn = DriverManager.getConnection(url, dbUser, dbPasswd);
      Statement stat = conn.createStatement();
      ResultSet rest = null;

      Dbdataparser bdp = new Dbdataparser();
      bdp.setStringArrays();
      errorMessages = bdp.getOgErrorArr();

      for (int i = 0; i < emessages.size(); i++) {
        index = searchBinary(errorMessages, emessages.get(i));
        if (index == -1) {
          try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO eMessagesTable VALUES(?)")) {
            pstmt.setString(1, emessages.get(i));
            pstmt.executeUpdate();
          } catch(Exception ex) {
            System.out.println(ex);
          }
        }
      }
      stat.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static int searchBinary(String[] array, String str) {
    low = 0;
		high = array.length - 1;

		while (low <= high) {
			mid = (low + high) / 2;

			if (array[mid].contains("(.*)")) {
				Pattern p = Pattern.compile(array[mid]);
				if (p.matcher(str).matches()) {
					return mid;
				}
			}
			if (array[mid].compareTo(str) < 0) {
				low = mid + 1;
			} else if (array[mid].compareTo(str) > 0) {
				high = mid - 1;
			} else {
				return mid;
			}
		}
		return -1;
  }
}
