package jgrader.parse;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.LineNumberReader;
import java.util.Scanner;
import jgrader.parse.objects.CompileErrorParseObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class CompileErrorParser extends Parser<Diagnostic<? extends JavaFileObject>, CompileErrorParseObject> {

	int numErrors;

	public CompileErrorParser() {
		numErrors = 0;
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
						message.setEnhanced(eErrorStrings[i]);
						message.printSuggestion();
					} else if (diag.getMessage(null).contains("non-static variable") && ogErrorStrings[i].contains("non-static variable")) {
						message.setEnhanced(eErrorStrings[i]);
						message.printSuggestion();
					} else if (diag.getMessage(null).contains("non-static method") && ogErrorStrings[i].contains("non-static method")) {
						message.setEnhanced(eErrorStrings[i]);
						message.printSuggestion();
					} else if (diag.getMessage(null).contains("should be declared in a file named") && ogErrorStrings[i].contains("should be declared in a file named")) {
						message.setEnhanced(eErrorStrings[i]);
						message.printSuggestion();
					} else if (diag.getMessage(null).contains("cannot be applied to given types") && ogErrorStrings[i].contains("cannot be applied to given types")) {
						message.setEnhanced(eErrorStrings[i]);
						message.printSuggestion();
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
}
