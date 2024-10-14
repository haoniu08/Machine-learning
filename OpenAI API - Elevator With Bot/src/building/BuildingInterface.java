package building;

import building.enums.ElevatorSystemStatus;
import elevator.ElevatorInterface;
import java.util.List;
import scanerzus.Request;

/**
 * This interface is used to represent a building,
 * which contains a number of floors and elevators.
 * It is responsible for managing the elevators and requests,
 * and providing a report of the elevator
 */
public interface BuildingInterface {

  /**
   * This method is used to get the number of floors in the building.
   *
   * @return the number of floors in the building.
   */
  int getNumberOfFloors();

  /**
   * This method is used to get the number of elevators in the building.
   *
   * @return the number of elevators in the building.
   */
  int getNumberOfElevators();

  /**
   * This method is used to get the capacity of the elevators in the building.
   *
   * @return the capacity of the elevators in the building.
   */
  int getElevatorCapacity();

  /**
   * This method is used to get the status of the elevator system.
   *
   * @return the status of the elevator system.
   */
  ElevatorSystemStatus getSystemStatus();

  /**
   * This method is used to get the up requests in the building.
   *
   * @return the up request in the building.
   */
  List<Request> getUpRequests();

  /**
   * This method is used to get the down requests in the building.
   *
   * @return the down request in the building.
   */
  List<Request> getDownRequests();

  /**
   * This method is used to get the elevators in the building.
   *
   * @return the elevators in the building.
   */
  List<ElevatorInterface> getElevators();

  /**
   * This method will add the request to the appropriate list of requests.
   *
   * @param request The request to be added.
   * @return true if the request was added.
   * @throws IllegalArgumentException If the request is invalid.
   * @throws IllegalStateException    If the elevator system is not running.
   */
  boolean addRequest(Request request)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * This method will step the building forward one step.
   */
  void step();

  /**
   * This method will start the elevator system.
   *
   * @return true if the elevator system was started.
   * @throws IllegalStateException If the system is stopping.
   */
  boolean startElevatorSystem() throws IllegalStateException;

  /**
   * This method will stop the elevator system.
   */
  void stopElevatorSystem();

  /**
   * This method is used to get the status of the elevator system.
   *
   * @return the status of the elevator system.
   */
  BuildingReport getElevatorSystemStatus();

  /**
   * This method is used to generate a random request.
   *
   * @return a random request.
   */
  Request generateRandomRequest();
}
