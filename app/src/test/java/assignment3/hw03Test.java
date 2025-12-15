package assignment3;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.*;

class hw03Test {

    private hw03 app;

    @BeforeEach
    void setUp() throws IOException {
        app = new hw03();
        // Delete testdata folder for each test
        Path dir = app.getTestDataDirPath();
        if (Files.exists(dir)) {
            deleteRecursively(dir);
        }
    }

    private void deleteRecursively(Path path) throws IOException {
        if (Files.notExists(path)) return;
        if (Files.isDirectory(path)) {
            try (var stream = Files.list(path)) {
                stream.forEach(p -> {
                    try {
                        deleteRecursively(p);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        Files.deleteIfExists(path);
    }

    @Test
    @DisplayName("folder testdata created")
    void testFolderCreated() throws IOException {
        Path dir = app.createTestDataDirectory();
        assertTrue(Files.exists(dir));
        assertTrue(Files.isDirectory(dir));
    }

    @Test
    @DisplayName("folder testdata already exists")
    void testFolderAlreadyExists() throws IOException {
        Path dir1 = app.createTestDataDirectory();
        assertTrue(Files.exists(dir1));

        Path dir2 = app.createTestDataDirectory();
        assertEquals(dir1, dir2);
        assertTrue(Files.exists(dir2));
    }

    @Test
    @DisplayName("read and display f1.txt")
    void testReadAndDisplayF1() throws IOException {
        Path dir = app.createTestDataDirectory();
        app.createFilesWithData(dir);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            app.readAndDisplayFile(app.getF1Path());
        } finally {
            System.setOut(originalOut);
        }

        String output = outContent.toString();
        assertTrue(output.contains("Contents of"));
        assertTrue(output.contains("Line 1"));
    }

    @Test
    @DisplayName("file f1.txt does not exist")
    void testF1DoesNotExist() throws IOException {
        app.createTestDataDirectory();
        Path f1 = app.getF1Path();
        Files.deleteIfExists(f1);

        assertThrows(NoSuchFileException.class,
                () -> app.readAndDisplayFile(f1));
    }

    @Test
    @DisplayName("read and display f2.txt")
    void testReadAndDisplayF2() throws IOException {
        Path dir = app.createTestDataDirectory();
        app.createFilesWithData(dir);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            app.readAndDisplayFile(app.getF2Path());
        } finally {
            System.setOut(originalOut);
        }

        String output = outContent.toString();
        assertTrue(output.contains("Line 27"));
    }

    @Test
    @DisplayName("file f2.txt does not exist")
    void testF2DoesNotExist() throws IOException {
        app.createTestDataDirectory();
        Path f2 = app.getF2Path();
        Files.deleteIfExists(f2);

        assertThrows(NoSuchFileException.class,
                () -> app.readAndDisplayFile(f2));
    }

    @Test
    @DisplayName("read and display f3.txt")
    void testReadAndDisplayF3() throws IOException {
        Path dir = app.createTestDataDirectory();
        app.createFilesWithData(dir);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            app.readAndDisplayFile(app.getF3Path());
        } finally {
            System.setOut(originalOut);
        }

        String output = outContent.toString();
        assertTrue(output.contains("Line 7"));
    }

    @Test
    @DisplayName("file f3.txt does not exist")
    void testF3DoesNotExist() throws IOException {
        app.createTestDataDirectory();
        Path f3 = app.getF3Path();
        Files.deleteIfExists(f3);

        assertThrows(NoSuchFileException.class,
                () -> app.readAndDisplayFile(f3));
    }
}
