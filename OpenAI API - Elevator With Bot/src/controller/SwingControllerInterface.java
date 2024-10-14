package controller;

import scanerzus.Request;

/**
 * This class is the controller interface for the GUI. It will handle all the user input and
 * update the GUI accordingly.
 */
public interface SwingControllerInterface {

  /**
   * This method is used to add a request to the building.
   *
   * @param request the request to be added.
   */
  void addRequest(Request request);

  /**
   * This method is used to start the elevator system.
   */
  void startElevatorSystem();

  /**
   * This method is used to stop the elevator system.
   */
  void stopElevatorSystem();

  /**
   * This method is to step the building forward a number of steps.
   *
   * @param steps the number of steps to step the building.
   */
  void step(int steps);

  /**
   * This method is used to update the building grid.
   */
  void update();

  /**
   * This method is used to add a random request to the building.
   */
  void addRandomRequest();
}
