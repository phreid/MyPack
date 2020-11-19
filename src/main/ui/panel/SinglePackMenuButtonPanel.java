package ui.panel;

import model.PackItem;
import model.PackList;
import ui.MyPackGUI;
import ui.controller.SinglePackButtonPanelController;

import javax.swing.*;
import java.awt.*;

/**
 * Panel for the single pack menu buttons.
 */
public class SinglePackMenuButtonPanel extends JPanel {
    private JButton newCategoryButton;
    private JButton newItemButton;
    private JButton returnButton;
    private SinglePackButtonPanelController controller;

    private static final int HEIGHT = 20;
    private static final int WIDTH = (int) (MyPackGUI.FRAME_WIDTH * 0.20);
    private static final Dimension buttonDim = new Dimension(WIDTH, HEIGHT);

    // EFFECTS: creates a new button panel
    public SinglePackMenuButtonPanel() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        newCategoryButton = new JButton("Add category");
        newCategoryButton.setPreferredSize(buttonDim);
        newCategoryButton.addActionListener(e -> {
            showNewCategoryDialog();
        });
        add(newCategoryButton);

        newItemButton = new JButton("Add item");
        newItemButton.setPreferredSize(buttonDim);
        newItemButton.addActionListener(e -> {
            showNewItemDialog();
        });
        newItemButton.setEnabled(false);
        add(newItemButton);

        returnButton = new JButton("Return to main menu");
        returnButton.setPreferredSize(buttonDim);
        returnButton.addActionListener(e -> {
            controller.returnToMainMenu();
        });
        add(returnButton);
    }

    // REQUIRES: the currently selected table row is a category
    // MODIFIES: this
    // EFFECTS: shows the new item dialog and adds the resulting item to the currently selected category
    private void showNewItemDialog() {
        JTextField nameField = new JTextField(10);
        JTextField descField = new JTextField(10);
        JTextField costField = new JTextField(10);
        JTextField weightField = new JTextField(10);
        JCheckBox wornField = new JCheckBox();
        JCheckBox consumableField = new JCheckBox();
        JLabel nameLabel = new JLabel("Name (required): ");
        JLabel descLabel = new JLabel("Description: ");
        JLabel costLabel = new JLabel("Cost (in dollars and cents): ");
        JLabel weightLabel = new JLabel("Weight (numbers only): ");
        JLabel wornLabel = new JLabel("Worn? ");
        JLabel consumableLabel = new JLabel("Consumable? ");

        JPanel dialogPanel = getNewItemDialogPanel(nameField, descField, costField,
                weightField, wornField, consumableField, nameLabel,
                descLabel, costLabel, weightLabel, wornLabel, consumableLabel);

        int result = JOptionPane.showConfirmDialog(this, dialogPanel,
                "Enter the item's information.", JOptionPane.OK_CANCEL_OPTION);
        checkResult(result, nameField, descField, costField, weightField, wornField, consumableField);
    }

    // MODIFIES: this
    // EFFECTS: checks that the result of the new item dialog is valid, and adds the item if it is.
    //          otherwise, does nothing.
    private void checkResult(int result, JTextField nameField, JTextField descField, JTextField costField,
                             JTextField weightField, JCheckBox wornField,
                             JCheckBox consumableField) {
        if (result == JOptionPane.OK_OPTION) {
            Double cost;
            Integer weight;

            String name = nameField.getText();
            String description = descField.getText();
            try {
                cost = Double.parseDouble(costField.getText());
            } catch (NumberFormatException e) {
                cost = 0.0;
            }

            try {
                weight = Integer.parseInt(weightField.getText());
            } catch (NumberFormatException e) {
                weight = 0;
            }

            Boolean isWorn = wornField.isSelected();
            Boolean isConsumable = consumableField.isSelected();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(this, "A name is required.");
            } else {
                controller.addItem(new PackItem(name, description, weight, cost, isWorn, isConsumable));
            }
        }
    }

    // EFFECTS: builds the new item dialog panel and returns it
    private JPanel getNewItemDialogPanel(JTextField nameField, JTextField descField, JTextField costField,
                                         JTextField weightField, JCheckBox wornField, JCheckBox consumableField,
                                         JLabel nameLabel, JLabel descLabel, JLabel costLabel, JLabel weightLabel,
                                         JLabel wornLabel, JLabel consumableLabel) {
        JPanel dialogPanel = new JPanel();
        dialogPanel.add(nameLabel);
        dialogPanel.add(nameField);
        dialogPanel.add(descLabel);
        dialogPanel.add(descField);
        dialogPanel.add(costLabel);
        dialogPanel.add(costField);
        dialogPanel.add(weightLabel);
        dialogPanel.add(weightField);
        dialogPanel.add(wornLabel);
        dialogPanel.add(wornField);
        dialogPanel.add(consumableLabel);
        dialogPanel.add(consumableField);
        return dialogPanel;
    }

    // MODIFIES: this
    // EFFECTS: shows the new category dialog and adds the resulting category to the current pack
    private void showNewCategoryDialog() {
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
                controller.addCategory(new PackList(name, description));
            }
        }
    }

    public void setController(SinglePackButtonPanelController controller) {
        this.controller = controller;
    }

    // MODIFIES: this
    // EFFECTS: enables the add item button
    public void setAddItemEnabled(boolean b) {
        newItemButton.setEnabled(b);
    }
}
