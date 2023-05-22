package cs3500.pa02.notesparser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotesControllerTest {
  private static final Path NOTES = Path.of("src/test/resources/notes");
  private static final Path OUTPUT = Path.of("src/test/resources/studyGuide");
  private static final Path STUDY_GUIDE = Path.of("src/test/resources/studyGuide.md");
  private static final Path SPACED_REPETITION = Path.of("src/test/resources/studyGuide.sr");
  private static final Path ARRAYS = Path.of("src/test/resources/notes/arrays.md");
  private static final Path VECTORS = Path.of("src/test/resources/notes/vectors.md");
  private static final String ARRAYS_CONTENTS =
      """
      # Java Arrays
      - An **array** is a collection of variables of the same type

      ## Declaring an Array
      - General Form: type[] arrayName;
      - only creates a reference
      - no array has actually been created yet

      ## Creating an Array (Instantiation)
      - General form:  arrayName = new type[numberOfElements];
      - numberOfElements must be a positive Integer.
      - Gotcha: Array size is not modifiable once instantiated.""";
  private static final String VECTORS_CONTENTS =
      """
      # Vectors
      - Vectors act like resizable arrays

      ## Declaring a vector
      - General Form: Vector<type> v = new Vector();
      - type needs to be a valid reference type

      ## Adding an element to a vector
      - v.add(object of type);""";
  private Set<String> questions;

  /**
   * sets up files for testing.
   */
  @BeforeAll
  public static void fileSetup() {
    try {
      Files.delete(STUDY_GUIDE);
    } catch (IOException ignored) {
      // An empty catch block
    }
    try {
      BasicFileAttributeView arrays = Files.getFileAttributeView(
          ARRAYS, BasicFileAttributeView.class
      );
      BasicFileAttributeView vectors = Files.getFileAttributeView(
          VECTORS, BasicFileAttributeView.class
      );
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      arrays.setTimes(
          FileTime.fromMillis(sdf.parse("4/4/2023").getTime()),
          null,
          FileTime.fromMillis(sdf.parse("31/3/2023").getTime())
      );
      vectors.setTimes(
          FileTime.fromMillis(sdf.parse("3/4/2023").getTime()),
          null,
          FileTime.fromMillis(sdf.parse("3/4/2023").getTime())
      );
    } catch (Exception e) {
      throw new RuntimeException("Error changing file times of files.", e);
    }
  }

  @BeforeEach
  public void setupQuestions() {
    questions = Set.of(
        "What is the syntax to initialize an array:::Type[numberOfElements];:::HARD",
        "Are arrays modifiable?:::Yes, but only the elements, not the size of the array:::HARD",
        "What is the syntax for declaring a vector?:::Vector<type> v = new Vector();:::HARD"
    );
  }

  /**
   * Tests program result is correct by filename.
   */
  @Test
  public void testDriverOutputFilename() {
    NotesController controller = new NotesController(NOTES, OrderingFlag.FILENAME, OUTPUT);
    controller.run();
    String studyGuide;
    Set<String> questionBank;
    try {
      studyGuide = Files.readString(STUDY_GUIDE);
      questionBank = new HashSet<>(Files.readAllLines(SPACED_REPETITION));
    } catch (IOException e) {
      throw new RuntimeException("Failed to read output of program.", e);
    }
    assertEquals(ARRAYS_CONTENTS + "\n\n" + VECTORS_CONTENTS, studyGuide);
    assertEquals(questions, questionBank);
  }

  /**
   * Tests program result is correct by creation time.
   */
  @Test
  public void testDriverOutputCreated() {
    NotesController controller = new NotesController(NOTES, OrderingFlag.CREATED, OUTPUT);
    controller.run();
    String studyGuide;
    Set<String> questionBank;
    FileTime arrayTime;
    FileTime vectorTime;
    try {
      studyGuide = Files.readString(STUDY_GUIDE);
      questionBank = new HashSet<>(Files.readAllLines(SPACED_REPETITION));
      arrayTime = (FileTime) Files.getAttribute(ARRAYS, "creationTime");
      vectorTime = (FileTime) Files.getAttribute(VECTORS, "creationTime");
    } catch (IOException e) {
      throw new RuntimeException("Failed to read output of program.", e);
    }
    String expected;
    if (arrayTime.compareTo(vectorTime) <= 0) {
      expected = ARRAYS_CONTENTS + "\n\n" + VECTORS_CONTENTS;
    } else {
      expected = VECTORS_CONTENTS + "\n\n" + ARRAYS_CONTENTS;
    }
    assertEquals(expected, studyGuide);
    assertEquals(questions, questionBank);
  }

  /**
   * Tests program result is correct by last modified time.
   */
  @Test
  public void testDriverOutputModified() {
    NotesController controller = new NotesController(NOTES, OrderingFlag.MODIFIED, OUTPUT);
    controller.run();
    String studyGuide;
    Set<String> questionBank;
    try {
      studyGuide = Files.readString(STUDY_GUIDE);
      questionBank = new HashSet<>(Files.readAllLines(SPACED_REPETITION));
    } catch (IOException e) {
      throw new RuntimeException("Failed to read output of program.", e);
    }
    assertEquals(VECTORS_CONTENTS + "\n\n" + ARRAYS_CONTENTS, studyGuide);
    assertEquals(questions, questionBank);
  }
}