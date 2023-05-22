package cs3500.pa02.studysession;

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
    return String.format("%s%s%s%s%s",
        problem.question(), DELIMITER,
        problem.answer(), DELIMITER,
        problem.difficulty());
  }
}
