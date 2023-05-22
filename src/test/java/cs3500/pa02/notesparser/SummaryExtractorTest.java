package cs3500.pa02.notesparser;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SummaryExtractorTest {
  private static final Path arrays = Path.of("src/test/resources/notes/arrays.md");
  private static final Path vectors = Path.of("src/test/resources/notes/vectors.md");
  private static final String ARRAYS_CONTENTS =
      """
      # Java Arrays
      - An **array** is a collection of variables of the same type

      ## Declaring an Array
      - General Form: type[] arrayName;
      - only creates a reference
      - no array has actually been created yet

      ## Creating an Array (Instantiation)
      - General form:  arrayName = new type[numberOfElements];
      - numberOfElements must be a positive Integer.
      - Gotcha: Array size is not modifiable once instantiated.
      """;
  private static final String VECTORS_CONTENTS =
      """
      # Vectors
      - Vectors act like resizable arrays

      ## Declaring a vector
      - General Form: Vector<type> v = new Vector();
      - type needs to be a valid reference type

      ## Adding an element to a vector
      - v.add(object of type);
      """;
  private final SummaryExtractor extractor = new SummaryExtractor();

  @Test
  public void testExtractOne() {
    List<String> extracted = extractor.extract(arrays);
    assertEquals(ARRAYS_CONTENTS, String.join("\n", extracted));
  }

  @Test
  public void testExtractTwo() {
    List<String> extracted = extractor.extract(vectors);
    assertEquals(VECTORS_CONTENTS, String.join("\n", extracted));
  }
}