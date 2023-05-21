package cs3500.pa01.studysession;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StudySessionModelImpl implements StudySessionModel {
  private final Path questionBank;
  private final List<Problem> problems;

  public StudySessionModelImpl(Path questionBank, int totalQuestions) {
    // TODO: use study session to get problems with totalQuestions
    this.questionBank = questionBank;
    this.problems = new ArrayList<>(totalQuestions);
  }

  @Override
  public Problem currentProblem() {
    return null;
  }

  @Override
  public SessionInfo getInfo() {
    return null;
  }

  @Override
  public void update(Difficulty difficulty) {
  }

  @Override
  public void exit() {
  }
}
