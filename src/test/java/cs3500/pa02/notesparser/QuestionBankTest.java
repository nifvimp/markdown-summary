package cs3500.pa02.notesparser;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class QuestionBankTest {
  private static final Path NOTES = Path.of("src/test/resources/notes");
  private static final Set<String> EXPECTED = Set.of(
      "What is the syntax to initialize an array:::Type[numberOfElements];:::HARD",
      "Are arrays modifiable?:::Yes, but only the elements, not the size of the array:::HARD",
      "What is the syntax for declaring a vector?:::Vector<type> v = new Vector();:::HARD"
      );
  private final QuestionBank questionBank = new QuestionBank(NOTES);

  @Test
  public void testCompile() {
    assertEquals(EXPECTED, new HashSet<>(questionBank.compile()));
  }

  @Test
  public void testFileExtension() {
    assertEquals(".sr", questionBank.fileExtension());
  }
}