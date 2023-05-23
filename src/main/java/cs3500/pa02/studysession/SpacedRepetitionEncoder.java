package cs3500.pa02.studysession;

import java.util.ArrayList;
import java.util.List;

/**
 * Transforms a set of problems into a set of strings that would represent those
 * problems inside a spaced repetition file.
 */
public class SpacedRepetitionEncoder implements Interpreter<Problem, String> {
  private static final String DELIMITER = ":::";

  /**
   * Transforms the contents of a set of problems into a set of strings that would
   * represent those problems inside a spaced repetition file.
   *
   * @param problems problems interpret
   * @return interpretation of the problems
   */
  @Override
  public List<String> interpret(List<Problem> problems) {
    List<String> encoded = new ArrayList<>();
    for (Problem problem : problems) {
      encoded.add(interpret(problem));
    }
    return encoded;
  }

  /**
   * Transform a problem the string representation of it in a spaced repetition
   * file.
   *
   * @param problem problem to interpret
   * @return interpretation of the problem
   */
  private String interpret(Problem problem) {
    return String.format("%s%s%s%s%s",
        problem.question(), DELIMITER,
        problem.answer(), DELIMITER,
        problem.difficulty());
  }
}
