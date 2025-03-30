import java.awt.*;
import java.awt.geom.AffineTransform;
class Bullet {
    private double x, y, dx, dy;
    private final double speed = 10;
    private final int SIZE = 10;
    private boolean active = true;

    public boolean isExplosive() { return false; }
    public boolean isPiercing() { return false; }

    public double getX() { return x; }
    public double getY() { return y; }

    // Orijinal constructor
    public Bullet(int x, int y, int targetX, int targetY) {
        this.x = x;
        this.y = y;
        double angle = Math.atan2(targetY - y, targetX - x);
        dx = Math.cos(angle) * speed;
        dy = Math.sin(angle) * speed;
    }

    // ➕ Yeni constructor: doğrudan angle ile
    public Bullet(int x, int y, double angle) {
        this.x = x;
        this.y = y;
        dx = Math.cos(angle) * speed;
        dy = Math.sin(angle) * speed;
    }

    public void update() {
        x += dx;
        y += dy;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval((int)x - SIZE / 2, (int)y - SIZE / 2, SIZE, SIZE);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x - SIZE / 2, (int)y - SIZE / 2, SIZE, SIZE);
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }
}
