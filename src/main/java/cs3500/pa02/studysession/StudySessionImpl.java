package cs3500.pa02.studysession;

import cs3500.pa02.writer.BasicFileWriter;
import cs3500.pa02.writer.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class StudySessionImpl implements StudySession {
  private final Path questionBank;
  private final List<Problem> problems = new ArrayList<>();
  private final SessionInfo info;

  /**
   * Creates a study session based on the spaced repetition file passed in.
   *
   * @param file   spaced repetition file to base the problem set on
   * @param random Random object to shuffle by
   */
  public StudySessionImpl(Path file, Random random) {
    this.questionBank = file;
    SpacedRepetitionReader reader = new SpacedRepetitionReader(file);
    List<Problem> hard = reader.getHardProblems();
    List<Problem> easy = reader.getEasyProblems();
    Collections.shuffle(hard, random);
    Collections.shuffle(easy, random);
    problems.addAll(hard);
    problems.addAll(easy);
    int hardCount = hard.size();
    int easyCount = easy.size();
    info = new SessionInfo(hardCount, easyCount);
  }

  /**
   * Generates a random set of problems with all the hard problems on top, and then easy
   * problems if there are no hard problems left.
   *
   * @param totalQuestions size of the problem set
   * @return problem set to practice on
   */
  @Override
  public List<Problem> getProblems(int totalQuestions) {
    if (totalQuestions < 0) {
      throw new IllegalArgumentException(
          "The size of the problem set must be positive.");
    }
    if (problems.size() < totalQuestions) {
      return problems;
    }
    // pass reference of Problems up so that any change is reflected here and
    // therefore can be rewritten to the file here
    return problems.subList(0, totalQuestions);
  }

  @Override
  public SessionInfo getInfo() {
    return info;
  }

  @Override
  public void write() {
    Writer writer = new BasicFileWriter(questionBank);
    List<String> encoded = new SpacedRepetitionEncoder().interpret(problems);
    writer.write(encoded);
  }
}