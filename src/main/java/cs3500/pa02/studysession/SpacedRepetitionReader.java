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
  private final List<List<Problem>> problemList;

  /**
   * Creates a spaced repetition file reader that reads the given file.
   *
   * @param file spaced repetition file to read
   */
  public SpacedRepetitionReader(Path file) {
    problemList = new ArrayList<>();
    for (Difficulty ignored : Difficulty.values()) {
      problemList.add(new ArrayList<>());
    }
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
      Difficulty difficulty = problem.difficulty();
      problemList.get(difficulty.ordinal()).add(problem);
    }
  }

  /**
   * Gets the hard problems inside the spaced repetition file.
   *
   * @return list of hard problems
   */
  public List<Problem> getHardProblems() {
    return problemList.get(Difficulty.HARD.ordinal());
  }

  /**
   * Gets the easy problems inside the spaced repetition file.
   *
   * @return list of easy problems
   */
  public List<Problem> getEasyProblems() {
    return problemList.get(Difficulty.EASY.ordinal());
  }
}