package cs3500.pa02.studysession;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests if SessionInfo updates properly
 */
public class SessionInfoTest {
  private SessionInfo session;

  /**
   * Sets up test session info.
   */
  @BeforeEach
  public void setup() {
    session = new SessionInfo(60, 120, 210, 10, 3);
  }

  /**
   * Tests if session info formats information correctly.
   */
  @Test
  public void testGetInfo() {
    assertEquals("""
            Session Information:
              Questions Answered: 60
              Questions Changed from Easy to Hard: 3
              Questions Changed from Hard to Easy: 10
              Updated Total Hard Questions: 120
              Updated Total Easy Questions: 210""",
        session.getInfo());
  }

  /**
   * Tests if output of session info reflects changes made to it.
   */
  @Test
  public void testEasyChanged() {
    session.easyChanged();
    session.easyChanged();
    assertEquals("""
            Session Information:
              Questions Answered: 62
              Questions Changed from Easy to Hard: 5
              Questions Changed from Hard to Easy: 10
              Updated Total Hard Questions: 122
              Updated Total Easy Questions: 208""",
        session.getInfo());
  }

  /**
   * Tests if output of session info reflects changes made to it.
   */
  @Test
  public void testHardChanged() {
    session.hardChanged();
    session.hardChanged();
    session.hardChanged();
    assertEquals("""
            Session Information:
              Questions Answered: 63
              Questions Changed from Easy to Hard: 3
              Questions Changed from Hard to Easy: 13
              Updated Total Hard Questions: 117
              Updated Total Easy Questions: 213""",
        session.getInfo());
  }

  /**
   * Tests if output of session info reflects changes made to it.
   */
  @Test
  public void testProblemAnswered() {
    session.problemAnswered();
    assertEquals("""
            Session Information:
              Questions Answered: 61
              Questions Changed from Easy to Hard: 3
              Questions Changed from Hard to Easy: 10
              Updated Total Hard Questions: 120
              Updated Total Easy Questions: 210""",
        session.getInfo());
  }
}