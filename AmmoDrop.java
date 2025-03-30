import java.awt.*;

public class AmmoDrop {
    private int x, y;
    private final int SIZE = 20;
    private boolean collected = false;

    public AmmoDrop(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        if (!collected) {
            g.setColor(Color.YELLOW);
            g.fillRect(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
            g.setColor(Color.BLACK);
            g.drawString("A", x - 4, y + 5); // "A" harfi: Ammo!
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
    }
}
