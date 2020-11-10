package ui;

import model.Pack;

import javax.swing.*;
import java.awt.*;

public class MainMenuButtonPanel extends JPanel {
    private JButton newButton;
    private JButton deleteButton;
    private JButton editButton;
    private MainButtonPanelController controller;

    private static final int HEIGHT = 20;
    private static final int WIDTH = (int) (MyPackGUI.FRAME_WIDTH * 0.15);
    private static final Dimension BUTTON_DIM = new Dimension(WIDTH, HEIGHT);

    // EFFECTS: constructs a new MainMenuButtonPanel
    public MainMenuButtonPanel() {
        super(new GridLayout(3, 1));

        setupNewButton();
        setupDeleteButton();
        setupEditButton();
    }

    private void setupEditButton() {
        editButton = new JButton("Edit pack");
        editButton.setPreferredSize(BUTTON_DIM);
        editButton.addActionListener(e -> {
            if (controller != null) {
                controller.editSelectedEntry();
            }
        });
        editButton.setEnabled(false);

        add(editButton);
    }

    private void setupDeleteButton() {
        deleteButton = new JButton("Delete pack");
        deleteButton.setPreferredSize(BUTTON_DIM);
        deleteButton.addActionListener(e -> {
            if (controller != null) {
                controller.deleteSelectedEntry();
            }
        });
        deleteButton.setEnabled(false);

        add(deleteButton);
    }

    private void setupNewButton() {
        newButton = new JButton("New pack");
        newButton.setPreferredSize(BUTTON_DIM);
        newButton.addActionListener(e -> {
            if (controller != null) {
                showNewPackDialog();
            }
        });

        add(newButton);
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

    void setController(MainButtonPanelController controller) {
        this.controller = controller;
    }

    public void setEditEnabled(boolean b) {
        editButton.setEnabled(b);
    }

    public void setDeleteEnabled(boolean b) {
        deleteButton.setEnabled(b);
    }
}
