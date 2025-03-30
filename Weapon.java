interface Weapon {
    Bullet[] fire(int x, int y, int targetX, int targetY);
    void reload();
    int getCurrentAmmo();
    int getTotalAmmo();
    void addAmmo(int amount);

}
