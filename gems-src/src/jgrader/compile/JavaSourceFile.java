package jgrader.compile;

import java.nio.file.*;
import java.io.InputStream;
import java.io.OutputStream;
import javax.tools.SimpleJavaFileObject;
import java.net.URI;
import java.util.StringJoiner;
import java.io.IOException;

public class JavaSourceFile extends SimpleJavaFileObject {

    Path file;
    String contents;

    public JavaSourceFile(URI file) {
        super(file, Kind.SOURCE);
        this.file = Paths.get(file);
    }

    public JavaSourceFile(String filename) {
        super(URI.create("file:///" + filename), Kind.SOURCE);
        this.file = Paths.get(toUri());
    }

    @Override
    public boolean delete() {
        try {
            return Files.deleteIfExists(file);
        } catch (IOException ex) {
            System.err.println("Failed to delete " + file);
            return false;
        }
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        if (contents == null) {
            StringJoiner j = new StringJoiner("\n");
            try {
                Files.readAllLines(file).forEach(line -> j.add(line));
            } catch (IOException ex) {
                System.err.println("Failed to read " + file);
                return null;
            }
            contents = j.toString();
        }
        return contents;
    }

    @Override
    public long getLastModified() {
        try {
            return Files.getLastModifiedTime(file).toMillis();
        } catch (IOException ex) {
            System.err.println("Failed to get last modified time for " + file);
            return 0L;
        }
    }

    @Override
    public InputStream openInputStream() {
        try {
            return Files.newInputStream(file);
        } catch (IOException ex) {
            System.err.println("Failed to open input stream for " + file);
            return null;
        }
    }

    @Override
    public OutputStream openOutputStream() {
        try {
            return Files.newOutputStream(file);
        } catch (IOException ex) {
            System.err.println("Failed to open output stream for " + file);
            return null;
        }
    }
}
