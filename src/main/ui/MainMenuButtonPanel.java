package ui;

import jdk.nashorn.internal.scripts.JO;
import model.AbstractEntry;
import model.Pack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.temporal.JulianFields;

public class MainMenuButtonPanel extends JPanel {
    private JButton newButton;
    private JButton deleteButton;
    private ButtonPanelController controller;

    private static final int HEIGHT = 20;
    private static final int WIDTH = (int) (MyPackGUI.FRAME_WIDTH * 0.15);

    // EFFECTS: constructs a new MainMenuButtonPanel
    public MainMenuButtonPanel() {
        super(new GridLayout(2, 1));

        newButton = new JButton("New pack");
        newButton.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        newButton.addActionListener(e -> {
            if (controller != null) {
                showNewPackDialog();
            }
        });

        add(newButton);

        deleteButton = new JButton("Delete pack");
        deleteButton.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        deleteButton.addActionListener(e -> {
            if (controller != null) {
                controller.deleteSelectedEntry();
            }
        });

        add(deleteButton);
    }

    // MODIFIES: controller
    // EFFECTS: shows the new pack dialog, and adds the resulting new pack to the table
    private void showNewPackDialog() {
        JTextField nameField = new JTextField(10);
        JTextField descField = new JTextField(10);
        JLabel nameLabel = new JLabel("Name (required): ");
        JLabel descLabel = new JLabel("Description: ");

        JPanel dialogPanel = new JPanel();
        dialogPanel.add(nameLabel);
        dialogPanel.add(nameField);
        dialogPanel.add(descLabel);
        dialogPanel.add(descField);

        int result = JOptionPane.showConfirmDialog(this, dialogPanel,
                "Enter a name and description.", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String description = descField.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(this, "A name is required.");
            } else {
                controller.addEntry(new Pack(name, description));
            }
        }
    }

    void setController(ButtonPanelController controller) {
        this.controller = controller;
    }
}
