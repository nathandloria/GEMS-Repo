package jgrader.updatedb;

import java.sql.*;
import java.util.Arrays;
import java.util.ArrayList;

public class updater {
  private String[] errorMessages;
  private int index;
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

      for(int d = 0; d < emessages.size(); d++) {
        int count = 0;
        rest = stat.executeQuery("SELECT * FROM eMessagesTable");
        while (rest.next()) {
          count++;
        }
        int i = 0;
        errorMessages = new String[count];
        rest = stat.executeQuery("SELECT * FROM eMessagesTable");
        while (rest.next()) {
          errorMessages[i] = rest.getString("emessages");
          i++;
        }
        Arrays.sort(errorMessages);
        index = searchBinary(errorMessages, emessages.get(d));

        if (index == -1) {

          try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO eMessagesTable VALUES(?)")) {
            pstmt.setString(1, emessages.get(d));
            pstmt.executeUpdate();
          } catch(Exception ex) {
            System.out.println(ex);
          }

          // stmt.executeUpdate("INSERT INTO eMessagesTable VALUES (\"" + emessage + "\")");
        } else {
          boolean cont = false;
          String sub1 = emessages.get(d).substring(0, 5);
          String sub2 = emessages.get(d).substring(emessages.get(d).length() - 5, emessages.get(d).length());
          for (int g = 0; g < errorMessages.length; g++) {
            if (errorMessages[g].contains(sub1) && errorMessages[g].contains(sub2)) {
              cont = true;
            }
          }
          if (cont == false) {

            try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO eMessagesTable VALUES(?)")) {
              pstmt.setString(1, emessages.get(d));
              pstmt.executeUpdate();
            } catch(Exception ex) {
              System.out.println(ex);
            }

            // stmt.executeUpdate("INSERT INTO eMessagesTable VALUES (\"" + emessage + "\")");
          }
        }
      }
      rest.close();
      stat.close();
      conn.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static int searchBinary(String[] array, String str) {
    low = 0;
    high = array.length - 1;

    while (low <= high) {
      mid = (low + high) / 2;

      if (array[mid].contains("cannot find symbol") && str.contains("cannot find symbol")) {
				return mid;
			} else if (array[mid].contains("non-static variable") && str.contains("non-static variable")) {
				return mid;
			} else if (array[mid].contains("non-static method") && str.contains("non-static method")) {
				return mid;
			} else if (array[mid].contains("should be declared in a file named") && str.contains("should be declared in a file named")) {
				return mid;
			} else if (array[mid].contains("cannot be applied to given types") && str.contains("cannot be applied to given types")) {
				return mid;
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
