package cs3500.pa01.interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * Extracts all the headers and important phases from a markdown file in the proper format.
 */
public class SummaryExtractor extends MarkdownStripper {
  /**
   * Extracts all the headers and important phrases from the given markdown file content.
   *
   * @param fileContent content of a markdown file to extract from
   * @return result of extraction
   */
  @Override
  public List<String> interpret(List<String> fileContent) {
    List<String> summary = new ArrayList<>();
    for (String line : fileContent) {
      if (isHeader(line) || isImportantPhrase(line) || isSpacing(line)) {
        summary.add(line);
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
