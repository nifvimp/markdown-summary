package cs3500.pa01.studysession;

public interface StudySessionView {
  /**
   * Welcomes the user.
   */
  void greetUser();

  /**
   * Show the user a menu of options for the current problem and ask them to enter the
   * appropriate value.
   */
  void showOptions();

  /**
   * Shows the user the current problem.
   *
   * @param problem current problem
   */
  void showQuestion(Problem problem);

  /**
   * Shows the user the answer to the current problem.
   *
   * @param problem current problem
   */
  void showAnswer(Problem problem);

  /**
   * Shows the user the session's stats
   *
   * @param info the session's stats
   */
  void showInfo(SessionInfo info);

  /**
   * Prompts the user with the given message.
   *
   * @param msg message to prompt the user with
   */
  void promptUser(String msg);
}
