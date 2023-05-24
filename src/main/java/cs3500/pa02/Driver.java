package cs3500.pa02;

import cs3500.pa02.notesparser.NotesController;
import cs3500.pa02.notesparser.OrderingFlag;
import cs3500.pa02.studysession.StudySessionController;
import cs3500.pa02.studysession.StudySessionTerminalView;
import cs3500.pa02.studysession.StudySessionView;
import java.io.InputStreamReader;
import java.nio.file.Path;

/**
 * This is the main driver of this project.
 */
public class Driver {
  public static String INCORRECT_ARGUMENT_AMOUNT_MESSAGE =
      "This program requires 0 arguments to start a study session, or the 3 following "
          + "arguments to condenses a collection of markdown files into a study guide "
          + "file and question bank file.\n"
          + """
                1. A path to a directory that has the markdown files you want to summarize.
                2. The ordering flag that indicates how the summary should be organized.
                3. A path to the file to write the summary to.
          """;

  /**
   * Project entry point.
   * Condenses a collection of Markdown files into a study guide and question bank, or starts
   * a study session.
   *
   * @param args requires 0 arguments to start a study session, or the 3 following arguments
   *             to condenses a collection of markdown files into a study guide file and
   *             question bank file.
   *
   *             <p>1. A relative or absolute path to a folder (directory) of markdown files
   *             containing the notes you want to summarize.</p>
   *             <p>2. Ordering Flag - A flag to indicate how the summary document should be
   *             organized.</p>
   *             <p>3. An output path (relative or absolute) and filename of where to write
   *             the files the program generates.</p>
   */
  public static void main(String[] args) {
    Controller controller;
    switch (args.length) {
      case 0 -> {
        Readable input = new InputStreamReader(System.in);
        StudySessionView view = new StudySessionTerminalView(System.out);
        controller = new StudySessionController(input, view);
      }
      case 3 -> {
        OrderingFlag order = OrderingFlag.valueOf(args[1].toUpperCase());
        Path output = Path.of(args[2]);
        Path root = Path.of(args[0]);
        controller = new NotesController(root, order, output);
      }
      default -> throw new IllegalArgumentException(INCORRECT_ARGUMENT_AMOUNT_MESSAGE);
    }
    controller.run();
  }
}