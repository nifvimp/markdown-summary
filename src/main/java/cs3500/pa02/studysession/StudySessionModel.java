package cs3500.pa02.studysession;

public interface StudySessionModel {
  // TODO: optimize way to tell when session is out of questions
  Problem currentProblem();
  SessionInfo getInfo();
  // TODO: needs to update current problem index as well
  void update(Difficulty difficulty);
  void exit();
}