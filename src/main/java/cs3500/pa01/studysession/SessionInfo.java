package cs3500.pa01.studysession;

public class SessionInfo {
  // TODO: Implement methods and functionality to update info as model updates
  private final int problemTotal;
  private int answeredCount;
  private int hardCount;
  private int easyCount;
  private int hardChanged;
  private int easyChanged;

  public SessionInfo(int problemTotal) {
    this.problemTotal = problemTotal;
  }
}
