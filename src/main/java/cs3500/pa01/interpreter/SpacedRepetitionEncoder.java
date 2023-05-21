package cs3500.pa01.interpreter;

import cs3500.pa01.studysession.Problem;
import java.util.ArrayList;
import java.util.List;

public class SpacedRepetitionEncoder implements Interpreter<Problem, String> {
  // TODO: change delimiter
  private static final String DELIMITER = ":::";

  @Override
  public List<String> interpret(List<Problem> problems) {
    List<String> encoded = new ArrayList<>();
    for (Problem problem : problems) {
      encoded.add(interpret(problem));
    }
    return encoded;
  }

  private String interpret(Problem problem) {
    return problem.question() + DELIMITER + problem.answer() + DELIMITER + problem.difficulty();
  }
}
