package swingAnimationViewAbandoned;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestBuilding extends JFrame {
    private int numOfFloors;
    private int numOfElevators;
    private int floorHeight;
    private int eleDoorHeight;
    private int eleDoorWidth;
    private int eleLeftDoorX;
    private int getEleLeftDoorY;
    private int eleRightDoorX;
    private int eleRightDoorY;
    private Timer moveTimer;
    private Timer doorTimer;
    private int currentFloor = 0;

    private boolean doorClosed = false;
    private int elevatorPosition = 0;
    private int doorPosition = 0;


    public TestBuilding() {

        setTitle("Fantastic Building Elevator Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton upButton = new JButton("Up");
        JButton downButton = new JButton("Down");
        JButton openButton = new JButton("Open");
        JButton closeButton = new JButton("Close");

        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveUp();
            }
        });

        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDown();
            }
        });

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDoor();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeDoor();
            }
        });



        JPanel buttonPanel = new JPanel();

        buttonPanel.add(upButton);
        buttonPanel.add(downButton);
        buttonPanel.add(openButton);
        buttonPanel.add(closeButton);

        add(buttonPanel);
        setSize(800, 800);

        doorTimer = new Timer(1000 / 60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (doorClosed) {
                    doorPosition -= 2;
                    if (doorPosition <= 0) {
                        doorPosition = 0;
                        doorTimer.stop();
                    }
                } else {
                    doorPosition += 2;
                    if (doorPosition >= 50) {
                        doorPosition = 50;
                        doorTimer.stop();
                    }
                }
                repaint();
            }
        });

        moveTimer = new Timer(1000 / 60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int targetPosition = currentFloor * floorHeight;
                if (elevatorPosition < targetPosition) {
                    elevatorPosition += 2;
                    if (elevatorPosition >= targetPosition) {
                        elevatorPosition = targetPosition;
                        moveTimer.stop();
                    }
                } else if (elevatorPosition > targetPosition) {
                    elevatorPosition -= 2;
                    if (elevatorPosition <= targetPosition) {
                        elevatorPosition = targetPosition;
                        moveTimer.stop();
                    }
                }
                repaint();
            }
        });

        setVisible(true);

    }

    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        // Draw the building
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 400, 400);

        // Draw the floors, suppose there are 5 floors
        for (int i = 0; i < 5; i++) {
            g.setColor(Color.WHITE);
            g.fillRect(0, i * 100, 400, 100);
            g.setColor(Color.BLACK);
            g.drawRect(0, i * 100, 400, 100);
        }

        // Draw the elevator, suppose the elevator is 50x100, and the door is 25x100
        g.setColor(Color.BLUE);
        g.fillRect(50, elevatorPosition, 50, 100);

        // Draw the left door
        g.setColor(Color.RED);
        g.fillRect(100, 0, doorPosition, 100);

        // Draw the right door
        g.setColor(Color.RED);
        g.fillRect(150, 0, doorPosition, 100);

    }


    private void moveUp() {
        if (currentFloor < 5) { // Assuming 5 floors
            currentFloor++;
            moveTimer.start();
        }
    }

    private void moveDown() {
        if (currentFloor > 0) {
            currentFloor--;
            moveTimer.start();
        }
    }

    private void openDoor() {
        doorClosed = false;
        moveTimer.start();
    }

    private void closeDoor() {
        doorClosed = true;
        moveTimer.start();
    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Elevator");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                TestBuilding building = new TestBuilding();
                frame.add(building);
                frame.setSize(500, 500);
                frame.setVisible(true);

            }
        });
    }


}
