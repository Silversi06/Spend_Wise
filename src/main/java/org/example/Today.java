package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Today extends JPanel {

    private Pay_plus payplusScreen;
    private Change changeScreen;
    private Delete deleteScreen;

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
        imageButton1.setBounds(750, 15, 400, 300);
        imageButton2.setBounds(750, 225, 400, 300);
        imageButton3.setBounds(750, 445, 400, 300);

        this.add(imageButton1);
        this.add(imageButton2);
        this.add(imageButton3);


        payplusScreen = new Pay_plus();
        changeScreen = new Change();
        deleteScreen = new Delete();

        // 이미지 버튼에 이벤트 리스너 추가 (클릭 시 동작을 정의할 수 있음)
        imageButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                Week weekPanel = new Week();
                changeFrame.add(weekPanel);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 추가적인 그리기 작업이 필요한 경우 여기에서 수행할 수 있습니다.
    }

}
