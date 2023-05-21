package cs3500.pa02.studysession;

import cs3500.pa02.writer.BasicWriter;
import cs3500.pa02.writer.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudySessionImpl implements StudySession {
  private final Path questionBank;
  private final List<Problem> problems = new ArrayList<>();
  private final SessionInfo info;

  public StudySessionImpl(Path file) {
    this.questionBank = file;
    SpacedRepetitionReader reader = new SpacedRepetitionReader(file);
    List<Problem> hard = reader.getHardQuestions();
    List<Problem> easy = reader.getEasyQuestions();
    Collections.shuffle(hard);
    Collections.shuffle(easy);
    problems.addAll(hard);
    problems.addAll(easy);
    int hardCount = hard.size();
    int easyCount = easy.size();
    info = new SessionInfo(hardCount, easyCount);
  }
  @Override
  public List<Problem> getProblems(int totalQuestions) {
    return problems.subList(0, totalQuestions);
  }

  @Override
  public SessionInfo getInfo() {
    return info;
  }

  @Override
  public void write() {
    Writer writer = new BasicWriter(questionBank);
    List<String> encoded = new SpacedRepetitionEncoder().interpret(problems);
    writer.write(encoded);
  }
}