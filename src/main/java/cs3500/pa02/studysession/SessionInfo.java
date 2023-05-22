package cs3500.pa02.studysession;

public class SessionInfo {
  private int answeredCount;
  private int hardCount;
  private int easyCount;
  private int hardChanged;
  private int easyChanged;

  public SessionInfo(int answeredCount, int hardCount, int easyCount,
                     int hardChanged, int easyChanged) {
    this.answeredCount = answeredCount;
    this.hardCount = hardCount;
    this.easyCount = easyCount;
    this.hardChanged = hardChanged;
    this.easyChanged = easyChanged;
  }

  public SessionInfo(int hardCount, int easyCount) {
    this(0, hardCount, easyCount, 0, 0);
  }

  public String getInfo() {
    return String.format("""
        Session Information:
          Questions Answered: %s
          Questions Changed from Easy to Hard: %s
          Questions Changed from Hard to Easy: %s
          Updated Total Hard Questions: %s
          Updated Total Easy Questions: %s
        """,
        this.answeredCount,
        this.easyChanged,
        this.hardChanged,
        this.hardCount,
        this.easyCount);
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