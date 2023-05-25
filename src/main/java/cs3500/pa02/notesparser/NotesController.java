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

  /**
   * Makes a new program controller that will parse through the markdown files in the given
   * root directory.
   *
   * @param root   root directory of markdown files
   * @param order  flag to organize summary file by
   * @param output output path of files
   */
  public NotesController(Path root, OrderingFlag order, Path output) {
    this.root = root;
    this.order = order;
    this.output = stripFileExtension(output);
  }

  private Path stripFileExtension(Path output) {
    int extensionIndex = output.toString().lastIndexOf('.');
    if (extensionIndex < 0) {
      return output;
    }
    return Path.of(output.toString().substring(0, extensionIndex));
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
