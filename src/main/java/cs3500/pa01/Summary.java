package cs3500.pa01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An object that represents a condensed collection of markdown files.
 */
public class Summary {
  /**
   * The root directory of this summary.
   */
  private final Path root;
  /**
   * Ordering Flag that indicates the organization of this summary.
   */
  private final OrderingFlag order;

  /**
   * Constructs a summary of all the markdown files from the root directory without any
   * specified order.
   *
   * @param root root directory
   * @param order flag to organize summary
   */
  public Summary(Path root, OrderingFlag order) {
    this.root = root;
    this.order = order;
  }

  /**
   * Writes this summary to the specified file.
   *
   * @param file path to file to be written to
   */
  public void write(Path file) {
    String contents = String.join("\n", compile());
    try {
      Files.write(file, contents.getBytes());
    } catch (IOException e) {
      throw new RuntimeException(String.format("Error writing to file '%s'", file), e);
    }
  }

  /**
   * Compiles a list of strings that represent the contents of a summary of all the markdown
   * files from the root directory that is organized according to this summary's ordering
   * flag.
   *
   * @return list of strings that represent the contents of this summary
   */
  public List<String> compile() {
    List<String> compilation = new ArrayList<>();
    MarkdownFileVisitor visitor = new MarkdownFileVisitor();
    List<MarkdownFile> markdownFiles;
    try {
      Files.walkFileTree(root, visitor);
      markdownFiles = visitor.getFoundMarkdownFiles();
    } catch (IOException | IllegalStateException e) {
      throw new RuntimeException(String.format("Error visiting directory '%s'", root), e);
    }
    sortByOrderingFlag(markdownFiles);
    for (MarkdownFile file : markdownFiles) {
      try {
        compilation.addAll(strip(file.path()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    compilation.remove(compilation.size() - 1);
    return compilation;
  }

  /**
   * Sort the given list of markdown files according to the ordering flag of this summary.
   *
   * @param files list of markdown files
   */
  private void sortByOrderingFlag(List<MarkdownFile> files) {
    switch (this.order) {
      case FILENAME -> files.sort(new SortByFilename());
      case CREATED -> files.sort(new SortByCreated());
      case MODIFIED -> files.sort(new SortByModified());
      default -> throw new UnsupportedOperationException(
          String.format("Organization by '%s' is has not been implemented yet.", order)
      );
    }
  }

  /**
   * Strips a markdown file down to just its headers and important phrases.
   *
   * @param file path to markdown file to strip
   * @return list of Strings that represent the stripped contents of the markdown file
   */
  public static List<String> strip(Path file) throws IOException {
    List<String> strippedContents = new ArrayList<>();
    List<String> headers = getHeaders(file);
    List<String> fileContents = splitByHeaders(file, headers);
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
  private static List<String> getHeaders(Path file) throws IOException {
    List<String> headers = new ArrayList<>();
    Scanner reader = new Scanner(file);
    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      // regex means "starts with one or more '#' followed by a space, then anything".
      if (line.matches("^#{1,6}\s.*")) {
        headers.add(line);
      }
    }
    reader.close();
    return headers;
  }

  /**
   * Gets contents of the file seperated by which headers the text falls under.
   *
   * @param file path to markdown file to get headers from
   * @param headers list of headers of file
   * @return List of strings that represent the contents of the file seperated by which header
   *      the text falls under
   * @throws IOException if an IOException occurs
   */
  private static List<String> splitByHeaders(Path file, List<String> headers) throws IOException {
    List<String> contents = new ArrayList<>();
    String regex = (String.join("|", escapeAllRegexMetaCharacters(headers)));
    Scanner reader = new Scanner(file);
    reader.useDelimiter(regex);
    while (reader.hasNext()) {
      String entry = reader.next().replaceAll("\\R+", " ");
      contents.add(entry); // \\R = any line break
    }
    reader.close();
    return contents;
  }

  /**
   * Escapes all the regex meta characters in the input.
   *
   * @param input list of strings to escape
   * @return escaped version of input
   */
  private static List<String> escapeAllRegexMetaCharacters(List<String> input) {
    List<String> output = new ArrayList<>();
    for (String str : input) {
      str = str.replaceAll("\\^", "\\\\^");
      str = str.replaceAll("\\\\", "\\\\\\");
      str = str.replaceAll("\\?", "\\\\?");
      str = str.replaceAll("\\(", "\\\\(");
      str = str.replaceAll("\\)", "\\\\)");
      str = str.replaceAll("\\{", "\\\\{");
      str = str.replaceAll("\\[", "\\\\[");
      str = str.replaceAll("\\.", "\\\\.");
      str = str.replaceAll("\\*", "\\\\*");
      str = str.replaceAll("\\+", "\\\\+");
      str = str.replaceAll("\\|", "\\\\|");
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
