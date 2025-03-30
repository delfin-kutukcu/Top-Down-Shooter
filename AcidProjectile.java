import java.awt.*;

public class AcidProjectile {
    private double x, y, dx, dy;
    private final int SIZE = 10;
    private final double speed = 4.0;
    private boolean active = true;

    public AcidProjectile(double startX, double startY, double targetX, double targetY) {
        this.x = startX;
        this.y = startY;

        double angle = Math.atan2(targetY - startY, targetX - startX);
        dx = Math.cos(angle) * speed;
        dy = Math.sin(angle) * speed;
    }

    public void update() {
        x += dx;
        y += dy;

        // Ekran dışına çıktıysa pasifleştir
        if (x < 0 || x > 800 || y < 0 || y > 600) {
            active = false;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillOval((int)(x - SIZE / 2), (int)(y - SIZE / 2), SIZE, SIZE);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)(x - SIZE / 2), (int)(y - SIZE / 2), SIZE, SIZE);
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }
}
