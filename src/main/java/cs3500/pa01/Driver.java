package cs3500.pa01;

import cs3500.pa01.notesparser.NotesController;
import cs3500.pa01.notesparser.OrderingFlag;
import cs3500.pa01.studysession.StudySessionController;
import cs3500.pa01.studysession.StudySessionTerminalView;
import cs3500.pa01.studysession.StudySessionView;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
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
   *                containing the notes you want to summarize.</p>
   *             <p>2. Ordering Flag - A flag to indicate how the summary document should be
   *                organized.</p>
   *             <p>3. An output path (relative or absolute) and filename of where to write
   *                the files the program generates.</p>
   */
  public static void main(String[] args) {
    Controller controller;
    switch (args.length) {
      case 0 -> {
        Readable input = new InputStreamReader(System.in);
        StudySessionView view = new StudySessionTerminalView();
        controller = new StudySessionController(input, view);
      }
      case 3 -> {
        validate(args);
        OrderingFlag order = OrderingFlag.valueOf(args[1].toUpperCase());
        Path output = Path.of(args[2]);
        Path root = Path.of(args[0]);
        controller = new NotesController(root, order, output);
      }
      default -> throw new IllegalArgumentException(INCORRECT_ARGUMENT_AMOUNT_MESSAGE);
    }
    controller.run();
  }

  // TODO: clean up validation
  private static void validate(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("""
          This program must take 3 arguments:
          \t1. A path to a directory that has the markdown files you want to summarize.
          \t2. The ordering flag that indicates how the summary should be organized.
          \t3. A path to the file to write the summary to.""");
    }
    Path root;
    Path output;
    // commandline argument validation
    try {
      root = Path.of(args[0]);
    } catch (InvalidPathException e) {
      throw new IllegalArgumentException(
          String.format("The root directory path '%s' (first argument) is not valid.", args[0]), e
      );
    }
    try {
      OrderingFlag.valueOf(args[1].toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(
          String.format("'%s' (2nd argument) is not an ordering flag.", args[1]), e
      );
    }
    try {
      output = Path.of(args[2]);
    } catch (InvalidPathException e) {
      throw new IllegalArgumentException(
          "The file output path '" + args[2] + "' (3rd argument) is not a valid path.", e
      );
    }
    if (!Files.exists(root)) {
      throw new IllegalArgumentException(
          String.format("'%s' (1st argument) does not exist.", root)
      );
    }
    // no check if output file exists since program will create file if it does not exist.

    if (!Files.isDirectory(root)) {
      throw new IllegalArgumentException(
          String.format("'%s' (1st argument) is not a directory.", root)
      );
    }
    if (hasExtension(output)) {
      throw new IllegalArgumentException(
          String.format("'%s' (3nd argument) should not have a file extension.", output)
      );
    }
    // no check if output path is file since program will create a file even if a directory
    // with the same name exists already.
    if (Files.exists(output) && !Files.isWritable(output)) {
      throw new IllegalArgumentException(
          String.format("'%s' (3rd argument) is not writable.", output)
      );
    }
  }

  private static boolean hasExtension(Path path) {
    String filename = path.getFileName().toString();
    int extensionStart = filename.lastIndexOf('.');
    return extensionStart > 0;
  }

//  private static Path tryCastOutput(Path output) {
//    String filename = output.getFileName().toString();
//    int extensionStart = filename.lastIndexOf('.');
//    if (extensionStart < 0) {
//      output = Path.of(output + ".md");
//    }
//    if (!output.toString().endsWith(".md")) {
//      throw new IllegalArgumentException(
//          String.format("'%s' (2nd argument) should be a markdown file.", output)
//      );
//    }
//    return output;
//  }
}