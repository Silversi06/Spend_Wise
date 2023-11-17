package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Week extends JPanel {
    private Color backgroundColor = Color.decode("#FFE6B7"); // 연한 주황색

    public Week() {
        this.setBackground(backgroundColor); // 배경 색상 설정

        setLayout(new BorderLayout());

        JTextArea expenseTextArea = new JTextArea();
        expenseTextArea.setEditable(false);
        expenseTextArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        expenseTextArea.setMargin(new Insets(10, 10, 10, 10));
        expenseTextArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(expenseTextArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // 데이터베이스에서 주간 지출 데이터를 가져옵니다.
        DatabaseConnector dbConnector = new DatabaseConnector();
        ArrayList<Expense> weeklyExpenses = dbConnector.getWeeklyExpensesFromDB();

        displayDailyExpenses(weeklyExpenses, expenseTextArea);

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(createBackButton(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        headerPanel.setBackground(backgroundColor);

        JLabel titleLabel = new JLabel("주간 지출 현황");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        headerPanel.add(titleLabel);

        return headerPanel;
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Choose로 돌아가기");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Choose 패널로 돌아가는 동작을 구현
                JFrame chooseFrame = new JFrame("Choose");
                Choose choosePanel = new Choose();
                chooseFrame.add(choosePanel);
                chooseFrame.setSize(1200, 800);
                chooseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창을 닫을 때 현재 창만 닫음
                chooseFrame.setVisible(true);

                // 현재 패널을 부모 컴포넌트에서 제거
                Window parentWindow = SwingUtilities.windowForComponent(Week.this);
                parentWindow.dispose();
            }
        });

        return backButton;
    }

    private void displayDailyExpenses(ArrayList<Expense> weeklyExpenses, JTextArea expenseTextArea) {
        HashMap<String, Double> dailyExpenses = new HashMap<>();
        double totalWeeklyExpense = 0.0;

        // 주간 지출 데이터를 요일별로 그룹화합니다.
        for (Expense expense : weeklyExpenses) {
            Date date = expense.getDate();
            if (date != null) {
                String dayOfWeek = getDayOfWeek(date);
                double currentAmount = dailyExpenses.getOrDefault(dayOfWeek, 0.0);
                dailyExpenses.put(dayOfWeek, currentAmount + expense.getAmount());
                totalWeeklyExpense += expense.getAmount();
            }
        }

        // 요일별 지출을 JTextArea에 표시합니다.
        for (String dayOfWeek : dailyExpenses.keySet()) {
            expenseTextArea.append(dayOfWeek + ": " + dailyExpenses.get(dayOfWeek) + " 원\n");
        }

        // 전체 주간 합계를 표시합니다.
        expenseTextArea.append("\n주간 합계: " + totalWeeklyExpense + " 원\n");
    }

    private String getDayOfWeek(Date date) {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // 요일 포맷 설정
        return simpleDateformat.format(date);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("주간 지출 현황");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Week());
        frame.pack();
        frame.setVisible(true);
    }
}
