package cs3500.pa02.studysession;

import java.util.Objects;

public class Problem {
  private final String question;
  private final String answer;
  private Difficulty difficulty;
  public Problem(String question, String answer, Difficulty difficulty) {
    this.question = question;
    this.answer = answer;
    this.difficulty = difficulty;
  }

  public String question() {
    return question;
  }

  public String answer() {
    return answer;
  }

  public Difficulty difficulty() {
    return difficulty;
  }

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
    return Objects.equals(question, problem.question) &&
        Objects.equals(answer, problem.answer) && difficulty == problem.difficulty;
  }

  @Override
  public int hashCode() {
    return Objects.hash(question, answer, difficulty);
  }
}
