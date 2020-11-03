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

    static int FRAME_WIDTH = 800;
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
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        // TODO: make loading optional
        try {
            packList = loadPacks();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setupPackTablePanel();

        pack();
        setVisible(true);
    }

    // EFFECTS: reads the saved packs file and returns it as a list of Packs,
    //          or throws IOException
    private List<AbstractEntry> loadPacks() throws IOException {
        JsonReader reader = new JsonReader(DEFAULT_FILE_PATH);
        return reader.read();
    }

    // MODIFIES: this
    // EFFECTS: creates the Pack table and adds it to this
    private void setupPackTablePanel() {
        TablePanel tablePanel = new TablePanel();
        tablePanel.setTableData(packList);

        add(tablePanel);
    }
}
