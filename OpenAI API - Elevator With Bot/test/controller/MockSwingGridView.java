package controller;


import java.util.List;
import scanerzus.Request;
import swingview.SwingGridViewInterface;

/**
 * This is the mock class for the SwingGridViewInterface, it will be used
 * for testing the SwingController class.
 * */
public class MockSwingGridView implements SwingGridViewInterface {
  @Override
  public void setController(SwingControllerInterface controller) {

  }

  @Override
  public void updateBuildingGrid(int[] currentFloors) {

  }

  @Override
  public void updateSystemStatus(String status) {
    status = "Running";
  }

  @Override
  public void displayUpRequests(List<Request> upRequests) {

  }

  @Override
  public void displayDownRequests(List<Request> downRequests) {

  }

  @Override
  public void displayElevatorInfo(List<String> elevatorInfo) {

  }

  @Override
  public void disableFunctionsWhenStopping() {

  }

  @Override
  public void disableFunctionsWhenOutOfService() {

  }

  @Override
  public void enableFunctionsWhenRunning() {

  }

  @Override
  public void displayError(String error) {
  }

  @Override
  public void enableSteps() {

  }

  @Override
  public void disableSteps() {

  }
}
