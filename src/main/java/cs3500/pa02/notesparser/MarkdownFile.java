package cs3500.pa02.notesparser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

/**
 * Represents a markdown file.
 *
 * @param path path to file
 * @param filename name of file
 * @param lastModified time file was last modified
 * @param creationTime time file was creation time
 */
public record MarkdownFile(Path path,
                           String filename,
                           FileTime lastModified,
                           FileTime creationTime) {
  /**
   * Makes an instance of MarkdownFile from path. For testing purposes.
   *
   * @param path path to markdown file
   * @return new markdownFile
   * @throws IOException if IOException occurs
   */
  public static MarkdownFile of(Path path) throws IOException {
    BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
    return new MarkdownFile(path, path.getFileName().toString(),
        attrs.lastModifiedTime(), attrs.creationTime());
  }
}
