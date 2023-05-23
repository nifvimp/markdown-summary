package cs3500.pa02.studysession;

import java.util.Objects;

/**
 * Represents a problem in a study session.
 */
public class Problem {
  private final String question;
  private final String answer;
  private Difficulty difficulty;

  /**
   * Creates a new problem with the specified question, answer and difficulty.
   *
   * @param question the problem
   * @param answer answer to problem
   * @param difficulty difficulty of problem
   */
  public Problem(String question, String answer, Difficulty difficulty) {
    this.question = question;
    this.answer = answer;
    this.difficulty = difficulty;
  }

  /**
   * Gets the question of this problem.
   *
   * @return the question of this problem
   */
  public String question() {
    return question;
  }

  /**
   * Gets the answer of this problem.
   *
   * @return the answer of this problem
   */
  public String answer() {
    return answer;
  }

  /**
   * Gets the difficulty of this problem.
   *
   * @return the difficulty of this problem
   */
  public Difficulty difficulty() {
    return difficulty;
  }

  /**
   * Updates the difficulty of this problem.
   *
   * @return true if the difficulty changed
   */
  public boolean updateDifficulty(Difficulty difficulty) {
    if (this.difficulty == difficulty) {
      return false;
    }
    this.difficulty = difficulty;
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Problem problem)) {
      return false;
    }
    return Objects.equals(question, problem.question)
        && Objects.equals(answer, problem.answer)
        && difficulty == problem.difficulty;
  }

  @Override
  public int hashCode() {
    return Objects.hash(question, answer, difficulty);
  }
}
