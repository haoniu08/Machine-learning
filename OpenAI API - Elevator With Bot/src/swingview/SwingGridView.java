package swingview;

import controller.SwingControllerInterface;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import scanerzus.Request;

/**
 * This class is the swing grid view for the elevator system.
 */
public class SwingGridView extends JFrame
        implements SwingGridViewInterface {

  private final int numOfFloors;
  private final int numOfElevators;
  private JLabel systemStatus;
  private JButton startButton;
  private JButton stopButton;
  private JButton quitButton;
  private JButton addRequestButton;
  private JLabel startFloorLabel;
  private JLabel toFloorLabel;
  private JButton randomRequestButton;
  private JButton stepButton;
  private JLabel upRequestsLabel;
  private JLabel downRequestsLabel;
  private JButton[][] buildingPanelButtons;
  private JButton[][] infoPanelButtons;
  private SwingControllerInterface controller;
  private  JComboBox<Integer> fromFloorComboBox;
  private  JComboBox<Integer> toFloorComboBox;
  private JComboBox<Integer> multipleStepsComboBox;

  /**
   * The constructor for the SwingGridView.
   *
   * @param numOfFloors the number of floors in the building.
   * @param numOfElevators the number of elevators in the building.
   */
  public SwingGridView(int numOfFloors, int numOfElevators) {
    this.numOfFloors = numOfFloors;
    this.numOfElevators = numOfElevators;

    setTitle("Fantastic Building Elevator Simulator");

    JFrame frame = new JFrame();
    frame.setTitle("Elevator System");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel controlPanel = initiateControlPanel();
    JPanel buildingPanel = initiateBuildingPanel();
    JPanel infoPanel = initiateInfoPanel(numOfElevators);

    add(controlPanel, BorderLayout.NORTH);
    add(buildingPanel, BorderLayout.CENTER);
    add(infoPanel, BorderLayout.EAST);
    setSize(1700, 1300);
    setVisible(true);
  }

  /**
   * This method initiates the control panel.
   *
   * @return the control panel
   */
  private JPanel initiateControlPanel() {
    JPanel controlPanel = new JPanel(new GridLayout(4, 1)) {
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(1300, 145);
      }
    };

    Integer[] floors = new Integer[numOfFloors];
    for (int i = 0; i < numOfFloors; i++) {
      floors[i] = i;
    }

    Integer[] multipleSteps = new Integer[50];
    for (int i = 0; i < 50; i++) {
      multipleSteps[i] = i + 1;
    }

    Border border = BorderFactory.createTitledBorder("System Info");
    controlPanel.setBorder(border);

    systemStatus = new JLabel("System Status: ");
    startButton = new JButton("Start Elevator System");
    stopButton = new JButton("Stop Elevator System");
    quitButton = new JButton("Quit");

    addRequestButton = new JButton("Add Request");
    startFloorLabel = new JLabel("Start Floor: ");
    toFloorLabel = new JLabel("To Floor: ");
    stepButton = new JButton("Step");
    multipleStepsComboBox = new JComboBox<>(multipleSteps);
    fromFloorComboBox = new JComboBox<>(floors);
    toFloorComboBox = new JComboBox<>(floors);
    randomRequestButton = new JButton("Random Request");

    JPanel firstRow = new JPanel(); // Create a panel for the first row
    firstRow.add(systemStatus);
    firstRow.add(startButton);
    firstRow.add(stopButton);
    firstRow.add(stepButton);
    firstRow.add(multipleStepsComboBox);
    firstRow.add(quitButton, BorderLayout.EAST);

    JPanel secondRow = new JPanel(); // Create a panel for the second row
    secondRow.add(addRequestButton);
    secondRow.add(startFloorLabel);
    secondRow.add(fromFloorComboBox);
    secondRow.add(toFloorLabel);
    secondRow.add(toFloorComboBox);
    secondRow.add(randomRequestButton);

    JPanel thirdRow = new JPanel(); // Create a panel for the third row
    upRequestsLabel = new JLabel("Up Requests: ");
    thirdRow.add(upRequestsLabel);

    JPanel forthRow = new JPanel(); // Create a panel for the fourth row
    downRequestsLabel = new JLabel("Down Requests: ");
    forthRow.add(downRequestsLabel);

    controlPanel.add(firstRow); // Add the first row to the main panel
    controlPanel.add(secondRow); // Add the second row to the main panel
    controlPanel.add(thirdRow); // Add the third row to the main panel
    controlPanel.add(forthRow); // Add the fourth row to the main panel

    addRequestButton.addActionListener(e -> {
      int fromFloor = Integer.parseInt(
              Objects.requireNonNull(fromFloorComboBox.getSelectedItem()).toString());
      int toFloor = Integer.parseInt(
              Objects.requireNonNull(toFloorComboBox.getSelectedItem()).toString());
      controller.addRequest(
              new Request(fromFloor, toFloor)
      );
      controller.update();
    });

    randomRequestButton.addActionListener(e -> {
      controller.addRandomRequest();
      controller.update();
    });

    quitButton.addActionListener(e -> {
      System.exit(0);
    });

    startButton.addActionListener(e -> {
      enableSteps();
      controller.startElevatorSystem();
      controller.update();
    });

    stopButton.addActionListener(e -> {
      controller.stopElevatorSystem();
      controller.update();
    });

    stepButton.addActionListener(e -> {
      int steps = Integer.parseInt(
              Objects.requireNonNull(multipleStepsComboBox.getSelectedItem()).toString());
      controller.step(steps);
      controller.update();
    });

    setVisible(true);

    return controlPanel;
  }

  /**
   * This method initiates the building panel.
   *
   * @return the building panel
   */
  private JPanel initiateBuildingPanel() {
    JPanel buildingPanel = new JPanel(new GridLayout(numOfFloors + 1, numOfElevators + 1)) {
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(1000, 800);
      }
    };

    buildingPanelButtons = new JButton[numOfFloors + 1][numOfElevators + 1];

    Border border = BorderFactory.createTitledBorder("Building");
    buildingPanel.setBorder(border);

    // Create the grid of buttons
    for (int i = numOfFloors; i >= 0; i--) {
      for (int j = 0; j <= numOfElevators; j++) {
        JButton button = new JButton();
        button.setOpaque(true);

        // If it's the leftmost column, set the button text to the floor number
        if (j == 0 && i > 0) {
          button.setText("Floor " + (i - 1));
          button.setBorder(null);
          button.setEnabled(false);
          button.setPreferredSize(
                  new Dimension(5, button.getPreferredSize().height));

        }

        // If it's the bottom row, set the button text to the elevator number
        if (i == 0 && j > 0) {
          button.setText("Elevator " + (j - 1));
          button.setBorder(null);
          button.setEnabled(false);
        }
        button.setEnabled(false);
        buildingPanel.add(button);
        buildingPanelButtons[i][j] = button;
      }
    }

    // Set the bottom left button to be empty
    buildingPanelButtons[0][0].setBorder(null);
    updateBuildingGrid(new int[numOfElevators]);

    setVisible(true);

    return buildingPanel;
  }

  /**
   * This method initiates the info panel.
   *
   * @param numOfElevators the number of elevators
   * @return the info panel
   */
  private JPanel initiateInfoPanel(int numOfElevators) {
    JPanel infoPanel = new JPanel();
    infoPanel.setBorder(BorderFactory.createTitledBorder("Elevator Info"));
    infoPanel.setLayout(new GridLayout(numOfElevators, 1));

    infoPanelButtons = new JButton[1][numOfElevators];

    for (int i = 0; i < numOfElevators; i++) {
      JButton button = new JButton();
      button.setOpaque(true);
      button.setText("Elevator " + i);
      button.setEnabled(false);
      button.setPreferredSize(new Dimension(650, button.getPreferredSize().height));
      infoPanel.add(button);
      infoPanelButtons[0][i] = button;
    }
    return infoPanel;
  }

  @Override
  public void setController(SwingControllerInterface controller) {
    this.controller = controller;
  }

  @Override
  public void updateBuildingGrid(int[] currentFloors) {

    for (int i = 1; i <= numOfFloors; i++) {
      for (int j = 1; j <= numOfElevators; j++) {
        buildingPanelButtons[i][j].setBackground(Color.white);
      }
    }

    for (int i = 0; i < numOfElevators; i++) {
      buildingPanelButtons[currentFloors[i] + 1][i + 1].setBackground(Color.GREEN);
    }
  }

  @Override
  public void updateSystemStatus(String status) {
    systemStatus.setText("System Status: " + status);
  }

  @Override
  public void displayUpRequests(List<Request> upRequests) {
    upRequestsLabel.setText("Up Requests: " + upRequests.toString());
  }

  @Override
  public void displayDownRequests(List<Request> downRequests) {
    downRequestsLabel.setText("Down Requests: " + downRequests.toString());
  }

  @Override
  public void displayElevatorInfo(List<String> elevatorInfo) {
    for (int i = 0; i < numOfElevators; i++) {
      infoPanelButtons[0][i].setText("E" + i + ":" + elevatorInfo.get(i));
    }
  }

  @Override
  public void disableFunctionsWhenStopping() {
    startButton.setEnabled(false);
    stopButton.setEnabled(false);
    addRequestButton.setEnabled(false);
    fromFloorComboBox.setEnabled(false);
    toFloorComboBox.setEnabled(false);
    randomRequestButton.setEnabled(false);
  }

  @Override
  public void disableFunctionsWhenOutOfService() {
    startButton.setEnabled(true);
    stopButton.setEnabled(false);
    addRequestButton.setEnabled(false);
    fromFloorComboBox.setEnabled(false);
    toFloorComboBox.setEnabled(false);
    randomRequestButton.setEnabled(false);
    disableSteps();
  }

  @Override
  public void enableFunctionsWhenRunning() {
    startButton.setEnabled(false);
    stopButton.setEnabled(true);
    addRequestButton.setEnabled(true);
    fromFloorComboBox.setEnabled(true);
    toFloorComboBox.setEnabled(true);
    randomRequestButton.setEnabled(true);
  }

  @Override
  public void displayError(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void enableSteps() {
    stepButton.setEnabled(true);
    multipleStepsComboBox.setEnabled(true);
  }

  @Override
  public void disableSteps() {
    stepButton.setEnabled(false);
    multipleStepsComboBox.setEnabled(false);
  }


}
