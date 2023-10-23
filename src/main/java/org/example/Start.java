package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Start extends JFrame {
    private Choose chooseScreen;

    public Start() {
        setTitle("Spend Wise");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);

        // 이미지 파일의 경로를 설정
        String startimg = "src/img/start.png"; // 이미지 파일 경로를 프로젝트 구조에 맞게 변경
        String startbutton = "src/img/start_button.png";

        // 이미지를 JLabel을 사용하여 배경으로 설정
        try {
            BufferedImage image = ImageIO.read(new File(startimg));
            JLabel background = new JLabel(new ImageIcon(image));
            background.setLayout(new FlowLayout(FlowLayout.RIGHT, -10, 550)); // FlowLayout을 사용하여 가운데 정렬

            BufferedImage buttonImg = ImageIO.read(new File(startbutton));
            ImageIcon buttonIcon = new ImageIcon(buttonImg);
            JButton imageButton = new JButton(buttonIcon);

            Dimension buttonSize = new Dimension(300, 150);
            imageButton.setPreferredSize(buttonSize);

            imageButton.setBorderPainted(false);
            imageButton.setOpaque(false);
            imageButton.setContentAreaFilled(false);
            imageButton.setBorderPainted(false);


            chooseScreen = new Choose(); // Choose 클래스의 인스턴스 생성

            imageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 버튼이 클릭될 때 Choose 화면으로 전환
                    setContentPane((JPanel) chooseScreen);
                    revalidate();
                }
            });

            background.add(imageButton);
            background.setVisible(true);

            setContentPane(background);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Start start = new Start();
            start.setVisible(true);
        });
    }
}
