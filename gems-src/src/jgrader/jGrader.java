package jgrader;

import jgrader.compile.Compiler;
import jgrader.execute.Executor;
import jgrader.gui.Gui;
import jgrader.util.FileFinder;

public class jGrader {
  public static void main(String[] args) {
    Gui gui = new Gui();
    gui.setGui();
    String projectDirectory = gui.getProjectDirectory();
    String reportDirectory = System.getProperty("user.dir");

    Compiler compiler = new Compiler(projectDirectory,
    FileFinder.convertToFileNames(FileFinder.find(projectDirectory, "**.java")), reportDirectory);

    int result = compiler.run();

    for (int i = 0; i < compiler.getErrorNumInt(); i++) {
      if (i == 0) {
        gui.appendTextArea(compiler.getArrayList().get(i).substring(1, compiler.getArrayList().get(i).length()));
      } else {
        gui.appendTextArea("\n" + compiler.getArrayList().get(i));
      }
    }

    if (compiler.getErrorNumInt() == 0) {
      gui.setGuiDone("SUCCESS! There were no errors found!");
    } else {
      gui.setGuiDone("FAILED! There were " + compiler.getErrorNumInt() + " errors found!");
    }

    if (result == SystemInteractor.SUCCESS) {
      Executor executor = new Executor();
      result = executor.run();
    }
  }
}
