package cs3500.pa02.studysession;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests if SpacedRepetitionEncoder encodes problems correctly
 */
public class SpacedRepetitionEncoderTest {
  private List<Problem> sample;
  private final SpacedRepetitionEncoder encoder = new SpacedRepetitionEncoder();

  /**
   * Setting up test problems
   */
  @BeforeEach
  public void setup() {
    sample = List.of(
        new Problem(
            "What is the syntax to initialize an array",
            "Type[numberOfElements];",
            Difficulty.HARD
        ),
        new Problem("Are arrays modifiable?",
            "Yes, but only the elements, not the size of the array",
            Difficulty.EASY
        ),
        new Problem("What is the syntax for declaring a vector?",
            "Vector<type> v = new Vector();",
            Difficulty.HARD
        ));
  }

  /**
   * Tests if the encoder encodes the problems correctly when turning into strings to put
   * back into the spaced repetition question bank file.
   */
  @Test
  public void testInterpret() {
    List<String> expected = List.of(
        "What is the syntax to initialize an array:::Type[numberOfElements];:::HARD",
        "Are arrays modifiable?:::Yes, but only the elements, not the size of the array:::EASY",
        "What is the syntax for declaring a vector?:::Vector<type> v = new Vector();:::HARD"
    );
    assertEquals(expected, encoder.interpret(sample));
  }
}