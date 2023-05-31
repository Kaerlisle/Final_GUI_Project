package LoginScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.*;
import java.util.Date;
import javax.swing.table.JTableHeader;



public class RecordListScreen extends Point {
    private final ArrayList<Person> records;
    private JTable recordsTable;
    private JFrame frame;

    public RecordListScreen(String username) {
        this.records = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("List of Records");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JPanel titlePanel = new JPanel();

        JPanel recordsPanel = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel();
        recordsTable = new JTable(model);
        model.addColumn("Name");
        model.addColumn("Birthday");
        model.addColumn("Age");
        JTableHeader header = recordsTable.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        header.setBackground(Color.decode("#D0F0C0"));
        header.setForeground(Color.BLACK);

        recordsTable.setFont(recordsTable.getFont().deriveFont(Font.PLAIN));
        recordsTable.setBackground(Color.white);
        recordsTable.setForeground(Color.black);
        recordsPanel.add(new JScrollPane(recordsTable), BorderLayout.CENTER);

        JPanel sortingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel sortLabel = new JLabel("Sort By:");
        sortingPanel.add(sortLabel);

        String[] sortingOptions = { "Name", "Birthday", "Age" };
        JComboBox<String> sortComboBox = new JComboBox<>(sortingOptions);
        sortComboBox.setBackground(Color.WHITE);
        sortingPanel.add(sortComboBox);

        JRadioButton ascButton = new JRadioButton("Ascending");
        JRadioButton descButton = new JRadioButton("Descending");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(ascButton);
        buttonGroup.add(descButton);
        sortingPanel.add(ascButton);
        sortingPanel.add(descButton);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add a Record");
        JButton removeButton = new JButton("Remove Record");
        JButton exportButton = new JButton("Export to CSV File");

        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(exportButton);
        addButton.setBackground(Color.WHITE);
        removeButton.setBackground(Color.WHITE);
        exportButton.setForeground(Color.WHITE);
        exportButton.setBackground(Color.decode("#028A0F"));

        frame.setLayout(new BorderLayout());
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(recordsPanel, BorderLayout.CENTER);
        frame.add(sortingPanel, BorderLayout.NORTH);
        frame.add(buttonsPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        addButton.addActionListener(e -> addRecord());

        removeButton.addActionListener(e -> removeRecordList());

        exportButton.addActionListener(e -> exportToCSV());

        sortComboBox.addActionListener(e -> {
            String selectedSortOption = (String) sortComboBox.getSelectedItem();
            boolean isAscending = ascButton.isSelected();
            assert selectedSortOption != null;
            sortRecords(selectedSortOption, isAscending);
        });

        for (Component component : buttonsPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                Font selectedFont = new Font("Arial", Font.BOLD, 12);
                button.setFocusPainted(false);
                button.setFont(selectedFont);

                button.setPreferredSize(new Dimension(150, 30));

                button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                button.setContentAreaFilled(false);
                button.setOpaque(true);
            }
        }
    }

