package cs3500.pa02.studysession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests if the view outputs correctly
 */
public class StudySessionTerminalViewTest {
  private static final Problem problem = new Problem("Question", "Answer", Difficulty.EASY);
  private static final SessionInfo info = new SessionInfo(
      3, 1, 2, 2, 0
  );
  private Appendable appendable;
  private StudySessionView view;

  /**
   * Sets up the view and output test stream.
   */
  @BeforeEach
  public void setup() {
    appendable = new StringBuilder();
    view = new StudySessionTerminalView(appendable);
  }

  /**
   * Tests if the view greets the user correctly.
   */
  @Test
  public void testGreetUser() {
    view.greetUser();
    assertEquals(
        """
            Welcome to the Spaced Repetition Study Session!
            """, appendable.toString()
    );
  }

  /**
   * Tests if the view shows options correctly.
   */
  @Test
  public void testShowOptions() {
    view.showOptions();
    assertEquals(
        """
        Select option:
          1. See answer
          2. Mark question as hard
          3. Mark question as easy
          4. Exit
        """, appendable.toString()
    );
  }

  /**
   * Tests if the view shows a question correctly.
   */
  @Test
  public void testShowQuestion() {
    view.showQuestion(problem);
    assertEquals("""
        Problem: Question
        """, appendable.toString()
    );
  }

  /**
   * Tests if the view shows an answer correctly.
   */
  @Test
  public void testShowAnswer() {
    view.showAnswer(problem);
    assertEquals("""
        Answer: Answer
        """, appendable.toString()
    );
  }

  /**
   * Tests if the view shows the session info correctly.
   */
  @Test
  public void testShowInfo() {
    view.showInfo(info.getInfo());
    assertEquals("""
        Session Complete!
        
        Session Information:
          Questions Answered: 3
          Questions Changed from Easy to Hard: 0
          Questions Changed from Hard to Easy: 2
          Updated Total Hard Questions: 1
          Updated Total Easy Questions: 2
        """, appendable.toString()
    );
  }

  /**
   * Tests if the view prompts the user correctly.
   */
  @Test
  public void testPromptUser() {
    view.promptUser("Hello World!");
    assertEquals("""
        Hello World!
        """, appendable.toString()
    );
  }

  /**
   * Tests if the view throws if the view cannot output to its output
   * stream.
   */
  @Test
  public void testPromptUserThrowsIfCannotOutput() {
    Appendable closed = new ClosedAppendable();
    StudySessionView badView = new StudySessionTerminalView(closed);
    assertThrows(RuntimeException.class, () -> badView.promptUser("This throws!"));
  }
}