package cs3500.pa02.studysession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests if the StudySessionImpl works as intented.
 */
public class StudySessionImplTest {
  private static final Path SAMPLE = Path.of("src/test/resources/sample.sr");
  private static final long SEED = 0;
  private StudySessionImpl session;
  private List<Problem> problemList;

  /**
   * Sets up expected output.
   */
  @BeforeEach
  public void setup() {
    resetFile();
    session = new StudySessionImpl(SAMPLE, new Random(SEED));
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
   * Tests if the study session implementation gives a satisfactory problem set.
   */
  @Test
  public void testGetProblems() {
    assertEquals(problemList.subList(0, 1), session.getProblems(1));
    assertEquals(problemList.subList(0, 2), session.getProblems(2));
    assertEquals(problemList, session.getProblems(3));
    assertEquals(problemList, session.getProblems(100));
  }

  /**
   * Tests if get problems throws if the input is negative.
   */
  @Test
  public void testGetProblemThrowsIfNegative() {
    assertThrows(IllegalArgumentException.class,
        () -> session.getProblems(-100));
  }

  /**
   * Tests if the info the study session implementation gets is correct.
   */
  @Test
  public void testGetInfo() {
    assertEquals(
        """
        Session Information:
          Questions Answered: 0
          Questions Changed from Easy to Hard: 0
          Questions Changed from Hard to Easy: 0
          Updated Total Hard Questions: 2
          Updated Total Easy Questions: 1""",
        session.getInfo().getInfo()
    );
  }

  /**
   * Tests if the study session updates the spaced repetition file correctly
   */
  @Test
  public void testWrite() {

    List<Problem> problems = session.getProblems(3);
    assertTrue(problems.get(0).updateDifficulty(Difficulty.EASY));
    assertTrue(problems.get(1).updateDifficulty(Difficulty.EASY));
    assertFalse(problems.get(2).updateDifficulty(Difficulty.EASY));
    session.write();
    String updatedContents;
    try {
      updatedContents = Files.readString(SAMPLE);
    } catch (IOException e) {
      throw new RuntimeException(String.format("Error reading sample file '%s'", SAMPLE), e);
    }
    String expected = String.join("\n",
            new SpacedRepetitionEncoder()
                .interpret(problemList))
        .replaceAll("HARD", "EASY");
    assertEquals(expected, updatedContents);
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
      throw new RuntimeException(
          String.format("Failed to write to sample file '%s' on cleanup", SAMPLE), e
      );
    }
  }
}