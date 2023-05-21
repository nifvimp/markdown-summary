package cs3500.pa01.interpreter;

import cs3500.pa01.studysession.Difficulty;
import cs3500.pa01.studysession.Problem;
import java.util.ArrayList;
import java.util.List;

public class SpacedRepetitionDecoder implements Interpreter<String, Problem> {

  // TODO: change delimiter
  private static final String DELIMITER = "\\\\-\\(:\\?\\|!:\\)-/";
  @Override
  public List<Problem> interpret(List<String> fileContent) {
    List<Problem> problems = new ArrayList<>();
    for (String line : fileContent) {
      Problem problem = interpret(line);
      problems.add(problem);
    }
    return problems;
  }

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
