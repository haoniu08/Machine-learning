package building;

import building.enums.ElevatorSystemStatus;
import elevator.Elevator;
import elevator.ElevatorInterface;
import elevator.ElevatorReport;
import java.util.ArrayList;
import java.util.List;
import scanerzus.Request;


/**
 * This class represents a building, which contains a number of floors and elevators,
 * and is responsible for managing the elevators and requests,
 * and providing a report of the elevator system.
 */
public class Building implements BuildingInterface {

  private final int numberOfFloors;
  private final int numberOfElevators;
  private final int elevatorCapacity;
  private final List<ElevatorInterface> elevators;
  private final List<Request> upRequests;
  private final List<Request> downRequests;
  private ElevatorSystemStatus systemStatus;

  /**
   * The constructor for the building.
   *
   * @param numberOfFloors the number of floors in the building.
   * @param numberOfElevators the number of elevators in the building.
   * @param elevatorCapacity the capacity of the elevators in the building.
   */
  public Building(int numberOfFloors, int numberOfElevators, int elevatorCapacity)
      throws IllegalArgumentException {

    final int maxFloor = 30;
    final int minFloor = 2;
    final int minElevators = 1;
    final int maxElevators = 10;
    final int minElevatorCapacity = 3;
    final int maxElevatorCapacity = 20;

    if (numberOfFloors < minFloor || numberOfFloors > maxFloor) {
      throw new IllegalArgumentException(
          "The number of floors must be between 2 and 30."
      );
    }
    if (numberOfElevators < minElevators || numberOfElevators > maxElevators) {
      throw new IllegalArgumentException(
          "The number of elevators must be between 1 and 10."
      );
    }
    if (
        elevatorCapacity < minElevatorCapacity
        || elevatorCapacity > maxElevatorCapacity
    ) {
      throw new IllegalArgumentException(
          "The elevator capacity must be between 3 and 20."
      );
    }

    this.numberOfFloors = numberOfFloors;
    this.numberOfElevators = numberOfElevators;
    this.elevatorCapacity = elevatorCapacity;
    this.upRequests = new ArrayList<>();
    this.downRequests = new ArrayList<>();

    this.elevators = new ArrayList<>();
    for (int i = 0; i < numberOfElevators; i++) {
      elevators.add(new Elevator(numberOfFloors, elevatorCapacity));
    }
    this.systemStatus = ElevatorSystemStatus.outOfService;
    startElevatorSystem();
  }

  @Override
  public boolean addRequest(Request request)
      throws IllegalArgumentException, IllegalStateException {

    final int groundFloor = 0;

    if (request == null) {
      throw new IllegalArgumentException("Request cannot be null.");
    }


    if (
        request.getStartFloor() < groundFloor
        || request.getStartFloor() > numberOfFloors - 1
        || request.getEndFloor() < groundFloor
        || request.getEndFloor() > numberOfFloors - 1
    ) {
      throw new IllegalArgumentException(
          "The request must have a start and end floor between " + groundFloor
              + " and " + (numberOfFloors - 1) + "."
      );
    }

    if (request.getStartFloor() == request.getEndFloor()) {
      throw new IllegalArgumentException(
          "The start and end floor of the request cannot be the same."
      );
    }

    if (systemStatus == ElevatorSystemStatus.outOfService
        || systemStatus == ElevatorSystemStatus.stopping) {
      throw new IllegalStateException(
          "The elevator system is out of service."
      );
    }

    if (request.getStartFloor() < request.getEndFloor()) {
      this.upRequests.add(request);
    } else {
      this.downRequests.add(request);
    }
    return true;
  }

  @Override
  public boolean startElevatorSystem() throws IllegalStateException {

    if (systemStatus == ElevatorSystemStatus.running) {
      return true;
    }

    // Check if the elevator system is stopping.
    if (systemStatus == ElevatorSystemStatus.stopping) {
      throw new IllegalStateException(
          "Elevator cannot be started until it is stopped"
      );
    }

    // Check if the elevator system is already running.
    if (systemStatus == ElevatorSystemStatus.outOfService) {
      for (ElevatorInterface elevator : elevators) {
        elevator.start();
      }
      systemStatus = ElevatorSystemStatus.running;
    }

    return true;
  }

