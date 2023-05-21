package cs3500.pa02.notesparser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

/**
 * Tests the Summary class's error handling. Output of Summary tested in Integration tests in
 * DriverTest.java.
 */
class SummaryTest {
  /**
   * Tests if write throws if program can't write.
   */
  @Test
  public void testWriteThrowsIfCanNotWriteToOutput() {
    Summary s = new Summary(Path.of("src/test/resources/notes"), OrderingFlag.FILENAME);
    File notWritable = new File("src/test/resources/NotWritableFile.md");
    if (!notWritable.setWritable(false)) {
      throw new RuntimeException();
    }
    assertThrows(RuntimeException.class,
        () -> s.write(Path.of("src/test/resources/NotWritableFile"))
    );
    if (!notWritable.setWritable(true)) {
      throw new RuntimeException();
    }
  }

  /**
   * Tests if compile throws if can't parse root for files.
   */
  @Test
  public void testCompileThrowsIfWalkFails()  {
    Summary s = new Summary(Path.of("src/test/fake"), OrderingFlag.FILENAME);
    assertThrows(RuntimeException.class, s::compile);
  }
}