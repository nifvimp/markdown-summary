package cs3500.pa02.studysession;

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
}
