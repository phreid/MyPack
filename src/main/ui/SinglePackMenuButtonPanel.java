package ui;

import model.Pack;
import model.PackItem;
import model.PackList;

import javax.swing.*;
import java.awt.*;

public class SinglePackMenuButtonPanel extends JPanel {
    private JButton newCategoryButton;
    private JButton newItemButton;
    private JButton returnButton;
    private SinglePackButtonPanelController controller;

    private static final int HEIGHT = 20;
    private static final int WIDTH = (int) (MyPackGUI.FRAME_WIDTH * 0.20);
    private static final Dimension buttonDim = new Dimension(WIDTH, HEIGHT);

    // EFFECTS: constructs and new SinglePackMenuButtonPanel
    public SinglePackMenuButtonPanel() {
        super(new GridLayout(2, 1));

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
    // MODIFIES: controller
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

        int result = JOptionPane.showConfirmDialog(this, dialogPanel,
                "Enter the item's information.", JOptionPane.OK_CANCEL_OPTION);
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

    // MODIFIES: controller
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

    public void setAddItemEnabled(boolean b) {
        newItemButton.setEnabled(b);
    }
}
