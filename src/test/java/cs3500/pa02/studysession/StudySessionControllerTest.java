package cs3500.pa02.studysession;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudySessionControllerTest {
  private static final long SEED = 0;
  private static final Path SAMPLE = Path.of("src/test/resources/sample.sr");

  @BeforeEach
  public void setup() {
    resetFile();
  }

  @AfterEach
  public void cleanup() {
    resetFile();
  }

  @Test
  public void testRun() {
    Random random = new Random(SEED);
    Appendable output = new StringBuilder();
    Readable instructions = new StringReader("""
        not a path
        src/test/resources/sample.sr
        not a number
        -1
        10
        10
        
        1
        2
        1
        3
        1
        2
        """);
    StudySessionController controller = new StudySessionController(
        instructions, new StudySessionTerminalView(output), random
    );
    controller.run();
    assertEquals("""
        Welcome to the Spaced Repetition Study Session!
        Path to Spaced Repetition Question Bank File:\s
        'not a path' is not a spaced repetition file.
        Path to Spaced Repetition Question Bank File:\s
        Number of Questions you would like to Practice:\s
        'not a number' is not a integer.
        Number of Questions you would like to Practice:\s
        input cannot be negative.
        Number of Questions you would like to Practice:\s
        What is the syntax to initialize an array
        Select option:
          1. See answer
          2. Mark question as hard
          3. Mark question as easy
        '10' is not an option.
        Select option:
          1. See answer
          2. Mark question as hard
          3. Mark question as easy
        '' is not an option.
        Select option:
          1. See answer
          2. Mark question as hard
          3. Mark question as easy
        Type[numberOfElements];
        What is the syntax to initialize an array
        Select option:
          1. See answer
          2. Mark question as hard
          3. Mark question as easy
        What is the syntax for declaring a vector?
        Select option:
          1. See answer
          2. Mark question as hard
          3. Mark question as easy
        Vector<type> v = new Vector();
        What is the syntax for declaring a vector?
        Select option:
          1. See answer
          2. Mark question as hard
          3. Mark question as easy
        Are arrays modifiable?
        Select option:
          1. See answer
          2. Mark question as hard
          3. Mark question as easy
        Yes, but only the elements, not the size of the array
        Are arrays modifiable?
        Select option:
          1. See answer
          2. Mark question as hard
          3. Mark question as easy
        Session Complete!
                
        Session Information:
          Questions Answered: 3
          Questions Changed from Easy to Hard: 1
          Questions Changed from Hard to Easy: 1
          Updated Total Hard Questions: 2
          Updated Total Easy Questions: 1
        """, output.toString());
  }

  public static void resetFile() {
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