package building.enums;

/**
 * This enum is used to represent the status of the elevators,
 * either running, stopping, or out of service.
 */
public enum ElevatorSystemStatus {
  running("Running"),
  stopping("Stopping"),
  outOfService("Out Of Service");
  final String display;

  ElevatorSystemStatus(String display) {
    this.display = display;
  }

  @Override
  public String toString() {
    return this.display;
  }
}
