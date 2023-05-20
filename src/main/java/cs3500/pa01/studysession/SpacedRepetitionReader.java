package cs3500.pa01.studysession;

import cs3500.pa01.interpreter.Interpreter;
import cs3500.pa01.interpreter.SpacedRepetitionDecoder;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SpacedRepetitionReader {
  private final Interpreter<String, Problem> decoder = new SpacedRepetitionDecoder();;
  private final List<Problem> hardProblems = new ArrayList<>();
  private final List<Problem> easyProblems = new ArrayList<>();

  public SpacedRepetitionReader(Path file) {
    // TODO: read file and populate lists;
  }

  public List<Problem> getHardQuestions() {
    return hardProblems;
  }

  public List<Problem> getEasyQuestions() {
    return easyProblems;
  }
}
