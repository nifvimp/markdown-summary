package cs3500.pa02.notesparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

/**
 * Tests the Summary class's error handling.
 */
public class SummaryTest {
  private static final Path NOTES = Path.of("src/test/resources/notes");
  private static final String EXPECTED = """
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
      
      # Vectors
      - Vectors act like resizable arrays

      ## Declaring a vector
      - General Form: Vector<type> v = new Vector();
      - type needs to be a valid reference type

      ## Adding an element to a vector
      - v.add(object of type);""";
  private final Summary summary = new Summary(NOTES, OrderingFlag.FILENAME);

  @Test
  public void testCompile() {
    assertEquals(EXPECTED, String.join("\n", summary.compile()));
  }

  @Test
  public void testFileExtension() {
    assertEquals(".md", summary.fileExtension());
  }


// TODO: move ot  writer tests

//  /**
//   * Tests if write throws if program can't write.
//   */
//  @Test
//  public void testWriteThrowsIfCanNotWriteToOutput() {
//    Summary s = new Summary(Path.of("src/test/resources/notes"), OrderingFlag.FILENAME);
//    File notWritable = new File("src/test/resources/NotWritableFile.md");
//    if (!notWritable.setWritable(false)) {
//      throw new RuntimeException();
//    }
//    assertThrows(RuntimeException.class,
//        () -> s.write(Path.of("src/test/resources/NotWritableFile"))
//    );
//    if (!notWritable.setWritable(true)) {
//      throw new RuntimeException();
//    }
//  }
//
//  /**
//   * Tests if compile throws if can't parse root for files.
//   */
//  @Test
//  public void testCompileThrowsIfWalkFails()  {
//    Summary s = new Summary(Path.of("src/test/fake"), OrderingFlag.FILENAME);
//    assertThrows(RuntimeException.class, s::compile);
//  }
}