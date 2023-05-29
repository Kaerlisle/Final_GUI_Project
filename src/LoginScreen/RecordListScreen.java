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

        JPanel lists = new JPanel();
        JLabel name = new JLabel("Name");
        JLabel bday = new JLabel("Birthday");
        JLabel age = new JLabel("Age");

        lists.add(name);
        lists.add(bday);
        lists.add(age);

        JPanel sorting = new JPanel();
        JLabel sorlabel = new JLabel("Sort By:");
        sorting.add(sorlabel);

        String[] sortingList = { "Name", "Birthday", "Age"};
        JComboBox combox = new JComboBox(sortingList);
        sorting.add(combox);

        JPanel buttons = new JPanel();
        JButton addb = new JButton("Add a Record");
        JButton rem = new JButton("Remove a Record");
        JButton expo = new JButton("Export to CSV File");

        buttons.add(addb);
        buttons.add(rem);
        buttons.add(expo);

        frame.add(lists);
        frame.add(sorting);
        frame.add(buttons);


        /*
        JComboBox
        JList
        JTable
        JTextArea
        JRadioButton

        JRadioButton asc = new JRadioButton("Ascending");
        JRadioButton desc = new JRadioButton("Descending");
        */
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecordListScreen(""));
    }
}

