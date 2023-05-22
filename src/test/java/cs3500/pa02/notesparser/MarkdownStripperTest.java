package cs3500.pa02.notesparser;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

public class MarkdownStripperTest {
  private static final Path arrays = Path.of("src/test/resources/notes/arrays.md");
  private static final Path vectors = Path.of("src/test/resources/notes/vectors.md");
  private final MarkdownStripper stripper = new MarkdownStripper();

  @Test
  public void testExtractOne() {
    List<String> extracted = stripper.extract(arrays);
    assertEquals(
        """
            # Java Arrays
            An **array** is a collection of variables of the same type
                        
            ## Declaring an Array
            General Form: type[] arrayName;
            only creates a reference
            no array has actually been created yet
                        
            ## Creating an Array (Instantiation)
            General form:  arrayName = new type[numberOfElements];
            What is the syntax to initialize an array:::Type[numberOfElements];
            numberOfElements must be a positive Integer.
            Gotcha: Array size is not modifiable once instantiated.
            Are arrays modifiable? ::: Yes, but only the elements, not the size of the array
            """, String.join("\n", extracted)
    );
  }

  @Test
  public void testExtractTwo() {
    List<String> extracted = stripper.extract(vectors);
    assertEquals(
        """
            # Vectors
            Vectors act like resizable arrays
                        
            ## Declaring a vector
            General Form: Vector<type> v = new Vector();
            What is the syntax for declaring a vector?:::Vector<type> v = new Vector();
            type needs to be a valid reference type
                        
            ## Adding an element to a vector
            v.add(object of type);
            """, String.join("\n", extracted)
    );
  }

  @Test
  public void testIsHeader() {
    assertTrue(stripper.isHeader("# header"));
    assertTrue(stripper.isHeader("########## header"));
    assertFalse(stripper.isHeader(""));
    assertFalse(stripper.isHeader("#header"));
    assertFalse(stripper.isHeader("hello # header"));
  }
}