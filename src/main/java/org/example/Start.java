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
            background.setLayout(new BorderLayout());

            BufferedImage buttonImg = ImageIO.read(new File(startbutton));
            ImageIcon buttonIcon = new ImageIcon(buttonImg);
            JButton imageButton = new JButton(buttonIcon);

            Dimension buttonSize = new Dimension(450, 135);
            imageButton.setPreferredSize(buttonSize);
            // 다른 컴포넌트를 추가할 수 있습니다.


            imageButton.setOpaque(false);
            imageButton.setContentAreaFilled(false);
            imageButton.setBorderPainted(false);
//            imageButton.setBounds(30, 170, 122, 30);
            int buttonX = 200;
            int buttonY = 300;
            imageButton.setBounds(buttonX, buttonY, buttonSize.width, buttonSize.height);

            // 이미지 버튼을 배경 패널에 추가
            background.add(imageButton);


            imageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 버튼이 클릭될 때 실행할 코드를 여기에 추가
                    JOptionPane.showMessageDialog(Start.this, "버튼이 클릭되었습니다.");
                }
            });

            setContentPane(background);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

