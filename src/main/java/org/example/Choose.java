package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder; // 추가

public class Choose extends JPanel {
    private Today todayScreen;

    public Choose() {
        // 배경 색상 설정 (16진수 색상 코드를 사용)
        Color backgroundColor = Color.decode("#FFE6B7"); // 예: 연한 주황색

        // 배경 색상을 그립니다.
        this.setBackground(backgroundColor);

        ImageIcon imageIcon1 = new ImageIcon("src/img/talk.png");
        ImageIcon imageIcon2 = new ImageIcon("src/img/pig.png");

        // JLabel을 사용하여 이미지를 표시
        JLabel imageLabel1 = new JLabel(imageIcon1);
        JLabel imageLabel2 = new JLabel(imageIcon2);

        // 이미지를 패널에 추가
        this.add(imageLabel1);
        this.add(imageLabel2);

        // 이미지의 위치 및 크기 설정
        imageLabel1.setBounds(100, 300, imageIcon1.getIconWidth(), imageIcon1.getIconHeight());
        imageLabel2.setBounds(700, 300, imageIcon2.getIconWidth(), imageIcon1.getIconHeight());

        // 이미지 아이콘을 생성
        ImageIcon icon1 = new ImageIcon("src/img/today_button.png");
        ImageIcon icon2 = new ImageIcon("src/img/week_button.png");
        ImageIcon icon3 = new ImageIcon("src/img/month_button.png");

        // 이미지 버튼을 생성
        JButton imageButton1 = new JButton(icon1);
        JButton imageButton2 = new JButton(icon2);
        JButton imageButton3 = new JButton(icon3);

        // 이미지 버튼의 배경을 투명으로 설정
        imageButton1.setContentAreaFilled(false);
        imageButton2.setContentAreaFilled(false);
        imageButton3.setContentAreaFilled(false);

        // 버튼의 외곽선 (border)을 숨김
        imageButton1.setBorderPainted(false);
        imageButton2.setBorderPainted(false);
        imageButton3.setBorderPainted(false);

        // 버튼의 레이아웃 관리자를 해제
        setLayout(null);

        // 버튼의 위치 및 크기 설정
        imageButton1.setBounds(100, 100, 212, 140); // 좌상단 좌표 (100, 100), 너비 100, 높이 50
        imageButton2.setBounds(500, 100, 212, 140);
        imageButton3.setBounds(900, 100, 212, 140);

        // 이미지 버튼을 패널에 추가
        this.add(imageButton1);
        this.add(imageButton2);
        this.add(imageButton3);

        todayScreen = new Today();

        // 이미지 버튼에 이벤트 리스너 추가 (클릭 시 동작을 정의할 수 있음)
        imageButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 오늘 버튼을 클릭했을 때 수행할 동작 정의
                // 예: JOptionPane.showMessageDialog(null, "이미지 버튼 클릭됨!");
               // Today todayScreen = new Today();

                // Show the Today window
                Choose.this.setVisible(false); // 현재 Choose 화면 숨기기
                todayScreen.setVisible(true);

            }
        });

        imageButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 주간 버튼을 클릭했을 때 수행할 동작 정의
                // 예: JOptionPane.showMessageDialog(null, "이미지 버튼 클릭됨!");
            }
        });
        imageButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 월간 버튼을 클릭했을 때 수행할 동작 정의
                // 예: JOptionPane.showMessageDialog(null, "이미지 버튼 클릭됨!");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 추가적인 그리기 작업이 필요한 경우 여기에서 수행할 수 있습니다.
    }
}
