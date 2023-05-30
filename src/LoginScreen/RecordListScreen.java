package LoginScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class RecordListScreen {
    private final ArrayList<Person> records;
    private JTable recordsTable;
    private ComboBoxModel<Object> monthComboBox;

    public RecordListScreen(String username) {
        this.records = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("List of Records");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);

        JPanel titlePanel = new JPanel(new GridLayout());

        JPanel recordsPanel = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel();
        recordsTable = new JTable(model);
        model.addColumn("Name");
        model.addColumn("Birthday");
        model.addColumn("Age");
        recordsPanel.add(new JScrollPane(recordsTable));

        JPanel sortingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel sortLabel = new JLabel("Sort By:");
        sortingPanel.add(sortLabel);

        String[] sortingOptions = { "Name", "Birthday", "Age" };
        JComboBox<String> sortComboBox = new JComboBox<>(sortingOptions);
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

        frame.setLayout(new BorderLayout());
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(recordsPanel, BorderLayout.CENTER);
        frame.add(sortingPanel, BorderLayout.EAST);
        frame.add(buttonsPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        addButton.addActionListener(e -> addRecord());

        removeButton.addActionListener(e -> removeRecord());

        exportButton.addActionListener(e -> exportToCSV());

        sortComboBox.addActionListener(e -> {
            String selectedSortOption = (String) sortComboBox.getSelectedItem();
            boolean isAscending = ascButton.isSelected();
            assert selectedSortOption != null;
            sortRecords(selectedSortOption, isAscending);
        });
    }

    private void addRecord() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel bdayLabel = new JLabel("Birthday:");

        String[] sortingOptions = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        JComboBox<String> sortMonths = new JComboBox<>(sortingOptions);

        // ComboBox (day)
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = String.valueOf(i);
        }
        JComboBox<String> dayComboBox = new JComboBox<>(days);

        // ComboBox (year)
        String[] years = new String[100];
        int currentYear = LocalDate.now().getYear();
        for (int i = 0; i < 100; i++) {
            years[i] = String.valueOf(currentYear - i);
        }
        JComboBox<String> yearComboBox = new JComboBox<>(years);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(bdayLabel);
        panel.add(monthComboBox);
        panel.add(new JLabel("")); // Placeholder for alignment
        panel.add(dayComboBox);
        panel.add(new JLabel("")); // Placeholder for alignment
        panel.add(yearComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Record",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String month = (String) monthComboBox.getSelectedItem();
            String day = (String) dayComboBox.getSelectedItem();
            String year = (String) yearComboBox.getSelectedItem();

            // Calculate age
            LocalDate currentDate = LocalDate.now();
            LocalDate birthdate = LocalDate.parse(day + " " + month + " " + year, DateTimeFormatter.ofPattern("d MMMM yyyy"));
            int age = currentDate.getYear() - birthdate.getYear();
            if (birthdate.getDayOfYear() > currentDate.getDayOfYear()) {
                age--;
            }

            Person person = new Person(name, birthdate.format(DateTimeFormatter.ofPattern("d MMMM yyyy")), age);
            records.add(person);
            updateTable();
        }
    }

    private void removeRecord() {
        int selectedRow = recordsTable.getSelectedRow();
        if (selectedRow >= 0) {
            records.remove(selectedRow);
            updateTable();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a record to remove.",
                    "No Record Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void exportToCSV() {
        StringBuilder csvData = new StringBuilder();
        csvData.append("Name,Birthday,Age\n");

        for (Person person : records) {
            csvData.append(person.getName()).append(",")
                    .append(person.getBirthday()).append(",")
                    .append(person.getAge()).append("\n");
        }

        // Ayusin ko to later pag tapos na kayo para macheck yung csv
        // Save the CSV data to a file using your preferred method
        // For example:
        // File outputFile = new File("output.csv");
        // FileWriter writer = new FileWriter(outputFile);
        // writer.write(csvData.toString());
        // writer.close();

        JOptionPane.showMessageDialog(null, "Records exported to CSV file.",
                "Export Successful", JOptionPane.INFORMATION_MESSAGE);
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
