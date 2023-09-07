package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Start frame = new Start();
            frame.setVisible(true);
        });
    }
}
