package swingview;

import building.Building;
import building.BuildingInterface;
import controller.SwingController;
import java.awt.GridLayout;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class is the start screen for the building swing view.
 */
public class SwingGridStartScreen extends JFrame {
  private final JComboBox<Integer> numOfFloorsCombo;
  private final JComboBox<Integer> numOfElevatorsCombo;
  private final JComboBox<Integer> elevatorCapacityCombo;

  /**
   * The constructor for the SwingGridStartScreen,
   * it will create the start screen for the building.
   */
  public SwingGridStartScreen() {

    final JLabel greetingLabel = new JLabel("Welcome to the fantastic building!");
    final JLabel numOfFloorsLabel = new JLabel("Please select the number of floors:");
    final JLabel numOfElevatorsLabel = new JLabel("Please select the number of elevators:");
    final JLabel elevatorCapacityLabel = new JLabel("Please select the elevator capacity:");
    final JButton startButton = new JButton("Start Simulation");
    final JPanel startPanel = new JPanel(new GridLayout(5, 1));

    setTitle("Fantastic Building Elevator Simulator");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Integer[] floorOptions = new Integer[29];
    for (int i = 3; i <= 30; i++) {
      floorOptions[i - 3] = i;
    }
    numOfFloorsCombo = new JComboBox<>(floorOptions);

    Integer[] elevatorOptions = new Integer[10];
    for (int i = 1; i <= 10; i++) {
      elevatorOptions[i - 1] = i;
    }
    numOfElevatorsCombo = new JComboBox<>(elevatorOptions);

    Integer[] capacityOptions = new Integer[18];
    for (int i = 3; i <= 20; i++) {
      capacityOptions[i - 3] = i;
    }
    elevatorCapacityCombo = new JComboBox<>(capacityOptions);

    JPanel  firstRow = new JPanel();
    firstRow.add(greetingLabel);

    JPanel  secondRow = new JPanel();
    secondRow.add(numOfFloorsLabel);
    secondRow.add(numOfFloorsCombo);

    JPanel  thirdRow = new JPanel();
    thirdRow.add(numOfElevatorsLabel);
    thirdRow.add(numOfElevatorsCombo);

    JPanel  fourthRow = new JPanel();
    fourthRow.add(elevatorCapacityLabel);
    fourthRow.add(elevatorCapacityCombo);

    JPanel  fifthRow = new JPanel();
    fifthRow.add(startButton);

    startPanel.add(firstRow);
    startPanel.add(secondRow);
    startPanel.add(thirdRow);
    startPanel.add(fourthRow);
    startPanel.add(fifthRow);

    add(startPanel);

    startButton.addActionListener(e -> {
      int numOfFloors = Integer.parseInt(
              Objects.requireNonNull(numOfFloorsCombo.getSelectedItem()).toString());
      int numOfElevators = Integer.parseInt(
              Objects.requireNonNull(numOfElevatorsCombo.getSelectedItem()).toString());
      int elevatorCapacity = Integer.parseInt(
              Objects.requireNonNull(elevatorCapacityCombo.getSelectedItem()).toString());

      BuildingInterface model = new Building(numOfFloors, numOfElevators, elevatorCapacity);
      SwingGridViewInterface view =  new SwingGridView(numOfFloors, numOfElevators);
      new SwingController(model, view);
      dispose();
    });

    setSize(600, 500);
    setVisible(true);
  }
}