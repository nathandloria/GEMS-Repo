package relchecker.solver;

import java.util.*;
import java.io.*;

public class Solver {
  private ArrayList<String> code = new ArrayList<>();
  private File javaFile = null;
  private File corrFile = null;
  private File txtFile = null;
  private FileWriter fw = null;
  private Scanner scan = null;
  private String varName = null;

  public Solver() {}

  public void resolve(String err, String fileName, String directory, long lineNum) {
    try {
      corrFile = new File(directory + "/" + fileName.substring(0, fileName.indexOf(".java")) + "_corrected.java");
      txtFile = new File(directory + "/" + fileName.substring(0, fileName.indexOf(".java")) + ".txt");
      javaFile = new File(directory + "/" + fileName);
      scan = new Scanner(javaFile);

      while(scan.hasNextLine()) {
        code.add(scan.nextLine());
      }

      if (err.equals("';' expected")) {
        if(!code.get((int)lineNum - 1).contains(";")) {
          code.set(((int)lineNum - 1), (code.get((int)lineNum - 1) + ";"));
        }
      } else if (err.equals("incompatible types: missing return value")) {
        for (int i = ((int)lineNum - 1); i >= 0; i--) {
          if (code.get(i).contains(")") == false) {
            if (code.get(i).contains("int")) {
              varName = code.get(i).substring(code.get(i).indexOf("int") + 4, code.get(i).indexOf("=") - 1);
            }
          }
          if (code.get(i).contains("public")) {
            if (code.get(i).substring(code.get(i).indexOf("public"), code.get(i).indexOf("find")).contains("int")) {
              code.set(((int)lineNum - 1), (code.get((int)lineNum - 1).replace(";", " " + varName + ";")));
            }
          }
        }
      } else if (err.equals)

      fw = new FileWriter(txtFile);

      for(int i = 0; i < code.size(); i++) {
        fw.write(code.get(i) + "\n");
      }

      txtFile.renameTo(corrFile);
      fw.close();
    } catch(Exception x) {
      System.out.println(x);
    }
  }
}
