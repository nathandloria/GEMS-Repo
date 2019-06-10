package jgrader.compile;

import jgrader.SystemInteractor;
import java.nio.file.*;
import java.io.IOException;
import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;
import java.util.List;
import java.util.Collections;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Arrays;
import jgrader.parse.CompileErrorParser;
import jgrader.parse.objects.CompileErrorParseObject;
import java.util.StringJoiner;
import jgrader.util.FileFinder;

public class Compiler extends SystemInteractor {

    List<String> classes;
    String projectDirectory;
    String reportDirectory;

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
                .map(filename -> new JavaSourceFile(filename)).collect(Collectors.toList());

        // classes = classes.stream().map(str -> Paths.get(str).toFile().getName().replaceAll(".java", ""))
        // .collect(Collectors.toList());
        // System.out.println("CLASSES: " + classes);
        classes = null;
        List<String> options = Arrays.asList("-d", projectDirectory + "/classes");

        CompilationTask task = compiler.getTask(null, null, diagnostics, options, classes, compilationUnits);

        boolean success = task.call();

        CompileErrorParser parser = new CompileErrorParser();
        List<CompileErrorParseObject> parsed = diagnostics.getDiagnostics().stream().map(diag -> parser.parse(diag))
                .collect(Collectors.toList());

        System.out.println(parser.complete());

        if (!success)
            report(parsed);

        return success ? SUCCESS : ERROR;
    }

    public void report(List<CompileErrorParseObject> errors) {
        StringJoiner joiner = new StringJoiner("\n-----\n");
        errors.forEach(error -> joiner.add(error.toString()));
        try {
            Files.write(Paths.get(reportDirectory + "/compile-errors.txt"), joiner.toString().getBytes());
        } catch (IOException ex) {
            System.err.println("Failed to write compile error report to " + reportDirectory + "/compile-errors.txt");
        }
    }
}
