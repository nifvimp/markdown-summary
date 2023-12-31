package cs3500.pa02.notesparser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * An object that represents a spaced repetition question bank file.
 */
public class QuestionBank extends OutputFile {
  /**
   * Constructs a question bank file from all the markdown files in the given root directory.
   *
   * @param root root directory of output file
   */
  public QuestionBank(Path root) {
    super(new ProblemExtractor(), root);
  }

  /**
   * Compiles a list of strings that represent the contents of a question bank from all the
   * markdown files in the root directory.
   *
   * @return list of strings that represent the contents of this question bank
   */
  @Override
  public List<String> compile() {
    List<String> questionBank = new ArrayList<>();
    List<MarkdownFile> markdownFiles = getMarkdownFiles();
    for (MarkdownFile file : markdownFiles) {
      List<String> problems = strip(file.path());
      questionBank.addAll(problems);
    }
    return questionBank;
  }

  @Override
  public String fileExtension() {
    return ".sr";
  }
}
