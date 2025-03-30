import java.awt.*; // ⬅️ Bu satır tüm eksik sınıfları çözer

public class CrawlerZombie implements Zombie {
    private double x, y;
    private final int SIZE = 25;
    private double speed = 1.2;
    private boolean hasJumped = false;
    private boolean dead = false;

    public CrawlerZombie(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(int playerX, int playerY) {
        double dist = Math.hypot(playerX - x, playerY - y);

        if (!hasJumped && dist < 120) {
            speed = 4.0; // Zıplama etkisi
            hasJumped = true;
        }

        double angle = Math.atan2(playerY - y, playerX - x);
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval((int)(x - SIZE/2), (int)(y - SIZE/2), SIZE, SIZE);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)(x - SIZE/2), (int)(y - SIZE/2), SIZE, SIZE);
    }

    public int getDamage() {
        return 15;
    }

    public void takeDamage() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }
}
