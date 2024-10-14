package swingAnimationViewAbandoned;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DynamicMovementExample extends JFrame {

    private ImageIcon imageIcon;
    private JLabel imageLabel;
    private JButton upButton;
    private JButton downButton;

    private int currentY = 200; // Initial Y position of the label

    public DynamicMovementExample() {
        setTitle("Dynamic Movement Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

//        label = new JLabel();
//        label.setHorizontalAlignment(SwingConstants.CENTER);
//        add(label, BorderLayout.CENTER);

        imageIcon = new ImageIcon(getClass().getResource("elevatorImg.jpg"));
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);
        imageLabel = new JLabel(imageIcon);
        currentY = getHeight() - imageIcon.getIconHeight();
        imageLabel.setBounds(0, currentY, imageIcon.getIconWidth(), imageIcon.getIconHeight());

        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        upButton = new JButton("Up");
        downButton = new JButton("Down");

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

        buttonPanel.add(upButton);
        buttonPanel.add(downButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(1000, 1000);
        setVisible(true);
    }

    private void moveUp() {
        currentY -= 10; // Move up by 10 pixels
        imageLabel.setLocation(imageLabel.getX(), currentY);
    }

    private void moveDown() {
        currentY += 10; // Move down by 10 pixels
        imageLabel.setLocation(imageLabel.getX(), currentY);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DynamicMovementExample example = new DynamicMovementExample();
                example.setVisible(true);
            }
        });
    }
}
