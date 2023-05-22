package cs3500.pa02.notesparser;

import cs3500.pa02.studysession.Difficulty;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Extracts all the question-answer blocks / problem from a markdown file.
 */
public class ProblemExtractor extends MarkdownStripper {
  private static final String DELIMITER = ":::";

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
    // Does not handle case where there are multiple :::
    String[] params = line.split(":::");
    for (int i = 0; i < params.length; i++) {
      params[i] = params[i].trim();
    }
    return String.join(DELIMITER, params) + DELIMITER + Difficulty.HARD;
  }
}
