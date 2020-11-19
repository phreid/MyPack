package ui.panel;

import model.Pack;
import ui.controller.MainButtonPanelController;
import ui.MyPackGUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * The button panel for the main menu.
 */
public class MainMenuButtonPanel extends JPanel {
    private JButton newButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton saveAndExitButton;
    private JButton exitWithoutSavingButton;
    private JButton loadButton;
    private MainButtonPanelController controller;

    private static final int HEIGHT = 20;
    private static final int WIDTH = (int) (MyPackGUI.FRAME_WIDTH * 0.15);
    private static final Dimension BUTTON_DIM = new Dimension(WIDTH, HEIGHT);

    // EFFECTS: constructs a new MainMenuButtonPanel
    public MainMenuButtonPanel() {
        super(new GridLayout(5, 1));

        setupNewButton();
        setupDeleteButton();
        setupEditButton();
        setupSaveAndExitButton();
        setupExitWithoutSavingButton();
        setupLoadButton();
    }

    // MODIFIES: this
    // EFFECTS: creates the load button and adds it to the panel
    private void setupLoadButton() {
        loadButton = new JButton("Load file");
        loadButton.setPreferredSize(BUTTON_DIM);
        loadButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser("./data");
            int result = chooser.showOpenDialog(getParent());
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                controller.load(file.getName());
            }
        });

        add(loadButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the exit without saving button and adds it to the panel
    private void setupExitWithoutSavingButton() {
        exitWithoutSavingButton = new JButton("Exit without saving");
        exitWithoutSavingButton.setPreferredSize(BUTTON_DIM);
        exitWithoutSavingButton.addActionListener(e -> {
            controller.exit();
        });

        add(exitWithoutSavingButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the save and exit button and adds it to the panel
    private void setupSaveAndExitButton() {
        saveAndExitButton = new JButton("Save changes and exit");
        saveAndExitButton.setPreferredSize(BUTTON_DIM);
        saveAndExitButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser("./data");
            int result = chooser.showSaveDialog(getParent());
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if (controller != null) {
                    controller.save(file.getName());
                }
            }
        });
        add(saveAndExitButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the edit button and adds it to the panel
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

    // MODIFIES: this
    // EFFECTS: creates the delete button and adds it to the panel
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

    // MODIFIES: this
    // EFFECTS: creates the new button and adds it to the panel
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

    // MODIFIES: this
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

    // MODIFIES: this
    // EFFECTS: enables the edit button
    public void setEditEnabled(boolean b) {
        editButton.setEnabled(b);
    }

    // MODIFIES: this
    // EFFECTS: enables the delete button
    public void setDeleteEnabled(boolean b) {
        deleteButton.setEnabled(b);
    }
}
