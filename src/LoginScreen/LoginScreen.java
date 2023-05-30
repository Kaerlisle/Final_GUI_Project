package LoginScreen;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Credentials credentials;
    private int loginAttempts;

    public LoginScreen() {
        credentials = new Credentials(); // Instantiate the Credentials object
        initializeUI();
        loadCredentialsFromFile();
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    private void loadCredentialsFromFile() {
        String fileName = "C:\\Users\\gohan\\Desktop\\FinalProject\\loginCredentials.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineCount = 0;
            String username = null;
            while ((line = br.readLine()) != null) {
                lineCount++;
                if (lineCount % 2 == 1) {
                    // Odd-numbered line, contains username
                    username = line.trim();
                } else {
                    // Even-numbered line, contains password
                    String password = line.trim();
                    credentials.addCredential(username, password);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading credentials from file: " + e.getMessage());
        }
    }

    private void initializeUI() {
        setTitle("Login Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);


        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 30, 100, 25);
        add(usernameLabel);


        usernameField = new JTextField();
        usernameField.setBounds(140, 30, 200, 25);
        add(usernameField);


        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 70, 100, 25);
        add(passwordLabel);


        passwordField = new JPasswordField();
        passwordField.setBounds(140, 70, 200, 25);
        add(passwordField);


        JButton loginButton = new JButton("Login");
        loginButton.setBounds(185, 110, 100, 25);
        add(loginButton);

        loginButton.addActionListener(e -> validateCredentials());

        setSize(430, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void validateCredentials() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (credentials.isValidCredential(username, password)) {
            JOptionPane.showMessageDialog(null, "Credentials have been authorized.", "Login Successful!", JOptionPane.INFORMATION_MESSAGE);
            openRecordListScreen(username);
        } else {
            loginAttempts++;
            int maxLoginAttempts = 3;
            int attempts = maxLoginAttempts - loginAttempts;

            if (attempts > 0) {
                JOptionPane.showMessageDialog(null, "Invalid Username / Password. \n         " + attempts + " more attempts.", "Invalid Credentials", JOptionPane.WARNING_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Sorry, you have reached the limit of " + maxLoginAttempts + " attempts. Goodbye!",
                        "Unauthorized User", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }

    private void openRecordListScreen(String username) {
        SwingUtilities.invokeLater(() -> {
            new RecordListScreen(username);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginScreen::new);
    }
}
