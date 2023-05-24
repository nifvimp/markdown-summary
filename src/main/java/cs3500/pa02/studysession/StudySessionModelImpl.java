package cs3500.pa02.studysession;

import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * A basic implementation of a study session model.
 */
public class StudySessionModelImpl implements StudySessionModel {
  private final StudySession session;
  private final List<Problem> problems;
  private int currProblemIndex;
  private final SessionInfo info;

  /**
   * Starts a new study session based on the specified spaced repetition file of
   * the specified length.
   *
   * @param questionBank   path to the spaced repetition file to base session on
   * @param totalQuestions number of problems to practice on during the session
   * @param random         Random object to shuffle by
   */
  public StudySessionModelImpl(Path questionBank, int totalQuestions, Random random) {
    this.session = new StudySessionImpl(questionBank, random);
    this.problems = this.session.getProblems(totalQuestions);
    this.info = this.session.getInfo();
    this.currProblemIndex = 0;
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
        default -> info.problemAnswered(); // should never reach here
      }
    } else {
      info.problemAnswered();
    }
    session.write();
    currProblemIndex++;
  }

  @Override
  public void exit() {
    currProblemIndex = Integer.MAX_VALUE;
    session.write();
  }
}
