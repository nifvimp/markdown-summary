package cs3500.pa01;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * A visitor of files that records and returns the markdown files it visits.
 */
public class MarkdownFileVisitor implements FileVisitor<Path> {
  private final List<MarkdownFile> foundMarkdownFiles;
  private boolean anyVisited;

  /**
   * Initializes a newly created MarkdownFileVisitor.
   */
  public MarkdownFileVisitor() {
    this.foundMarkdownFiles = new ArrayList<>();
    this.anyVisited = false;
  }

  /**
   * Returns a list of files a file visitor has visited that have the '.md' file extension.
   *
   * @return list of markdown files this file visitor has visited
   * @throws IllegalStateException if this markdown file visitor has not visited any files yet
   */
  public List<MarkdownFile> getFoundMarkdownFiles() {
    if (!anyVisited) {
      throw new IllegalStateException("MarkdownFileVisitor has not visited any files.");
    }
    return new ArrayList<>(this.foundMarkdownFiles);
  }

  /**
   * Returns the file extension of the given file.
   *
   * @param file file to extract file extension from
   * @return the file's file extension
   * @throws IllegalArgumentException if the file does not have a file extension
   */
  private static String extractFileExtension(Path file) {
    String filename = file.getFileName().toString();
    int extensionStart = filename.lastIndexOf('.');
    if (extensionStart < 0) {
      throw new IllegalArgumentException(
          "The file '" + file + "' does not have a file extension."
      );
    }
    return filename.substring(extensionStart);
  }

  /**
   * Invoked for a file in a directory. Adds the visited file to this MarkdownFileVisitor's
   * foundMarkdownFiles list if the file has the '.md' file extension.
   *
   * @param file a reference to the file
   * @param attrs the file's basic attributes
   * @return the visit result
   */
  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
    this.anyVisited = true;
    if (!Files.isRegularFile(file)) {
      return FileVisitResult.CONTINUE;
    }
    try {
      if (extractFileExtension(file).equals(".md")) {
        this.foundMarkdownFiles.add(
            new MarkdownFile(file, file.getFileName().toString(),
                attrs.lastModifiedTime(), attrs.creationTime())
        );
      }
    } catch (IllegalArgumentException ignored) {
      // An empty catch block
    }
    return FileVisitResult.CONTINUE;
  }

  /**
   * Invoked for a directory before entries in the directory are visited.
   *
   * @param dir a reference to the directory
   * @param attrs the directory's basic attributes
   * @return the visit result
   */
  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
    this.anyVisited = true;
    return FileVisitResult.CONTINUE;
  }

  /**
   * Invoked for a file that could not be visited. This method is invoked if the file's attributes
   * could not be read, the file is a directory that could not be opened, and other reasons.
   *
   * @param file a reference to the file
   * @param exc the I/O exception that prevented the file from being visited
   * @return the visit result
   */
  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) {
    return FileVisitResult.CONTINUE;
  }

  /**
   * Invoked for a directory after entries in the directory, and all of their descendants, have
   * been visited. This method is also invoked when iteration of the directory completes
   * prematurely (by a {@link MarkdownFileVisitor#visitFile(Path, BasicFileAttributes) visitFile}
   * method returning {@link FileVisitResult#SKIP_SIBLINGS SKIP_SIBLINGS}, or an I/O error when
   * iterating over the directory).
   *
   * @param dir a reference to the directory
   * @param exc
   *        {@code null} if the iteration of the directory completes without
   *        an error; otherwise the I/O exception that caused the iteration
   *        of the directory to complete prematurely
   *
   * @return the visit result
   */
  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
    return FileVisitResult.CONTINUE;
  }
}
