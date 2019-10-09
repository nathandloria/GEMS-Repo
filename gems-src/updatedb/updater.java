package jgrader.updatedb;

import java.sql.*;
import java.util.Arrays; 

public class updater {
	private String[] errorMessages;
	private int index;
	private static int low;
	private static int high;
	private static int mid;
	
	public updater() {
	}
	
	public void update(String emessage) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		    Connection con = DriverManager.getConnection (
		    		"jdbc:mysql://test-int-database.cxg9j6dtitgh.us-east-2.rds.amazonaws.com/innodb", "admin", "npj3sTTOgk3UhKuCSyof"
		    );
		    Statement stmt = con.createStatement();
		    ResultSet rs;
		    int count = 0;
		    rs = stmt.executeQuery("SELECT * FROM eMessagesTable");
		    while (rs.next()) {
		    	count++;
		    }
		    int i = 0;
		    errorMessages = new String[count];
		    rs = stmt.executeQuery("SELECT * FROM eMessagesTable");
		    while (rs.next()) {
		    	errorMessages[i] = rs.getString("emessages");
		    	i++;
		    }
		    Arrays.sort(errorMessages);
		    index = searchBinary(errorMessages, emessage);

		    if (index == -1) {
	    		stmt.executeUpdate("INSERT INTO eMessagesTable VALUES (\"" + emessage + "\")");
	    	} else {
	    		boolean cont = false;
	    		String sub1 = emessage.substring(0, 5);
	    		String sub2 = emessage.substring(emessage.length() - 5, emessage.length());
	    		for (int g = 0; g < errorMessages.length; g++) {
	    			if (errorMessages[g].contains(sub1) && errorMessages[g].contains(sub2)) {
	    	    		cont = true;
	    			}
	    		}
	    		if (cont == false) {
		    		stmt.executeUpdate("INSERT INTO eMessagesTable VALUES (\"" + emessage + "\")");
	    		}
	    	}
		    rs.close();
		    stmt.close();
		    con.close();
			} catch (Exception e) {
		      System.out.println(e);
			}
	}
	
	public static int searchBinary(String[] array, String str) {
		low = 0;
		high = array.length - 1;

		while (low <= high) {
			mid = (low + high) / 2;

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