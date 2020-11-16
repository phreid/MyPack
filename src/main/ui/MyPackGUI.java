package ui;

import model.AbstractEntry;
import model.Pack;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.panel.MainMenuPanel;
import ui.panel.SinglePackMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main frame of the app. Responsible for creating panels and saving/loading data.
 */
public class MyPackGUI extends JFrame {
    private List<AbstractEntry> packList;
    private MainMenuPanel mainMenuPanel;

    public static int FRAME_WIDTH = 1200;
    public static int FRAME_HEIGHT = 1000;
    public static final String DEFAULT_FILE_PATH = "./data/";

    // EFFECTS: creates the main GUI frame
    public MyPackGUI() {
        super("MyPack");
        init();
    }

    // MODIFIES: this
    // EFFECTS: initializes the app and sets up main menu panes
    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        packList = new ArrayList<>();

        mainMenuPanel = new MainMenuPanel(this, packList);
        showMainMenu();

        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the frame's content pane to show the main menu
    public void showMainMenu() {
        setContentPane(mainMenuPanel);
    }

    // EFFECTS: reads the saved packs file and returns it as a list of Packs,
    //          or throws IOException
    private List<AbstractEntry> loadPacks(String name) throws IOException {
        JsonReader reader = new JsonReader(DEFAULT_FILE_PATH + name);
        return reader.read();
    }

    // MODIFIES: this
    // EFFECTS: creates the single pack menu and displays it
    public void openSinglePackMenu(Pack pack) {
        SinglePackMenuPanel newPanel = new SinglePackMenuPanel(this, pack);
        JScrollPane scrollPane = new JScrollPane(newPanel);
        setContentPane(scrollPane);
        pack();
    }

    // EFFECTS: save the current list of packs to file with given name, and exits
    //          if successful
    public void saveAndExit(String name) {
        if (! name.endsWith(".json")) {
            name += ".json";
        }
        JsonWriter writer = new JsonWriter(DEFAULT_FILE_PATH + name);

        try {
            writer.open();
            writer.write(packList);
            exit();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file.");
        } finally {
            writer.close();
        }
    }

    // MODIFIES: this
    // EFFECTS: disposes of all frames and closes the app
    public void exit() {
        Frame[] frames = Frame.getFrames();
        for (int i = 0; i < frames.length; i++) {
            frames[i].dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads saved pack data from file
    public void load(String name) {
        try {
            packList = loadPacks(name);
            mainMenuPanel.setTableData(packList);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading file.");
        }
    }
}
