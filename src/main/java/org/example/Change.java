package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Change extends JPanel {
    private Color backgroundColor = Color.decode("#FFE6B7"); // 연한 주황색
    private ArrayList<Expense> expenses = new ArrayList<>();
    private JComboBox<Expense> expenseComboBox;

    public Change() {
        this.setBackground(backgroundColor); // 배경 색상 설정
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        // Add a combo box to select expenses for modification
        expenseComboBox = new JComboBox<>();
        inputPanel.add(new JLabel("지출 선택:"));
        inputPanel.add(expenseComboBox);

        JButton modifyButton = new JButton("수정");
        inputPanel.add(modifyButton);
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyExpense();
            }
        });

        JButton backButton = new JButton("Today로 돌아가기");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Choose 패널로 돌아가는 동작을 구현
                JFrame todayFrame = new JFrame("Today");
                Today todayPanel = new Today();
                todayFrame.add(todayPanel);
                todayFrame.setSize(1200, 800);
                todayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창을 닫을 때 현재 창만 닫음
                todayFrame.setVisible(true);

                // Login 패널을 부모 컴포넌트에서 제거
                Window parentWindow = SwingUtilities.windowForComponent(Change.this);
                parentWindow.dispose();
            }
        });

        this.add(inputPanel, BorderLayout.CENTER); // 변경된 부분
        this.add(backButton, BorderLayout.SOUTH);

        // Load expenses from the database when Change is created
        loadExpensesFromDB();
        // Update the combo box initially
        updateExpenseComboBox();
    }

    // Method to handle the modification of expenses
    private void modifyExpense() {
        Expense selectedExpense = (Expense) expenseComboBox.getSelectedItem();
        if (selectedExpense != null) {
            // Get the new description and amount
            String newDescription = JOptionPane.showInputDialog(this, "Enter new description:");
            String newAmountString = JOptionPane.showInputDialog(this, "Enter new amount:");
            if (newDescription != null && newAmountString != null) {
                try {
                    // Parse the new amount
                    double newAmount = Double.parseDouble(newAmountString);
                    String old = selectedExpense.getDescription();
                    // Update the selected expense
                    selectedExpense.setDescription(newDescription);
                    selectedExpense.setAmount(newAmount);
                    // Update the combo box
                    updateExpenseComboBox();
                    // Update the expense in the database
                    DatabaseConnector dbConnector = new DatabaseConnector();
                    dbConnector.updateExpenseInDB(selectedExpense, old);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid numeric value for amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }



    // Method to update the combo box with the current list of expenses
    private void updateExpenseComboBox() {
        expenseComboBox.removeAllItems();
        for (Expense expense : expenses) {
            expenseComboBox.addItem(expense);
        }
    }

    // Method to load expenses from the database
    private void loadExpensesFromDB() {
        DatabaseConnector dbConnector = new DatabaseConnector();
        expenses = dbConnector.getExpensesFromDB();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Expense Modification");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Change());
        frame.pack();
        frame.setVisible(true);
    }
}
