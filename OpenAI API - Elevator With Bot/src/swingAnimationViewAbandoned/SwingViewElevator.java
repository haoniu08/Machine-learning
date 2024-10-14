package swingAnimationViewAbandoned;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class SwingViewElevator extends JPanel {
    private boolean doorClosed = false;
    private int currentFloor = 0;
    private Timer doorTimer;
    private int height;
    private int width;
    // The x coordinate of the elevator’s upper left corner
    private int xco;
    // The y coordinate of the elevator’s upper left corner
    private int yco;
    private int numOfFloors;
    private int floorHeight;
    private int yTopFloor;
    private int yGroundFloor;
    private int doorDx;
    private int doorPosition = 0;
    private JButton upButton;
    private JButton downButton;
    private JButton openDoorButton;
    private JButton closeDoorButton;

    public SwingViewElevator(int numOfFloors, int width, int height) {
        this.numOfFloors = numOfFloors;
        this.width = width;
        this.height = height;
        this.setSize(width, height);

        upButton = new JButton("Up");
        downButton = new JButton("Down");
        openDoorButton = new JButton("Open Door");
        closeDoorButton = new JButton("Close Door");

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


        updateElevator();

        LineBorder border = new LineBorder(Color.BLACK, 1);
        setBorder(BorderFactory.createTitledBorder(border, "Elevator"));

    }

    public void updateElevator() {
        int elevatorWidth = getWidth();
        int elevatorHeight = getHeight();

        floorHeight = height / numOfFloors;

        yGroundFloor = elevatorHeight - 1;
        yTopFloor = yGroundFloor - floorHeight * (numOfFloors - 1);

        this.height = floorHeight * 4/5;
        this.width = height;

        xco = elevatorWidth / 2 - width / 2;
        yco = yGroundFloor - height;

        repaint();
    }

    public void paintElevator(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLUE);
        for (int y = yTopFloor; y < yGroundFloor; y += floorHeight) {
            g2d.drawLine(xco, y, xco + width, y);
        }

        // Draw the elevator doors
        g2d.setColor(Color.RED);
        // Draw the left door
        g2d.fillRect(xco, yco, width / 2 - doorDx, height);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(xco, yco, width / 2 - doorDx, height);

        // Draw the right door
        g2d.setColor(Color.RED);
        g2d.fillRect(xco + width / 2 + 1 + doorDx, yco, width / 2 - doorDx, height);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(xco + width / 2 + 1 + doorDx, yco, width / 2 - doorDx, height);

        // Draw the elevator
        g2d.setColor(Color.BLUE);
        g2d.fillRect(xco, yco, width, height);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == upButton) {
            moveUp();
        } else if (e.getSource() == downButton) {
            moveDown();
        } else if (e.getSource() == openDoorButton) {
            openDoor();
        } else if (e.getSource() == closeDoorButton) {
            closeDoor();
        } else if (e.getSource() == doorTimer) {
            if (doorClosed) {
                doorPosition -= 2;
                if (doorPosition <= 0) {
                    doorPosition = 0;
                    doorTimer.stop();
                }
            } else {
                doorPosition += 2;
                if (doorPosition >= width / 2 - doorDx) {
                    doorPosition = width / 2 - doorDx;
                    doorTimer.stop();
                }
            }
            repaint();
        }
    }

    private void moveUp() {
        if (currentFloor < numOfFloors - 1) {
            currentFloor++;
            doorTimer.start();
        }
    }

    private void moveDown() {
        if (currentFloor > 0) {
            currentFloor--;
            doorTimer.start();
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


    public static void main(String[] args) {
        SwingViewElevator elevator = new SwingViewElevator(5, 100, 200);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(elevator);
        frame.setSize(200, 400);
        frame.setVisible(true);
    }

}
