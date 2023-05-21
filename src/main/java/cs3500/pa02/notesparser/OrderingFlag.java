package cs3500.pa02.notesparser;

import java.util.Comparator;

/**
 * Flags that indicate how a summary document should be organized.
 */
public enum OrderingFlag {
  /**
   * Organize the content in the output summary file in order based on the alphabetically
   * sorted source file names.
   */
  FILENAME(new SortByFilename()),
  /**
   * Organize the content in the output summary file in order based on the create-date time
   * stamp of the source file.
   */
  CREATED(new SortByCreated()),
  /**
   * Organize the content in the output summary file in order based on the last modified
   * time stamp of the source file.
   */
  MODIFIED(new SortByModified());

  private final Comparator<MarkdownFile> sortBy;

  OrderingFlag(Comparator<MarkdownFile> sortBy) {
    this.sortBy = sortBy;
  }

  public Comparator<MarkdownFile> getSortBy() {
    return this.sortBy;
  }
}
