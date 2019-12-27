package jgrader.parse;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import jgrader.parse.objects.CompileErrorParseObject;
import jgrader.updatedb.updater;

public class CompileErrorParser extends Parser<Diagnostic<? extends JavaFileObject>, CompileErrorParseObject> {

	private int numErrors;
	private int index;
	private static int low;
	private static int high;
	private static int mid;
	private CompileErrorParseObject message;
	private static ArrayList<String> eMessageArrs;
	private static ArrayList<String> oMessageArrs;
	private static ArrayList<Long> lineNumArr;
	private static ArrayList<String> fileArr;
	private static ArrayList<String> compArr;
	private String[] eErrorStrings;
	private String[] ogErrorStrings;
	private Dbdataparser data;

	public CompileErrorParser() {
		new updater();
		numErrors = 0;
		new ArrayList<>();
		eMessageArrs = new ArrayList<>();
		oMessageArrs = new ArrayList<>();
		lineNumArr = new ArrayList<>();
		fileArr = new ArrayList<>();
		compArr = new ArrayList<>();
		data = new Dbdataparser();
		data.setStringArrays();
		ogErrorStrings = data.getOgErrorArr();
		eErrorStrings = data.getEnhErrorArr();
	}

	public int getErrorNum() {
		return numErrors;
	}

	public String complete() {
		if (numErrors > 0) {
			return "FAILED! There were " + numErrors + " errors!";
		} else {
			return "SUCCESS! There were no errors!";
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

	public CompileErrorParseObject parse(Diagnostic<? extends JavaFileObject> diag) {
		updateOmessageArr(diag.getMessage(null));
		updateLineNumberArr(diag.getLineNumber());
		String temp = "[file:///" + System.getProperty("user.dir") + "/";
		updateFileArr(diag.getSource().toString().substring(diag.getSource().toString().indexOf(temp) + temp.length(), diag.getSource().toString().length() - 1));

		index = searchBinary(ogErrorStrings, diag.getMessage(null));

		if (index == -1) {
			updateEmessageArr("Error Not Logged - Sorry!");
			updateCompMessageArr("");
		} else {
			if (ogErrorStrings[index].contains("(.*)")) {
				Pattern pat = Pattern.compile(ogErrorStrings[index]);
				if (pat.matcher(diag.getMessage(null)).matches()) {
					updateEmessageArr(eErrorStrings[index]);
					updateCompMessageArr(ogErrorStrings[index]);
				} else {
					updateCompMessageArr("");
				}
			} else if (diag.getMessage(null).equals(ogErrorStrings[index])) {
					updateEmessageArr(eErrorStrings[index]);
					updateCompMessageArr(ogErrorStrings[index]);
			} else {
				System.out.println("There was a problem with the error message data. Sorry!");
				System.exit(0);
			}
		}
		numErrors++;
		return message;
	}

	public static void updateEmessageArr(String str) {
		eMessageArrs.add(str);
	}

	public static void updateOmessageArr(String str) {
		oMessageArrs.add(str);
	}

	public ArrayList<String> getEmessageArr() {
		return eMessageArrs;
	}

	public ArrayList<String> getOmessageArr() {
		return oMessageArrs;
	}

	public static void updateLineNumberArr(long linenum) {
		lineNumArr.add(linenum);
	}

	public ArrayList<Long> getLineNumberArr() {
		return lineNumArr;
	}

	public static void updateFileArr(String str) {
		fileArr.add(str);
	}

	public ArrayList<String> getFileArr() {
		return fileArr;
	}

	public static void updateCompMessageArr(String str) {
		compArr.add(str);
	}

	public ArrayList<String> getCompMessageArr() {
		return compArr;
	}
}
