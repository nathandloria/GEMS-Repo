package jgrader;

import jgrader.compile.Compiler;
import jgrader.SystemInteractor;
import jgrader.execute.Executor;
import jgrader.util.FileFinder;
import java.util.Scanner;
import java.nio.file.Paths;
import java.io.IOException;

public class jGrader {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String projectDirectory, reportDirectory, userSpecPath;
        System.out.println("Please enter the directory containing the files you would like to compile: ");
        userSpecPath = scan.next();

        projectDirectory = userSpecPath;
        reportDirectory = System.getProperty("user.dir");

        // compile everything in projectDirectory/src, put it in projectDirectory/classes, and report on errors to the current working directory, in a file called compile-errors.txt
        Compiler compiler = new Compiler(projectDirectory,
                FileFinder.convertToFileNames(FileFinder.find(projectDirectory, "**.java")), reportDirectory);

        int result = compiler.run();

        if (result == SystemInteractor.SUCCESS) {
            Executor executor = new Executor();

            result = executor.run();

        }

    }
}
