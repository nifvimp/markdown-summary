package cs3500.pa02.notesparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests if the MarkdownFile class contains information correctly.
 */
public class MarkdownFileTest {
  private MarkdownFile arrays;
  private MarkdownFile vectors;
  private FileTime arraysModified;
  private FileTime arraysCreated;
  private FileTime vectorsModified;
  private FileTime vectorsCreated;

  /**
   * Sets up the test files.
   */
  @BeforeEach
  public void setup() {
    // tests initialization when using of method at the same time
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    try {
      arraysModified = FileTime.fromMillis(sdf.parse("3/2/2023").getTime());
      arraysCreated = FileTime.fromMillis(sdf.parse("1/1/2023").getTime());
      vectorsModified = FileTime.fromMillis(sdf.parse("2/2/2023").getTime());
      vectorsCreated = FileTime.fromMillis(sdf.parse("2/1/2023").getTime());
    } catch (ParseException e) {
      fail("Parsing of dateTime failed.");
    }
    arrays = new MarkdownFile(
        Path.of("src/test/resources/notes/arrays.md"),
        "arrays.md", arraysModified, arraysCreated);
    vectors = new MarkdownFile(
        Path.of("src/test/resources/notes/vectors.md"),
        "vectors.md", vectorsModified, vectorsCreated);
  }

  /**
   * Tests if the markdown file returns correct path.
   */
  @Test
  public void testPath() {
    assertEquals(Path.of("src/test/resources/notes/arrays.md"), arrays.path());
    assertEquals(Path.of("src/test/resources/notes/vectors.md"), vectors.path());
  }

  /**
   * Tests if the markdown file returns correct filename.
   */
  @Test
  public void testFilename() {
    assertEquals("arrays.md", arrays.filename());
    assertEquals("vectors.md", vectors.filename());
  }

  /**
   * Tests if the markdown file returns correct modified time.
   */
  @Test
  public void testLastModified() {
    assertEquals(arraysModified, arrays.lastModified());
    assertEquals(vectorsModified, vectors.lastModified());
  }

  /**
   * Tests if the markdown file returns correct creation time.
   */
  @Test
  public void testCreationTime() {
    assertEquals(arraysCreated, arrays.creationTime());
    assertEquals(vectorsCreated, vectors.creationTime());
  }
}