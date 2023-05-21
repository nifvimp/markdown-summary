package cs3500.pa01.studysession;

import cs3500.pa01.Controller;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;

public class StudySessionController implements Controller {
  private final Scanner input;
  private final StudySessionView view;
  private StudySessionModel model;

  public StudySessionController(Readable input, StudySessionView view) {
    this.input = new Scanner(Objects.requireNonNull(input));
    this.view = Objects.requireNonNull(view);
  }

  /**
   * Asks for parameters to make the model from the user, then start a study session based
   * on the user input.
   */
  @Override
  public void run() {
    view.greetUser();
    getSession();
    while (model.currentProblem() != null) {
      chooseOption(model.currentProblem());
    }
    view.showInfo(model.getInfo());
    model.exit();
  }

  private void getSession() {
    // TODO: handle invalid inputs
    // TODO: change prompt messages
    view.promptUser("Choose a SR Question Bank File: ");
    Path questionBank = Path.of(input.nextLine());
    view.promptUser("How many questions would you like to practice today?");
    int totalQuestions = input.nextInt();
    model = new StudySessionModelImpl(questionBank, totalQuestions);
  }

  /**
   * Gives the user a set of options on how they can respond to a question and acts on the
   * given input.
   *
   * @param currProblem problem the session is currently on
   */
  private void chooseOption(Problem currProblem) {
    view.showOptions();
    switch(input.nextInt()) { // TODO: handle input mismatch
      case 1 -> view.showAnswer(currProblem);
      case 2 -> model.update(Difficulty.HARD);
      case 3 -> model.update(Difficulty.EASY);
      default -> view.promptUser("incorrect input"); // TODO: change message
    }
  }
}
