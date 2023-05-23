package cs3500.pa02.notesparser;

import cs3500.pa02.Controller;
import cs3500.pa02.writer.BasicFileWriter;
import cs3500.pa02.writer.Writer;
import java.nio.file.Path;

/**
 * Controls all aspects of the program that involves creating a summary and spaced repetition
 * file based on the passed in parameters.
 */
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
    Writer studyGuideWriter = new BasicFileWriter(Path.of(output + studyGuide.fileExtension()));
    Writer questionBankWriter = new BasicFileWriter(Path.of(output + questionBank.fileExtension()));
    studyGuideWriter.write(studyGuide.compile());
    questionBankWriter.write(questionBank.compile());
  }
}
