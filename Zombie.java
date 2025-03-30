import java.awt.*;

public interface Zombie {
    void update(int playerX, int playerY);
    void draw(Graphics g);
    Rectangle getBounds();
    int getDamage();         // <-- Gerekli!
    void takeDamage();
    boolean isDead();        // <-- Gerekli!
}
