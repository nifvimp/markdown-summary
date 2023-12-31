package cs3500.pa02.notesparser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Extracts all the headers and important phases from a markdown file in the proper format.
 */
public class SummaryExtractor extends MarkdownStripper {
  /**
   * Extracts all the headers and important phrases from the given markdown file content.
   *
   * @param file markdown file to extract from
   * @return result of extraction
   */
  @Override
  public List<String> extract(Path file) {
    List<String> stripped = super.extract(file);
    List<String> summary = new ArrayList<>();
    for (String line : stripped) {
      if (isHeader(line) || isSpacing(line)) {
        summary.add(line);
      } else if (isImportantPhrase(line)) {
        summary.add("- " + line);
      }
    }
    return summary;
  }

  /**
   * Checks if the given line is an important phrase.
   *
   * @param line line to check
   * @return true if the given line is an important phrase
   */
  private boolean isImportantPhrase(String line) {
    return !isHeader(line) && !line.contains(":::");
  }

  /**
   * Checks if the given line is spacing put in by stripper (For formatting).
   *
   * @param line line to check
   * @return true if the given line is spacing
   */
  private boolean isSpacing(String line) {
    return line.equals("");
  }
}
