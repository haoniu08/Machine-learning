package swingAnimationViewAbandoned;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestElevator extends JPanel {

    private boolean doorClosed = true; // Status of the elevator door
    private int currentFloor = 0; // Current floor of the elevator

    private Timer doorTimer; // Timer for door animation
    private int doorPosition = 100; // Position of the door (100 = closed, 0 = open)

    private Timer moveTimer; // Timer for elevator movement animation
    private int elevatorPosition = 0; // Position of the elevator (0 = bottom floor)

    public TestElevator() {
        setLayout(new BorderLayout());

        JButton upButton = new JButton("Up");
        JButton downButton = new JButton("Down");
        JButton openDoorButton = new JButton("Open Door");
        JButton closeDoorButton = new JButton("Close Door");

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

        openDoorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDoor();
            }
        });

        closeDoorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeDoor();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(upButton);
        buttonPanel.add(downButton);
        buttonPanel.add(openDoorButton);
        buttonPanel.add(closeDoorButton);

        add(buttonPanel, BorderLayout.SOUTH);

        doorTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (doorClosed) {
                    doorPosition += 2;
                    if (doorPosition >= 100) {
                        doorPosition = 100;
                        doorTimer.stop();
                    }
                } else {
                    doorPosition -= 2;
                    if (doorPosition <= 0) {
                        doorPosition = 0;
                        doorTimer.stop();
                    }
                }
                repaint();
            }
        });

        moveTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int targetPosition = currentFloor * 100; // Assuming each floor is 100 pixels tall
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
    }

    private void moveUp() {
        if (currentFloor < 5) { // Assuming 5 floors
            currentFloor++;
            moveTimer.start();
            doorTimer.start(); // Start the door timer when moving
        }
    }

    private void moveDown() {
        if (currentFloor > 0) {
            currentFloor--;
            moveTimer.start();
            doorTimer.start(); // Start the door timer when moving
        }
    }

    private void openDoor() {
        doorClosed = false;
        doorTimer.start();
    }

    private void closeDoor() {
        doorClosed = true;
        doorTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

//         Draw left door
        g2d.setColor(Color.blue);
        g2d.fillRect(100, getHeight() - 100 - elevatorPosition, doorPosition, 100);

        // Draw right door
        g2d.setColor(Color.blue);
        g2d.fillRect(275 - doorPosition, getHeight() - 100 - elevatorPosition, doorPosition, 100);

        // Draw two levels of the elevator
        g2d.setColor(Color.red);
        g2d.fillRect(100, getHeight() - 100 - elevatorPosition, 175, 100);
        // draw the boarder of the elevator
        g2d.setColor(Color.black);
        g2d.drawRect(100, getHeight() - 100 - elevatorPosition, 175, 100);

//        int centerX = getWidth() / 2;
//        // draw the left door of the elevator, in the middle of the panel
//        g2d.setColor(Color.blue);
//        g2d.fillRect(doorPosition, getHeight() - 100 - elevatorPosition, doorPosition, 100);
//
//        // draw the right door of the elevator, in the middle of the panel
//        g2d.setColor(Color.blue);
//        g2d.fillRect(centerX - doorPosition, getHeight() - 100 - doorPosition, doorPosition, 100);


//        // Draw elevator
//        g2d.setColor(Color.red);
//        g2d.fillRect(100, getHeight() - 100 - elevatorPosition, 175, 100);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Elevator");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 500); // Assuming elevator panel size is 500x500
                TestElevator elevator = new TestElevator();
                frame.getContentPane().add(elevator);
                frame.setVisible(true);
            }
        });
    }
}
