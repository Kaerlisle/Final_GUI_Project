package LoginScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        // List of records of user's name, birthday, and age
        JPanel titleOfRecords = new JPanel(new GridLayout(1, 3));
        JLabel name = new JLabel("Name");
        JLabel bday = new JLabel("Birthday");
        JLabel age = new JLabel("Age");

        titleOfRecords.add(name);
        titleOfRecords.add(bday);
        titleOfRecords.add(age);

        // Table for the records of the user
        JPanel listsOfRecords = new JPanel(new BorderLayout());
        JTable records = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        records.setModel(model);
        listsOfRecords.add(new JScrollPane(records));

        // Sorting part where you sort the list in a given order
        JPanel sortingLabels = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel sortLabelText = new JLabel("Sort By:");
        sortingLabels.add(sortLabelText);

        String[] sortingList = {"Name", "Birthday", "Age"};
        JComboBox<String> comboBox = new JComboBox<>(sortingList);
        comboBox.setPreferredSize(new Dimension(100, comboBox.getPreferredSize().height)); // Set preferred size
        sortingLabels.add(comboBox);

        JRadioButton ascButton = new JRadioButton("Ascending");
        JRadioButton descButton = new JRadioButton("Descending");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(ascButton);
        buttonGroup.add(descButton);

        sortingLabels.add(ascButton);
        sortingLabels.add(descButton);


        // Buttons where another action executes
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addB = new JButton("Add a Record");
        JButton remB = new JButton("Remove a Record");
        JButton expoB = new JButton("Export to CSV File");

        buttons.add(addB);
        buttons.add(remB);
        buttons.add(expoB);

        frame.setLayout(new BorderLayout());
        frame.add(titleOfRecords, BorderLayout.NORTH);
        frame.add(listsOfRecords, BorderLayout.CENTER);
        frame.add(sortingLabels, BorderLayout.WEST);
        frame.add(buttons, BorderLayout.SOUTH);

        frame.setVisible(true);

        addB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRecords();
            }
        });
    }

    private void addRecords() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel bdayLabel = new JLabel("Birthday:");
        JTextField bdayField = new JTextField(10);
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(3);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(bdayLabel);
        panel.add(bdayField);
        panel.add(ageLabel);
        panel.add(ageField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Record",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String birthday = bdayField.getText();
            String age = ageField.getText();

            // Add logic to save the record or update the table
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecordListScreen(""));
    }
}
