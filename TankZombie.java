import java.awt.*;

public class TankZombie implements Zombie {
    private double x, y;
    private final int SIZE = 40;
    private final double speed = 0.4;
    private int health = 5;
    
    public TankZombie(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(int playerX, int playerY) {
        double angle = Math.atan2(playerY - y, playerX - x);
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int)(x - SIZE/2), (int)(y - SIZE/2), SIZE, SIZE);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)(x - SIZE/2), (int)(y - SIZE/2), SIZE, SIZE);
    }

    public int getDamage() {
        return 20;
    }

    public void takeDamage() {
        health--;
    }

    public boolean isDead() {
        return health <= 0;
    }
}