import java.awt.*;
import java.util.ArrayList;

public class AcidZombie implements Zombie {
    private double x, y;
    private final int SIZE = 30;
    private final double speed = 1.0;
    private boolean dead = false;

    public static ArrayList<AcidProjectile> acidShots = new ArrayList<>();

    public AcidZombie(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(int playerX, int playerY) {
        double angle = Math.atan2(playerY - y, playerX - x);
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;

        // Her 100 frame'de bir asit atabilir (örnek)
        if (Math.random() < 0.01) {
            acidShots.add(new AcidProjectile(x, y, playerX, playerY));
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval((int)(x - SIZE / 2), (int)(y - SIZE / 2), SIZE, SIZE);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)(x - SIZE / 2), (int)(y - SIZE / 2), SIZE, SIZE);
    }

    public int getDamage() {
        return 10;
    }

    public void takeDamage() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public void explode(Player player, ArrayList<Zombie> zombies) {
        int radius = 60;
        double centerX = x;
        double centerY = y;

        for (Zombie z : zombies) {
            if (z == this) continue;
            double dist = Math.hypot(centerX - z.getBounds().getCenterX(), centerY - z.getBounds().getCenterY());
            if (dist <= radius) {
                z.takeDamage();
            }
        }

        double playerDist = Math.hypot(centerX - player.getX(), centerY - player.getY());
        if (playerDist <= radius) {
            player.takeDamage(15);
        }
    }
}
