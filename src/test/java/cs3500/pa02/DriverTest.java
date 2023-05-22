package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Tests the output of this program and commandline argument validation of
 * the driver.
 */
public class DriverTest {
  private static final Path STUDY_GUIDE = Path.of("src/test/resources/studyGuide.md");
  private static final Path SPACED_REPETITION = Path.of("src/test/resources/studyGuide.sr");

  /**
   * Tests program file writing capabilities is correct more robustly.
   */
  @Test
  public void testDriverFileOutput() {
    Driver.main(new String[] {
        "src/test/resources/pa01Test",
        "FILENAME",
        "src/test/resources/studyGuide"
    });
    String studyGuide;
    Set<String> questionBank;
    try {
      studyGuide = Files.readString(STUDY_GUIDE);
      questionBank = new HashSet<>(Files.readAllLines(SPACED_REPETITION));
    } catch (IOException e) {
      throw new RuntimeException("Failed to read output of program.", e);
    }
    assertEquals("""
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
                
        ## Formatting is Important
        - formatting is a way of life

        ## Formatting is for pros!?

        # Regex is very powerful
        - Use it well, and you got infinite power!
                
        # Vectors
        - Vectors act like resizable arrays
          
        ## Declaring a vector
        - General Form: Vector<type> v = new Vector();
        - type needs to be a valid reference type
          
        ## Adding an element to a vector
        - v.add(object of type);""", studyGuide);
    assertEquals(Set.of(
        "What is the syntax to initialize an array:::Type[numberOfElements];:::HARD",
        "Are arrays modifiable?:::Yes, but only the elements, not the size of the array:::HARD",
        "What is the syntax for declaring a vector?:::Vector<type> v = new Vector();:::HARD",
        "What does \\\\R indicate?:::It signifies any line break character:::HARD"
    ), questionBank);
  }

  /**
   * Tests driver throws if incorrect number of  arguments.
   */
  @Test
  public void testDriverThrowsIfIncorrectNumberOfArguments() {
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(new String[] {"src/test/resources/notes", "src/test/resources"})
    );
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(
            new String[] {"src/test/resources/notes", "FileName", "src/test/resources", "stop"})
    );
  }

//  /**
//   * Tests driver throws if root is invalid.
//   */
//  @Test
//  public void testDriverThrowsIfRootIsInvalidPath() {
//    assertThrows(IllegalArgumentException.class,
//        () -> Driver.main(
//            new String[] {"src/test/re|sourc%>es/notes", "FileName", "src/test/resources"})
//    );
//  }
//
//  /**
//   * Tests driver throws if output is invalid.
//   */
//  @Test
//  public void testDriverThrowsIfOutputIsnvalidPath() {
//    assertThrows(IllegalArgumentException.class,
//        () -> Driver.main(
//            new String[] {"src/test/resources/notes", "CREATED",
//                "src/test/resources/<study|Guide>"
//            })
//    );
//  }
//
//  /**
//   * Tests driver throws if ordering flag is invalid.
//   */
//  @Test
//  public void testDriverThrowsIfOrderingFlagDoesNotExist() {
//    assertThrows(IllegalArgumentException.class,
//        () -> Driver.main(new String[] {"src/test/resources/notes", "none", "src/test/resources"})
//    );
//    assertThrows(IllegalArgumentException.class,
//        () -> Driver.main(new String[] {"src/test/resources/notes", "", "src/test/resources"})
//    );
//  }
//
//  /**
//   * Tests driver throws if root doesn't exist.
//   */
//  @Test
//  public void testDriverThrowsIfRootDoesNotExist() {
//    assertThrows(IllegalArgumentException.class,
//        () -> Driver.main(
//            new String[] {"src/test/resources/notNotes", "filename", "src/test/resources"})
//    );
//  }
//
//  /**
//   * Tests driver throws if root is not a directory.
//   */
//  @Test
//  public void testDriverThrowsIfRootIsNotDirectory() {
//    assertThrows(IllegalArgumentException.class,
//        () -> Driver.main(
//            new String[] {"src/test/resources/studyGuide", "created", "src/test/resources"})
//    );
//  }
//
//  /**
//   * Tests driver throws if output is not writable.
//   */
//  @Test
//  public void testDriverThrowsIfOutputIsNotWritable() {
//    //TODO: fix write method and test.
//    // throws run time b/c validation didn't check with given output + extensions
//    if (!new File("src/test/resources/NotWritableFile.md").setWritable(false)) {
//      throw new RuntimeException();
//    }
//    assertThrows(IllegalArgumentException.class,
//        () -> Driver.main(new String[] {
//            "src/test/resources/notes", "filename",
//            "src/test/resources/NotWritableFile"
//        })
//    );
//  }
//
//  /**
//   * Tests driver throws if root is not a markdown file.
//   */
//  @Test
//  public void testDriverThrowsIfOutputIsNotMarkdownFile() {
//    assertThrows(IllegalArgumentException.class,
//        () -> Driver.main(
//            new String[] {
//                "src/test/resources/notes", "created",
//                "src/test/resources/studyGuide.pdf"
//            })
//    );
//  }
//
//  /**
//   * Tests driver constructs properly.
//   */
//  @Test
//  public void fakeTest() {
//    assertEquals(5, 5);
//  }
}