package com.employeedb;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.io.*;

import javax.swing.*;

public class EmployeeForm extends JFrame {

    // Database connection properties
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ems";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgresql";

    private JTextArea outputTextArea;
    private PrintStream standardOut;

    private JTextField idField;
    private JTextField nameField;
    private JTextField genderField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField designationField;
    private JTextField salaryField;
    private JButton submitButton;
    private JButton retrieveButton;
    private JButton updateButton;
    private JButton deleteButton;

    public EmployeeForm() {
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Employee Form");

        JPanel panel = new JPanel(new GridLayout(0, 2));

        JLabel headingLabel = new JLabel("Employee Management System");
        headingLabel.setFont(new Font("Verdana", Font.BOLD, 50));
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingLabel.setForeground(Color.DARK_GRAY);
        headingLabel.setOpaque(true);
        headingLabel.setBackground(Color.lightGray);

        getContentPane().add(headingLabel, BorderLayout.NORTH);
        setSize(900, 400);

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(10);
        panel.add(idLabel);
        panel.add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(10);
        panel.add(nameLabel);
        panel.add(nameField);

        JLabel genderLabel = new JLabel("Gender:");
        genderField = new JTextField(10);
        panel.add(genderLabel);
        panel.add(genderField);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneField = new JTextField(10);
        panel.add(phoneLabel);
        panel.add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(10);
        panel.add(emailLabel);
        panel.add(emailField);

        JLabel designationLabel = new JLabel("Designation:");
        designationField = new JTextField(10);
        panel.add(designationLabel);
        panel.add(designationField);

        JLabel salaryLabel = new JLabel("Salary:");
        salaryField = new JTextField(10);
        panel.add(salaryLabel);
        panel.add(salaryField);

        submitButton = new JButton("Submit");
        retrieveButton = new JButton("Retrieve");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        // Create the JTextArea for displaying the output
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        
        // Create a PrintStream to redirect the standard output to the JTextArea
        PrintStream printStream = new PrintStream(new CustomOutputStream(outputTextArea));
        
        // Redirect the standard output to the PrintStream
        standardOut = System.out;
        System.setOut(printStream);
        
        // ...
        
        // Add the JTextArea to the GUI layout
        JScrollPane textScrollPane = new JScrollPane(outputTextArea);
        add(textScrollPane, BorderLayout.CENTER);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitEmployee();
            }
        });

        retrieveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retrieveEmployee();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(retrieveButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private class CustomOutputStream extends OutputStream {
        private JTextArea textArea;
        
        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }
        
        @Override
        public void write(int b) throws IOException {
            // Redirect the output to the JTextArea
            textArea.append(String.valueOf((char) b));
            
            // Scroll to the end of the text area
            textArea.setCaretPosition(textArea.getDocument().getLength());
            
            // Write the output to the standard output as well
            standardOut.write(b);
        }
    }
    


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    private void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void submitEmployee() {
        String name = nameField.getText();
        String gender = genderField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String designation = designationField.getText();
        double salary = Double.parseDouble(salaryField.getText());

        String insertQuery = "INSERT INTO employee (name, gender, phonenum, email, designation, salary) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setString(1, name);
            statement.setString(2, gender);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setString(5, designation);
            statement.setDouble(6, salary);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Employee inserted successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void retrieveEmployee() {
        String name = nameField.getText();
        String retrieveQuery = "SELECT * FROM employee WHERE name = ?";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(retrieveQuery)) {

            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String gender = resultSet.getString("gender");
                String phone = resultSet.getString("phonenum");
                String email = resultSet.getString("email");
                String designation = resultSet.getString("designation");
                double salary = resultSet.getDouble("salary");

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Gender: " + gender);
                System.out.println("Phone Number: " + phone);
                System.out.println("Email: " + email);
                System.out.println("Designation: " + designation);
                System.out.println("Salary: " + salary);
            } else {
                System.out.println("Employee not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String gender = genderField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String designation = designationField.getText();
        double salary = Double.parseDouble(salaryField.getText());

        String updateQuery = "UPDATE employee SET name = ?, gender = ?, phonenum = ?, email = ?, " +
                "designation = ?, salary = ? WHERE id = ?";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            statement.setString(1, name);
            statement.setString(2, gender);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setString(5, designation);
            statement.setDouble(6, salary);
            statement.setInt(7, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteEmployee() {
        int id = Integer.parseInt(idField.getText());
        String deleteQuery = "DELETE FROM employee WHERE id = ?";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EmployeeForm();
            }
        });
    }
}
