package cs3500.pa02.studysession;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProblemTest {
  private Problem easy;
  private Problem hard;

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

  @Test
  public void testQuestion() {
    assertEquals("Is the sky blue?", easy.question());
    assertEquals("What is the krebs cycle?", hard.question());
  }

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
  public void testUpdateDifficulty() {
    assertFalse(hard.updateDifficulty(Difficulty.HARD));
    assertFalse(hard.updateDifficulty(Difficulty.HARD));
    assertTrue(hard.updateDifficulty(Difficulty.EASY));
    assertFalse(hard.updateDifficulty(Difficulty.EASY));
    assertTrue(easy.updateDifficulty(Difficulty.HARD));
    assertTrue(easy.updateDifficulty(Difficulty.EASY));
  }

  @Test
  public void testEquals() {
    Problem problemA1 = new Problem("A", "B", Difficulty.EASY);
    Problem problemA2 = new Problem("A", "B", Difficulty.EASY);
    assertEquals(problemA1, problemA2);
  }

  @Test
  public void testHashCode() {
    Problem problemA1 = new Problem("A", "B", Difficulty.EASY);
    Problem problemA2 = new Problem("A", "B", Difficulty.EASY);
    assertEquals(problemA1.hashCode(), problemA2.hashCode());
  }
}