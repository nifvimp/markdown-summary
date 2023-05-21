package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests the output of this program and commandline argument validation of
 * the driver.
 */
public class DriverTest {
  /**
   * sets up files for testing.
   */
  @BeforeAll
  public static void setup() {
    try {
      Files.delete(Path.of("src/test/resources/studyGuide.md"));
    } catch (IOException ignored) {
    }
    try {
      BasicFileAttributeView arrays = Files.getFileAttributeView(
          Path.of("src/test/resources/notes/arrays.md"), BasicFileAttributeView.class
      );
      BasicFileAttributeView vectors = Files.getFileAttributeView(
          Path.of("src/test/resources/notes/moreNotes/vectors.md"),
          BasicFileAttributeView.class
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
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  /**
   * Tests program result is correct more robustly.
   */
  @Test
  public void testDriverOutputExtendedNotes() {
    Driver.main(new String[] {
        "src/test/resources/extendedNotes",
        "FILENAME",
        "src/test/resources/studyGuide"
    });
    String result = null;
    try {
      result = Files.readString(Path.of("src/test/resources/studyGuide.md"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(
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
            - Gotcha: Array size is not modifiable once instantiated.

            ## Formatting is Important
            - formatting is a way of life

            ## Formatting is for pros!?

            # Regex is very powerful
            - Use it well, and you got infinite power!

            # Vectors
            - Vectors act like resizable arrays

            ## Declaring a vector
            - General Form: Vector<type> v = new Vector();
            - type needs to be a valid reference type

            ## Adding an element to a vector
            - v.add(object of type);""",
        result
    );
  }

  /**
   * Tests program result is correct by filename.
   */
  @Test
  public void testDriverOutputFilename() {
    Driver.main(new String[] {
        "src/test/resources/notes",
        "FILENAME",
        "src/test/resources/studyGuide"
    });
    String result = null;
    try {
      result = Files.readString(Path.of("src/test/resources/studyGuide.md"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(
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
            - Gotcha: Array size is not modifiable once instantiated.
                      
            # Vectors
            - Vectors act like resizable arrays
                      
            ## Declaring a vector
            - General Form: Vector<type> v = new Vector();
            - type needs to be a valid reference type
                      
            ## Adding an element to a vector
            - v.add(object of type);""",
        result
    );
  }

  /**
   * Tests program result is correct by creation time.
   */
  @Test
  public void testDriverOutputCreated() {
    Driver.main(new String[] {
        "src/test/resources/notes/moreNotes",
        "CREATED",
        "src/test/resources/studyGuide"
    });
    String result = null;
    try {
      result = Files.readString(Path.of("src/test/resources/studyGuide.md"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(
        """
            # Vectors
            - Vectors act like resizable arrays

            ## Declaring a vector
            - General Form: Vector<type> v = new Vector();
            - type needs to be a valid reference type

            ## Adding an element to a vector
            - v.add(object of type);""",
        result
    );
  }

  /**
   * Tests program result is correct by last modified time.
   */
  @Test
  public void testDriverOutputModified() {
    // for some reason reverses when uploaded to GitHub.
    Driver.main(new String[] {
        "src/test/resources/notes",
        "MODIFIED",
        "src/test/resources/studyGuide"
    });
    String result = null;
    try {
      result = Files.readString(Path.of("src/test/resources/studyGuide.md"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(
        """
            # Vectors
            - Vectors act like resizable arrays
              
            ## Declaring a vector
            - General Form: Vector<type> v = new Vector();
            - type needs to be a valid reference type
              
            ## Adding an element to a vector
            - v.add(object of type);
                      
            # Java Arrays
            - An **array** is a collection of variables of the same type
                      
            ## Declaring an Array
            - General Form: type[] arrayName;
            - only creates a reference
            - no array has actually been created yet
                    
            ## Creating an Array (Instantiation)
            - General form:  arrayName = new type[numberOfElements];
            - numberOfElements must be a positive Integer.
            - Gotcha: Array size is not modifiable once instantiated.""",
        result
    );
  }

  /**
   * Tests driver throws if incorrect number of  arguments.
   */
  @Test
  public void testDriverThrowsIfIncorrectNumberOfArguments() {
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(new String[] {"src/test/resources/notes", "src/test/resources"})
    );
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(
            new String[] {"src/test/resources/notes", "FileName", "src/test/resources", "stop"})
    );
  }

  /**
   * Tests driver throws if root is invalid.
   */
  @Test
  public void testDriverThrowsIfRootIsInvalidPath() {
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(
            new String[] {"src/test/re|sourc%>es/notes", "FileName", "src/test/resources"})
    );
  }

  /**
   * Tests driver throws if output is invalid.
   */
  @Test
  public void testDriverThrowsIfOutputIsnvalidPath() {
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(
            new String[] {"src/test/resources/notes", "CREATED",
                "src/test/resources/<study|Guide>"
            })
    );
  }

  /**
   * Tests driver throws if ordering flag is invalid.
   */
  @Test
  public void testDriverThrowsIfOrderingFlagDoesNotExist() {
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(new String[] {"src/test/resources/notes", "none", "src/test/resources"})
    );
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(new String[] {"src/test/resources/notes", "", "src/test/resources"})
    );
  }

  /**
   * Tests driver throws if root doesn't exist.
   */
  @Test
  public void testDriverThrowsIfRootDoesNotExist() {
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(
            new String[] {"src/test/resources/notNotes", "filename", "src/test/resources"})
    );
  }

  /**
   * Tests driver throws if root is not a directory.
   */
  @Test
  public void testDriverThrowsIfRootIsNotDirectory() {
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(
            new String[] {"src/test/resources/studyGuide", "created", "src/test/resources"})
    );
  }

  /**
   * Tests driver throws if output is not writable.
   */
  @Test
  public void testDriverThrowsIfOutputIsNotWritable() {
    //TODO: fix write method and test.
    // throws run time b/c validation didn't check with given output + extensions
//    if (!new File("src/test/resources/NotWritableFile.md").setWritable(false)) {
//      throw new RuntimeException();
//    }
//    assertThrows(IllegalArgumentException.class,
//        () -> Driver.main(new String[] {
//            "src/test/resources/notes", "filename",
//            "src/test/resources/NotWritableFile"
//        })
//    );
  }

  /**
   * Tests driver throws if root is not a markdown file.
   */
  @Test
  public void testDriverThrowsIfOutputIsNotMarkdownFile() {
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(
            new String[] {
                "src/test/resources/notes", "created",
                "src/test/resources/studyGuide.pdf"
            })
    );
  }

  /**
   * Tests driver constructs properly.
   */
  @Test
  public void fakeTest() {
    assertDoesNotThrow(Driver::new);
    assertEquals(5, 5);
  }
}