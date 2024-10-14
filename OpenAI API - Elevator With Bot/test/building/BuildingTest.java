package building;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import building.enums.ElevatorSystemStatus;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import scanerzus.Request;

/**
 * A JUnit test class for the Building class,
 * which includes unit tests for the Building class.
 */
public class BuildingTest {

  private Building building1;

  /**
   * Sets up the building for testing.
   */
  @Before
  public void setUp() {
    building1 = new Building(10, 5, 10);
  }

  /**
   * Test the constructor exceptions.
   * Number of floors must be greater than 2.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testBuildingInvalidFloorsLessThan2() {
    new Building(1, 5, 10);
  }

  /**
   * Test the constructor exceptions.
   * Number of floors must be less than 31.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testBuildingInvalidFloorsGreaterThan30() {
    new Building(31, 5, 10);
  }

  /**
   * Test the constructor exceptions.
   * Number of elevators must be greater than 0.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testBuildingInvalidElevatorsLessThan1() {
    new Building(10, 0, 10);
  }

  /**
   * Test the constructor exceptions.
   * Number of elevators must be less than 11.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testBuildingInvalidElevatorsGreaterThan10() {
    new Building(10, 11, 10);
  }

  /**
   * Test the constructor exceptions.
   * Elevator capacity must be greater than 2.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testBuildingInvalidCapacityLessThan3() {
    new Building(10, 5, 2);
  }

  /**
   * Test the constructor exceptions.
   * Elevator capacity must be less than 21.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testBuildingInvalidCapacityGreaterThan20() {
    new Building(10, 5, 21);
  }

  /**
   * Test the constructor works as expected.
   * Test the building constructor for building 1.
   */
  @Test
  public void testBuildingConstructor() {
    assertEquals(10, building1.getNumberOfFloors());
    assertEquals(5, building1.getNumberOfElevators());
    assertEquals(10, building1.getElevatorCapacity()
    );
  }

  @Test
  public void testGetElevators() {
    assertEquals(5, building1.getElevators().size());
  }

  /**
   * Test the addRequest method exceptions.
   * Test adding a null request.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddRequestNull() {
    building1.addRequest(null);
  }

  /**
   * Test the addRequest method exceptions.
   * Test adding a request with an invalid start floor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddRequestInvalidFloorNegativeStartFloor() {
    building1.addRequest(new Request(-1, 5));
  }

  /**
   * Test the addRequest method exceptions.
   * Test adding a request with an invalid end floor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddRequestInvalidFloorEndFloorGreaterThan30() {
    building1.addRequest(new Request(5, 31));
  }

  /**
   * Test the addRequest method exceptions.
   * Test adding a request with the same start and end floor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddRequestSameFloor() {
    building1.addRequest(new Request(5, 5));
  }

  /**
   * Test the addRequest method exceptions.
   * Test adding a request when the elevator system is stopped.
   */
  @Test(expected = IllegalStateException.class)
  public void testAddRequestStopped() {
    building1.stopElevatorSystem();
    building1.step();
    assertEquals(ElevatorSystemStatus.outOfService, building1.getSystemStatus());
    building1.addRequest(new Request(5, 9));
  }

  /**
   * Test the addRequest method works as expected.
   * Test adding a request to the building.
   */
  @Test
  public void testAddRequest() {
    // Test adding a request to the upRequests list.
    assertTrue(building1.addRequest(new Request(5, 9)));

    // Test adding a request to the downRequests list.
    assertTrue(building1.addRequest(new Request(9, 5)));
  }

