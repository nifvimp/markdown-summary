package cs3500.pa01.interpreter;

import cs3500.pa01.studysession.Difficulty;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Extracts all the question-answer blocks / problem from a markdown file.
 */
public class ProblemExtractor extends MarkdownStripper {
  // TODO: change delimiter
  private static final String DELIMITER = "\\-(:?|!:)-/";
  /**
   * Extracts all the question-answer blocks from the given markdown file content.
   *
   * @param file markdown file to extract from
   * @return result of extraction
   */
  @Override
  public List<String> extract(Path file) {
    List<String> stripped = super.extract(file);
    List<String> questionBank = new ArrayList<>();
    for (String line : stripped) {
      if (isProblem(line)) {
        String problem = toProblem(line);
        questionBank.add(problem);
      }
    }
    return questionBank;
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

  /**
   * Formats the question-answer block to be written to a spaced repetition file and sets the
   * default difficulty of the problem to hard.
   *
   * @param line line to convert
   * @return properly formatted problem
   */
  private String toProblem(String line) {
    // TODO: (optional) deal with white space
    return String.join(DELIMITER, line.split(":::")) + DELIMITER + Difficulty.HARD;
  }
}
