package cs3500.pa02.studysession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * A spaced repetition file reader.
 */
public class SpacedRepetitionReader {
  private final List<Problem> hardProblems = new ArrayList<>();
  private final List<Problem> easyProblems = new ArrayList<>();

  /**
   * Creates a spaced repetition file reader that reads the given file.
   *
   * @param file spaced repetition file to read
   */
  public SpacedRepetitionReader(Path file) {
    Interpreter<String, Problem> decoder = new SpacedRepetitionDecoder();
    List<Problem> problems;
    try {
      problems = decoder.interpret(Files.readAllLines(file));
    } catch (IOException e) {
      throw new IllegalArgumentException(
          String.format("Error reading file '%s'", file), e
      );
    }
    for (Problem problem : problems) {
      switch(problem.difficulty()) {
        case HARD -> hardProblems.add(problem);
        case EASY -> easyProblems.add(problem);
      }
    }
  }

  /**
   * Gets the hard problems inside the spaced repetition file.
   *
   * @return list of hard problems
   */
  public List<Problem> getHardProblems() {
    return hardProblems;
  }

  /**
   * Gets the easy problems inside the spaced repetition file.
   *
   * @return list of easy problems
   */
  public List<Problem> getEasyProblems() {
    return easyProblems;
  }
}