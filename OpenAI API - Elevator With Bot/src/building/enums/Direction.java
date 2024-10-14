package building.enums;

/**
 * The direction of the elevator, either up, down, or stopped.
 */
public enum Direction {
  UP("^"),
  DOWN("v"),
  STOPPED("-");

  private final String display;

  Direction(String symbol) {
    this.display = symbol;
  }

  @Override
  public String toString() {
    return this.display;
  }
}
