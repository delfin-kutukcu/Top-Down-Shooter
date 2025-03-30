import java.awt.*;

class ExplosiveBullet extends Bullet {
    private final int explosionRadius = 50;

    public ExplosiveBullet(int x, int y, int targetX, int targetY) {
        super(x, y, targetX, targetY);
    }

    @Override
    public boolean isExplosive() {
        return true;
    }

    public int getExplosionRadius() {
        return explosionRadius;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval((int)getX() - 6, (int)getY() - 6, 12, 12);
    }
}
