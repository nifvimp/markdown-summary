package cs3500.pa01.notesparser;

import java.util.Comparator;

/**
 * A Comparator that orders file paths by filename.
 */
public class SortByFilename implements Comparator<MarkdownFile> {
  /**
   * Compares two markdown files for order. Returns a negative integer, zero, or a positive
   * integer as the first markdown file's name is lexicographically less than, equal to, or
   * greater than the second's filename.
   *
   * @param file1 the first markdown file to be compared.
   * @param file2 the second markdown file to be compared.
   * @return a negative integer, zero, or a positive integer as the first markdown file's
   *      filename is lexicographically less than, equal to, or greater than the second's
   *      filename.
   */
  @Override
  public int compare(MarkdownFile file1, MarkdownFile file2) {
    return file1.filename().compareToIgnoreCase(file2.filename());
  }
}
