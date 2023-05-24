package cs3500.pa02.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests if the writer writes correctly.
 */
public class BasicWriterTest {
  private static final Path OUTPUT = Path.of("src/test/resources/output.txt");
  private Writer writer;

  /**
   * Rests the writer and test file before each test.
   */
  @BeforeEach
  public void setup() {
    writer = new BasicFileWriter(OUTPUT);
    try {
      Files.delete(OUTPUT);
    } catch (IOException ignored) {
      // An empty catch block
    }
  }

  /**
   * Deletes the test file after testing.
   */
  @AfterEach
  public void cleanup() {
    try {
      Files.delete(OUTPUT);
    } catch (IOException ignored) {
      // An empty catch block
    }
  }

  /**
   * Tests if the writer writes to a file correctly.
   */
  @Test
  public void write() {
    writer.write("Hello World");
    try {
      assertEquals("Hello World", Files.readString(OUTPUT));
    } catch (IOException e) {
      fail(String.format("Error reading output file '%s'", OUTPUT));
    }

  }

  /**
   * Tests if the writer writes a list of string correctly.
   */
  @Test
  public void testWrite() {
    writer.write(List.of(
        "the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"
    ));
    try {
      assertEquals("""
              the
              quick
              brown
              fox
              jumps
              over
              the
              lazy
              dog""",
          Files.readString(OUTPUT));
    } catch (IOException e) {
      fail(String.format("Error reading output file '%s'", OUTPUT));
    }
  }

  /**
   * Tests if write throws if program can't write.
   */
  @Test
  public void testWriteThrowsIfCannotWrite() {
    File notWritable = new File("src/test/resources/NotWritableFile.md");
    Writer notWriter = new BasicFileWriter(notWritable.toPath());
    if (!notWritable.setWritable(false)) {
      fail("Error setting writability of test file.");
    }
    assertThrows(RuntimeException.class,
        () -> notWriter.write("this cannot be written!")
    );
    if (!notWritable.setWritable(true)) {
      fail("Error setting writability of test file.");
    }
  }
}