package cs3500.pa02.writer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BasicWriterTest {
  private static final Path OUTPUT = Path.of("src/test/resources/output.txt");
  private Writer writer;

  @BeforeEach
  public void setup() {
    writer = new BasicWriter(OUTPUT);
    try {
      Files.delete(OUTPUT);
    } catch (IOException ignored) {
      // An empty catch block
    }
  }

  @AfterEach
  public void cleanup() {
    try {
      Files.delete(OUTPUT);
    } catch (IOException ignored) {
      // An empty catch block
    }
  }

  @Test
  public void write() {
    writer.write("Hello World");
    try {
      assertEquals("Hello World", Files.readString(OUTPUT));
    } catch (IOException e) {
      fail(String.format("Error reading output file '%s'", OUTPUT));
    }

  }

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
}