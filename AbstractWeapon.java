abstract class AbstractWeapon implements Weapon {
    protected int magazineSize;
    protected int currentAmmo;
    protected int totalAmmo;
    protected long lastFiredTime = 0;
    protected int fireRateMs;

    public AbstractWeapon(int magazineSize, int totalAmmo, int fireRateRpm) {
        this.magazineSize = magazineSize;
        this.currentAmmo = magazineSize;
        this.totalAmmo = totalAmmo;
        this.fireRateMs = 60000 / fireRateRpm;
    }

    protected boolean canFire() {
        return currentAmmo > 0 && System.currentTimeMillis() - lastFiredTime >= fireRateMs;
    }

    public void reload() {
        int needed = magazineSize - currentAmmo;
        int reloadAmount = Math.min(needed, totalAmmo);
        currentAmmo += reloadAmount;
        totalAmmo -= reloadAmount;
    }
    @Override
    public void addAmmo(int amount) {
        totalAmmo += amount;
    }

    public int getCurrentAmmo() { return currentAmmo; }
    public int getTotalAmmo() { return totalAmmo; }
}
