import java.awt.*;

public class NormalZombie implements Zombie {
    private double x, y;
    private final int SIZE = 30;
    private final double speed = 1.0;
    private int health = 2;

    public NormalZombie(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(int playerX, int playerY) {
        double angle = Math.atan2(playerY - y, playerX - x);
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int)(x - SIZE / 2), (int)(y - SIZE / 2), SIZE, SIZE);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)(x - SIZE / 2), (int)(y - SIZE / 2), SIZE, SIZE);
    }

    public void takeDamage() {
        health--;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getDamage() {
        return 10;
    }
}
