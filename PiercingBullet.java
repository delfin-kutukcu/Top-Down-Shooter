import java.awt.Graphics;
import java.awt.Color;

class PiercingBullet extends Bullet {
    public PiercingBullet(int x, int y, int targetX, int targetY) {
        super(x, y, targetX, targetY);
    }

    @Override
    public boolean isPiercing() {
        return true;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillOval((int)getX() - 6, (int)getY() - 6, 12, 12);
    }
}
