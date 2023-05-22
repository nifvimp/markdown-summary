package cs3500.pa02.studysession;

import cs3500.pa02.writer.BasicWriter;
import cs3500.pa02.writer.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StudySessionImpl implements StudySession {
  private final Path questionBank;
  private final List<Problem> problems = new ArrayList<>();
  private final SessionInfo info;

  public StudySessionImpl(Path file, Random random) {
    this.questionBank = file;
    SpacedRepetitionReader reader = new SpacedRepetitionReader(file);
    List<Problem> hard = reader.getHardQuestions();
    List<Problem> easy = reader.getEasyQuestions();
    Collections.shuffle(hard, random);
    Collections.shuffle(easy, random);
    problems.addAll(hard);
    problems.addAll(easy);
    int hardCount = hard.size();
    int easyCount = easy.size();
    info = new SessionInfo(hardCount, easyCount);
  }

  @Override
  public List<Problem> getProblems(int totalQuestions) {
    if (totalQuestions < 0) {
      throw new IllegalArgumentException(
          "The size of the problem set must be positive.");
    }
    if (problems.size() < totalQuestions) {
      return problems;
    }
    // pass reference of Problems up so that any change is reflected here
    return problems.subList(0, totalQuestions);
  }

  @Override
  public SessionInfo getInfo() {
    return info;
  }

  @Override
  public void write() {
    // any changes in the problems passed up will be reflected here when rewriting
    // the problems
    Writer writer = new BasicWriter(questionBank);
    List<String> encoded = new SpacedRepetitionEncoder().interpret(problems);
    writer.write(encoded);
  }
}