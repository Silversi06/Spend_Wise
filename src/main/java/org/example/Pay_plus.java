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

public class Pay_plus extends JPanel {
    private Color backgroundColor = Color.decode("#FFE6B7"); // 연한 주황색
    private ArrayList<String> expenses = new ArrayList<>();

    public Pay_plus() {
        this.setBackground(backgroundColor); // 배경 색상 설정
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel descriptionLabel = new JLabel("지출 내용:");
        JTextField descriptionField = new JTextField(20);

        JLabel amountLabel = new JLabel("금액:");
        JTextField amountField = new JTextField(10);

        JButton addButton = new JButton("추가");

        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(addButton);

        JTextArea expenseList = new JTextArea(10, 30);
        expenseList.setEditable(false);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String description = descriptionField.getText();
                String amount = amountField.getText();
                if (!description.isEmpty() && !amount.isEmpty()) {
                    double amountValue = Double.parseDouble(amount); // 문자열을 double로 변환
                    expenses.add(description + ": " + amount + " 원");
                    updateExpenseList(expenseList);
                    descriptionField.setText("");
                    amountField.setText("");

                    // DatabaseConnector 인스턴스 생성
                    DatabaseConnector dbConnector = new DatabaseConnector();
                    // 데이터베이스에 지출 정보 추가
                    dbConnector.addExpenseToDB(description, amountValue);
                }
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
                Window parentWindow = SwingUtilities.windowForComponent(Pay_plus.this);
                parentWindow.dispose();
            }
        });

        this.add(inputPanel, BorderLayout.NORTH);
        this.add(backButton, BorderLayout.SOUTH);
        this.add(expenseList, BorderLayout.CENTER);
    }

    private void updateExpenseList(JTextArea expenseList) {
        expenseList.setText("");
        for (String expense : expenses) {
            expenseList.append(expense + "\n");
        }
    }


    //DB연결
    public class DatabaseConnector {
        private static final String DB_URL = "jdbc:mysql://localhost:3307/mysql";
        private static final String DB_USER = "root";
        private static final String DB_PASSWORD = "1234";

        public void addExpenseToDB(String description, double amount) {
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String insertQuery = "INSERT INTO expenses (description, amount, date) VALUES (?, ?, NOW())";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, description);
                preparedStatement.setDouble(2, amount);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("지출 관리");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Pay_plus());
        frame.pack();
        frame.setVisible(true);
    }
}

