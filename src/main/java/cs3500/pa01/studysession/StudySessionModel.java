package cs3500.pa01.studysession;

public interface StudySessionModel {
  Problem next();
  SessionInfo getInfo();
  boolean update(Difficulty difficulty);
  void exit();
}