  @Override
  public void stopElevatorSystem() {

    // Check if the elevator system is already stopped.
    if (
        systemStatus == ElevatorSystemStatus.outOfService
        || systemStatus == ElevatorSystemStatus.stopping
    ) {
      return;
    }

    // Clear the requests.
    upRequests.clear();
    downRequests.clear();

    // Take all the elevators out of service.
    for (ElevatorInterface elevator : elevators) {
      elevator.takeOutOfService();
    }

    // Set the elevator system status to stopping.
    systemStatus = ElevatorSystemStatus.stopping;
  }

  @Override
  public void step() {
    if (systemStatus == ElevatorSystemStatus.outOfService) {
      return;
    }

    if (systemStatus == ElevatorSystemStatus.running) {
      distributeRequests();
    }

    for (ElevatorInterface elevator : elevators) {
      elevator.step();
    }

    if (systemStatus == ElevatorSystemStatus.stopping) {
      for (ElevatorInterface elevator : elevators) {
        if (elevator.getCurrentFloor() != 0) {
          return;
        }
      }
      systemStatus = ElevatorSystemStatus.outOfService;
    }

  }

  @Override
  public BuildingReport getElevatorSystemStatus() {

    ElevatorReport[] elevatorReports = new ElevatorReport[numberOfElevators];

    for (int i = 0; i < numberOfElevators; i++) {
      elevatorReports[i] = elevators.get(i).getElevatorStatus();
    }

    return new BuildingReport(
        numberOfFloors,
        numberOfElevators,
        elevatorCapacity,
        elevatorReports,
        upRequests,
        downRequests,
        systemStatus
    );
  }

  @Override
  public Request generateRandomRequest() {
    int startFloor = (int) (Math.random() * numberOfFloors);
    int endFloor = (int) (Math.random() * numberOfFloors);
    while (startFloor == endFloor) {
      endFloor = (int) (Math.random() * numberOfFloors);
    }
    return new Request(startFloor, endFloor);
  }

  @Override
  public int getNumberOfFloors() {
    return this.numberOfFloors;
  }

  @Override
  public int getNumberOfElevators() {
    return this.numberOfElevators;
  }

  @Override
  public int getElevatorCapacity() {
    return this.elevatorCapacity;
  }

  @Override
  public ElevatorSystemStatus getSystemStatus() {
    return this.systemStatus;
  }

  /**
   * This method is used to distribute the requests to the elevators.
   */
  private void distributeRequests() {
    if (!this.upRequests.isEmpty() || !this.downRequests.isEmpty()) {
      for (ElevatorInterface elevator : this.elevators) {
        if (!elevator.isTakingRequests()) {
          continue;
        }

        if (elevator.getCurrentFloor() == 0) {
          List<Request> upRequestsForElevator = this.sortRequest(this.upRequests);
          elevator.processRequests(upRequestsForElevator);
        }

        if (elevator.getCurrentFloor() == this.numberOfFloors - 1) {
          List<Request> downRequestsForElevator = this.sortRequest(this.downRequests);
          elevator.processRequests(downRequestsForElevator);
        }
      }
    }
  }

  /**
   * This method is used to process the requests.
   *
   * @param requests The requests to be processed.
   * @return The requests to distribute.
   */
  private List<Request> sortRequest(List<Request> requests) {
    List<Request> requestsToDistribute = new ArrayList<>();
    while (!requests.isEmpty() && requestsToDistribute.size() < elevatorCapacity) {
      requestsToDistribute.add(requests.remove(0));
    }
    return requestsToDistribute;
  }

  @Override
  public List<Request> getUpRequests() {
    return this.upRequests;
  }

  @Override
  public List<Request> getDownRequests() {
    return this.downRequests;
  }

  @Override
  public List<ElevatorInterface> getElevators() {
    return this.elevators;
  }
}


