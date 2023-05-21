package cs3500.pa02.studysession;

import cs3500.pa02.Controller;
import cs3500.pa02.Util;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.InputMismatchException;
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
    Path questionBank = null;
    while (questionBank == null) {
      view.promptUser("Path to Spaced Repetition Question Bank File: ");
      String inputPath = input.nextLine();
      try {
        questionBank = Path.of(inputPath);
        questionBank = validateSpacedRepetition(questionBank);
      } catch (InvalidPathException e) {
        view.promptUser(String.format("'%s' is not a valid path.", inputPath));
      }
    }
    Integer totalQuestions = null;
    while (totalQuestions == null) {
      view.promptUser("Number of Questions you would like to Practice: ");
      try {
        totalQuestions = input.nextInt();
        totalQuestions = validateQuestionTotal(totalQuestions);
      } catch (InputMismatchException e) {
        view.promptUser(String.format("'%s' is not a integer.", totalQuestions));
      }
    }
    model = new StudySessionModelImpl(questionBank, totalQuestions);
  }

  private Path validateSpacedRepetition(Path questionBank) {
    try {
      String extension = Util.extractFileExtension(questionBank);
      if (extension.equals(".sr")) {
        return questionBank;
      }
    } catch (IllegalArgumentException ignored) {
      // An empty catch block
    }
    view.promptUser(String.format("'%s' is not a spaced repetition file.", questionBank));
    return null;
  }

  private Integer validateQuestionTotal(int totalQuestions) {
    if (totalQuestions >= 0) {
      return totalQuestions;
    }
    view.promptUser(String.format("'%s' is not a spaced repetition file.", totalQuestions));
    return null;
  }

  /**
   * Gives the user a set of options on how they can respond to a question and acts on the
   * given input.
   *
   * @param currProblem problem the session is currently on
   */
  private void chooseOption(Problem currProblem) {
    //TODO: should it show option to show answer of answer was already shown?
    view.showOptions();
    String userInput = input.nextLine();
    switch (userInput) {
      case "1" -> view.showAnswer(currProblem);
      case "2" -> model.update(Difficulty.HARD);
      case "3" -> model.update(Difficulty.EASY);
      default -> {
        view.promptUser("'%s' is not an option.");
        chooseOption(currProblem); // TODO: recursion ok?
      }
    }
  }
}
