package cs3500.pa02.notesparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

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
public class MarkdownFileVisitorTest {
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
   */
  @Test
  public void testGetFoundMarkdownFiles() {
    // getting files without visiting anything
    assertThrows(IllegalStateException.class,
        () -> mfv.getFoundMarkdownFiles()
    );
    // walk files
    try {
      Files.walkFileTree(Path.of("src/test/resources/pa01Test"), mfv);
      assertEquals(Set.of(
          MarkdownFile.of(Path.of("src/test/resources/pa01Test/new/formatting.md")),
          MarkdownFile.of(Path.of("src/test/resources/pa01Test/new/Regex.md")),
          MarkdownFile.of(Path.of("src/test/resources/pa01Test/arrays.md")),
          MarkdownFile.of(Path.of("src/test/resources/pa01Test/vectors.md"))
      ), new HashSet<>(mfv.getFoundMarkdownFiles()));
    } catch (IOException e) {
      fail("Error reading test files.");
    }
  }

  /**
   * Tests visitFile adds markdown file to instance variable if the visited file is a markdown
   * file.
   */
  @Test
  public void testVisitFile() {
    try {
      // not regular file since file doesn't exist
      assertEquals(FileVisitResult.CONTINUE, mfv.visitFile(Path.of(""), null));
      // visit file with no header
      Path fileWithNoExtension = Path.of("src/test/resources/pa01Test/FakeNews");
      assertEquals(FileVisitResult.CONTINUE,
          mfv.visitFile(fileWithNoExtension,
              Files.readAttributes(fileWithNoExtension,
                  BasicFileAttributes.class))
      );
      assertEquals(0, mfv.getFoundMarkdownFiles().size());
      Path pdf = Path.of("src/test/resources/pa01Test/FakeUML.pdf");
      assertEquals(FileVisitResult.CONTINUE,
          mfv.visitFile(pdf, Files.readAttributes(pdf, BasicFileAttributes.class))
      );
      assertEquals(0, mfv.getFoundMarkdownFiles().size());
      Path md = Path.of("src/test/resources/pa01Test/arrays.md");
      assertEquals(FileVisitResult.CONTINUE,
          mfv.visitFile(md, Files.readAttributes(md, BasicFileAttributes.class))
      );
      assertEquals(List.of(MarkdownFile.of(md)), mfv.getFoundMarkdownFiles());
    } catch (IOException e) {
      fail("Error reading tests files.");
    }
  }

  /**
   * Tests visitor continues.
   */
  @Test
  public void testPreVisitDirectory() {
    Path dir = Path.of("src/test/resources/pa01Test");
    try {
      assertEquals(FileVisitResult.CONTINUE,
          mfv.preVisitDirectory(dir, Files.readAttributes(dir, BasicFileAttributes.class))
      );
    } catch (IOException e) {
      fail("Error reading test files.");
    }
  }

  /**
   * Tests visitor continues.
   */
  @Test
  public void testVisitFileFailed() {
    assertEquals(FileVisitResult.CONTINUE, mfv.visitFileFailed(
        Path.of("src/test/resources/pa01Test/FakeNews"), new IOException())
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