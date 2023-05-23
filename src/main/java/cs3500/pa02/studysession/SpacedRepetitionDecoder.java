package cs3500.pa02.studysession;

import java.util.ArrayList;
import java.util.List;

/**
 * Transforms the contents of a spaced repetition file into a set of problems.
 */
public class SpacedRepetitionDecoder implements Interpreter<String, Problem> {
  private static final String DELIMITER = ":::";

  /**
   * Transforms the contents of a spaced repetition file into a set of problems.
   *
   * @param fileContent file content to interpret
   * @return interpretation of contents of the file
   */
  @Override
  public List<Problem> interpret(List<String> fileContent) {
    List<Problem> problems = new ArrayList<>();
    for (String line : fileContent) {
      Problem problem = interpret(line);
      problems.add(problem);
    }
    return problems;
  }

  /**
   * Transform a line from a spaced repetition file into a problem.
   *
   * @param line line to interpret
   * @return interpretation of line
   */
  private Problem interpret(String line) {
    String[] params = line.split(DELIMITER);
    String question;
    String answer;
    Difficulty difficulty;
    try {
      question = params[0];
      answer = params[1];
      difficulty = Difficulty.valueOf(params[2]);
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException(
          String.format(
              "the problem '%s' in the inputted '.sr' file was not properly formatted.", line), e
      );
    }
    return new Problem(question, answer, difficulty);
  }
}
