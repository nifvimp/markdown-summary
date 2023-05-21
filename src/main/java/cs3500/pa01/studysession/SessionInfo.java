package cs3500.pa01.studysession;

public class SessionInfo {
  // TODO: Implement methods and functionality to update info as model updates
  private final int problemTotal;
  private int answeredCount;
  private int hardCount;
  private int easyCount;
  private int hardChanged;
  private int easyChanged;

  public SessionInfo(int problemTotal, int answeredCount, int hardCount, int easyCount,
                     int hardChanged, int easyChanged) {
    this.problemTotal = problemTotal;
    this.answeredCount = answeredCount;
    this.hardCount = hardCount;
    this.easyCount = easyCount;
    this.hardChanged = hardChanged;
    this.easyChanged = easyChanged;
  }

  public SessionInfo(int hardCount, int easyCount) {
    this(hardCount + easyCount, 0, hardCount, easyCount, 0, 0);
  }

  public int getProblemTotal() {
    return problemTotal;
  }

  public int getAnsweredCount() {
    return answeredCount;
  }

  public int getHardCount() {
    return hardCount;
  }

  public int getEasyCount() {
    return easyCount;
  }

  public int getHardChanged() {
    return hardChanged;
  }

  public int getEasyChanged() {
    return easyChanged;
  }

  public void easyChanged() {
    answeredCount++;
    hardCount++;
    easyCount--;
    easyChanged++;
  }

  public void hardChanged() {
    answeredCount++;
    hardCount--;
    easyCount++;
    hardChanged++;
  }
}
