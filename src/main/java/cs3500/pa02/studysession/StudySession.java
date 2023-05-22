package cs3500.pa02.studysession;

import java.util.List;

public interface StudySession {
  // TODO: change into question getter and put get info into reader
  // TODO: maybe put write functionality directly into the model
  List<Problem> getProblems(int totalQuestions);
  SessionInfo getInfo();
  void write();
}
