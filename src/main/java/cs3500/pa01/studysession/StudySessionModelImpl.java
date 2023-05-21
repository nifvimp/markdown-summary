package cs3500.pa01.studysession;

import java.nio.file.Path;
import java.util.List;

public class StudySessionModelImpl implements StudySessionModel {
  private final Path questionBank;
  private final List<Problem> problems;
  private int currentProblem;
  private SessionInfo info;

  public StudySessionModelImpl(Path questionBank, int totalQuestions) {
    // TODO: use study session to get problems with totalQuestions
    this.questionBank = questionBank;
    StudySession session = new StudySessionImpl(questionBank);
    this.problems = session.getProblems(totalQuestions);
    this.info = session.getInfo();
    this.currentProblem = 0;
  }

  @Override
  public Problem currentProblem() {
    return (currentProblem < problems.size()) ? problems.get(currentProblem) : null;
  }

  @Override
  public SessionInfo getInfo() {
    return null;
  }

  @Override
  public void update(Difficulty difficulty) {
    if (!currentProblem().updateDifficulty(difficulty)) {

    }
  }

  @Override
  public void exit() {
  }
}
