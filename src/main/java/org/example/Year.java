package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Year extends JPanel {
    private Color backgroundColor = Color.decode("#FFE6B7"); // 연한 주황색

    public Year() {
        this.setBackground(backgroundColor); // 배경 색상 설정

        setLayout(new BorderLayout());

        JTextArea expenseTextArea = new JTextArea();
        expenseTextArea.setEditable(false);
        expenseTextArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        expenseTextArea.setMargin(new Insets(10, 10, 10, 10));
        expenseTextArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(expenseTextArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // 데이터베이스에서 월간 지출 데이터를 가져옵니다.
        // 현재 월을 구합니다.
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // 월은 0부터 시작하므로 +1 해줍니다.

        // 데이터베이스에서 현재 월의 지출 내역을 가져옵니다.
        DatabaseConnector dbConnector = new DatabaseConnector();
        ArrayList<Expense> monthlyExpenses = dbConnector.getMonthlyExpensesFromDB(currentMonth);

        displayMonthlyExpenses(monthlyExpenses, expenseTextArea);

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(createPrintButton(), BorderLayout.EAST);  // 추가된 부분
        add(createBackButton(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        headerPanel.setBackground(backgroundColor);

        JLabel titleLabel = new JLabel("월간 지출 현황");
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
                Window parentWindow = SwingUtilities.windowForComponent(Year.this);
                parentWindow.dispose();
            }
        });

        return backButton;
    }

    private JButton createPrintButton() {
        JButton printButton = new JButton("프린트");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 프린트 동작을 구현
                try {
                    JTextArea printArea = new JTextArea();
                    printArea.append("월간 지출 현황\n\n");

                    // 복사된 텍스트를 JTextArea에 추가
                    printArea.append("이번 달 지출 내역:\n");
                    printArea.append(getPrintableText());

                    // 프린트 작업 수행
                    printArea.print();
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "프린트 오류: " + ex.getMessage());
                }
            }
        });

        // 버튼 크기 및 폰트 설정
        printButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12)); // 작은 폰트
        printButton.setPreferredSize(new Dimension(80, 20)); // 작은 크기

        return printButton;
    }

    private void displayMonthlyExpenses(ArrayList<Expense> monthlyExpenses, JTextArea expenseTextArea) {
        double totalMonthlyExpense = 0.0;

        // 월간 지출 데이터를 JTextArea에 표시합니다.
        expenseTextArea.setText("이번 달 지출 내역:\n\n");
        for (Expense expense : monthlyExpenses) {
            expenseTextArea.append(expense.getDescription() + ": " + expense.getAmount() + " 원\n");
            totalMonthlyExpense += expense.getAmount();
        }
        expenseTextArea.append("\n이번 달 총 지출 합계: " + totalMonthlyExpense + " 원");
    }

    private String getPrintableText() {
        StringBuilder printableText = new StringBuilder();

        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // 월은 0부터 시작하므로 +1 해줍니다.

// 데이터베이스에서 현재 월의 지출 내역을 가져옵니다.
        DatabaseConnector dbConnector = new DatabaseConnector();
        ArrayList<Expense> monthlyExpenses = dbConnector.getMonthlyExpensesFromDB(currentMonth);

        double totalMonthlyExpense = 0.0;

        // 월간 지출 데이터를 JTextArea에 표시합니다.
        printableText.append("이번 달 지출 내역:\n\n");
        for (Expense expense : monthlyExpenses) {
            printableText.append(expense.getDescription() + ": " + expense.getAmount() + " 원\n");
            totalMonthlyExpense += expense.getAmount();
        }
        printableText.append("\n이번 달 총 지출 합계: " + totalMonthlyExpense + " 원");

        return printableText.toString();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("월간 지출 현황");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Year());
        frame.pack();
        frame.setVisible(true);
    }
}
