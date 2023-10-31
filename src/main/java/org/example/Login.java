package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public Login() {
        // 패널 배경 색상 설정
        Color backgroundColor = Color.decode("#FFE6B7"); // 연한 주황색
        this.setBackground(backgroundColor);

        // 레이아웃 설정 (GridBagLayout 사용)
        setLayout(new GridBagLayout());

        // 사용자명 입력 필드
        usernameField = new JTextField(20);
        // 비밀번호 입력 필드
        passwordField = new JPasswordField(20);

        // 로그인 버튼
        loginButton = new JButton("로그인");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 로그인 버튼 클릭 시 실행될 코드 추가
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // 여기서 로그인 기능을 구현
                if(login(username, password)){
                    JOptionPane.showMessageDialog(Login.this, "로그인 완료");
                }else{
                    JOptionPane.showMessageDialog(Login.this, "로그인 실패");
                }
            }
        });

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
                Window parentWindow = SwingUtilities.windowForComponent(Login.this);
                parentWindow.dispose();
            }
        });
        backButton.setBounds(100, 600, 200, 50);

        this.add(backButton);

        // 글자 크기 설정
        Font font = loginButton.getFont();
        Font newFont = new Font(font.getName(), font.getStyle(), 20); // 글자 크기 20으로 설정
        loginButton.setFont(newFont);
        backButton.setFont(newFont);

        // JLabel의 글자 크기 설정

        JLabel usernameLabel = new JLabel("사용자명: ");
        JLabel passwordLabel = new JLabel("비밀번호: ");
        Font font1 = usernameLabel.getFont();
        Font newFont2 = new Font(font1.getName(), font.getStyle(), 20);
        usernameLabel.setFont(newFont2);
        passwordLabel.setFont(newFont2);




        // 컴포넌트의 크기 설정
        Dimension componentSize = new Dimension(500, 100);
        usernameField.setPreferredSize(componentSize);
        passwordField.setPreferredSize(componentSize);
        loginButton.setPreferredSize(componentSize);

        // 컴포넌트를 그리드백에 추가하고 정렬 설정
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(usernameLabel, constraints); // 사용자명 레이블 추가

        constraints.gridx = 1;
        constraints.gridy = 0;
        add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(passwordLabel, constraints); // 비밀번호 레이블 추가

        constraints.gridx = 1;
        constraints.gridy = 1;
        add(passwordField, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        add(loginButton, constraints);

//        constraints.gridx = 1;
//        constraints.gridy = 2;
//        add(signupButton, constraints);

        // 버튼를 아래로 내리기
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(new JPanel(), constraints); // 빈 패널을 추가하여 공간을 확보


        constraints.gridy = 4;
        add(backButton, constraints);

        // 기타 설정
        setPreferredSize(new Dimension(400, 300)); // 패널의 크기 설정
    }


    private boolean login(String username, String password) {
        // 데이터베이스 연결 정보 설정
        String jdbcURL = "jdbc:mysql://localhost:3307/mysql";
        String dbUsername = "root";
        String dbPassword = "1234";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUsername, dbPassword)) {
            // 사용자명과 비밀번호를 사용하여 데이터베이스에서 검색
            String selectSQL = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                // 결과가 있는 경우 로그인 성공
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL 오류: " + e.getMessage());
        }
        return false;
    }
}
