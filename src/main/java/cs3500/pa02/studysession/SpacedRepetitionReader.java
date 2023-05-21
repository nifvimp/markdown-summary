package cs3500.pa02.studysession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SpacedRepetitionReader {
  private final List<Problem> hardProblems = new ArrayList<>();
  private final List<Problem> easyProblems = new ArrayList<>();

  public SpacedRepetitionReader(Path file) {
    Interpreter<String, Problem> decoder = new SpacedRepetitionDecoder();
    List<Problem> problems;
    try {
      problems = decoder.interpret(Files.readAllLines(file));
    } catch (IOException e) {
      throw new IllegalArgumentException("path doesn't exist.", e); // TODO: change error message
    }
    for (Problem problem : problems) {
      switch(problem.difficulty()) {
        case HARD -> hardProblems.add(problem);
        case EASY -> easyProblems.add(problem);
      }
    }
  }

  public List<Problem> getHardQuestions() {
    return hardProblems;
  }

  public List<Problem> getEasyQuestions() {
    return easyProblems;
  }
}
