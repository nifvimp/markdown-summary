package cs3500.pa01.studysession;

import cs3500.pa01.Controller;
import java.util.Objects;

public class StudySessionController implements Controller {
  private final Readable input;
  private final StudySessionView view;
  private StudySessionModel model;

  public StudySessionController(Readable input, StudySessionView view) {
    this.input = Objects.requireNonNull(input);
    this.view = Objects.requireNonNull(view);
  }

  /**
   * Asks for parameters to make the model from the user, then start a study session based
   * on the user input.
   */
  @Override
  public void run() {
    // TODO: implement
  }
}
