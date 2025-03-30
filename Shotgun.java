import java.lang.Math;

class Shotgun extends AbstractWeapon {
    public Shotgun() {
        super(5, 20, 60); // 5 mermi, 20 toplam, 60 RPM
    }

    @Override
    public Bullet[] fire(int x, int y, int targetX, int targetY) {
        if (!canFire()) return null;
        lastFiredTime = System.currentTimeMillis();
        currentAmmo--;

        Bullet[] pellets = new Bullet[9];
        double baseAngle = Math.atan2(targetY - y, targetX - x);
        for (int i = 0; i < 9; i++) {
            double offset = Math.toRadians((i - 4) * 5); // -20° ila +20°
            double angle = baseAngle + offset;
            int tx = x + (int)(1000 * Math.cos(angle));
            int ty = y + (int)(1000 * Math.sin(angle));
            pellets[i] = new Bullet(x, y, tx, ty);
        }

        return pellets;
    }

    
}
