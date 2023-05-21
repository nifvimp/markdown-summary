package cs3500.pa01.studysession;

import java.io.IOException;

public class StudySessionTerminalView implements StudySessionView {
  private final Appendable output;

  public StudySessionTerminalView(Appendable output) {
    this.output = output;
  }

  @Override
  public void greetUser() {
    promptUser("Welcome to Spaced Repetition Study Session!");
  }

  @Override
  public void showOptions() {
    promptUser("""
        Select option:
          1. Mark question as easy
          2. Mark question as hard
          3. See answer
        """
    );
  }

  @Override
  public void showQuestion(Problem problem) {
    promptUser(problem.question());
  }

  @Override
  public void showAnswer(Problem problem) {
    promptUser(problem.answer());
  }

  @Override
  public void showInfo(SessionInfo info) {
    promptUser(String.format("""
        Session Complete!
        
        Session Information:
          Questions Answered: %s
          Questions Changed from Easy to Hard: %s
          Questions Changed from Hard to Easy: %s
          Updated Total Hard Questions: %s
          Updated Total Easy Questions: %s
        """,
        info.getAnsweredCount(),
        info.getEasyChanged(),
        info.getHardChanged(),
        info.getHardCount(),
        info.getEasyCount())
    );
  }

  @Override
  public void promptUser(String msg) {
    try {
      output.append(msg + "\n");
    } catch (IOException e) {
      throw new RuntimeException(String.format("Error outputting to '%s'", output), e);
    }
  }
}
