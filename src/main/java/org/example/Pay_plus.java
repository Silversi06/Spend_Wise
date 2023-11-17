package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Pay_plus extends JPanel {
    private Color backgroundColor = Color.decode("#FFE6B7"); // 연한 주황색
    private ArrayList<Expense> expenses = new ArrayList<>();
    private JComboBox<Expense> expenseComboBox;


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


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String description = descriptionField.getText();
                String amount = amountField.getText();
                if (!description.isEmpty() && !amount.isEmpty()) {
                    double amountValue = Double.parseDouble(amount);
                    Expense newExpense = new Expense(description, amountValue);  // 수정된 생성자 사용
                    expenses.add(newExpense);

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


        // Load expenses from the database when Pay_plus is created
        loadExpensesFromDB();
    }

    private void updateExpenseList(JTextArea expenseList) {
        expenseList.setText("");
        for (Expense expense : expenses) {
            expenseList.append(expense.getDescription() + ": " + expense.getAmount() + " 원\n");
        }
    }




    // Method to load expenses from the database
    private void loadExpensesFromDB() {
        DatabaseConnector dbConnector = new DatabaseConnector();
        expenses = dbConnector.getExpensesFromDB();
//        updateExpenseComboBox();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("지출 관리");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Pay_plus());
        frame.pack();
        frame.setVisible(true);
    }
}

class Expense {
    private String description;
    private double amount;

    private String originalDescription;
    private double originalAmount;
    private String newDescription;
    private double newAmount;
    private int id;
    private Date date;

    public Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
        this.date = new Date();  // 현재 날짜로 초기화
    }

    public Expense(String description, double amount, Date date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return description + ": " + amount + " 원";
    }

    public String getOriginalDescription() {
        return originalDescription;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }

    public double getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(double newAmount) {
        this.newAmount = newAmount;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

class DatabaseConnector {
    private static final String DB_URL = "jdbc:mysql://localhost:3307/mysql";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    public void addExpenseToDB(String description, double amount) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO expenses (description, amount, date) VALUES (?, ?, NOW())";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, description);
                preparedStatement.setDouble(2, amount);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Expense> getExpensesFromDB() {
        ArrayList<Expense> expenses = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String selectQuery = "SELECT id, description, amount FROM expenses";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String description = resultSet.getString("description");
                        double amount = resultSet.getDouble("amount");
                        Expense expense = new Expense(description, amount);
                        expense.setId(id); // 이 부분을 추가하여 id를 설정
                        expenses.add(expense);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }


    public void updateExpenseInDB(Expense expense, String oldDescription) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String updateQuery = "UPDATE expenses SET description = ?, amount = ? WHERE description = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, expense.getDescription());  // 새로운 description을 문자열로 설정
                preparedStatement.setDouble(2, expense.getAmount()); // 새로운 amount를 double로 설정
                preparedStatement.setString(3, oldDescription);  // 원래의 description을 문자열로 설정
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExpenseFromDB(Expense expense) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "DELETE FROM expenses WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, expense.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("비용이 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("비용 삭제 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 예외 처리를 더 구체적으로 수정하세요.
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Expense> getWeeklyExpensesFromDB() {
        ArrayList<Expense> weeklyExpenses = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String selectQuery = "SELECT id, description, amount, date FROM expenses WHERE date >= NOW() - INTERVAL 7 DAY";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String description = resultSet.getString("description");
                        double amount = resultSet.getDouble("amount");
                        Date date = resultSet.getDate("date");

                        Expense expense = new Expense(description, amount,date);
                        expense.setId(id);
                        expense.setDate(date);
                        weeklyExpenses.add(expense);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weeklyExpenses;
    }

    public ArrayList<Expense> getMonthlyExpensesFromDB(int month) {
        ArrayList<Expense> monthlyExpenses = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String selectQuery = "SELECT description, amount, date FROM expenses WHERE MONTH(date) = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, month);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String description = resultSet.getString("description");
                        double amount = resultSet.getDouble("amount");
                        Date date = resultSet.getDate("date");

                        Expense expense = new Expense(description, amount, date);
                        monthlyExpenses.add(expense);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return monthlyExpenses;
    }
}

