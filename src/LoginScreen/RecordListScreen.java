package LoginScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RecordListScreen {
    private String username;

    public RecordListScreen(String username) {
        this.username = username;
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("List of Records");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(true);
        frame.setLayout(new GridLayout(4, 4));

        //table for the records of user
        JPanel listsOfRecords = new JPanel(new BorderLayout());
        JTable records1 = new JTable();

        String[] titleRecords = {"Name", "Birthday", "Age"};
        DefaultTableModel records2 = new DefaultTableModel(titleRecords, 0);
        JTable records = new JTable(records2);
        JScrollPane scroll = new JScrollPane(records);
        listsOfRecords.add(records);


        //sorting part where you sort the list in a given order
        JPanel sortingLabels = new JPanel();
        JLabel sotLabelText = new JLabel("Sort By:");
        sortingLabels.add(sotLabelText);

        String[] sortingList = { "Name", "Birthday", "Age"};
        JComboBox combox = new JComboBox(sortingList);
        sortingLabels.add(combox);

        JRadioButton ascButton = new JRadioButton("Ascending");
        JRadioButton descButton = new JRadioButton("Descending");

        sortingLabels.add(ascButton);
        sortingLabels.add(descButton);

        //buttons on where another action executes
        JPanel buttons = new JPanel();
        JButton addB = new JButton("Add a Record");
        JButton remB = new JButton("Remove a Record");
        JButton expoB = new JButton("Export to CSV File");

        buttons.add(addB);
        buttons.add(remB);
        buttons.add(expoB);

        //
        //frame.add(titleOfRecords);
        frame.add(listsOfRecords);
        frame.add(sortingLabels);
        frame.add(buttons);
    }

    /*
    private void addRecords() {
        //
        JPanel name = new JPanel();
        JLabel label11 = new JLabel("Name:");
        name.add(label11);
        JTextField recName = new JTextField(10);
        name.add(recName);

        //
        String[] sortingList = { "January"};
        JComboBox comboxMonth = new JComboBox(sortingList);
        .add(comboxMonth);
    }
    */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecordListScreen(""));

        /*
        AbstractButton ascButton;
        ascButton.addActionListener(e -> {
            String getSortBy = sortingList.getText();

        });
        */
    }
}

