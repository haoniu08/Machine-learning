import building.Building;
import building.BuildingInterface;
import building.BuildingReport;
import java.util.Scanner;
import scanerzus.Request;

/**
 * The driver for the elevator system.
 * This class will create the elevator system and run it.
 * this is for testing the elevator system.
 * It provides a user interface to the elevator system.
 */
public class MainConsole {

  /**
   * The main method for the elevator system.
   * This method creates the elevator system and runs it.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    // the number of floors, the number of elevators, and the number of people.

    final int numFloors = 10;
    final int numElevators = 1;
    final int elevatorCapacity = 3;


    String[] introText = {
        "Welcome to the Elevator System!",
        "This system will simulate the operation of an elevator system.",
        "The system will be initialized with the following parameters:",
        "Number of floors: " + numFloors,
        "Number of elevators: " + numElevators,
        "Elevator Capacity: " + elevatorCapacity,
        "The system will then be run and the results will be displayed.",
        "",
        "Press enter to continue."
    };

    for (String line : introText) {
      System.out.println(line);

    }
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();

    BuildingInterface building = new Building(numFloors, numElevators, elevatorCapacity);

    // simulateElevatorSystemNoInput(building);
    simulateElevatorSystemWithInput(building);
  }

  /**
   * Simulates the elevator system without user input.
   *
   * @param building the building to simulate.
   */
  private static void simulateElevatorSystemNoInput(BuildingInterface building) {
    BuildingReport report = building.getElevatorSystemStatus();
    // Start the elevator system
    building.startElevatorSystem();
    System.out.println(report.toString());

    // Add some requests to simulate user input
    building.addRequest(new Request(0, 5)); // Request to go from floor 0 to 5
    building.addRequest(new Request(2, 7)); // Request to go from floor 2 to 7
    building.addRequest(new Request(4, 1)); // Request to go from floor 4 to 1
    building.addRequest(new Request(6, 3)); // Request to go from floor 6 to 3

    System.out.println(report.toString());
    // Simulate multiple steps of the elevator system
    for (int i = 0; i < 40; i++) {
      building.step(); // Perform one step of the elevator system
      BuildingReport report1 = building.getElevatorSystemStatus();
      System.out.println(report1.toString());
    }
    System.out.println("**********************");
  }

  /**
   * Simulates the elevator system with user input.
   *
   * @param building the building to simulate.
   */
  private static void simulateElevatorSystemWithInput(BuildingInterface building) {
    BuildingReport report = building.getElevatorSystemStatus();
    // Start the elevator system
    building.startElevatorSystem();
    System.out.println(report.toString());

    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println(
          "Enter 'r' to add a request, "
              + "'s' to step one time unit forward, "
              + "'h' to stop the elevator system, "
              + "'c' to start the elevator system, "
              + " or 'q' to quit the program."
      );
      String input = scanner.nextLine().trim();

      switch (input) {
        case "r":
          System.out.println("Enter the start floor:");
          int startFloor = scanner.nextInt();
          System.out.println("Enter the end floor:");
          int endFloor = scanner.nextInt();
          building.addRequest(new Request(startFloor, endFloor));
          BuildingReport reportRequest = building.getElevatorSystemStatus();
          System.out.println(reportRequest.toString());
          break;
        case "s":
          building.step();
          BuildingReport reportStep = building.getElevatorSystemStatus();
          System.out.println(reportStep.toString());
          break;
        case "h":
          building.stopElevatorSystem();
          BuildingReport reportStop = building.getElevatorSystemStatus();
          System.out.println(reportStop.toString());
          break;
        case "c":
          building.startElevatorSystem();
          BuildingReport reportStart = building.getElevatorSystemStatus();
          System.out.println(reportStart.toString());
          break;
        case "q":
          System.out.println("Quitting program.");
          return;
        default:
          System.out.println("Invalid input. Please try again.");
      }
    }
  }
}