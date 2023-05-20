package cs3500.pa01;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * General utilities for project.
 */
public final class Util {
  /**
   * Prevents initialization.
   */
  private Util() {
  }

  /**
   * Returns the file extension of the given file.
   *
   * @param file file to extract file extension from
   * @return the file's file extension
   * @throws IllegalArgumentException if the file does not have a file extension
   */
  public static String extractFileExtension(Path file) {
    String filename = file.getFileName().toString();
    int extensionStart = filename.lastIndexOf('.');
    if (extensionStart < 0) {
      throw new IllegalArgumentException(
          String.format("The file '%s' does not have a file extension.", file)
      );
    }
    return filename.substring(extensionStart);
  }

  /**
   * Escapes all the regex meta characters in the input.
   *
   * @param input list of strings to escape
   * @return escaped version of input
   */
  public static List<String> escapeAllRegexMetaCharacters(List<String> input) {
    List<String> output = new ArrayList<>();
    // this \\\\ interpreted as find '\'
    String regex = "[.+*?^$()\\[\\]{}|\\\\]";
    for (String str : input) {
      // this \\\\ interpreted as replace with '\\' + found character.
      str = str.replaceAll(regex, "\\\\$0");
      output.add(str);
    }
    return output;
  }
}
