package cs3500.pa02.studysession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests if the Problem class constructs and returns correctly.
 */
public class ProblemTest {
  private Problem easy;
  private Problem hard;

  /**
   * Sets up test problems.
   */
  @BeforeEach
  public void setup() {
    easy = new Problem(
        "Is the sky blue?",
        "yes",
        Difficulty.EASY);
    hard = new Problem(
        "What is the krebs cycle?",
        "The way in which cells use oxygen to produce energy.",
        Difficulty.HARD);
  }

  /**
   * Tests get question gets question correctly.
   */
  @Test
  public void testQuestion() {
    assertEquals("Is the sky blue?", easy.question());
    assertEquals("What is the krebs cycle?", hard.question());
  }

  /**
   * Tests get answer gets answer correctly.
   */
  @Test
  public void testAnswer() {
    assertEquals(
        "yes",
        easy.answer()
    );
    assertEquals(
        "The way in which cells use oxygen to produce energy.",
        hard.answer()
    );
  }

  @Test
  public void testDifficulty() {
    assertEquals(hard.difficulty(), Difficulty.HARD);
    assertEquals(easy.difficulty(), Difficulty.EASY);
  }

  /**
   * Tests update difficulty updates the question difficulty correctly and
   * returns correctly.
   */
  @Test
  public void testUpdateDifficulty() {
    assertFalse(hard.updateDifficulty(Difficulty.HARD));
    assertFalse(hard.updateDifficulty(Difficulty.HARD));
    assertEquals(hard.difficulty(), Difficulty.HARD);
    assertTrue(hard.updateDifficulty(Difficulty.EASY));
    assertEquals(hard.difficulty(), Difficulty.EASY);
    assertFalse(hard.updateDifficulty(Difficulty.EASY));
    assertEquals(hard.difficulty(), Difficulty.EASY);
    assertTrue(easy.updateDifficulty(Difficulty.HARD));
    assertEquals(easy.difficulty(), Difficulty.HARD);
    assertTrue(easy.updateDifficulty(Difficulty.EASY));
    assertEquals(easy.difficulty(), Difficulty.EASY);
  }

  /**
   * Tests equals works.
   */
  @Test
  public void testEquals() {
    Problem problemA = new Problem("A", "B", Difficulty.EASY);
    Problem problemB = new Problem("A", "B", Difficulty.EASY);
    assertEquals(problemA, problemA);
    assertEquals(problemA, problemB);
    assertNotEquals(easy, hard);
    Problem problemC = new Problem("A", "A", Difficulty.EASY);
    Problem problemD = new Problem("A", "A", Difficulty.HARD);
    assertNotEquals(problemA, problemC);
    assertNotEquals(problemA, problemD);
    assertNotEquals(problemA, "problemA");
  }

  /**
   * Tests hashcode is different.
   */
  @Test
  public void testHashCode() {
    Problem problemA1 = new Problem("A", "B", Difficulty.EASY);
    Problem problemA2 = new Problem("A", "B", Difficulty.EASY);
    assertEquals(problemA1.hashCode(), problemA2.hashCode());
  }
}