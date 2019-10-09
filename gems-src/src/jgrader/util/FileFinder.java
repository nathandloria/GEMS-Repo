package jgrader.util;

import java.util.List;
import java.util.Collections;
import java.nio.file.*;
import java.util.stream.Collectors;
import java.io.IOException;

public class FileFinder {

    public static List<Path> find(String baseDirectory, String glob) {
        Path base = Paths.get(baseDirectory);
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
        try {
            return Files
                    .find(base, Integer.MAX_VALUE, (path, attr) -> matcher.matches(path), FileVisitOption.FOLLOW_LINKS)
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            System.err.println("Failed to read file: " + ex);
            return null;
        }
    }

    public static List<String> convertToFileNames(Path... paths) {
        List<String> list = Collections.emptyList();
        for (Path p : paths) {
            list.add(toRealPath(p));
        }
        return list;
    }

    public static List<String> convertToFileNames(List<Path> paths) {
        if (paths == null)
            return null;
        return paths.stream().map(path -> toRealPath(path)).collect(Collectors.toList());
    }

    public static String toRealPath(String path) {
        try {
            return Paths.get(path).toRealPath().toString();
        } catch (IOException ex) {
            return path;
        }
    }

    public static String toRealPath(Path path) {
        try {
            return path.toRealPath().toString();
        } catch (IOException ex) {
            return path.toString();
        }
    }
}
