package ui;

import model.AbstractEntry;
import model.Pack;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class MyPackGUI extends JFrame {
    private List<AbstractEntry> packList;
    private MainMenuPanel mainMenuPanel;

    static int FRAME_WIDTH = 1000;
    static int FRAME_HEIGHT = 800;
    private static final String DEFAULT_FILE_PATH = "./data/savedPacks.json";

    // EFFECTS: creates the main GUI frame
    public MyPackGUI() {
        super("MyPack");

        init();
    }

    // MODIFIES: this
    // EFFECTS: initializes the app and sets up main menu panes
    private void init() {
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        // TODO: make loading optional
        try {
            packList = loadPacks();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setupMainMenuPanel();

        pack();
        setVisible(true);
    }

    private void setupMainMenuPanel() {
        mainMenuPanel = new MainMenuPanel(this, packList);
        setContentPane(mainMenuPanel);
    }

    public void showMainMenu() {
        setContentPane(mainMenuPanel);
    }

    // EFFECTS: reads the saved packs file and returns it as a list of Packs,
    //          or throws IOException
    private List<AbstractEntry> loadPacks() throws IOException {
        JsonReader reader = new JsonReader(DEFAULT_FILE_PATH);
        return reader.read();
    }

    public void openSinglePackMenu(Pack pack) {
        SinglePackMenuPanel newPanel = new SinglePackMenuPanel(this, pack);
        setContentPane(newPanel);
        pack();
    }
}
