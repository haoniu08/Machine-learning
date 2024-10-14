package building;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import building.enums.Direction;
import building.enums.ElevatorSystemStatus;
import elevator.ElevatorReport;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import scanerzus.Request;

/**
 * A JUnit test class for the BuildingReport class,
 * which includes unit tests for the BuildingReport class.
 */
public class BuildingReportTest {
  BuildingReport report;

  /**
   * Sets up the building report for testing.
   */
  @Before
  public void setUp() {
    report = new BuildingReport(
        10,  // numFloors
        5,  // numElevators
        10,  // elevatorCapacity
        new ElevatorReport[]{
            new ElevatorReport(
                1,  // elevatorId
                1,  // currentFloor
                Direction.STOPPED, // direction
                true,  // doorClosed
                new boolean[]{false, true, true},  // floorRequests
                0,  // doorOpenTimer
                0,  // endWaitTimer
                false,  // outOfService
                false  // isTakingRequests
            )
        },
        new ArrayList<>(),  // upRequests
        new ArrayList<>(),  // downRequests
        ElevatorSystemStatus.outOfService  // systemStatus
    );
  }

  /**
   * Test the getNumFloors method.
   */
  @Test
  public void getNumFloors() {
    assertEquals(10, report.getNumFloors());
  }

  /**
   * Test the getNumElevators method.
   */
  @Test
  public void getNumElevators() {
    assertEquals(5, report.getNumElevators());
  }

  /**
   * Test the getElevatorCapacity method.
   */
  @Test
  public void getElevatorCapacity() {
    assertEquals(10, report.getElevatorCapacity());
  }

  /**
   * Test the getElevatorReports method.
   */
  @Test
  public void getElevatorReports() {
    ElevatorReport[] elevatorReports = report.getElevatorReports();
    assertEquals(1, elevatorReports.length);
    assertEquals(1, elevatorReports[0].getElevatorId());
    assertEquals(1, elevatorReports[0].getCurrentFloor());
    assertEquals(Direction.STOPPED, elevatorReports[0].getDirection());
    assertTrue(elevatorReports[0].isDoorClosed());
    assertArrayEquals(new boolean[]{false, true, true}, elevatorReports[0].getFloorRequests());
    assertEquals(0, elevatorReports[0].getDoorOpenTimer());
    assertEquals(0, elevatorReports[0].getEndWaitTimer());
    assertFalse(elevatorReports[0].isOutOfService());
    assertFalse(elevatorReports[0].isTakingRequests());
  }

  /**
   * Test the getUpRequests method.
   */
  @Test
  public void getUpRequests() {
    assertEquals(0, report.getUpRequests().size());
  }

  /**
   * Test the getDownRequests method.
   */
  @Test
  public void getDownRequests() {
    assertEquals(0, report.getDownRequests().size());
  }

  /**
   * Test the getSystemStatus method.
   */
  @Test
  public void getSystemStatus() {
    assertEquals(ElevatorSystemStatus.outOfService, report.getSystemStatus());
  }

  /**
   * Test the toString method.
   */
  @Test
  public void testToString() {
    String expected =
        "Elevator System Status: Out Of Service\n"
        + "Elevator Reports:\n"
        + "Elevator 0 Report: Current Floor: 1 Direction: - Door Closed: true Door Open Timer: "
        + "0 End Wait Timer: 0 Out of Service: false Taking Requests: false\n"
        + "Floor Requests: \n"
        + "Floor 1: Requested\n"
        + "Floor 2: Requested\nUp Requests:\nDown Requests:\n";
    assertEquals(expected, report.toString());
  }

  /**
   * Test the toString method when there are up requests.
   */
  @Test
  public void testToStringWithUpRequests() {
    report.getUpRequests().add(new Request(1, 2));
    String expected =
        "Elevator System Status: Out Of Service\n"
        + "Elevator Reports:\n"
        + "Elevator 0 Report: Current Floor: 1 Direction: - Door Closed: true Door Open Timer: "
        + "0 End Wait Timer: 0 Out of Service: false Taking Requests: false\n"
        + "Floor Requests: \n"
        + "Floor 1: Requested\n"
        + "Floor 2: Requested\nUp Requests:\n1 -> 2\nDown Requests:\n";
    assertEquals(expected, report.toString());
  }

  /**
   * Test the toString method when there are down requests.
   */
  @Test
  public void testToStringWithDownRequests() {
    report.getDownRequests().add(new Request(2, 1));
    String expected =
        "Elevator System Status: Out Of Service\n"
        + "Elevator Reports:\n"
        + "Elevator 0 Report: Current Floor: 1 Direction: - Door Closed: true Door Open Timer: "
        + "0 End Wait Timer: 0 Out of Service: false Taking Requests: false\n"
        + "Floor Requests: \n"
        + "Floor 1: Requested\n"
        + "Floor 2: Requested\nUp Requests:\nDown Requests:\n2 -> 1\n";
    assertEquals(expected, report.toString());
  }
}