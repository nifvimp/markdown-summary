package cs3500.pa02.studysession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests if the model functions properly.
 */
public class StudySessionModelImplTest {
  private static final Path SAMPLE = Path.of("src/test/resources/sample.sr");
  private static final long SEED = 0;
  private StudySessionModelImpl model;
  private List<Problem> problemList;

  /**
   * Sets up the test file and expected list.
   */
  @BeforeEach
  public void setup() {
    resetFile();
    model = new StudySessionModelImpl(SAMPLE, 10, new Random(SEED));
    List<Problem> hard = new ArrayList<>(List.of(
        new Problem(
            "What is the syntax to initialize an array",
            "Type[numberOfElements];",
            Difficulty.HARD
        ),
        new Problem("What is the syntax for declaring a vector?",
            "Vector<type> v = new Vector();",
            Difficulty.HARD
        )
    ));
    List<Problem> easy = new ArrayList<>(List.of(
        new Problem("Are arrays modifiable?",
            "Yes, but only the elements, not the size of the array",
            Difficulty.EASY
        )
    ));
    Random random = new Random(SEED);
    Collections.shuffle(hard, random);
    Collections.shuffle(easy, random);
    problemList = new ArrayList<>();
    problemList.addAll(hard);
    problemList.addAll(easy);
  }

  /**
   * Resets the test file after every test.
   */
  @AfterEach
  public void cleanup() {
    resetFile();
  }

  /**
   * Tests if the model gets the correct problem based on the current state of the
   * study session / model.
   */
  @Test
  public void testCurrentProblem() {
    assertNull(new StudySessionModelImpl(
        SAMPLE, 0, new Random(SEED)).currentProblem()
    );
    for (Problem problem : problemList) {
      assertEquals(problem, model.currentProblem());
      model.update(Difficulty.EASY);
    }
    assertNull(model.currentProblem());
  }

  /**
   * Tests if the model gets the info from the study session correctly.
   */
  @Test
  public void testGetInfo() {
    assertEquals("""
            Session Information:
              Questions Answered: 0
              Questions Changed from Easy to Hard: 0
              Questions Changed from Hard to Easy: 0
              Updated Total Hard Questions: 2
              Updated Total Easy Questions: 1""",
        model.getInfo());
  }

  /**
   * Tests if the model updates the spaced repetition file correctly
   * as the study session goes on.
   */
  @Test
  public void testUpdate() {
    model.update(Difficulty.EASY);
    assertEquals("""
            Session Information:
              Questions Answered: 1
              Questions Changed from Easy to Hard: 0
              Questions Changed from Hard to Easy: 1
              Updated Total Hard Questions: 1
              Updated Total Easy Questions: 2""",
        model.getInfo());
    model.update(Difficulty.EASY);
    assertEquals("""
            Session Information:
              Questions Answered: 2
              Questions Changed from Easy to Hard: 0
              Questions Changed from Hard to Easy: 2
              Updated Total Hard Questions: 0
              Updated Total Easy Questions: 3""",
        model.getInfo());
    model.update(Difficulty.EASY);
    assertEquals("""
            Session Information:
              Questions Answered: 3
              Questions Changed from Easy to Hard: 0
              Questions Changed from Hard to Easy: 2
              Updated Total Hard Questions: 0
              Updated Total Easy Questions: 3""",
        model.getInfo());
    assertThrows(NoSuchElementException.class, () -> model.update(Difficulty.EASY));
    String expected = String.join("\n",
            new SpacedRepetitionEncoder()
                .interpret(problemList))
        .replaceAll("HARD", "EASY");
    String result = null;
    try {
      result = Files.readString(SAMPLE);
    } catch (IOException e) {
      fail(String.format("Error reading sample file '%s'.", SAMPLE));
    }
    assertEquals(expected, result);
  }

  /**
   * Tests if the model exits correctly.
   */
  @Test
  public void testExit() {
    model.update(Difficulty.EASY);
    model.update(Difficulty.EASY);
    model.exit();
    assertNull(model.currentProblem());
    String expected = String.join("\n",
            new SpacedRepetitionEncoder()
                .interpret(problemList))
        .replaceAll("HARD", "EASY");
    String result = null;
    try {
      result = Files.readString(SAMPLE);
    } catch (IOException e) {
      fail(String.format("Error reading sample file '%s'.", SAMPLE));
    }
    assertEquals(expected, result);
  }

  /**
   * Resets the test file.
   */
  private static void resetFile() {
    try {
      Files.write(SAMPLE, """
          What is the syntax to initialize an array:::Type[numberOfElements];:::HARD
          Are arrays modifiable?:::Yes, but only the elements, not the size of the array:::EASY
          What is the syntax for declaring a vector?:::Vector<type> v = new Vector();:::HARD"""
          .getBytes());
    } catch (IOException e) {
      fail(String.format("Failed to write to sample file '%s' on cleanup", SAMPLE));
    }
  }
}