package cs3500.pa02.notesparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Tests if the problem extractor extracts problems correctly from a markdown file.
 */
public class ProblemExtractorTest {
  private static final Path arrays = Path.of("src/test/resources/notes/arrays.md");
  private static final Path vectors = Path.of("src/test/resources/notes/vectors.md");
  private final ProblemExtractor extractor = new ProblemExtractor();

  /**
   * Tests if the extractor extracts from a markdown file correctly.
   */
  @Test
  public void testExtractOne() {
    Set<String> extracted = new HashSet<>(extractor.extract(arrays));
    assertEquals(Set.of(
        "What is the syntax to initialize an array:::Type[numberOfElements];:::HARD",
        "Are arrays modifiable?:::Yes, but only the elements, not the size of the array:::HARD"
    ), extracted);
  }

  /**
   * Tests if the extractor extracts from a markdown file correctly.
   */
  @Test
  public void testExtractTwo() {
    Set<String> extracted = new HashSet<>(extractor.extract(vectors));
    assertEquals(Set.of(
        "What is the syntax for declaring a vector?:::Vector<type> v = new Vector();:::HARD"
    ), extracted);
  }

  /**
   * Tests if extract throws if cannot read file.
   */
  @Test
  public void testExtractThrowsIfCannotRead() {
    assertThrows(RuntimeException.class, () -> extractor.extract(Path.of("src/test/fake.md")));
  }
}