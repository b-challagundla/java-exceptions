package assignment3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class hw03 {

    private static final String TESTDATA_DIR_NAME = "testdata";

    public static void main(String[] args) {
        hw03 app = new hw03();
        try {
            Path dir = app.createTestDataDirectory();
            app.createFilesWithData(dir);
            app.readAndDisplayFile(dir.resolve("f1.txt"));
            app.readAndDisplayFile(dir.resolve("f2.txt"));
            app.readAndDisplayFile(dir.resolve("f3.txt"));
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creates the testdata directory and returns the directory path.
     */
    public Path createTestDataDirectory() throws IOException {
        Path dir = Paths.get(TESTDATA_DIR_NAME);
        try {
            if (Files.notExists(dir)) {
                Files.createDirectory(dir);
                System.out.println("Directory created: " + dir.toAbsolutePath());
            } else {
                System.out.println("Directory already exists: " + dir.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Failed to create or access directory: " + e.getMessage());
            throw e;
        }
        return dir;
    }

    /**
     * Creates f1.txt, f2.txt, and f3.txt in the provided directory
     */
    public void createFilesWithData(Path dir) throws IOException {
        createFileWithLines(dir.resolve("f1.txt"), 3, "Line ");
        createFileWithLines(dir.resolve("f2.txt"), 27, "Line ");
        createFileWithLines(dir.resolve("f3.txt"), 7, "Line ");
    }

    private void createFileWithLines(Path filePath, int lineCount, String baseText) throws IOException {
        List<String> lines = new ArrayList<>();
        for (int i = 1; i <= lineCount; i++) {
            lines.add(baseText + i);
        }

        try {
            Files.write(
                    filePath,
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
            System.out.printf("Created %s with %d lines%n", filePath, lineCount);
        } catch (IOException e) {
            System.err.println("Failed to write file " + filePath + ": " + e.getMessage());
            throw e;
        }
    }

    /**
     * Reads the contents of the given file and prints them to stdout.
     */
    public void readAndDisplayFile(Path filePath) throws IOException {
        try {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            System.out.println("Contents of " + filePath + ":");
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (NoSuchFileException e) {
            System.err.println("File does not exist: " + filePath);
            throw e;
        } catch (IOException e) {
            System.err.println("Failed to read file " + filePath + ": " + e.getMessage());
            throw e;
        }
    }

    // Helper methods for tests

    public Path getTestDataDirPath() {
        return Paths.get(TESTDATA_DIR_NAME);
    }

    public Path getF1Path() {
        return getTestDataDirPath().resolve("f1.txt");
    }

    public Path getF2Path() {
        return getTestDataDirPath().resolve("f2.txt");
    }

    public Path getF3Path() {
        return getTestDataDirPath().resolve("f3.txt");
    }
}

