package cs3500.pa02.studysession;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

public class SpacedRepetitionDecoderTest {
  private static final List<String> SAMPLE = List.of(
      "What is the syntax to initialize an array:::Type[numberOfElements];:::HARD",
      "Are arrays modifiable?:::Yes, but only the elements, not the size of the array:::EASY",
      "What is the syntax for declaring a vector?:::Vector<type> v = new Vector();:::HARD"
      );
  private final SpacedRepetitionDecoder decoder = new SpacedRepetitionDecoder();

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
}