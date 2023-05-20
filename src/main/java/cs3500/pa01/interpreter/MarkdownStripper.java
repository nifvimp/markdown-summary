package cs3500.pa01.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Strips the contents of a markdown file down to just its notable parts.
 */
public class MarkdownStripper implements Interpreter<String, String> {
  /**
   * Strips the contents of a markdown file that was given down to just its notable parts.
   *
   * @param fileContent contents of markdown file to strip
   * @return result of stripping
   */
  @Override
  public List<String> interpret(List<String> fileContent) {
    List<String> strippedContent = new ArrayList<>();
    List<String> headers = getHeaders(fileContent);
    List<String> headerContent = splitByHeaders(fileContent, headers);
    for (int header = 0; header < headers.size(); header++) {
      strippedContent.add(headers.get(header));
      strippedContent.addAll(extractImportantPhrases(headerContent.get(header)));
      strippedContent.add("");
    }
    return strippedContent;
  }

  /**
   * Gets the headers from the contents of a markdown file.
   *
   * @param fileContent content of markdown file to get headers from
   * @return list of headers from the given file content.
   */
  private List<String> getHeaders(List<String> fileContent) {
    List<String> headers = new ArrayList<>();
    for (String line : fileContent) {
      if (isHeader(line)) {
        headers.add(line);
      }
    }
    return headers;
  }

  /**
   * Checks if the given line is a header.
   *
   * @param line line to check
   * @return true if the given line is a header
   */
  protected boolean isHeader(String line) {
    // regex means "starts with one or more '#' followed by a space, then anything".
    return line.matches("^#+\s.*");
  }

  /**
   * Gets contents of a file seperated by which headers the text falls under.
   *
   * @param fileContent content of markdown file to split
   * @param headers list of headers of markdown file
   * @return List of strings that represent the contents of the file seperated by which header
   *      the text falls under
   */
  private static List<String> splitByHeaders(List<String> fileContent, List<String> headers) {
    if (headers.size() < 1) {
      return List.of(
          fileContent.stream().map(String::trim).collect(Collectors.joining(" "))
      );
    }
    List<String> content = new ArrayList<>();
    int currLine = fileContent.indexOf(headers.get(0)) + 1;
    int currHeader = 1;
    StringBuilder currSplit = new StringBuilder();
    while (currLine < fileContent.size()) {
      String line = fileContent.get(currLine);
      String header = headers.get(currHeader);
      if (line.equals(header)) {
        content.add(currSplit.toString());
        currSplit = new StringBuilder();
        currHeader++;
      } else {
        currSplit.append(line.trim()).append(' ');
      }
      currLine++;
    }
    return content;
  }

  /**
   * Extracts all the important phrases denoted by [[]] from a string.
   *
   * @param contents text body to extract important phrases from
   * @return list of important phrases
   */
  private static List<String> extractImportantPhrases(String contents) {
    List<String> phrases = new ArrayList<>();
    int prefix = contents.indexOf("[[");
    int postfix = contents.indexOf("]]");
    while (prefix >= 0 && postfix >= 0) {
      try {
        phrases.add("- " + contents.substring(prefix + 2, postfix));
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
