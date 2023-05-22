package cs3500.pa02.studysession;

import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class StudySessionModelImpl implements StudySessionModel {
  private final StudySession session;
  private final List<Problem> problems;
  private int currProblemIndex;
  private final SessionInfo info;

  public StudySessionModelImpl(Path questionBank, int totalQuestions, Random random) {
    this.session = new StudySessionImpl(questionBank, random);
    this.problems = this.session.getProblems(totalQuestions);
    this.info = this.session.getInfo();
    this.currProblemIndex = 0;
  }

  public StudySessionModelImpl(Path questionBank, int totalQuestions) {
    this(questionBank, totalQuestions, new Random());
  }

  @Override
  public Problem currentProblem() {
    return (currProblemIndex < problems.size()) ? problems.get(currProblemIndex) : null;
  }

  @Override
  public String getInfo() {
    return info.getInfo();
  }

  @Override
  public void update(Difficulty difficulty) {
    Problem currentProblem = currentProblem();
    if (currentProblem == null) {
      throw new NoSuchElementException("There are no more problems left for this session.");
    }
    Difficulty currDifficulty = currentProblem.difficulty();
    if (currentProblem.updateDifficulty(difficulty)) {
      switch (currDifficulty) {
        case HARD -> info.hardChanged();
        case EASY -> info.easyChanged();
      }
    } else {
      info.problemAnswered();
    }
    session.write(); // TODO: do I overwrite every update?
    currProblemIndex++;
  }

  @Override
  public void exit() {
    session.write();
  }
}
