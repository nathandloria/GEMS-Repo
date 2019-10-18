package jgrader;

import jgrader.compile.Compiler;
import jgrader.parse.CompileErrorParser;
import jgrader.util.FileFinder;
import java.util.ArrayList;
import java.util.Scanner;
import jgrader.updatedb.updater;
import java.io.*;

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
      ArrayList<String> fileNames = parser.getFileArr();
      
      if (compiler.getErrorNumInt() == 0) {
          System.out.println("\nSUCCESS! There were no errors found! Have a nice day!");
          System.exit(0);
        } else {
          System.out.println("\nFAILED! There were " + compiler.getErrorNumInt() + " errors found!");
        }
      
      System.out.println("\nWould you like to view the results in the terminal window or in a generated text file? (term/txt): ");
      String choiceterm = scan.next();
      if (choiceterm.equals("term")) {
    	  System.out.println("\nWould you like to view the original error messages or an enhanced version of these messages? (og/enh): ");
          String choice = scan.next();
          
          if (choice.equals("enh")) {
        	for (int i = 0; i < eMessageArr.size(); i++) {
        		System.out.println("\nError #" + (i + 1) + " @ Line " + lineNums.get(i) + " in file " + fileNames.get(i) + ": ");
        	  System.out.println(eMessageArr.get(i));
          	}
          } else if (choice.equals("og")) {
        	  for (int i = 0; i < eMessageArr.size(); i++) {
          		System.out.println("\nError #" + (i + 1) + " @ Line " + lineNums.get(i) + " in file " + fileNames.get(i) + ": ");
            	  System.out.println(ogMessageArr.get(i));
              }
          }
      } else if (choiceterm.equals("txt")) {
    	  BufferedWriter out = null;
    	  FileWriter fstream = null;
    	  try {
    		  @SuppressWarnings("unused")
    		  File file = new File(projectDirectory + "/out.txt");
    	      fstream = new FileWriter((projectDirectory + "/out.txt"), false); //true tells to append data.
    	      out = new BufferedWriter(fstream);
    	      for (int i = 0; i < eMessageArr.size(); i++) {
    	    	 if (i == 0) {
        	    	 fstream.write("+==================================================+\n");
    	    	 } else {
        	    	 fstream.write("\n\n+==================================================+\n");
    	    	 }
    	    	 fstream.write("Error #" + (i + 1) + "\n");
    	    	 fstream.write("File name: " + fileNames.get(i) + "\n");
    	    	 fstream.write("Line number: " + lineNums.get(i) + "\n");
    	    	 fstream.write("+--------------------------------------------------+\n");
    	    	 fstream.write("Original error message:\n" + "\t" + ogMessageArr.get(i) + "\n");
    	    	 fstream.write("+--------------------------------------------------+\n");
    	    	 fstream.write("Suggestion:\n" + "\t" + eMessageArr.get(i) + "\n");
    	    	 fstream.write("+==================================================+\n");
    	      }
    	      out.close();
    	  } catch (IOException e) {
    	      System.out.println(e);
    	  }
    	  System.out.println("\nThe results have been printed to a text file called \"out.txt\" in your project directory!");
      }
      for (int i = 0; i < ogMessageArr.size(); i++) {
    	  upd.update(ogMessageArr.get(i));
      }
      scan.close();
    }
  }
