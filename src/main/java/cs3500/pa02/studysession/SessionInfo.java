package cs3500.pa02.studysession;

/**
 * Represents the current state of this session.
 */
public class SessionInfo {
  private int answeredCount;
  private int hardCount;
  private int easyCount;
  private int hardChanged;
  private int easyChanged;

  /**
   * Creates a new session info object with the specified information.
   *
   * @param answeredCount total problems attempted
   * @param hardCount     total number of hard problems
   * @param easyCount     total number of easy problems
   * @param hardChanged   total number of hard problems changed to easy
   * @param easyChanged   total number of easy problems changed to hard
   */
  public SessionInfo(int answeredCount, int hardCount, int easyCount,
                     int hardChanged, int easyChanged) {
    this.answeredCount = answeredCount;
    this.hardCount = hardCount;
    this.easyCount = easyCount;
    this.hardChanged = hardChanged;
    this.easyChanged = easyChanged;
  }

  /**
   * Creates a new session info object of a new session.
   *
   * @param hardCount total number of hard problems
   * @param easyCount total number of easy problems
   */
  public SessionInfo(int hardCount, int easyCount) {
    this(0, hardCount, easyCount, 0, 0);
  }

  /**
   * Returns a string representation of this session's current state.
   *
   * @return String representing the session's current state
   */
  public String getInfo() {
    return String.format("""
            Session Information:
              Questions Answered: %s
              Questions Changed from Easy to Hard: %s
              Questions Changed from Hard to Easy: %s
              Updated Total Hard Questions: %s
              Updated Total Easy Questions: %s""",
        this.answeredCount,
        this.easyChanged,
        this.hardChanged,
        this.hardCount,
        this.easyCount);
  }

  /**
   * Changes the session information to what it would be if an easy problem was changed
   * to a hard problem.
   */
  public void easyChanged() {
    problemAnswered();
    hardCount++;
    easyCount--;
    easyChanged++;
  }

  /**
   * Changes the session information to what it would be if an easy problem was changed
   * to a hard problem.
   */
  public void hardChanged() {
    problemAnswered();
    hardCount--;
    easyCount++;
    hardChanged++;
  }

  /**
   * Increments the answer count.
   */
  public void problemAnswered() {
    answeredCount++;
  }
}