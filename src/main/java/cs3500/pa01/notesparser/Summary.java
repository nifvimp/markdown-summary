package cs3500.pa01.notesparser;

import cs3500.pa01.interpreter.SummaryExtractor;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * An object that represents a condensed collection of markdown files.
 */
public class Summary extends OutputFile {

  /**
   * Ordering Flag that indicates the organization of this summary.
   */
  private final OrderingFlag order;

  /**
   * Constructs a summary of all the markdown files from the root directory with the specified
   * organization.
   *
   * @param root root directory
   * @param order flag to organize summary
   */
  public Summary(Path root, OrderingFlag order) {
    super(new SummaryExtractor(), root);
    this.order = order;
  }

  /**
   * Compiles a list of strings that represent the contents of a summary of all the markdown
   * files from the root directory that is organized according to this summary's ordering
   * flag.
   *
   * @return list of strings that represent the contents of this summary
   */
  public List<String> compile() {
    List<String> compilation = new ArrayList<>();
    List<MarkdownFile> markdownFiles = getMarkdownFiles();
    sortByOrderingFlag(markdownFiles);
    for (MarkdownFile file : markdownFiles) {
      List<String> strippedContent = strip(file.path());
      compilation.addAll(strippedContent);
    }
    compilation.remove(compilation.size() - 1);
    return compilation;
  }

  /**
   * Sort the given list of markdown files according to the ordering flag of this summary.
   *
   * @param files list of markdown files
   */
  private void sortByOrderingFlag(List<MarkdownFile> files) {
    switch (this.order) {
      case FILENAME -> files.sort(new SortByFilename());
      case CREATED -> files.sort(new SortByCreated());
      case MODIFIED -> files.sort(new SortByModified());
      default -> throw new UnsupportedOperationException(
          String.format("Organization by '%s' is has not been implemented yet.", order)
      );
    }
  }
}
