package cs3500.pa02.studysession;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudySessionTerminalViewTest {
  private static final Problem problem = new Problem("Question", "Answer", Difficulty.EASY);
  private static final SessionInfo info = new SessionInfo(
      3, 1, 2, 2, 0
  );
  private Appendable appendable;
  private StudySessionView view;

  @BeforeEach
  public void setup() {
    appendable = new StringBuilder();
    view =  new StudySessionTerminalView(appendable);
  }

  @Test
  public void testGreetUser() {
    view.greetUser();
    assertEquals(
        """
            Welcome to the Spaced Repetition Study Session!
            """, appendable.toString()
    );
  }

  @Test
  public void testShowOptions() {
    view.showOptions();
    assertEquals(
        """
        Select option:
          1. See answer
          2. Mark question as hard
          3. Mark question as easy
        """, appendable.toString()
    );
  }

  @Test
  public void testShowQuestion() {
    view.showQuestion(problem);
    assertEquals("""
        Question
        """, appendable.toString()
    );
  }

  @Test
  public void testShowAnswer() {
    view.showAnswer(problem);
    assertEquals("""
        Answer
        """, appendable.toString()
    );
  }

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

  @Test
  public void testPromptUser() {
    view.promptUser("Hello World!");
    assertEquals("""
        Hello World!
        """, appendable.toString()
    );
  }
}