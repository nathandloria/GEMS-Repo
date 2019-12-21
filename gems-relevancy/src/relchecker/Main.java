package relchecker;

import relchecker.compile.Compiler;
import relchecker.parse.CompileErrorParser;
import relchecker.util.FileFinder;
import relchecker.solver.Solver;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.awt.Desktop;

public class Main {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    CompileErrorParser parser = new CompileErrorParser();
    Solver solver = new Solver();
    String projectDirectory;
    String reportDirectory;
    Compiler compiler;
    projectDirectory = System.getProperty("user.dir");
    reportDirectory = System.getProperty("user.dir");

    System.out.println("Compiling Java Programs...");

    compiler = new Compiler(projectDirectory,
    FileFinder.convertToFileNames(FileFinder.find(projectDirectory, "**.java")), reportDirectory);

    compiler.run();

    ArrayList<String> ogMessageArr = parser.getOmessageArr();
    ArrayList<String> eMessageArr = parser.getEmessageArr();
    ArrayList<Long> lineNums = parser.getLineNumberArr();
    ArrayList<String> fileNames = parser.getFileArr();

    if (compiler.getErrorNumInt() == 0) {
      System.out.println("\nSUCCESS! There were no errors found! Have a nice day!");
      System.exit(0);
    } else {
      System.out.println("\nFAILED! There were " + compiler.getErrorNumInt() + " errors found! Fixing errors...");
    }

    for(int i = 0; i < ogMessageArr.size(); i++) {
      System.out.println(ogMessageArr.get(i));
      solver.resolve(ogMessageArr.get(i), fileNames.get(i), projectDirectory, lineNums.get(i));
    }

    scan.close();
    System.exit(0);
  }
}
