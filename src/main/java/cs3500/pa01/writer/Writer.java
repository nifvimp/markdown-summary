package cs3500.pa01.writer;

import java.util.List;

/**
 * Handles writing outputs.
 */
public interface Writer {
  /**
   * Writes a given message.
   *
   * @param value the content to write
   */
  void write(String value);

  /**
   * Writes the given messages.
   *
   * @param values the content to write
   */
  void write(List<String> values);
}
