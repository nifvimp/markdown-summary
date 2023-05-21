package cs3500.pa02.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class BasicWriter implements Writer {
  private final Path file;

  /**
   * Makes a new Writer.
   *
   * @param file file to write to
   */
  public BasicWriter(Path file) {
    this.file = file;
  }

  @Override
  public void write(String value) {
    try {
      Files.write(file, value.getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public void write(List<String> values) {
    write(String.join("\n", values));
  }
}

//class BasicWriter2 implements Writer {
//  private final Appendable appendable;
//
//  /**
//   * Makes a new Writer.
//   *
//   * @param appendable where to write to
//   */
//  public BasicWriter2(Appendable appendable) {
//    this.appendable = Objects.requireNonNull(appendable);
//  }
//
//  @Override
//  public void write(String phrase) {
//    try {
//      appendable.append(phrase);
//    } catch (IOException e) {
//      throw new RuntimeException(e.getMessage());
//    }
//  }
//
//  @Override
//  public void write(List<String> phrases) {
//    for (String phrase : phrases) {
//      write(phrase);
//    }
//  }
//}
