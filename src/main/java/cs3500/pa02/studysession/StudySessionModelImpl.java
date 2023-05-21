package cs3500.pa02.studysession;

import java.nio.file.Path;
import java.util.List;

public class StudySessionModelImpl implements StudySessionModel {
  private final StudySession session;
  private final List<Problem> problems;
  private int currProblemIndex;
  private final SessionInfo info;

  public StudySessionModelImpl(Path questionBank, int totalQuestions) {
    this.session = new StudySessionImpl(questionBank);
    this.problems = session.getProblems(totalQuestions);
    this.info = session.getInfo();
    this.currProblemIndex = 0;
  }

  @Override
  public Problem currentProblem() {
    return (currProblemIndex < problems.size()) ? problems.get(currProblemIndex) : null;
  }

  @Override
  public SessionInfo getInfo() {
    return null;
  }

  @Override
  public void update(Difficulty difficulty) {
    Problem currentProblem = currentProblem();
    switch(currentProblem.difficulty()) {
      case HARD -> {
        if (currentProblem.updateDifficulty(difficulty)) {
          info.easyChanged();
        } else {
          info.hardChanged();
        }
      }
      case EASY -> {
        if ((currentProblem.updateDifficulty(difficulty))) {
          info.hardChanged();
        } else {
          info.easyChanged();
        }
      }
    }
    currProblemIndex++;
  }

  @Override
  public void exit() {
    session.write();
  }
}
