class RocketLauncher extends AbstractWeapon {
    public RocketLauncher() {
        super(1, 5, 10); // 1 roket, 5 toplam, 10 RPM
    }

    @Override
    public Bullet[] fire(int x, int y, int targetX, int targetY) {
        if (!canFire()) return null;
        lastFiredTime = System.currentTimeMillis();
        currentAmmo--;

        return new Bullet[] { new ExplosiveBullet(x, y, targetX, targetY) };
    }

    
}
