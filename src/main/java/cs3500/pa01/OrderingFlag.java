package cs3500.pa01;

/**
 * Flags that indicate how a summary document should be organized.
 */
public enum OrderingFlag {
  /**
   * Organize the content in the output summary file in order based on the alphabetically
   * sorted source file names.
   */
  FILENAME,
  /**
   * Organize the content in the output summary file in order based on the create-date time
   * stamp of the source file.
   */
  CREATED,
  /**
   * Organize the content in the output summary file in order based on the last modified
   * time stamp of the source file.
   */
  MODIFIED
}
