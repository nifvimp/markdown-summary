package cs3500.pa01.notesparser;

import java.util.Comparator;

/**
 * A Comparator that orders file paths by the date modified.
 */
public class SortByModified implements Comparator<MarkdownFile> {
  /**
   * Compares two markdown files for order. Returns a negative integer, zero, or a positive
   * integer as the first markdown file was modified before, at the same time, or after the
   * second.
   *
   * @param file1 the first markdown file to be compared.
   * @param file2 the second markdown file to be compared.
   * @return a negative integer, zero, or a positive integer as the first file was
   *      modified before, at the same time, or after the second.
   */
  @Override
  public int compare(MarkdownFile file1, MarkdownFile file2) {
    return file1.lastModified().compareTo(file2.lastModified());
  }
}
