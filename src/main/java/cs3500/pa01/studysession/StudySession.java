package cs3500.pa01.studysession;

import java.nio.file.Path;
import java.util.List;

public interface StudySession {
  List<Problem> getProblems();
  SessionInfo getInfo();
  void write(Path output);
}
