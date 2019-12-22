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
  private String str = null;
  private ArrayList<String> varNames = null;

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
          if (code.get(i).contains(")") == false && code.get(i).contains("int")) {
            varName = code.get(i).substring(code.get(i).indexOf("int") + 4, code.get(i).indexOf("=") - 1);
          }
          if (code.get(i).contains("public")) {
            if (code.get(i).substring(code.get(i).indexOf("public"), code.get(i).indexOf("find")).contains("int")) {
              code.set(((int)lineNum - 1), (code.get((int)lineNum - 1).replace(";", " " + varName + ";")));
            }
          }
        }
      } else if (err.equals("incompatible types: possible lossy conversion from double to int")) {
        if (code.get((int)lineNum - 1).contains("int")) {
          str = code.get((int)lineNum - 1).replace("int", "double");
          code.set(((int)lineNum - 1), str);
        }
      } else if (err.contains("cannot find symbol")) {
        varNames = new ArrayList<String>();
        for (int i = ((int)lineNum - 1); i >= 0; i--) {
          if (code.get(i).contains("public")) {
            break;
          }
          if (code.get(i).contains("int") && code.get(i).contains("=") && code.get(i).contains(";")) {
            varName = code.get(i).substring(code.get(i).indexOf("int") + 4, code.get(i).indexOf("=") - 1);
            varNames.add(varName);
          }
        }
        if (varNames.size() > 0) {
          for (int g = 0; g < varNames.size(); g++) {
            if ("gmax".contains(varNames.get(g))) {
              varName = varNames.get(g);
            }
          }
        }
        code.set(((int)lineNum - 1), code.get(((int)lineNum - 1)).replace("gmax", varName));
      } else if (err.contains("method findAvg in class Lab1 cannot be applied to given types")) {
        for (int i = ((int)lineNum - 1); i >= 0; i--) {
          if (code.get(i).contains("findAvg") && code.get(i).contains(",")) {
            code.set(((int)lineNum - 1), code.get(((int)lineNum - 1)).replace("grades", "grades, size"));
          }
        }
      }

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
