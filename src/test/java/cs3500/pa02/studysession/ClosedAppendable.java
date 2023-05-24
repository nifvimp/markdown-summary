package cs3500.pa02.studysession;

import java.io.IOException;

/**
 * Appendable for testing IOExceptions
 */
public class ClosedAppendable implements Appendable {
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throwException();
    return null;
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throwException();
    return null;
  }

  @Override
  public Appendable append(char c) throws IOException {
    throwException();
    return null;
  }

  /**
   * Throws IOException.
   *
   * @throws IOException always
   */
  private void throwException() throws IOException {
    throw new IOException("Test Passed.");
  }
}
