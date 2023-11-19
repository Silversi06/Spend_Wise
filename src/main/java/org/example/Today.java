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
import java.text.DecimalFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.RingPlot;
import org.jfree.ui.RectangleInsets;

public class Today extends JPanel {

    private Pay_plus payplusScreen;
    private Change changeScreen;
    private Delete deleteScreen;
    private static final String DB_URL = "jdbc:mysql://localhost:3307/mysql";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";


    public Today() {
        Color backgroundColor = Color.decode("#FFE6B7"); // 연한 주황색
        this.setBackground(backgroundColor); // 배경 색상 설정

        ImageIcon imageIcon1 = new ImageIcon("src/img/today_pay.png");
        // JLabel을 사용하여 이미지를 표시
        JLabel imageLabel1 = new JLabel(imageIcon1);

        this.add(imageLabel1);

        imageLabel1.setBounds(0, 0, imageIcon1.getIconWidth(), imageIcon1.getIconHeight());

        ImageIcon icon1 = new ImageIcon("src/img/pay_plus_button.png");
        ImageIcon icon2 = new ImageIcon("src/img/change_button.png");
        ImageIcon icon3 = new ImageIcon("src/img/delete_button.png");

        JButton imageButton1 = new JButton(icon1);
        JButton imageButton2 = new JButton(icon2);
        JButton imageButton3 = new JButton(icon3);

        imageButton1.setContentAreaFilled(false);
        imageButton2.setContentAreaFilled(false);
        imageButton3.setContentAreaFilled(false);

        imageButton1.setBorderPainted(false);
        imageButton2.setBorderPainted(false);
        imageButton3.setBorderPainted(false);

        setLayout(null);
        imageButton1.setBounds(750, 60, 400, 200);
        imageButton2.setBounds(750, 225, 400, 300);
        imageButton3.setBounds(750, 445, 400, 300);

        this.add(imageButton1);
        this.add(imageButton2);
        this.add(imageButton3);


        payplusScreen = new Pay_plus();
        changeScreen = new Change();
        deleteScreen = new Delete();

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

                // Login 패널을 부모 컴포넌트에서 제거
                Window parentWindow = SwingUtilities.windowForComponent(Today.this);
                parentWindow.dispose();
            }
        });

        backButton.setBounds(880, 10, 150, 50);
        this.add(backButton);


        // 이미지 버튼에 이벤트 리스너 추가 (클릭 시 동작을 정의할 수 있음)
        imageButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 백그라운드 스레드에서 데이터베이스 작업을 수행
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        // "오늘" 버튼을 클릭했을 때 새로운 창으로 Today 패널을 표시
                        JFrame pay_plusFrame = new JFrame("Pay_plus");
                        Pay_plus pay_plusPanel = new Pay_plus();
                        pay_plusFrame.add(pay_plusPanel);
                        pay_plusFrame.setSize(1200, 800);
                        pay_plusFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창을 닫을 때 현재 창만 닫음
                        pay_plusFrame.setVisible(true);

                        // Choose 패널을 부모 컴포넌트에서 제거하고 창을 닫습니다.
                        Window parentWindow = SwingUtilities.windowForComponent(Today.this);
                        parentWindow.dispose();

                        return null;
                    }
                };

                worker.execute(); // 백그라운드 스레드 실행
            }
        });

        // Choose 패널에 Today 패널 추가
        this.add(payplusScreen);

        imageButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 주간 버튼을 클릭했을 때 수행할 동작 정의
                // 예: JOptionPane.showMessageDialog(null, "이미지 버튼 클릭됨!");
                JFrame changeFrame = new JFrame("Change");
                Change changePanel = new Change();
                changeFrame.add(changePanel);
                changeFrame.setSize(1200, 800);
                changeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창을 닫을 때 현재 창만 닫음
                changeFrame.setVisible(true);

                // Choose 패널을 부모 컴포넌트에서 제거하고 창을 닫습니다.
                Window parentWindow = SwingUtilities.windowForComponent(Today.this);
                parentWindow.dispose();
            }
        });

        this.add(changeScreen);
        imageButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 월간 버튼을 클릭했을 때 수행할 동작 정의
                // 예: JOptionPane.showMessageDialog(null, "이미지 버튼 클릭됨!");
                JFrame deleteFrame = new JFrame("Delete");
                Delete deletePanel = new Delete();
                deleteFrame.add(deletePanel);
                deleteFrame.setSize(1200, 800);
                deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창을 닫을 때 현재 창만 닫음
                deleteFrame.setVisible(true);

                // Choose 패널을 부모 컴포넌트에서 제거하고 창을 닫습니다.
                Window parentWindow = SwingUtilities.windowForComponent(Today.this);
                parentWindow.dispose();
            }
        });

        this.add(deleteScreen);
        this.setLayout(null);
        this.setVisible(true);
    }

    public static double getTodayExpenses() {
        double totalExpenses = 0.0;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String selectQuery = "SELECT amount FROM expenses WHERE DATE(date) = CURDATE()";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                totalExpenses += resultSet.getDouble("amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalExpenses;
    }

    public static double getLoggedInUserExpenses(String username) {
        double totalExpenses = 0.0;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // 사용자 아이디(username)를 기반으로 해당 사용자의 지출 내역을 조회
            String selectQuery = "SELECT amount FROM expenses WHERE username = ? AND DATE(date) = CURDATE()";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, username);  // 사용자 아이디 설정
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        totalExpenses += resultSet.getDouble("amount");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL 오류: " + e.getMessage());
        }
        return totalExpenses;
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 이미지 아래에 텍스트를 그릴 위치 설정
        int x = 250;
        int y = 200;

        // 오늘 지출 합계를 가져와서 포맷
        double todayExpenses = getTodayExpenses();
        DecimalFormat df = new DecimalFormat("#");
        String todayTotalText = df.format(todayExpenses) + " 원";

        // 글씨 크기만 변경
        Font originalFont = g.getFont();
        Font newFont = new Font(originalFont.getName(), originalFont.getStyle(), 60); // 30은 원하는 크기로 변경 가능
        g.setFont(newFont);

        // 텍스트 색상 설정
        g.setColor(Color.RED);

        // 텍스트 그리기
        g.drawString(todayTotalText, x, y);

        // 원 그래프 그리기
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("pay", todayExpenses);
        dataset.setValue("money", 1000000 - todayExpenses); // 잔액은 임의로 설정

        JFreeChart chart = ChartFactory.createRingChart(
                "pay chart", dataset, false, true, false);
        RingPlot plot = (RingPlot) chart.getPlot();
        plot.setCircular(true);
        plot.setLabelGenerator(null); // 레이블 제거

        // 각 섹션에 대한 색상 설정
        plot.setSectionPaint("pay", new Color(255, 0, 0)); // "pay" 섹션을 빨간색으로 설정
        plot.setSectionPaint("money", new Color(255, 255, 0)); // "money" 섹션을 파란색으로 설정


        // 오늘 남은 돈을 가져와서 포맷
        double remainingMoney = 1000000 - getTodayExpenses(); // Assuming initial amount is 1,000,000
        String remainingMoneyText = df.format(remainingMoney) + " 원";

        // 글씨 크기만 변경
        Font remainingFont = new Font(originalFont.getName(), originalFont.getStyle(), 30);
        g.setFont(remainingFont);

        // 텍스트 색상 설정
        g.setColor(Color.BLUE);

        // 텍스트 그리기
        g.drawString("남은 돈: " + remainingMoneyText, x+10, y + 90); // Adjust y position accordingly

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 400));
        chartPanel.setBounds(200, 300, 400, 400);
        add(chartPanel);
    }

}

