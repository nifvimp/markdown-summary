package cs3500.pa02.studysession;

import cs3500.pa02.Controller;
import cs3500.pa02.Util;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class StudySessionController implements Controller {
  private final Scanner input;
  private final StudySessionView view;
  private final Random random;
  private StudySessionModel model;

  public StudySessionController(Readable input, StudySessionView view, Random random) {
    this.input = new Scanner(Objects.requireNonNull(input));
    this.view = Objects.requireNonNull(view);
    this.random = random;
  }

  public StudySessionController(Readable input, StudySessionView view) {
    this(input, view, new Random());
  }

  /**
   * Asks for parameters to make the model from the user, then start a study session based
   * on the user input.
   */
  @Override
  public void run() {
    view.greetUser();
    Path questionBank = getQuestionBank();
    int questionTotal = getQuestionTotal();
    model = new StudySessionModelImpl(questionBank, questionTotal, random);
    Problem currProblem = model.currentProblem();
    while (currProblem != null) {
      view.showQuestion(currProblem);
      chooseOption(currProblem);
      currProblem = model.currentProblem();
    }
    view.showInfo(model.getInfo());
    model.exit();
  }

  private Path getQuestionBank() {
    view.promptUser("Path to Spaced Repetition Question Bank File: ");
    String path = null;
    Path questionBank;
    try {
      path = input.nextLine();
      questionBank = Path.of(path);
      String extension = Util.extractFileExtension(questionBank);
      if (!Files.exists(questionBank)) {
        throw new NoSuchFileException(path);
      } else if (!extension.equals(".sr")) {
        throw new IllegalArgumentException(
            String.format("'%s' is not a spaced repetition file.", path)
        );
      }
    } catch (InvalidPathException e) {
      view.promptUser(String.format("'%s' is not a valid path.", path));
      questionBank = getQuestionBank();
    }  catch (NoSuchFileException e) {
      view.promptUser(String.format("the file '%s' does not exist.", path));
      questionBank = getQuestionBank();
    } catch (IllegalArgumentException e) {
      view.promptUser(String.format("'%s' is not a spaced repetition file.", path));
      questionBank = getQuestionBank();
    }
    return questionBank;
  }

  private int getQuestionTotal() {
    view.promptUser("Number of Questions you would like to Practice: ");
    String amount = null;
    int questionTotal;
    try {
      amount = input.nextLine();
      questionTotal = Integer.parseInt(amount);
      if (questionTotal < 0) {
        view.promptUser("input cannot be negative.");
        questionTotal = getQuestionTotal();
      }
    } catch (NumberFormatException e) {
      view.promptUser(String.format("'%s' is not a integer.", amount));
      questionTotal = getQuestionTotal();
    }
    return questionTotal;
  }

  /**
   * Gives the user a set of options on how they can respond to a question and acts on the
   * given input.
   *
   * @param currProblem problem the session is currently on
   */
  private void chooseOption(Problem currProblem) {
    // TODO: should it show option to show answer of answer was already shown?
    view.showOptions();
    String userInput = input.nextLine();
    switch (userInput) {
      case "1" -> view.showAnswer(currProblem);
      case "2" -> model.update(Difficulty.HARD);
      case "3" -> model.update(Difficulty.EASY);
      default -> {
        view.promptUser(String.format("'%s' is not an option.", userInput));
        chooseOption(currProblem);
      }
    }
  }
}
