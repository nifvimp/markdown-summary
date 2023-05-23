package cs3500.pa02.studysession;

import java.util.List;

/**
 * Handles getting the specified problem set to practice on for the current study session.
 */
public interface ProblemSetGenerator {
  /**
   * Gets a problem set of the specified size that the user will practice on. The order
   * of the problem set will be decided by the implementation.
   *
   * @param totalQuestions size of the problem set
   * @return problem set of the given size
   */
  List<Problem> getProblems(int totalQuestions);

  /**
   * Gets the information about the current study session and provided spaced repetition
   * file.
   *
   * @return object that contains the described information.
   */
  SessionInfo getInfo();

  /**
   * Updates the given spaced repetition file to reflect the changes made in the current
   * study session.
   */
  void write();
}
