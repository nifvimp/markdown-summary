package cs3500.pa01.studysession;

import java.nio.file.Path;
import java.util.List;

public class StudySessionModelImpl implements StudySessionModel {
  private final StudySession session;
  private final List<Problem> problems;
  private int currProblemIndex;
  private final SessionInfo info;

  public StudySessionModelImpl(Path questionBank, int totalQuestions) {
    // TODO: use study session to get problems with totalQuestions
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
    currProblemIndex++;
    Problem currentProblem = currentProblem();
    switch(currentProblem.difficulty()) {
      // TODO: do polymorphism bullshit
      // pass in info into problem instead of calling stuff here.
      case HARD -> {
        if ((currentProblem.updateDifficulty(difficulty))) {
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
  }

  @Override
  public void exit() {
    session.write();
  }
}
