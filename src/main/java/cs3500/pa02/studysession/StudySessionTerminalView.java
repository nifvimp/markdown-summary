package cs3500.pa02.studysession;

import java.io.IOException;

/**
 * A terminal view of a study session.
 */
public class StudySessionTerminalView implements StudySessionView {
  private final Appendable output;

  public StudySessionTerminalView(Appendable output) {
    this.output = output;
  }

  @Override
  public void greetUser() {
    promptUser("Welcome to the Spaced Repetition Study Session!");
  }

  @Override
  public void showOptions() {
    promptUser("""
        Select option:
          1. See answer
          2. Mark question as hard
          3. Mark question as easy
          4. Exit"""
    );
  }

  @Override
  public void showQuestion(Problem problem) {
    promptUser("Problem: " + problem.question());
  }

  @Override
  public void showAnswer(Problem problem) {
    promptUser("Answer: " + problem.answer());
  }

  @Override
  public void showInfo(String info) {
    promptUser("Session Complete!\n\n" + info);
  }

  @Override
  public void promptUser(String msg) {
    try {
      output.append(msg).append("\n");
    } catch (IOException e) {
      throw new RuntimeException(String.format("Error outputting to '%s'", output), e);
    }
  }
}
