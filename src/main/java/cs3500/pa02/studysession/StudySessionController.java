package cs3500.pa02.studysession;

import cs3500.pa02.Controller;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 * Controls all aspects of the program that runs a study session.
 */
public class StudySessionController implements Controller {
  private final Scanner input;
  private final StudySessionView view;
  private final Random random;
  private StudySessionModel model;

  /**
   * Makes a new study session that is randomized according to the random object passed in.
   *
   * @param input  where teh session will get input from
   * @param view   the view the study session will use
   * @param random the random the session will use
   */
  public StudySessionController(Readable input, StudySessionView view, Random random) {
    this.input = new Scanner(Objects.requireNonNull(input));
    this.view = Objects.requireNonNull(view);
    this.random = random;
  }

  /**
   * Makes a new study session.
   *
   * @param input where the session will get input from
   * @param view  the view the study session will use
   */
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
    int questionTotal = getProblemTotal();
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

  /**
   * Gets the path to the spaced repetition file the user wants to practice on.
   *
   * @return the path the user inputted
   */
  private Path getQuestionBank() {
    view.promptUser("Path to Spaced Repetition Question Bank File: ");
    String path = null;
    Path questionBank;
    try {
      path = input.nextLine();
      questionBank = Path.of(path);
      if (!Files.exists(questionBank)) {
        view.promptUser(String.format("the file '%s' does not exist.", path));
        questionBank = getQuestionBank();
      }
    } catch (InvalidPathException e) {
      view.promptUser(String.format("the file '%s' does not exist.", path));
      questionBank = getQuestionBank();
    }
    return questionBank;
  }

  /**
   * Gets the number of problems the user wants to practice on.
   *
   * @return the number the user inputted
   */
  private int getProblemTotal() {
    view.promptUser("Number of Questions you would like to Practice: ");
    String amount = null;
    int questionTotal;
    try {
      amount = input.nextLine();
      questionTotal = Integer.parseInt(amount);
      if (questionTotal < 0) {
        view.promptUser("input cannot be negative.");
        questionTotal = getProblemTotal();
      }
    } catch (NumberFormatException e) {
      view.promptUser(String.format("'%s' is not a integer.", amount));
      questionTotal = getProblemTotal();
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
    view.showOptions();
    String userInput = input.nextLine();
    switch (userInput) {
      case "1" -> view.showAnswer(currProblem);
      case "2" -> model.update(Difficulty.HARD);
      case "3" -> model.update(Difficulty.EASY);
      case "4" -> model.exit();
      default -> view.promptUser(String.format("'%s' is not an option.", userInput));
    }
  }
}
