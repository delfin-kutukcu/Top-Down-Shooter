
// import src.weapons.Pistol;
// import src.weapons.Rifle;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    private int x, y, speed = 5;
    private boolean up, down, left, right;
    private Weapon weapon;
    private int health = 100;
    private int score = 0;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.health = 100;
        this.weapon = new Pistol(); // Başlangıç silahı
    }

    public void update() {
        if (up) y -= speed;
        if (down) y += speed;
        if (left) x -= speed;
        if (right) x += speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE); // ya da MAGENTA, WHITE, vs.

        g.fillOval(x - 15, y - 15, 30, 30);
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 40); // oyuncunun boyutuna göre ayarla
    }
    
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = true;
            case KeyEvent.VK_S -> down = true;
            case KeyEvent.VK_A -> left = true;
            case KeyEvent.VK_D -> right = true;
            case KeyEvent.VK_1 -> weapon = new Pistol();
            case KeyEvent.VK_2 -> weapon = new Rifle();
            case KeyEvent.VK_3 -> weapon = new Shotgun();
            case KeyEvent.VK_4 -> weapon = new Sniper();
            case KeyEvent.VK_5 -> weapon = new RocketLauncher();
            case KeyEvent.VK_R -> weapon.reload(); // 💥 R ile şarjör doldurma!
        }
    }
    
    public void reloadWeapon() {
        weapon.reload();
    }
    
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = false;
            case KeyEvent.VK_S -> down = false;
            case KeyEvent.VK_A -> left = false;
            case KeyEvent.VK_D -> right = false;
        }
    }

    public Bullet[] shoot(int targetX, int targetY) {
        Bullet[] result = weapon.fire(x, y, targetX, targetY);
        return result != null ? result : new Bullet[0];
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }


    public int getHealth() { return health; }
    public int getScore() { return score; }
    public void addScore(int amount) { score += amount; }
    public String getWeaponName() {
        return weapon.getClass().getSimpleName();
    }

    public int getCurrentAmmo() { return weapon.getCurrentAmmo(); }
    public int getTotalAmmo() { return weapon.getTotalAmmo(); }
    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }
    
    public void addAmmo(int amount) {
        weapon.addAmmo(amount); // totalAmmo Weapon sınıfında tutuluyor
    }
    
    public void setHealth(int h) { health = h; }
    public void setScore(int s) { score = s; }
    public void setWeaponByName(String name) {
        switch (name) {
            case "Pistol" -> weapon = new Pistol();
            case "Rifle" -> weapon = new Rifle();
            case "Shotgun" -> weapon = new Shotgun();
            case "Sniper" -> weapon = new Sniper();
            case "RocketLauncher" -> weapon = new RocketLauncher();
            default -> weapon = new Pistol(); // fallback
        }
    }
    
}
