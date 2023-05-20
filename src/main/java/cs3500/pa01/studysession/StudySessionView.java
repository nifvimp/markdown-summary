package cs3500.pa01.studysession;

public interface StudySessionView {
  void greetUser();
  void showOptions();
  void showQuestion(Problem problem);
  void showAnswer(Problem problem);
  void showInfo(SessionInfo info);
  void promptUser(String msg);
}
