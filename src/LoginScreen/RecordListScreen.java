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

        JPanel titleOfRecords = new JPanel();
        JLabel name = new JLabel("Name");
        JLabel bday = new JLabel("Birthday");
        JLabel age = new JLabel("Age");

        titleOfRecords.add(name);
        titleOfRecords.add(bday);
        titleOfRecords.add(age);


        JPanel listsOfRecords = new JPanel(new BorderLayout());
        JTable records = new JTable();
        listsOfRecords.add(records);


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

        JPanel buttons = new JPanel();
        JButton addB = new JButton("Add a Record");
        JButton remB = new JButton("Remove a Record");
        JButton expoB = new JButton("Export to CSV File");

        buttons.add(addB);
        buttons.add(remB);
        buttons.add(expoB);

        frame.add(titleOfRecords);
        //frame.add();
        frame.add(sortingLabels);
        frame.add(buttons);

        /*
        JComboBox
        JList
        JTable
        JTextArea
        JRadioButton
        */
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecordListScreen(""));
    }
}

