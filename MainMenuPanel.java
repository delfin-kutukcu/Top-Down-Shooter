import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(JFrame frame) {
        setLayout(new GridLayout(3, 1));
        setBackground(Color.DARK_GRAY);

        JButton startButton = new JButton("Yeni Oyun");
        JButton loadButton = new JButton("Oyunu Yükle");
        JButton exitButton = new JButton("Çıkış");

        startButton.addActionListener(e -> {
            GamePanel game = new GamePanel();
            frame.setContentPane(game);
            frame.revalidate();
            game.startGame();
        });

        loadButton.addActionListener(e -> {
            GamePanel game = new GamePanel();
            game.loadGame(); // File I/O'dan yükle
            frame.setContentPane(game);
            frame.revalidate();
            game.startGame();
        });

        exitButton.addActionListener(e -> System.exit(0));

        add(startButton);
        add(loadButton);
        add(exitButton);
    }
}
