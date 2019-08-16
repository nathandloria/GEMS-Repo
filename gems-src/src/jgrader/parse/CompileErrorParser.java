package jgrader.parse;

import java.util.ArrayList;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import jgrader.parse.objects.CompileErrorParseObject;

public class CompileErrorParser extends Parser<Diagnostic<? extends JavaFileObject>, CompileErrorParseObject> {

	private int numErrors;
	private int index;
	private static int low;
	private static int high;
	private static int mid;
	private static int i;
	private CompileErrorParseObject message;
	private static ArrayList<String> messageArrs;
	private String[] eErrorStrings;
	private String[] ogErrorStrings;
	private Dbdataparser data;

	public CompileErrorParser() {
		numErrors = 0;
		messageArrs = new ArrayList<>();
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

		message = new CompileErrorParseObject(diag.getMessage(null),
		numErrors, diag.getCode(), diag.getKind().toString(),
		diag.getStartPosition(), diag.getEndPosition(), diag.getSource().toString());

		index = setIndex(ogErrorStrings, diag.getMessage(null));

		if (index == -1) {
			return message;
		} else {
			if (diag.getMessage(null).equals(ogErrorStrings[index])) {
				setMessageArr("\nError #" + (numErrors + 1) + ":" + "\n===============================================================================\nStart Pos: "
				+ diag.getStartPosition() + "\n-------------------------------------------------------------------------------\nEnd Pos: "
				+ diag.getEndPosition() + "\n-------------------------------------------------------------------------------\nSource: "
				+ diag.getSource().toString() + "\n-------------------------------------------------------------------------------\nOriginal Message: "
				+ diag.getMessage(null) + "\n-------------------------------------------------------------------------------\nSuggestion: "
				+ eErrorStrings[index] + "\n===============================================================================");
			} else if (diag.getMessage(null).contains("non-static variable") && ogErrorStrings[index].contains("non-static variable")) {
				setMessageArr("\nError #" + (numErrors + 1) + ":" + "\n===============================================================================\nStart Pos: "
				+ diag.getStartPosition() + "\n-------------------------------------------------------------------------------\nEnd Pos: "
				+ diag.getEndPosition() + "\n-------------------------------------------------------------------------------\nSource: "
				+ diag.getSource().toString() + "\n-------------------------------------------------------------------------------\nOriginal Message: "
				+ diag.getMessage(null) + "\n-------------------------------------------------------------------------------\nSuggestion: "
				+ eErrorStrings[index] + "\n===============================================================================");
			} else if (diag.getMessage(null).contains("non-static method") && ogErrorStrings[index].contains("non-static method")) {
				setMessageArr("\nError #" + (numErrors + 1) + ":" + "\n===============================================================================\nStart Pos: "
				+ diag.getStartPosition() + "\n-------------------------------------------------------------------------------\nEnd Pos: "
				+ diag.getEndPosition() + "\n-------------------------------------------------------------------------------\nSource: "
				+ diag.getSource().toString() + "\n-------------------------------------------------------------------------------\nOriginal Message: "
				+ diag.getMessage(null) + "\n-------------------------------------------------------------------------------\nSuggestion: "
				+ eErrorStrings[index] + "\n===============================================================================");
			} else if (diag.getMessage(null).contains("should be declared in a file named") && ogErrorStrings[index].contains("should be declared in a file named")) {
				setMessageArr("\nError #" + (numErrors + 1) + ":" + "\n===============================================================================\nStart Pos: "
				+ diag.getStartPosition() + "\n-------------------------------------------------------------------------------\nEnd Pos: "
				+ diag.getEndPosition() + "\n-------------------------------------------------------------------------------\nSource: "
				+ diag.getSource().toString() + "\n-------------------------------------------------------------------------------\nOriginal Message: "
				+ diag.getMessage(null) + "\n-------------------------------------------------------------------------------\nSuggestion: "
				+ eErrorStrings[index] + "\n===============================================================================");
			} else if (diag.getMessage(null).contains("cannot be applied to given types") && ogErrorStrings[index].contains("cannot be applied to given types")) {
				setMessageArr("\nError #" + (numErrors + 1) + ":" + "\n===============================================================================\nStart Pos: "
				+ diag.getStartPosition() + "\n-------------------------------------------------------------------------------\nEnd Pos: "
				+ diag.getEndPosition() + "\n-------------------------------------------------------------------------------\nSource: "
				+ diag.getSource().toString() + "\n-------------------------------------------------------------------------------\nOriginal Message: "
				+ diag.getMessage(null) + "\n-------------------------------------------------------------------------------\nSuggestion: "
				+ eErrorStrings[index] + "\n===============================================================================");
			} else {
				System.out.println("There was a problem with the error message data. Sorry!");
				System.exit(0);
			}
		}
		numErrors++;
		return message;
	}

	public static int setIndex(String[] ogerr, String comperr) {
		i = searchBinary(ogerr, comperr);
		return i;
	}

	public static void setMessageArr(String str) {
		messageArrs.add(str);
	}

	public ArrayList<String> getMessageArr() {
		return messageArrs;
	}
}
