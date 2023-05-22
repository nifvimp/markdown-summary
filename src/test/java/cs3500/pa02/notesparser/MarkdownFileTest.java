package cs3500.pa02.notesparser;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MarkdownFileTest {
  private MarkdownFile arrays;
  private MarkdownFile vectors;
  private FileTime arraysModified;
  private FileTime arraysCreated;
  private FileTime vectorsModified;
  private FileTime vectorsCreated;

  @BeforeEach
  public void setup() throws ParseException {

    // tests initialization when using of method at the same time
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    arraysModified = FileTime.fromMillis(sdf.parse("3/2/2023").getTime());
    arraysCreated = FileTime.fromMillis(sdf.parse("1/1/2023").getTime());
    vectorsModified = FileTime.fromMillis(sdf.parse("2/2/2023").getTime());
    vectorsCreated = FileTime.fromMillis(sdf.parse("2/1/2023").getTime());
    arrays = new MarkdownFile(
        Path.of("src/test/resources/notes/arrays.md"),
        "arrays.md", arraysModified, arraysCreated);
    vectors = new MarkdownFile(
        Path.of("src/test/resources/notes/vectors.md"),
        "vectors.md", vectorsModified, vectorsCreated);
  }

  @Test
  public void testPath() {
    assertEquals(Path.of("src/test/resources/notes/arrays.md"), arrays.path());
    assertEquals(Path.of("src/test/resources/notes/vectors.md"), vectors.path());
  }

  @Test
  public void testFilename() {
    assertEquals("arrays.md", arrays.filename());
    assertEquals("vectors.md", vectors.filename());
  }

  @Test
  public void testLastModified() {
    assertEquals(arraysModified, arrays.lastModified());
    assertEquals(vectorsModified, vectors.lastModified());
  }

  @Test
  public void testCreationTime() {
    assertEquals(arraysCreated, arrays.creationTime());
    assertEquals(vectorsCreated, vectors.creationTime());
  }
}