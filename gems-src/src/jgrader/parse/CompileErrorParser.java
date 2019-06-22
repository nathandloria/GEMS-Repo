package jgrader.parse;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Scanner;
import jgrader.parse.objects.CompileErrorParseObject;
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

		String[] ogErrorStrings;
    String[] eErrorStrings;
    int ogLineNum = 0;
    int eLineNum = 0;
    int count = 0;
		String dir = System.getProperty("user.dir");
		//simple print for now
		System.out.println("Error #" + (numErrors + 1));
		System.out.println("----------------------------------------------------");
		System.out.println("Code: " + diag.getCode());
		System.out.println("Kind: " + diag.getKind());
		System.out.println("Position: " + diag.getPosition());
		System.out.println("Start Position: " + diag.getStartPosition());
		System.out.println("End Position: " + diag.getEndPosition());
		System.out.println("Source: " + diag.getSource());
		System.out.println("Message: " + diag.getMessage(null));
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
						System.out.println("Suggestion: " + eErrorStrings[i]);
					}
				}
      } else {
        System.out.println("There was a problem with the error message files. Sorry!");
        System.exit(0);
      }
    } catch (Exception x) {
      System.out.println(x);
    }
		System.out.println("----------------------------------------------------");
		//do parsing
		CompileErrorParseObject error = new CompileErrorParseObject("", "", diag.getMessage(null));

		numErrors++;
		return error;
	}
}
