package cs3500.pa02.studysession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests if SpacedRepetitionDecoder decodes problems correctly.
 */
public class SpacedRepetitionDecoderTest {
  private static final List<String> SAMPLE = List.of(
      "What is the syntax to initialize an array:::Type[numberOfElements];:::HARD",
      "Are arrays modifiable?:::Yes, but only the elements, not the size of the array:::EASY",
      "What is the syntax for declaring a vector?:::Vector<type> v = new Vector();:::HARD"
  );
  private final SpacedRepetitionDecoder decoder = new SpacedRepetitionDecoder();

  /**
   * Tests if the decoder interprets strings into problems correctly
   */
  @Test
  public void testInterpret() {
    List<Problem> expected = List.of(
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
    assertEquals(expected, decoder.interpret(SAMPLE));
  }

  /**
   * Tests if the decoder throws if the strings the decoder tries to interpret
   * are formatted incorrectly.
   */
  @Test
  public void testInterpretThrowsIfBadFormat() {
    assertThrows(IllegalArgumentException.class, () -> decoder.interpret(List.of("Bad")));
  }
}