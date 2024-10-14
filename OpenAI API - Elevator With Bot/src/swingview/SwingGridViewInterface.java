package swingview;

import controller.SwingControllerInterface;
import java.util.List;
import scanerzus.Request;

/**
 * This interface is used to represent a swing grid view,
 * which contains a control panel and a number of floors and elevators.
 * It is responsible for managing the elevators and requests,
 * and providing a report of the elevator
 */
public interface SwingGridViewInterface {

  /**
   * This method is used to set the controller for the swing grid view.
   *
   * @param controller the controller for the swing grid view.
   */
  void setController(SwingControllerInterface controller);

  /**
   * This method is used to update the building grid.
   *
   * @param currentFloors the current floors in the building.
   */
  void updateBuildingGrid(int[] currentFloors);

  /**
   * This method is used to update the system status.
   *
   * @param status the status of the system.
   */
  void updateSystemStatus(String status);

  /**
   * This method is used to display the up requests in the building.
   *
   * @param upRequests the up requests in the building.
   */
  void displayUpRequests(List<Request> upRequests);

  /**
   * This method is used to display the down requests in the building.
   *
   * @param downRequests the down requests in the building.
   */
  void displayDownRequests(List<Request> downRequests);

  /**
   * This method is used to display the elevator information.
   *
   * @param elevatorInfo the elevator information.
   */
  void displayElevatorInfo(List<String> elevatorInfo);

  /**
   * This method is used to disable the functions when stopping the elevator system.
   */
  void disableFunctionsWhenStopping();

  /**
   * This method is used to disable the functions when the elevator system is out of service.
   */
  void disableFunctionsWhenOutOfService();

  /**
   * This method is used to enable the functions when the elevator system is running.
   */
  void enableFunctionsWhenRunning();

  /**
   * This method is used to display an error message.
   *
   * @param error the error message to be displayed.
   */
  void displayError(String error);

  /**
   * This method is used to enable the step button and the combo box.
   */
  void enableSteps();

  /**
   * This method is used to disable the step button and the combo box.
   */
  void disableSteps();
}

