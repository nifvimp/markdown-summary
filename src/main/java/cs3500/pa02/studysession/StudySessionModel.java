package cs3500.pa02.studysession;

/**
 * The model of a study session.
 */
public interface StudySessionModel {
  /**
   * Gets the problem the session is currently on.
   *
   * @return the current problem
   */
  Problem currentProblem();

  /**
   * Gets the information of the current session.
   *
   * @return object that contains the information of the current session
   */
  String getInfo();

  /**
   * Trys to updates the current problem to the specified difficulty and then
   * updates the spaced repetition file to reflect the update
   *
   * @param difficulty difficulty to change the current question to
   */
  void update(Difficulty difficulty);

  /**
   * Signals the current session.
   */
  void exit();
}