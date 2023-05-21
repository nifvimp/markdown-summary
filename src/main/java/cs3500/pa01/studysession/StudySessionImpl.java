package cs3500.pa01.studysession;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudySessionImpl implements StudySession {
  private final List<Problem> problems = new ArrayList<>();
  private final SessionInfo info;

  public StudySessionImpl(Path file) {
    SpacedRepetitionReader reader = new SpacedRepetitionReader(file);
    List<Problem> hard = reader.getHardQuestions();
    List<Problem> easy = reader.getEasyQuestions();
    Collections.shuffle(hard);
    Collections.shuffle(easy);
    problems.addAll(hard);
    problems.addAll(easy);
    int hardCount = hard.size();
    int easyCount = easy.size();
    int totalCount = hardCount + easyCount;
    info = new SessionInfo(totalCount, hardCount, easyCount);
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
  public void write(Path output) {
    // TODO: replace with writer
  }
}