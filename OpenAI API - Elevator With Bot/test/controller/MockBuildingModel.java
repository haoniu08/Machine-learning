package controller;

import building.BuildingInterface;
import building.BuildingReport;
import building.enums.ElevatorSystemStatus;
import elevator.ElevatorInterface;
import java.util.List;
import scanerzus.Request;

/**
 * This is the mock class for the BuildingInterface, it will be used
 * for testing the SwingController class.
 * */
public class MockBuildingModel implements BuildingInterface {
  @Override
  public int getNumberOfFloors() {
    return 0;
  }

  @Override
  public int getNumberOfElevators() {
    return 0;
  }

  @Override
  public int getElevatorCapacity() {
    return 0;
  }

  @Override
  public ElevatorSystemStatus getSystemStatus() {
    return ElevatorSystemStatus.outOfService;
  }

  @Override
  public List<Request> getUpRequests() {
    return null;
  }

  @Override
  public List<Request> getDownRequests() {
    return null;
  }

  @Override
  public List<ElevatorInterface> getElevators() {
    return null;
  }

  @Override
  public boolean addRequest(Request request)
          throws IllegalArgumentException, IllegalStateException {
    return false;
  }

  @Override
  public void step() {

  }

  @Override
  public boolean startElevatorSystem() throws IllegalStateException {
    return false;
  }

  @Override
  public void stopElevatorSystem() {

  }

  @Override
  public BuildingReport getElevatorSystemStatus() {
    return new BuildingReport(
        0,
        0,
        0,
        null,
        null,
        null,
        ElevatorSystemStatus.running
    );
  }

  @Override
  public Request generateRandomRequest() {
    return null;
  }
}