    private String[] months() {
        return new String[]{
                "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        };
    }

    private String[] days() {
        String[] days = new String[31];

        for (int i = 1; i <= 31; i++) {
            days[i - 1] = String.valueOf(i);
        }
        return days;
    }

    private String[] year() {
        String[] year = new String[100];
        int currentYear = LocalDate.now().getYear();

        for (int i = 0; i < 100; i++) {
            year[i] = String.valueOf(currentYear - i);
        }
        return year;
    }

    private void addRecord() {
        frame.setVisible(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        JPanel bdayPanel = new JPanel();
        bdayPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel bdayLabel = new JLabel("Birthday:");
        JComboBox<String> monthComboBox = new JComboBox<>(months());
        JComboBox<String> dayComboBox = new JComboBox<>(days());
        JComboBox<String> yearComboBox = new JComboBox<>(year());
        monthComboBox.setBackground(Color.WHITE);
        dayComboBox.setBackground(Color.WHITE);
        yearComboBox.setBackground(Color.WHITE);
        bdayPanel.add(bdayLabel);
        bdayPanel.add(monthComboBox);
        bdayPanel.add(dayComboBox);
        bdayPanel.add(yearComboBox);

        panel.add(namePanel);
        panel.add(bdayPanel);

        boolean addAnother = true;
        while (addAnother) {
            int result = JOptionPane.showOptionDialog(null, panel, "Add a Record", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    new String[]{"Save and Go Back", "Save and Add Another", "Back"}, "Save and Go Back");
            if (result == JOptionPane.CLOSED_OPTION) {
                addAnother = false;
            }
                else if (result == 2) {
                    break;
                }
                else {
                    String name = nameField.getText();

                    if (!name.isEmpty()) {
                        int selectedMonth = monthComboBox.getSelectedIndex() + 1;
                        int selectedDay = Integer.parseInt((String) dayComboBox.getSelectedItem());
                        int selectedYear = Integer.parseInt((String) yearComboBox.getSelectedItem());

                        try {
                            LocalDate currentDate = LocalDate.now();
                            LocalDate birthdate = LocalDate.of(selectedYear, selectedMonth, selectedDay);

                            if (birthdate.isAfter(currentDate)) {
                                throw new IllegalArgumentException("Birthdate non-existent.");
                            }
                            int age = currentDate.getYear() - birthdate.getYear();
                            Person person = new Person(name, birthdate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), age);
                            records.add(person);
                            updateTable();

                            if (result == 0) {
                                addAnother = false; // Save and Go Back
                                } else {
                                // Clear input fields for adding another record
                                nameField.setText("");
                                monthComboBox.setSelectedIndex(0);
                                dayComboBox.setSelectedIndex(0);
                                yearComboBox.setSelectedIndex(0);
                            }
                        }
                        catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error: Invalid Input", JOptionPane.ERROR_MESSAGE);
                        }
                        catch (Exception error) {
                            JOptionPane.showMessageDialog(null, "Invalid birthdate format.", "Error: Invalid Input", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Name input is missing.", "Error: Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
        }
        frame.setVisible(true);
    }

    private void removeRecordList() {
        frame.setVisible(false);
        JFrame removeRecord = new JFrame("Remove a Record");
        JPanel removePanel = new JPanel(new GridLayout(5, 2, 5, 5));
        removePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel namePanel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JButton removeGoBackButton = new JButton("Remove and Go Back");
        JButton removeAnotherButton = new JButton("Remove and Remove Another");
        JButton backButton = new JButton("Back");

        removePanel.add(namePanel);
        removePanel.add(nameField);
        removePanel.add(removeGoBackButton);
        removePanel.add(removeAnotherButton);
        removePanel.add(backButton);

        backButton.setBackground(Color.WHITE);
        removeGoBackButton.setForeground(Color.WHITE);
        removeGoBackButton.setBackground(Color.decode("#028A0F"));
        removeAnotherButton.setForeground(Color.WHITE);
        removeAnotherButton.setBackground(Color.decode("#028A0F"));

        removeGoBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                if (!name.isEmpty()) {
                    int confirm = JOptionPane.showConfirmDialog(removePanel, "Remove this record?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            boolean removed = removeRecord(name);
                            if (removed) {
                                JOptionPane.showMessageDialog(removePanel, "The record is removed.");
                                updateTable();
                                removeRecord.setVisible(false);
                                frame.setVisible(true);
                            } else {
                                throw new IllegalArgumentException("No such record is found.");
                            }
                        } catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(removePanel, ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(removePanel, "Please enter a name.");
                }
            }
        });
        removeAnotherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                if (!name.isEmpty()) {
                    int confirm = JOptionPane.showConfirmDialog(removePanel, "Remove this record?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            boolean removed = removeRecord(name);
                            if (removed) {
                                JOptionPane.showMessageDialog(removePanel, "The record is removed.");
                                updateTable();
                                nameField.setText(""); // Clear the name field for removing another record
                            } else {
                                throw new IllegalArgumentException("No such record is found.");
                            }
                        } catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(removePanel, ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(removePanel, "Please enter a name.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeRecord.setVisible(false); frame.setVisible(true);
            }
        });

        removeRecord.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(true);
            }
        });

        removeRecord.add(removePanel);
        removeRecord.setSize(300, 200);
        removeRecord.setLocationRelativeTo(null);
        removeRecord.setVisible(true);
    }

    private boolean removeRecord(String name) {
        for (Person person : records) {
            if (person.getName().equalsIgnoreCase(name)) {
                records.remove(person);
                return true;
            }
        }
        return false;
    }


    private void exportToCSV() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        String filename = timestamp + ".csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            StringBuilder csvData = new StringBuilder();
            csvData.append("Name,Birthday,Age\n");

            for (Person person : records) {
                csvData.append(person.getName()).append(",")
                        .append(person.getBirthday()).append(",")
                        .append(person.getAge()).append("\n");
            }

            writer.write(csvData.toString());
            JOptionPane.showMessageDialog(null, "CSV file exported successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error exporting CSV file: " + e.getMessage());
        }
    }


    private void sortRecords(String sortBy, boolean isAscending) {
        Comparator<Person> comparator = switch (sortBy) {
            case "Birthday" -> Comparator.comparing(Person::getBirthday);
            case "Age" -> Comparator.comparingInt(Person::getAge);
            default -> Comparator.comparing(Person::getName);
        };

        if (!isAscending) {
            comparator = comparator.reversed();
        }

        records.sort(comparator);
        updateTable();
    }

    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) recordsTable.getModel();
        model.setRowCount(0);

        for (Person person : records) {
            Object[] row = {person.getName(), person.getBirthday(), person.getAge()};
            model.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecordListScreen(""));
    }
}

class Person {
    private final String name;
    private final String birthday;
    private final int age;

    public Person(String name, String birthday, int age) {
        this.name = name;
        this.birthday = birthday;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getAge() {
        return age;
    }
}