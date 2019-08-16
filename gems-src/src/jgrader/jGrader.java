package jgrader;

import jgrader.compile.Compiler;
import jgrader.gui.Gui;
import jgrader.util.FileFinder;

public class jGrader {
  public static void main(String[] args) {
	boolean redo = true;
	int count = 0;
    String projectDirectory;
    String reportDirectory;
    Compiler compiler;
    Gui gui;
    while (redo == true) {
      redo = false;
      gui = new Gui();
      projectDirectory = "";
      if (count > 0) {
    	  gui.setAgain();
      }
      gui.setGui();
      projectDirectory = gui.getProjectDirectory();
      reportDirectory = System.getProperty("user.dir");

      compiler = new Compiler(projectDirectory,
      FileFinder.convertToFileNames(FileFinder.find(projectDirectory, "**.java")), reportDirectory);

      compiler.run();
      
      for (int i = 0; i < compiler.getErrorNumInt(); i++) {
        if (i == 0) {
          gui.appendTextArea(compiler.getArrayList().get(i).substring(1, compiler.getArrayList().get(i).length()));
        } else {
          gui.appendTextArea("\n" + compiler.getArrayList().get(i));
        }
      }

      if (compiler.getErrorNumInt() == 0) {
        gui.setRedoButton("SUCCESS! There were no errors found!");
        redo = gui.getRedo();
      } else {
        gui.setRedoButton("FAILED! There were " + compiler.getErrorNumInt() + " errors found!");
        redo = gui.getRedo();
      }
      
      count++;
    }
  }
}
