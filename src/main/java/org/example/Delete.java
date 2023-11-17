package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete extends JPanel {
    private Color backgroundColor = Color.decode("#FFE6B7"); // 연한 주황색
    private ArrayList<Expense> expenses = new ArrayList<>();
    private JComboBox<Expense> expenseComboBox;

    public Delete() {
        this.setBackground(backgroundColor); // 배경 색상 설정
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        // Add a combo box to select expenses for deletion
        expenseComboBox = new JComboBox<>();
        inputPanel.add(new JLabel("지출 선택:"));
        inputPanel.add(expenseComboBox);

        JButton deleteButton = new JButton("삭제");
        inputPanel.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteExpense();
            }
        });

        JButton backButton = new JButton("Today로 돌아가기");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cahoose 패널로 돌아가는 동작을 구현
                JFrame todayFrame = new JFrame("Today");
                Today todayPanel = new Today();
                todayFrame.add(todayPanel);
                todayFrame.setSize(1200, 800);
                todayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창을 닫을 때 현재 창만 닫음
                todayFrame.setVisible(true);

                // Login 패널을 부모 컴포넌트에서 제거
                Window parentWindow = SwingUtilities.windowForComponent(Delete.this);
                parentWindow.dispose();
            }
        });

        this.add(inputPanel, BorderLayout.CENTER);
        this.add(backButton, BorderLayout.SOUTH);

        // Load expenses from the database when Delete is created
        loadExpensesFromDB();
        // Update the combo box initially
        updateExpenseComboBox();
    }

    // Method to handle the deletion of expenses
    private void deleteExpense() {
        Expense selectedExpense = (Expense) expenseComboBox.getSelectedItem();
        if (selectedExpense != null) {
            // 사용자에게 삭제를 확인하도록 요청
            int confirmResult = JOptionPane.showConfirmDialog(this, "이 비용을 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
            if (confirmResult == JOptionPane.YES_OPTION) {
                // 데이터베이스에서 선택한 비용 삭제
                DatabaseConnector dbConnector = new DatabaseConnector();
                dbConnector.deleteExpenseFromDB(selectedExpense);

                // 로컬 목록에서 선택한 비용 제거
                expenses.remove(selectedExpense);

                // 콤보 박스 업데이트
                updateExpenseComboBox();
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
        JFrame frame = new JFrame("Expense Deletion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Delete());
        frame.pack();
        frame.setVisible(true);
    }
}
