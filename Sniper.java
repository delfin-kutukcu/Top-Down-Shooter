class Sniper extends AbstractWeapon {
    public Sniper() {
        super(5, 15, 30); // 5 mermi, 15 toplam, 30 RPM
    }

    @Override
    public Bullet[] fire(int x, int y, int targetX, int targetY) {
        if (!canFire()) return null;
        lastFiredTime = System.currentTimeMillis();
        currentAmmo--;

        return new Bullet[] { new PiercingBullet(x, y, targetX, targetY) };
    }

   
}
