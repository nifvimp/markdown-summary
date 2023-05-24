package cs3500.pa02.notesparser;

import java.nio.file.Path;

/**
 * Extracts the necessary information out of file.
 *
 * @param <T> type of extraction result
 */
public interface Extractor<T> {
  /**
   * Extracts the necessary information out of file.
   */
  T extract(Path file);
}
