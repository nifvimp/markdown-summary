package cs3500.pa02.notesparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests sorting by creation time.
 */
public class SortByCreatedTest {
  List<MarkdownFile> files;
  MarkdownFile arrays;
  MarkdownFile vectors;
  MarkdownFile regex;
  MarkdownFile regexDup;
  MarkdownFile formatting;

  /**
   * Sets up fake files to be sorted.
   *
   * @throws ParseException if code is incorrect
   */
  @BeforeEach
  public void setup() throws ParseException {
    files = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    arrays = new MarkdownFile(
        Path.of("src/test/resources/pa01Test/arrays.md"),
        "arrays.md",
        FileTime.fromMillis(sdf.parse("3/2/2023").getTime()),
        FileTime.fromMillis(sdf.parse("1/1/2023").getTime())
    );
    vectors = new MarkdownFile(
        Path.of("src/test/resources/pa01Test/vectors.md"),
        "vectors.md",
        FileTime.fromMillis(sdf.parse("2/2/2023").getTime()),
        FileTime.fromMillis(sdf.parse("2/1/2023").getTime())
    );
    regex = new MarkdownFile(
        Path.of("src/test/resources/pa01Test/Regex.md"),
        "Regex.md",
        FileTime.fromMillis(sdf.parse("4/2/2023").getTime()),
        FileTime.fromMillis(sdf.parse("5/1/2023").getTime())
    );
    regexDup = new MarkdownFile(
        Path.of("src/test/resources/pa01Test/RegexDup.md"),
        "RegexDup.md",
        FileTime.fromMillis(sdf.parse("4/2/2023").getTime()),
        FileTime.fromMillis(sdf.parse("5/1/2023").getTime())
    );
    formatting = new MarkdownFile(
        Path.of("src/test/resources/pa01Test/formatting.md"),
        "formatting.md",
        FileTime.fromMillis(sdf.parse("5/2/2023").getTime()),
        FileTime.fromMillis(sdf.parse("3/1/2023").getTime())
    );
    files.add(arrays);
    files.add(vectors);
    files.add(regex);
    files.add(regexDup);
    files.add(formatting);
  }

  /**
   * Tests if SortByCreated sorts files correctly.
   */
  @Test
  public void testComparator() {
    files.sort(new SortByCreated());
    assertEquals(List.of(arrays, vectors, formatting, regex, regexDup), files);
  }
}
