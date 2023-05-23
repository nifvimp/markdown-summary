package cs3500.pa02.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * A basic implementation of a file writer.
 */
public class BasicFileWriter implements Writer {
  private final Path file;

  /**
   * Makes a new Writer.
   *
   * @param file file to write to
   */
  public BasicFileWriter(Path file) {
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
