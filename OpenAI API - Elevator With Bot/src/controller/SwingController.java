package controller;

import building.BuildingInterface;
import building.enums.ElevatorSystemStatus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import scanerzus.Request;
import swingview.SwingGridViewInterface;

/**
 * This class is the controller for the GUI. It will handle all the user input and
 * update the GUI accordingly.
 */
public class SwingController implements SwingControllerInterface {
  private final BuildingInterface model;
  private final SwingGridViewInterface view;
  private Timer timer;
  private int counter;

  /**
   * The constructor for the SwingController.
   *
   * @param model the model for the MVC pattern.
   * @param view the view for the MVC pattern.
   */
  public SwingController(BuildingInterface model, SwingGridViewInterface view) {
    this.model = model;
    this.view = view;
    view.setController(this);
    update();
  }

  @Override
  public void addRequest(Request request) {
    try {
      model.addRequest(request);
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
  }

  @Override
  public void startElevatorSystem() {
    model.startElevatorSystem();
  }

  @Override
  public void stopElevatorSystem() {
    model.stopElevatorSystem();
  }

  @Override
  public void update() {
    updateBuildingGrid();
    updateSystemStatus();
    displayElevatorInfo();
    displayUpRequests();
    displayDownRequests();
    updateButtonStatus();
  }

  @Override
  public void addRandomRequest() {
    Request request = model.generateRandomRequest();
    addRequest(request);
  }

  @Override
  public void step(int steps) {
    if (timer != null && timer.isRunning()) {
      return;
    }

    timer = new Timer(500, null); // Create a timer that triggers every 500 ms
    counter = 0;
    view.disableSteps();
    timer.addActionListener(new ActionListener() {// Count the number of steps that have been performed
      @Override
      public void actionPerformed(ActionEvent e) {
        model.step(); // Perform a step
        update(); // Update the view

        counter++; // Increment the counter
        if (counter >= steps) {
          timer.stop(); // Stop the timer if the desired number of steps have been performed
          view.enableSteps();
        }
      }
    });
    timer.start(); // Start the timer
  }

  /**
   * This method is used to update the system status.
   * */
  private void updateSystemStatus() {
    view.updateSystemStatus(model.getSystemStatus().toString());
  }

  /**
   * This method is used to update the building grid.
   * */
  private void updateBuildingGrid() {
    int[] currentFloors = new int[model.getNumberOfElevators()];
    for (int i = 0; i < model.getNumberOfElevators(); i++) {
      currentFloors[i] = model.getElevators().get(i).getCurrentFloor();
    }
    view.updateBuildingGrid(currentFloors);
  }

  /**
   * This method is used to display the elevator information.
   * */
  private void displayElevatorInfo() {
    List<String> elevatorInfo = new ArrayList<>();
    for (int i = 0; i < model.getNumberOfElevators(); i++) {
      elevatorInfo.add(model.getElevators().get(i).toString());
    }
    view.displayElevatorInfo(elevatorInfo);
  }

  /**
   * This method is used to display the up requests.
   * */
  private void displayUpRequests() {
    view.displayUpRequests(model.getUpRequests());
  }

  /**
   * This method is used to display the down requests.
   * */
  private void displayDownRequests() {
    view.displayDownRequests(model.getDownRequests());
  }

  /**
   * This method is used to update the button status.
   * */
  private void  updateButtonStatus() {
    if (model.getSystemStatus() == ElevatorSystemStatus.running) {
      view.enableFunctionsWhenRunning();
    }
    if (model.getSystemStatus() == ElevatorSystemStatus.outOfService) {
      view.disableFunctionsWhenOutOfService();
      if (timer != null) {
        timer.stop();
      }
      counter = 0;
    }
    if (model.getSystemStatus() == ElevatorSystemStatus.stopping) {
      view.disableFunctionsWhenStopping();
    }
  }
}
