package ui;

import javax.swing.*;

/**
 * Main class, responsible for running the app.
 */
public class Main {

    // EFFECTS: runs the app
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyPackGUI();
            }
        });
    }
}
