import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener {
    private JDialog pauseDialog;
    private Timer timer;
    private Player player;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private ArrayList<AcidProjectile> acidShots = AcidZombie.acidShots;
    private boolean paused = false;
    private boolean gameOver = false;

    private int waveNumber = 1;
    private boolean waveInProgress = true;

    private long rewardMessageTime = 0;
    private String rewardMessage = "";
    private ArrayList<AmmoDrop> ammoDrops = new ArrayList<>();

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        player = new Player(400, 300);
        spawnZombies();
        setupPauseDialog();
    }

    private void setupPauseDialog() {
        pauseDialog = new JDialog((Frame) null, "Menü", true);
        pauseDialog.setSize(200, 200);
        pauseDialog.setLayout(new GridLayout(4, 1));

        JButton resumeButton = new JButton("Devam Et");
        resumeButton.addActionListener(e -> {
            paused = false;
            pauseDialog.setVisible(false);
        });

        JButton saveButton = new JButton("Oyunu Kaydet");
        saveButton.addActionListener(e -> saveGame());

        JButton loadButton = new JButton("Oyunu Yükle");
        loadButton.addActionListener(e -> loadGame());

        JButton exitButton = new JButton("Çıkış");
        exitButton.addActionListener(e -> System.exit(0));

        pauseDialog.add(resumeButton);
        pauseDialog.add(saveButton);
        pauseDialog.add(loadButton);
        pauseDialog.add(exitButton);
        pauseDialog.setLocationRelativeTo(null);
    }

    public void startGame() {
        timer = new Timer(16, this);
        timer.start();
        requestFocusInWindow();
    }

    private void spawnZombies() {
        zombies.clear();
        int count = 3 + waveNumber * 2;
        for (int i = 0; i < count; i++) {
            double rand = Math.random();
            int x = (int)(Math.random() * 800);
            int y = (int)(Math.random() * 600);

            if (waveNumber == 1) {
                zombies.add(new NormalZombie(x, y));
            } else if (waveNumber == 2) {
                zombies.add(rand < 0.7 ? new NormalZombie(x, y) : new CrawlerZombie(x, y));
            } else {
                if (rand < 0.25) zombies.add(new NormalZombie(x, y));
                else if (rand < 0.5) zombies.add(new CrawlerZombie(x, y));
                else if (rand < 0.75) zombies.add(new TankZombie(x, y));
                else zombies.add(new AcidZombie(x, y));
            }
        }
        waveInProgress = true;
        if (waveNumber > 1 && Math.random() < 0.5) {
            int dropX = (int)(Math.random() * 800);
            int dropY = (int)(Math.random() * 600);
            ammoDrops.add(new AmmoDrop(dropX, dropY));
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (paused || gameOver) return;

        player.update();
        for (Bullet bullet : bullets) bullet.update();
        for (Zombie zombie : zombies) zombie.update(player.getX(), player.getY());
        for (AcidProjectile acid : acidShots) acid.update();

        checkCollisions();
        repaint();

        if (player.getHealth() <= 0 || (player.getTotalAmmo() <= 0 && player.getCurrentAmmo() == 0)) {
            gameOver = true;
            timer.stop();
            repaint();
            return;
        }

        if (waveInProgress && zombies.isEmpty()) {
            waveNumber++;
            waveInProgress = false;
            giveWeaponReward();
            Timer delay = new Timer(2000, evt -> spawnZombies());
            delay.setRepeats(false);
            delay.start();
        }
    }

    private void checkCollisions() {
        ArrayList<Zombie> zombiesToRemove = new ArrayList<>();
        ArrayList<AcidProjectile> acidToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            if (!bullet.isActive()) continue;

            for (Zombie zombie : zombies) {
                if (zombie.getBounds().intersects(bullet.getBounds())) {

                    if (zombie instanceof TankZombie tank) {
                        tank.takeDamage();
                        if (tank.isDead()) {
                            zombiesToRemove.add(zombie);
                            player.addScore(10);
                        }
                    } else if (zombie instanceof AcidZombie acid) {
                        acid.takeDamage();
                        if (acid.isDead()) {
                            acid.explode(player, zombies);
                            zombiesToRemove.add(zombie);
                            player.addScore(10);
                        }
                    } else {
                        zombiesToRemove.add(zombie);
                        player.addScore(10);
                    }

                    bullet.deactivate();
                    break;
                }
            }
        }
        ArrayList<AmmoDrop> dropsToRemove = new ArrayList<>();

    for (AmmoDrop drop : ammoDrops) {
        if (!drop.isCollected() && drop.getBounds().intersects(player.getBounds())) {
            drop.collect();
            player.addAmmo(20); // oyuncuya mermi ekle
            dropsToRemove.add(drop);
        }
    }

    ammoDrops.removeAll(dropsToRemove);

        for (Zombie zombie : zombies) {
            if (zombie.getBounds().intersects(player.getBounds())) {
                player.takeDamage(zombie.getDamage());
            }
        }

        for (AcidProjectile acid : acidShots) {
            if (acid.getBounds().intersects(player.getBounds())) {
                player.takeDamage(15);
                acid.deactivate();
                acidToRemove.add(acid);
            }
        }

        bullets.removeIf(b -> !b.isActive());
        zombies.removeAll(zombiesToRemove);
        acidShots.removeIf(a -> !a.isActive());
    }

    private void giveWeaponReward() {
        String reward = null;
        switch (waveNumber) {
            case 2 -> reward = "Rifle";
            case 4 -> reward = "Shotgun";
            case 6 -> reward = "Sniper";
            case 11 -> reward = "RocketLauncher";
        }

        if (reward != null) {
            player.setWeaponByName(reward);
            rewardMessage = "Yeni silah kazandın: " + reward;
            rewardMessageTime = System.currentTimeMillis();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (AmmoDrop drop : ammoDrops) drop.draw(g);

        player.draw(g);
        for (Bullet bullet : bullets) bullet.draw(g);
        for (Zombie zombie : zombies) zombie.draw(g);
        for (AcidProjectile acid : acidShots) acid.draw(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Silah: " + player.getWeaponName(), 10, 20);
        g.drawString("Mermi: " + player.getCurrentAmmo() + "/" + player.getTotalAmmo(), 10, 40);

        String hpText = "Can: " + player.getHealth();
        String scoreText = "Skor: " + player.getScore();
        int panelWidth = getWidth();
        FontMetrics fm = g.getFontMetrics();
        g.drawString(hpText, panelWidth - fm.stringWidth(hpText) - 10, 20);
        g.drawString(scoreText, panelWidth - fm.stringWidth(scoreText) - 10, 40);

        if (!rewardMessage.isEmpty() && System.currentTimeMillis() - rewardMessageTime < 3000) {
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setColor(Color.YELLOW);
            g.drawString(rewardMessage, getWidth() / 2 - 120, 70);
        }

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", getWidth() / 2 - 150, getHeight() / 2);
        }
    }

    @Override public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !gameOver) {
            paused = true;
            pauseDialog.setVisible(true);
            return;
        }
        if (!paused && !gameOver) {
            player.keyPressed(e);
        }
    }

    @Override public void keyReleased(KeyEvent e) { player.keyReleased(e); }
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    @Override public void mouseClicked(MouseEvent e) {
        Bullet[] newBullets = player.shoot(e.getX(), e.getY());
        for (Bullet b : newBullets) bullets.add(b);
    }

    public void saveGame() {
        try (PrintWriter writer = new PrintWriter("save.txt")) {
            writer.println(player.getX());
            writer.println(player.getY());
            writer.println(player.getHealth());
            writer.println(player.getScore());
            writer.println(player.getWeaponName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        try (Scanner sc = new Scanner(new File("save.txt"))) {
            int x = Integer.parseInt(sc.nextLine());
            int y = Integer.parseInt(sc.nextLine());
            int hp = Integer.parseInt(sc.nextLine());
            int score = Integer.parseInt(sc.nextLine());
            String weapon = sc.nextLine();

            player = new Player(x, y);
            player.setHealth(hp);
            player.setScore(score);
            player.setWeaponByName(weapon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

