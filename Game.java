import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Top-Down Shooter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        GamePanel game = new GamePanel();
        frame.setContentPane(game);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.requestFocusInWindow(); // Klavye kontrolleri için odak ver
        game.startGame();            // Oyunu başlat
    }
}
