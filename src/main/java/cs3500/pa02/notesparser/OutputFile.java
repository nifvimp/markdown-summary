package cs3500.pa02.notesparser;

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
   * Markdown to output file interpreter.
   */
  private final MarkdownStripper extractor;
  /**
   * The root directory of this output file.
   */
  protected final Path root;

  /**
   * Creates a output file based on the given root and given interpreter.
   *
   * @param extractor markdown to output file interpreter
   * @param root      root directory of output file
   */
  public OutputFile(MarkdownStripper extractor, Path root) {
    this.extractor = extractor;
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
    return extractor.extract(file);
  }

  /**
   * Gets the file extension of an output file.
   *
   * @return the file extension of this output file
   */
  public abstract String fileExtension();
}