  /**
   * Test the startElevatorSystem method exceptions.
   * Test starting the elevator system when it is stopping.
   */
  @Test (expected = IllegalStateException.class)
  public void testStartElevatorSystemWhileStopping() {
    assertEquals(ElevatorSystemStatus.running, building1.getSystemStatus());
    building1.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.stopping, building1.getSystemStatus());
    building1.startElevatorSystem();
  }

  /**
   * Test the startElevatorSystem method return true.
   * Test starting the elevator system when it is running.
   */
  @Test
  public void testStartElevatorSystemRunning() {
    assertEquals(ElevatorSystemStatus.running, building1.getSystemStatus());
    assertTrue(building1.startElevatorSystem());
  }

  /**
   * Test the startElevatorSystem method works as expected.
   * Test starting the elevator system when it is out of service.
   */
  @Test
  public void testStartElevatorSystem() {
    assertEquals(ElevatorSystemStatus.running, building1.getSystemStatus());
    building1.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.stopping, building1.getSystemStatus());
    building1.step();
    assertEquals(ElevatorSystemStatus.outOfService, building1.getSystemStatus());
    building1.startElevatorSystem();
    assertEquals(ElevatorSystemStatus.running, building1.getSystemStatus());
  }

  /**
   * Test the stopElevatorSystem method works as expected.
   * Test stopping the elevator system when it is running, and will be eventually stopped.
   */
  @Test
  public void testStopElevatorSystem() {
    assertEquals(ElevatorSystemStatus.running, building1.getSystemStatus());
    building1.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.stopping, building1.getSystemStatus());
    building1.step();
    assertEquals(ElevatorSystemStatus.outOfService, building1.getSystemStatus());
  }

  /**
   * Test the stopElevatorSystem method works as expected.
   * Test stopping the elevator system triggers no effects when it is being stopped.
   */
  @Test
  public void testStopElevatorSystemStopping() {
    assertEquals(ElevatorSystemStatus.running, building1.getSystemStatus());
    building1.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.stopping, building1.getSystemStatus());
    building1.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.stopping, building1.getSystemStatus());
  }

  /**
   * Test the stopElevatorSystem method works as expected.
   * Test stopping the elevator system triggers no effects when it is out of service.
   */
  @Test
  public void testStopElevatorSystemOutOfService() {
    assertEquals(ElevatorSystemStatus.running, building1.getSystemStatus());
    building1.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.stopping, building1.getSystemStatus());
    building1.step();
    assertEquals(ElevatorSystemStatus.outOfService, building1.getSystemStatus());
    building1.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.outOfService, building1.getSystemStatus());
  }

  /**
   * Test the step method works as expected.
   * Test stepping the building forward when the elevator system is running,
   * and there is a request in the upRequests list.
   */
  @Test
  public void testStepUpRequests() {
    assertEquals(ElevatorSystemStatus.running, building1.getSystemStatus());
    building1.addRequest(new Request(1, 3));
    ArrayList<Request> upRequests = new ArrayList<>();
    upRequests.add(new Request(1, 3));
    assertEquals(upRequests.size(), building1.getUpRequests().size());
    for (int i = 0; i < upRequests.size(); i++) {
      assertEquals(upRequests.get(i).getStartFloor(),
          building1.getUpRequests().get(i).getStartFloor());
      assertEquals(upRequests.get(i).getEndFloor(),
          building1.getUpRequests().get(i).getEndFloor());
    }
  }

  /**
   * Test the step method works as expected.
   * Test stepping the building forward when the elevator system is running,
   * and there is a request in the downRequests list.
   */
  @Test
  public void testStepDownRequests() {
    assertEquals(ElevatorSystemStatus.running, building1.getSystemStatus());
    building1.addRequest(new Request(9, 5));
    ArrayList<Request> downRequests = new ArrayList<>();
    downRequests.add(new Request(9, 5));
    building1.step();
    assertEquals(downRequests.size(), building1.getDownRequests().size());
    for (int i = 0; i < downRequests.size(); i++) {
      assertEquals(downRequests.get(i).getStartFloor(),
          building1.getDownRequests().get(i).getStartFloor());
      assertEquals(downRequests.get(i).getEndFloor(),
          building1.getDownRequests().get(i).getEndFloor());
    }
  }

  /**
   * Test the getElevatorSystemStatus method works as expected.
   * Test getting the status of the elevator system.
   */
  @Test
  public void getElevatorSystemStatusReturnsCorrectReport() {
    Building building = new Building(4, 2, 5);
    BuildingReport report = building.getElevatorSystemStatus();

    assertEquals(4, report.getNumFloors());
    assertEquals(2, report.getNumElevators());
    assertEquals(5, report.getElevatorCapacity());
    assertEquals(ElevatorSystemStatus.running, report.getSystemStatus());
    assertEquals(0, report.getUpRequests().size());
    assertEquals(0, report.getDownRequests().size());
  }

  /**
   * Test that one request is handled properly by one elevator.
   * */
  @Test
  public void testSingleElevatorHandlesRequest() {
    Building building = new Building(10, 1, 5);
    Request request = new Request(1, 5);
    building.addRequest(request);
    building.step();

    // Check that the request has been removed from the building's upRequests
    assertEquals(0, building.getUpRequests().size());

    // Check that the elevator is at the requested floor
    BuildingReport report = building.getElevatorSystemStatus();
    assertEquals(1, report.getElevatorReports()[0].getCurrentFloor());
  }

  /**
   * Test that multiple requests are stored in the order they are added.
   * */
  @Test
  public void testUpRequestsOrder() {
    Building building = new Building(10, 1, 5);
    Request request1 = new Request(1, 3);
    Request request2 = new Request(2, 5);
    Request request3 = new Request(3, 6);

    building.addRequest(request1);
    building.addRequest(request2);
    building.addRequest(request3);

    List<Request> upRequests = building.getUpRequests();

    assertEquals(3, upRequests.size());
    assertEquals(request1, upRequests.get(0));
    assertEquals(request2, upRequests.get(1));
    assertEquals(request3, upRequests.get(2));
  }

  /**
   * Test that multiple requests are stored in the order they are added.
   * */
  @Test
  public void testDownRequestsOrder() {
    Building building = new Building(10, 1, 5);
    Request request1 = new Request(6, 3);
    Request request2 = new Request(5, 2);
    Request request3 = new Request(4, 1);

    building.addRequest(request1);
    building.addRequest(request2);
    building.addRequest(request3);

    List<Request> downRequests = building.getDownRequests();

    assertEquals(3, downRequests.size());
    assertEquals(request1, downRequests.get(0));
    assertEquals(request2, downRequests.get(1));
    assertEquals(request3, downRequests.get(2));
  }

  /**
   * Test that the elevator system can allocate requests as expected.
   * */
  @Test
  public void testUpRequestAllocation() {
    Building building = new Building(10, 2, 5);
    Request request = new Request(1, 5);
    building.addRequest(request);
    building.step();

    // Check that the request has been removed from the building's upRequests
    assertEquals(0, building.getUpRequests().size());

    // Check that the elevator has the request
    BuildingReport report = building.getElevatorSystemStatus();
    boolean floorRequests1 = report.getElevatorReports()[0].getFloorRequests()[1];
    boolean floorRequests2 = report.getElevatorReports()[0].getFloorRequests()[5];
    assertTrue(floorRequests1);
    assertTrue(floorRequests2);

    Request request2 = new Request(2, 6);
    building.addRequest(request2);
    building.step();
    BuildingReport report2 = building.getElevatorSystemStatus();
    boolean floorRequests3 = report2.getElevatorReports()[1].getFloorRequests()[2];
    boolean floorRequests4 = report2.getElevatorReports()[1].getFloorRequests()[6];
    assertTrue(floorRequests3);
    assertTrue(floorRequests4);
  }

  /**
   * Test that the building will eventually stop.
   * when a take out of service is issued and one elevator has a door open
   * */
  @Test
  public void testBuildingStopsWithOpenElevatorDoor() {
    Building building = new Building(3, 1, 3);
    Request request = new Request(0, 2);
    building.addRequest(request);
    building.step();
    // check if the elevator's door is open
    BuildingReport report = building.getElevatorSystemStatus();
    assertFalse(report.getElevatorReports()[0].isDoorClosed());
    // stop the elevator system
    building.stopElevatorSystem();
    // check if the elevator's door is closed
    BuildingReport report2 = building.getElevatorSystemStatus();
    assertFalse(report2.getElevatorReports()[0].isDoorClosed());
    // check if the elevator system is stopping
    assertEquals(ElevatorSystemStatus.stopping, building.getSystemStatus());
    // multiple steps to let the building stop
    for (int i = 0; i < 10; i++) {
      building.step();
    }
    // check if the elevator system is out of service
    assertEquals(ElevatorSystemStatus.outOfService, building.getSystemStatus());
  }

  /**
   * Test that the building will not given more requests to an elevator than its capacity.
   * */
  @Test
  public void testNoRequestsMoreThanElevatorCapacity() {
    Building building = new Building(10, 2, 3);
    Request request1 = new Request(1, 3);
    Request request2 = new Request(2, 5);
    Request request3 = new Request(3, 6);
    Request request4 = new Request(4, 7);
    Request request5 = new Request(5, 8);

    building.addRequest(request1);
    building.addRequest(request2);
    building.addRequest(request3);
    building.addRequest(request4);
    building.addRequest(request5);

    List<Request> upRequests = building.getUpRequests();

    assertEquals(5, upRequests.size());

    // Check that the first elevator has the first 3 requests
    building.step();
    BuildingReport report = building.getElevatorSystemStatus();
    boolean floorRequests1 = report.getElevatorReports()[0].getFloorRequests()[1];
    boolean floorRequests2 = report.getElevatorReports()[0].getFloorRequests()[2];
    boolean floorRequests3 = report.getElevatorReports()[0].getFloorRequests()[3];
    assertTrue(floorRequests1);
    assertTrue(floorRequests2);
    assertTrue(floorRequests3);

    // Check that the second elevator has the last 2 requests
    BuildingReport report2 = building.getElevatorSystemStatus();
    boolean floorRequests4 = report2.getElevatorReports()[1].getFloorRequests()[4];
    boolean floorRequests5 = report2.getElevatorReports()[1].getFloorRequests()[5];
    assertTrue(floorRequests4);
    assertTrue(floorRequests5);
  }

  /**
   * Test that the building can generate random requests.
   * */
  @Test
  public void testGenerateRandomRequest() {
    Building building = new Building(10, 2, 3);
    Request request = building.generateRandomRequest();
    building.addRequest(request);
    if (!building.getUpRequests().isEmpty()) {
      assertEquals(1, building.getUpRequests().size());
    } else {
      assertEquals(1, building.getDownRequests().size());
    }
  }
}