package cs3500.pa02.studysession;

import java.util.List;

/**
 * Handles transformations of information.
 *
 * @param <P> type to interpret
 * @param <R> type of interpretation
 */
public interface Interpreter<P, R> {
  /**
   * Transforms the given information according to the interpretation specified by this
   * interpreter.
   *
   * @param objects objects to interpret
   * @return result of interpretation
   */
  List<R> interpret(List<P> objects);
}