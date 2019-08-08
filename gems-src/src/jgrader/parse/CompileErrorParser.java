package jgrader.parse;

import java.util.ArrayList;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import jgrader.parse.objects.CompileErrorParseObject;

public class CompileErrorParser extends Parser<Diagnostic<? extends JavaFileObject>, CompileErrorParseObject> {

	int numErrors;
	ArrayList<String> messageArrs;

	public CompileErrorParser() {
		numErrors = 0;
		messageArrs = new ArrayList<>();
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

	public CompileErrorParseObject parse(Diagnostic<? extends JavaFileObject> diag) {

		CompileErrorParseObject message = new CompileErrorParseObject(diag.getMessage(null),
		numErrors, diag.getCode(), diag.getKind().toString(),
		diag.getStartPosition(), diag.getEndPosition(), diag.getSource().toString());
		Dbdataparser data = new Dbdataparser();
		data.setStringArrays();
		String[] ogErrorStrings = data.getOgErrorArr();
		String[] eErrorStrings = data.getEnhErrorArr();
		try {
			if (ogErrorStrings.length == eErrorStrings.length) {
				for (int i = 0; i < ogErrorStrings.length; i++) {
					if (diag.getMessage(null).equals(ogErrorStrings[i])) {
						setMessageArr("\nError #" + (numErrors + 1) + ":" + "\n===============================================================================\nStart Pos: "
				                + diag.getStartPosition() + "\n-------------------------------------------------------------------------------\nEnd Pos: "
				                + diag.getEndPosition() + "\n-------------------------------------------------------------------------------\nOriginal Message: "
				                + diag.getMessage(null) + "\n-------------------------------------------------------------------------------\nSuggestion: "
				                + eErrorStrings[i] + "\n===============================================================================");
					} else if (diag.getMessage(null).contains("non-static variable") && ogErrorStrings[i].contains("non-static variable")) {
						setMessageArr("\nError #" + (numErrors + 1) + ":" + "\n===============================================================================\nStart Pos: "
				                + diag.getStartPosition() + "\n-------------------------------------------------------------------------------\nEnd Pos: "
				                + diag.getEndPosition() + "\n-------------------------------------------------------------------------------\nOriginal Message: "
				                + diag.getMessage(null) + "\n----------------------------------------------------------------------------------\nSuggestion: "
				                + eErrorStrings[i] + "\n===============================================================================");
					} else if (diag.getMessage(null).contains("non-static method") && ogErrorStrings[i].contains("non-static method")) {
						setMessageArr("\nError #" + (numErrors + 1) + ":" + "\n===============================================================================\nStart Pos: "
				                + diag.getStartPosition() + "\n-------------------------------------------------------------------------------\nEnd Pos: "
				                + diag.getEndPosition() + "\n-------------------------------------------------------------------------------\nOriginal Message: "
				                + diag.getMessage(null) + "\n-------------------------------------------------------------------------------\nSuggestion: "
				                + eErrorStrings[i] + "\n===============================================================================");
					} else if (diag.getMessage(null).contains("should be declared in a file named") && ogErrorStrings[i].contains("should be declared in a file named")) {
						setMessageArr("\nError #" + (numErrors + 1) + ":" + "\n===============================================================================\nStart Pos: "
				                + diag.getStartPosition() + "\n-------------------------------------------------------------------------------\nEnd Pos: "
				                + diag.getEndPosition() + "\n-------------------------------------------------------------------------------\nOriginal Message: "
				                + diag.getMessage(null) + "\n-------------------------------------------------------------------------------\nSuggestion: "
				                + eErrorStrings[i] + "\n===============================================================================");
					} else if (diag.getMessage(null).contains("cannot be applied to given types") && ogErrorStrings[i].contains("cannot be applied to given types")) {
						setMessageArr("\nError #" + (numErrors + 1) + ":" + "\n===============================================================================\nStart Pos: "
				                + diag.getStartPosition() + "\n-------------------------------------------------------------------------------\nEnd Pos: "
				                + diag.getEndPosition() + "\n-------------------------------------------------------------------------------\nOriginal Message: "
				                + diag.getMessage(null) + "\n-------------------------------------------------------------------------------\nSuggestion: "
				                + eErrorStrings[i] + "\n===============================================================================");
					}
				}
			} else {
				System.out.println("There was a problem with the error message data. Sorry!");
				System.exit(0);
			}
		} catch (Exception x) {
			System.out.println(x);
		}
		numErrors++;
		return message;
	}

	public void setMessageArr(String str) {
		messageArrs.add(str);
	}

	public ArrayList<String> getMessageArr() {
		return messageArrs;
	}
}
