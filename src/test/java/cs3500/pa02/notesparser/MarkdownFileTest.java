package cs3500.pa02.notesparser;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MarkdownFileTest {
  private MarkdownFile arrays;
  private MarkdownFile vectors;
  @BeforeEach
  public void setup() throws ParseException {
    // tests initialization when using of method at the same time
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    arrays = new MarkdownFile(
        Path.of("src/test/resources/notes/arrays.md"),
        "arrays.md",
        FileTime.fromMillis(sdf.parse("3/2/2023").getTime()),
        FileTime.fromMillis(sdf.parse("1/1/2023").getTime())
    );
    vectors = new MarkdownFile(
        Path.of("src/test/resources/notes/vectors.md"),
        "vectors.md",
        FileTime.fromMillis(sdf.parse("2/2/2023").getTime()),
        FileTime.fromMillis(sdf.parse("2/1/2023").getTime())
    );
  }

  @Test
  public void path() {
    assertEquals(Path.of("src/test/resources/notes/arrays.md"), arrays.path());
    assertEquals(Path.of("src/test/resources/notes/vectors.md"), vectors.path());
  }

  @Test
  public void filename() {
    assertEquals("arrays.md", arrays.filename());
    assertEquals("vectors.md", vectors.filename());
  }

  @Test
  public void lastModified() {
    assertEquals("2023-02-03T05:00:00Z", arrays.lastModified().toString());
    assertEquals("2023-02-02T05:00:00Z", vectors.lastModified().toString());
  }

  @Test
  public void creationTime() {
    assertEquals("2023-01-01T05:00:00Z", arrays.creationTime().toString());
    assertEquals("2023-01-02T05:00:00Z", vectors.creationTime().toString());
  }
}