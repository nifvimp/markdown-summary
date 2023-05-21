package cs3500.pa02.studysession;

import java.util.List;

public interface StudySession {
  List<Problem> getProblems(int totalQuestions);
  SessionInfo getInfo();
  void write();
}
