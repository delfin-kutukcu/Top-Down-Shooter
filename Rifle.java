import java.lang.Math;

class Rifle extends AbstractWeapon {
    public Rifle() {
        super(30, 90, 600); // 30 mermi, 90 toplam, 600 RPM
    }

    @Override
    public Bullet[] fire(int x, int y, int targetX, int targetY) {
        if (!canFire()) return null;
        lastFiredTime = System.currentTimeMillis();
        currentAmmo--;

        double angle = Math.atan2(targetY - y, targetX - x);
        double spread = Math.toRadians((Math.random() - 0.5) * 30); // -15° ila +15°
        double finalAngle = angle + spread;

        int newTargetX = x + (int) (1000 * Math.cos(finalAngle));
        int newTargetY = y + (int) (1000 * Math.sin(finalAngle));

        return new Bullet[] { new Bullet(x, y, newTargetX, newTargetY) };
    }

    
}
