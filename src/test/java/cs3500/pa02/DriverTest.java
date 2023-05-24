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

        ## Formatting ::: is for pros!?

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

  // Can't test study session part of the project by directly calling main due to
  //  it being way too complicated to pass in my own input stream by just calling
  //  the driver main.

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
}