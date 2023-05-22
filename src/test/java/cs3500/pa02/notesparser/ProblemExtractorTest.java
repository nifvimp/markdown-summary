package cs3500.pa02.notesparser;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class ProblemExtractorTest {
  private static final Path arrays = Path.of("src/test/resources/notes/arrays.md");
  private static final Path vectors = Path.of("src/test/resources/notes/vectors.md");
  private final ProblemExtractor extractor = new ProblemExtractor();

  @Test
  public void testExtractOne() {
    Set<String> extracted = new HashSet<>(extractor.extract(arrays));
    assertEquals(Set.of(
        "What is the syntax to initialize an array:::Type[numberOfElements];:::HARD",
        "Are arrays modifiable?:::Yes, but only the elements, not the size of the array:::HARD"
    ), extracted);
  }

  @Test
  public void testExtractTwo() {
    Set<String> extracted = new HashSet<>(extractor.extract(vectors));
    assertEquals(Set.of(
        "What is the syntax for declaring a vector?:::Vector<type> v = new Vector();:::HARD"
    ), extracted);
  }
}