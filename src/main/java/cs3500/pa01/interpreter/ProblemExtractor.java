package cs3500.pa01.interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * Extracts all the question-answer blocks / problem from a markdown file.
 */
public class ProblemExtractor extends MarkdownStripper {
  /**
   * Extracts all the question-answer blocks from the given markdown file content.
   *
   * @param fileContent content of a markdown file to extract from
   * @return result of extraction
   */
  @Override
  public List<String> interpret(List<String> fileContent) {
    fileContent = super.interpret(fileContent);
    List<String> summary = new ArrayList<>();
    for (String line : fileContent) {
      if (isProblem(line)) {
        summary.add(line);
      }
    }
    return summary;
  }

  /**
   * Checks if the given line is a question-answer block.
   *
   * @param line line to check
   * @return true if line is a question-answer block
   */
  private boolean isProblem(String line) {
    return !isHeader(line) && line.contains(":::");
  }
}
