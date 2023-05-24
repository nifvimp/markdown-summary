package cs3500.pa02.notesparser;

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
   * @param root  root directory
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
    files.sort(order.getSortBy());
  }

  @Override
  public String fileExtension() {
    return ".md";
  }
}
