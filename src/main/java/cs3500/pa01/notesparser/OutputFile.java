package cs3500.pa01.notesparser;

import cs3500.pa01.interpreter.MarkdownStripper;
import cs3500.pa01.writer.BasicWriter;
import cs3500.pa01.writer.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * An abstract implementation of an output file that is based the information contained in the
 * markdown files inside a given directory.
 */
public abstract class OutputFile {
  /**
   * Writer output file writes with.
   */
  private static final Class<? extends Writer> WRITER_TYPE = BasicWriter.class;
  /**
   * Markdown to output file interpreter.
   */
  private final MarkdownStripper interpreter;
  /**
   * The root directory of this output file.
   */
  protected final Path root;

  /**
   * Creates a output file based on the given root and given interpreter.
   *
   * @param interpreter markdown to output file interpreter
   * @param root root directory of output file
   */
  public OutputFile(MarkdownStripper interpreter, Path root) {
    this.interpreter = interpreter;
    this.root = root;
  }

  public abstract List<String> compile();

  /**
   * Returns a list of markdown files in the root directory.
   *
   * @return list of markdown files in root directory
   */
  protected List<MarkdownFile> getMarkdownFiles() {
    MarkdownFileVisitor visitor = new MarkdownFileVisitor();
    List<MarkdownFile> markdownFiles;
    try {
      Files.walkFileTree(root, visitor);
      markdownFiles = visitor.getFoundMarkdownFiles();
    } catch (IOException | IllegalStateException e) {
      throw new RuntimeException(String.format("Error while searching directory '%s'", root), e);
    }
    return markdownFiles;
  }

  /**
   * Strips a markdown file down to just its notable contents.
   *
   * @param file path to markdown file to strip
   * @return list of Strings that represent the stripped contents of the markdown file
   */
  protected List<String> strip(Path file) {
    List<String> lines;
    try {
      lines = Files.readAllLines(file);
    } catch (IOException e) {
      throw new RuntimeException(String.format("Error reading file '%s'", file), e);
    }
    return interpreter.interpret(lines);
  }

  /**
   * Writes this output file to the specified path.
   *
   * @param output path to file to be written to
   */
  public void write(Path output) {
    // TODO: move to controller
    output = Path.of(output + fileExtension());
    Writer writer;
    try {
      writer = WRITER_TYPE.getConstructor(new Class[] {Path.class}).newInstance(output);
    } catch (Exception e) {
      throw new RuntimeException("Error creating instance of Writer used by 'OutputFile.java'", e);
    }
    writer.write(compile());
  }

  /**
   * Gets the file extension of an output file.
   *
   * @return the file extension of this output file
   */
  protected abstract String fileExtension();
}
