package cs3500.pa02.studysession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests if SpacedRepetitionReader reads spaced repetition files correctly
 */
public class SpacedRepetitionReaderTest {
  private static final Path SAMPLE = Path.of("src/test/resources/sample.sr");
  private final SpacedRepetitionReader reader = new SpacedRepetitionReader(SAMPLE);

  /**
   * Sets up test files.
   */
  @BeforeAll
  public static void setup() {
    try {
      Files.write(SAMPLE, """
          What is the syntax to initialize an array:::Type[numberOfElements];:::HARD
          Are arrays modifiable?:::Yes, but only the elements, not the size of the array:::EASY
          What is the syntax for declaring a vector?:::Vector<type> v = new Vector();:::HARD"""
          .getBytes());
    } catch (IOException e) {
      throw new RuntimeException(
          String.format("Failed to write to sample file '%s' on cleanup", SAMPLE), e
      );
    }
  }

  /**
   * Tests if reader gets the hard questions out of a spaced repetition file correctly.
   */
  @Test
  public void testGetHardQuestions() {
    assertEquals(List.of(
        new Problem(
            "What is the syntax to initialize an array",
            "Type[numberOfElements];",
            Difficulty.HARD
        ),
        new Problem("What is the syntax for declaring a vector?",
            "Vector<type> v = new Vector();",
            Difficulty.HARD
        )
    ), reader.getHardProblems());
  }

  /**
   * Tests if reader gets the easy questions out of a spaced repetition file correctly.
   */
  @Test
  public void testGetEasyQuestions() {
    assertEquals(List.of(
        new Problem("Are arrays modifiable?",
            "Yes, but only the elements, not the size of the array",
            Difficulty.EASY
        )
    ), reader.getEasyProblems());
  }

  /**
   * Tests if reader throws if the reader cannot read a file.
   */
  @Test
  public void testReaderThrowsIfCannotReadFile() {
    assertThrows(RuntimeException.class,
        () -> new SpacedRepetitionReader(Path.of("src/test/fake.sr"))
    );
  }
}