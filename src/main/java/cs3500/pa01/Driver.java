package cs3500.pa01;

import cs3500.pa01.notesparser.OrderingFlag;
import cs3500.pa01.notesparser.Summary;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

/**
   * This is the main driver of this project.
 */
public class Driver {
  // TODO: Implement running different controllers based on arguments
  // TODO: update JDoc
  /**
   * Project entry point.
   * Condenses a collection of Markdown files into a single-file study guide.
   *
   * @param args has 3 required command line arguments.
   *             <p>1. A relative or absolute path to a folder (directory) of markdown files
   *                containing the notes you want to summarize.</p>
   *             <p>2. Ordering Flag - A flag to indicate how the summary document should be
   *                organized.</p>
   *             <p>3. An output path (relative or absolute) and filename of where to write
   *                the study guide your program generates.</p>
   */
  public static void main(String[] args) {
    validate(args);
    Path root = Path.of(args[0]);
    OrderingFlag orderingFlag = OrderingFlag.valueOf(args[1].toUpperCase());
    Path output = tryCastOutput(Path.of(args[2]));
    Summary studyGuide = new Summary(root, orderingFlag);
    studyGuide.write(output);
  }

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
    // no check if output path is file since program will create a file even if a directory
    // with the same name exists already.
    if (Files.exists(output) && !Files.isWritable(output)) {
      throw new IllegalArgumentException(
          String.format("'%s' (3rd argument) is not writable.", output)
      );
    }
  }

  private static Path tryCastOutput(Path output) {
    String filename = output.getFileName().toString();
    int extensionStart = filename.lastIndexOf('.');
    if (extensionStart < 0) {
      output = Path.of(output + ".md");
    }
    if (!output.toString().endsWith(".md")) {
      throw new IllegalArgumentException(
          String.format("'%s' (2nd argument) should be a markdown file.", output)
      );
    }
    return output;
  }
}