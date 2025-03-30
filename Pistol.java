import java.awt.*;

class Pistol extends AbstractWeapon {
    public Pistol() {
        super(12, Integer.MAX_VALUE, 120);
    }

    public Bullet[] fire(int x, int y, int targetX, int targetY) {
        if (!canFire()) return null;
        currentAmmo--;
        lastFiredTime = System.currentTimeMillis();
        return new Bullet[]{ new Bullet(x, y, targetX, targetY) };
    }
}
