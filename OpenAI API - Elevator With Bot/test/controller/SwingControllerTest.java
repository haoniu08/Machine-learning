package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import building.BuildingInterface;
import building.enums.ElevatorSystemStatus;
import org.junit.Before;
import org.junit.Test;
import scanerzus.Request;
import swingview.SwingGridViewInterface;

/**
 * This class is used to test the SwingController class.
 */
public class SwingControllerTest {
  private BuildingInterface model;
  private SwingGridViewInterface view;
  private SwingControllerInterface controller;

  /**
   * This method is used to set up the test fixture.
   */
  @Before
  public void setUp()  {
    model = new MockBuildingModel();
    view = new MockSwingGridView();
    controller = new SwingController(model, view);
  }

  /**
   * This method is used to test the addRequest method.
   */
  @Test
  public void addRequest() {
    Request request = new Request(1, 2);
    controller.addRequest(request);
    assertNull(model.getUpRequests());
    assertNull(model.getDownRequests());
  }

  /**
   * This method is used to test the addRequest method with an invalid request.
   */
  @Test
  public void testAddRequestInvalid() {
    Request invalidRequest = new Request(-1, -2);
    controller.addRequest(invalidRequest);
    assertNull(model.getUpRequests());
    assertNull(model.getDownRequests());
  }

  /**
   * This method is used to test the startElevatorSystem method.
   */
  @Test
  public void testStartElevatorSystem() {
    controller.startElevatorSystem();
    assertEquals(ElevatorSystemStatus.outOfService, model.getSystemStatus());
  }

  @Test
  public void testStartElevatorSystemWhileOutOfService() {
    controller.startElevatorSystem();
    assertEquals(ElevatorSystemStatus.outOfService, model.getSystemStatus());
  }

  /**
   * This method is used to test the stopElevatorSystem method.
   */
  @Test
  public void testStopElevatorSystem() {
    controller.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.outOfService, model.getSystemStatus());
  }

  /**
   * This method is used to test the start and stop elevator system method.
   */
  @Test
  public void testStartAndStopElevatorSystem() {
    controller.startElevatorSystem();
    assertEquals(ElevatorSystemStatus.outOfService, model.getSystemStatus());

    controller.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.outOfService, model.getSystemStatus());
  }

  /**
   * This method is used to test the update method.
   */
  @Test
  public void testUpdate() {
    controller.update();
    assertEquals(ElevatorSystemStatus.outOfService, model.getSystemStatus());
    assertNull(model.getUpRequests());
    assertNull(model.getDownRequests());
  }

  /**
   * This method is used to test the addRandomRequest method.
   */
  @Test
  public void testAddRandomRequest() {
    controller.addRandomRequest();
    assertNull(model.getUpRequests());
    assertNull(model.getDownRequests());
  }

  /**
   * This method is used to test the step method.
   */
  @Test
  public void testStep() {
    controller.step(1);
    assertEquals(ElevatorSystemStatus.outOfService, model.getSystemStatus());
    assertNull(model.getUpRequests());
    assertNull(model.getDownRequests());
    assertNull(model.getElevators());
    assertEquals(0, model.getNumberOfFloors());
    assertEquals(0, model.getNumberOfElevators());
  }

  /**
   * This method is used to test the update button status method.
   */
  @Test
  public void testUpdateButtonStatus() {
    view.enableFunctionsWhenRunning();
    assertEquals(ElevatorSystemStatus.outOfService, model.getSystemStatus());
    view.disableFunctionsWhenOutOfService();
    assertEquals(ElevatorSystemStatus.outOfService, model.getSystemStatus());
    view.disableFunctionsWhenStopping();
    assertEquals(ElevatorSystemStatus.outOfService, model.getSystemStatus());
  }

}