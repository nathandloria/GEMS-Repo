package jgrader;

import jgrader.compile.Compiler;
import jgrader.SystemInteractor;
import jgrader.execute.Executor;
import jgrader.util.FileFinder;
import java.nio.file.Paths;
import java.io.IOException;

public class jGrader {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("You must specify a project directory, where /src contains the java source files!");
            return;
        }

        String projectDirectory, reportDirectory;

        try {
            reportDirectory = projectDirectory = Paths.get(args[0]).toRealPath().toString();
        } catch (IOException ex) {
            System.err.println("Failed to read project directory " + args[0]);
            reportDirectory = projectDirectory = ".";
        }

        // compile everything in projectDirectory/src, put it in projectDirectory/classes, and report on errors to the current working directory, in a file called compile-errors.txt
        Compiler compiler = new Compiler(projectDirectory,
                FileFinder.convertToFileNames(FileFinder.find(projectDirectory + "/src", "**.java")), reportDirectory);

        int result = compiler.run();

        if (result == SystemInteractor.SUCCESS) {
            Executor executor = new Executor();

            result = executor.run();

        }

    }
}
