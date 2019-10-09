package jgrader;

import jgrader.compile.Compiler;
import jgrader.parse.CompileErrorParser;
import jgrader.util.FileFinder;
import java.util.ArrayList;
import java.util.Scanner;
import jgrader.updatedb.updater;

public class jGrader {
  public static void main(String[] args) {
	  Scanner scan = new Scanner(System.in);
	  CompileErrorParser parser = new CompileErrorParser();
	  updater upd = new updater();
	  String projectDirectory;
	  String reportDirectory;
	  Compiler compiler;
      projectDirectory = System.getProperty("user.dir");
      reportDirectory = System.getProperty("user.dir");

      compiler = new Compiler(projectDirectory,
      FileFinder.convertToFileNames(FileFinder.find(projectDirectory, "**.java")), reportDirectory);

      System.out.println("Compiling Java Programs...");
      
      compiler.run();
      
      ArrayList<String> ogMessageArr = parser.getOmessageArr();
      ArrayList<String> eMessageArr = parser.getEmessageArr();
      ArrayList<Long> lineNums = parser.getLineNumberArr();
      
      if (compiler.getErrorNumInt() == 0) {
          System.out.println("\nSUCCESS! There were no errors found! Have a nice day!");
          System.exit(0);
        } else {
          System.out.println("\nFAILED! There were " + compiler.getErrorNumInt() + " errors found!");
        }
      
      System.out.println("\nWould you like to view the original error messages or an enhanced version of these messages? (og/enh): ");
      String choice = scan.nextLine();
      
      if (choice.equals("enh")) {
    	for (int i = 0; i < eMessageArr.size(); i++) {
    		System.out.println("\nError #" + (i + 1) + " @ Line " + lineNums.get(i) + ": ");
    	  System.out.println(eMessageArr.get(i));
      	}
      } else if (choice.equals("og")) {
    	  for (int i = 0; i < eMessageArr.size(); i++) {
    		  System.out.println("\nError #" + (i + 1) + " @ Line " + lineNums.get(i) + ": ");
        	  System.out.println(ogMessageArr.get(i));
          }
      }
      
      for (int i = 0; i < ogMessageArr.size(); i++) {
    	  upd.update(ogMessageArr.get(i));
      }
      scan.close();
    }
  }
