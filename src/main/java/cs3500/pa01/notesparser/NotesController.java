package cs3500.pa01.notesparser;

import cs3500.pa01.Controller;
import java.nio.file.Path;

public class NotesController implements Controller {
  private final Path root;
  private final OrderingFlag order;
  private final Path output;
  public NotesController(Path root, OrderingFlag order, Path output) {
    this.root = root;
    this.order = order;
    this.output = output;
  }

  /**
   * Creates a summary and question bank from the markdown files in the given root directory
   * to the given output path.
   */
  @Override
  public void run() {
    Summary studyGuide = new Summary(root, order);
    QuestionBank questionBank = new QuestionBank(root);
    studyGuide.write(output);
    questionBank.write(output);
  }
}
