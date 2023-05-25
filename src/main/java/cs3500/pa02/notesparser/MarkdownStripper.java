package cs3500.pa02.notesparser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Strips the contents of a markdown file down to just its notable parts.
 */
public class MarkdownStripper implements Extractor<List<String>> {
  /**
   * Strips the contents of a markdown file that was given down to just its notable parts.
   * Does not handle cases where the file is improperly formatted, especially when phrases
   * are not nested in headers.
   *
   * @param file markdown file to strip
   * @return result of stripping
   */
  @Override
  public List<String> extract(Path file) {
    List<String> strippedContents = new ArrayList<>();
    List<String> headers;
    List<String> fileContents;
    try {
      headers = getHeaders(file);
      fileContents = splitByHeaders(file, headers);
    } catch (IOException e) {
      throw new RuntimeException(String.format("Error reading file '%s'", file), e);
    }
    for (int header = 0; header < headers.size(); header++) {
      strippedContents.add(headers.get(header));
      strippedContents.addAll(extractImportantPhrases(fileContents.get(header)));
      strippedContents.add("");
    }
    return strippedContents;
  }

  /**
   * Gets the headers of a markdown file.
   *
   * @param file path to file to get headers from
   * @return list of headers of the specified file.
   * @throws IOException if an IOException occurs
   */
  private List<String> getHeaders(Path file) throws IOException {
    List<String> headers = new ArrayList<>();
    Scanner reader = new Scanner(file);
    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      if (isHeader(line)) {
        headers.add(line);
      }
    }
    reader.close();
    return headers;
  }

  /**
   * Checks if the given line is a header.
   *
   * @param line line to check
   * @return true if the given line is a header
   */
  protected boolean isHeader(String line) {
    return line.matches("^#+\s.*");
  }

  /**
   * Gets contents of the file seperated by which headers the text falls under.
   *
   * @param file    path to markdown file to get headers from
   * @param headers list of headers of file
   * @return List of strings that represent the contents of the file seperated by which header
   *      the text falls under
   * @throws IOException if an IOException occurs
   */
  private List<String> splitByHeaders(Path file, List<String> headers) throws IOException {
    String[] contents = new String[headers.size()];
    int index = 0;
    String regex = (String.join("|", escapeAllRegexMetaCharacters(headers)));
    Scanner reader = new Scanner(file);
    reader.useDelimiter(regex);
    while (reader.hasNext()) {
      String entry = reader.next().replaceAll("\s*\\R+\s*", " ");
      contents[index++] = entry;
    }
    return Arrays.stream(contents).map(str -> (str == null) ? "" : str).toList();
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

  /**
   * Extracts all the important phrases denoted by [[]] from a string.
   *
   * @param contents text body to extract important phrases from
   * @return list of important phrases
   */
  private List<String> extractImportantPhrases(String contents) {
    List<String> phrases = new ArrayList<>();
    int prefix = contents.indexOf("[[");
    int postfix = contents.indexOf("]]");
    while (prefix >= 0 && postfix >= 0) {
      try {
        phrases.add(contents.substring(prefix + 2, postfix));
      } catch (IndexOutOfBoundsException ignored) {
        // An empty catch block
      }
      contents = contents.substring(Math.min(prefix, postfix) + 2);
      prefix = contents.indexOf("[[");
      postfix = contents.indexOf("]]");
    }
    return phrases;
  }
}
