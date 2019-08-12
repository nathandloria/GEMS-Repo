package jgrader.compile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import jgrader.SystemInteractor;
import jgrader.parse.CompileErrorParser;
import jgrader.util.FileFinder;

public class Compiler extends SystemInteractor {

    ArrayList<String> errorMessages;
    List<String> classes;
    String projectDirectory;
    String reportDirectory;
    int errorNum;
	// main class is the first in the list
    public Compiler(String projectDirectory, List<String> classes, String reportDirectory) {
        this.classes = classes;
        this.projectDirectory = projectDirectory;
        this.reportDirectory = reportDirectory;
    }

    @Override
    public int run() {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        // StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        // Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(classes);

        List<JavaFileObject> compilationUnits = classes.stream().map(filename -> FileFinder.toRealPath(filename))
                .map(filename -> new JavaSourceFile(filename.replace(" ", "%20"))).collect(Collectors.toList());

        // classes = classes.stream().map(str -> Paths.get(str).toFile().getName().replaceAll(".java", ""))
        // .collect(Collectors.toList());
        // System.out.println("CLASSES: " + classes);
        classes = null;

        List<String> options = Arrays.asList("-d", projectDirectory);
        if (Files.exists(Paths.get(projectDirectory + "/classes")) == true) {
          options = Arrays.asList("-d", projectDirectory + "/classes");
        }

        CompilationTask task = compiler.getTask(null, null, diagnostics, options, classes, compilationUnits);

        boolean success = task.call();

        CompileErrorParser parser = new CompileErrorParser();
        diagnostics.getDiagnostics().stream().map(diag -> parser.parse(diag))
                .collect(Collectors.toList());

        errorNum = parser.getErrorNum();
        errorMessages = parser.getMessageArr();

        return success ? SUCCESS : ERROR;
    }

    public ArrayList<String> getArrayList() {
      return errorMessages;
    }

    public int getErrorNumInt() {
      return errorNum;
    }
}
