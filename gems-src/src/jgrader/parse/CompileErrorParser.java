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
		String[] ogErrorStrings;
		String[] eErrorStrings;
		int ogLineNum = 0;
		int eLineNum = 0;
		int count = 0;
		String dir = System.getProperty("user.dir");
		//simple print for now
		try {
			File ogerrorFile = new File(dir + "/src/jgrader/errors/ogerrors.txt");
			File eerrorFile = new File(dir + "/src/jgrader/errors/enhancederrors.txt");
			FileReader ogerror = new FileReader(ogerrorFile);
			FileReader eerror = new FileReader(eerrorFile);
			Scanner ogerrorScan = new Scanner(ogerrorFile);
			Scanner eerrorScan = new Scanner(eerrorFile);
			LineNumberReader ogLineCounter = new LineNumberReader(ogerror);
			LineNumberReader eLineCounter = new LineNumberReader(eerror);
			while (ogLineCounter.readLine() != null) {
				ogLineNum++;
			}
			while (eLineCounter.readLine() != null) {
				eLineNum++;
			}
			ogErrorStrings = new String[ogLineNum];
			eErrorStrings = new String[eLineNum];
			if (ogLineNum == eLineNum) {
				while (ogerrorScan.hasNextLine()) {
					ogErrorStrings[count] = ogerrorScan.nextLine();
					eErrorStrings[count] = eerrorScan.nextLine();
					count++;
				}
				for (int i = 0; i < ogErrorStrings.length; i++) {
					if (diag.getMessage(null).equals(ogErrorStrings[i])) {
						message.setEnhanced(eErrorStrings[i]);
						message.printSuggestion();
					}
				}
			} else {
				System.out.println("There was a problem with the error message files. Sorry!");
				System.exit(0);
			}
		} catch (Exception x) {
			System.out.println(x);
		}
		numErrors++;
		return message;
	}
}
