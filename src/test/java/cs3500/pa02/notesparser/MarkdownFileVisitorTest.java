package cs3500.pa02.notesparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the MarkdownFileVisitor's error handling and functionality.
 */
class MarkdownFileVisitorTest {
  MarkdownFileVisitor mfv;

  /**
   * Makes new MarkdownFIleVisitor before each test.
   */
  @BeforeEach
  public void setup() {
    mfv = new MarkdownFileVisitor();
  }

  /**
   * Tests visitor gets back markdown files correctly.
   *
   * @throws IOException if IOException occurs
   */
  @Test
  public void testGetFoundMarkdownFiles() throws IOException {
    // getting files without visiting anything
    assertThrows(IllegalStateException.class,
        () -> mfv.getFoundMarkdownFiles()
    );
    // walk files
    Files.walkFileTree(Path.of("src/test/resources/extendedNotes"), mfv);
    assertEquals(Set.of(
        MarkdownFile.of(Path.of("src/test/resources/extendedNotes/new/formatting.md")),
        MarkdownFile.of(Path.of("src/test/resources/extendedNotes/new/Regex.md")),
        MarkdownFile.of(Path.of("src/test/resources/extendedNotes/arrays.md")),
        MarkdownFile.of(Path.of("src/test/resources/extendedNotes/vectors.md"))
    ), new HashSet<>(mfv.getFoundMarkdownFiles()));
  }

  /**
   * Tests visitFile adds markdown file to instance variable if the visited file is a markdown
   * file.
   *
   * @throws IOException if IOException occurs
   */
  @Test
  public void testVisitFile() throws IOException {
    // not regular file since file doesn't exist
    assertEquals(FileVisitResult.CONTINUE, mfv.visitFile(Path.of(""), null));
    // visit file with no header
    Path fileWithNoExtension = Path.of("src/test/resources/extendedNotes/FakeNews");
    assertEquals(FileVisitResult.CONTINUE,
        mfv.visitFile(fileWithNoExtension,
            Files.readAttributes(fileWithNoExtension,
                BasicFileAttributes.class))
    );
    assertEquals(0, mfv.getFoundMarkdownFiles().size());
    Path pdf = Path.of("src/test/resources/extendedNotes/FakeUML.pdf");
    assertEquals(FileVisitResult.CONTINUE,
        mfv.visitFile(pdf, Files.readAttributes(pdf, BasicFileAttributes.class))
    );
    assertEquals(0, mfv.getFoundMarkdownFiles().size());
    Path md = Path.of("src/test/resources/extendedNotes/arrays.md");
    assertEquals(FileVisitResult.CONTINUE,
        mfv.visitFile(md, Files.readAttributes(md, BasicFileAttributes.class))
    );
    assertEquals(List.of(MarkdownFile.of(md)), mfv.getFoundMarkdownFiles());
  }

  /**
   * Tests visitor continues.
   *
   * @throws IOException if IOException occurs
   */
  @Test
  public void testPreVisitDirectory() throws IOException {
    Path dir = Path.of("src/test/resources");
    assertEquals(FileVisitResult.CONTINUE,
        mfv.preVisitDirectory(dir, Files.readAttributes(dir, BasicFileAttributes.class))
    );
  }

  /**
   * Tests visitor continues.
   */
  @Test
  public void testVisitFileFailed() {
    assertEquals(FileVisitResult.CONTINUE,
        mfv.visitFileFailed(Path.of("src/test/resources/FakeNews"), new IOException())
    );
  }

  /**
   * Tests visitor continues.
   */
  @Test
  public void testPostVisitDirectory() {
    assertEquals(FileVisitResult.CONTINUE,
        mfv.postVisitDirectory(Path.of("src/test/resources"), new IOException())
    );
  }
}