
import java.io.*;

class FileManager {
    private static final String SAVE_FILE = "savegame.txt";

    public static void saveGame(Player player) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            writer.write(player.getX() + "\n");
            writer.write(player.getY() + "\n");
            writer.write(player.getHealth() + "\n");
            writer.write(player.getScore() + "\n");
            writer.write(player.getWeaponName() + "\n");
            System.out.println("Oyun kaydedildi.");
        } catch (IOException e) {
            System.out.println("Kaydetme hatası: " + e.getMessage());
        }
    }

    public static void loadGame(Player player) {
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            int x = Integer.parseInt(reader.readLine());
            int y = Integer.parseInt(reader.readLine());
            int health = Integer.parseInt(reader.readLine());
            int score = Integer.parseInt(reader.readLine());
            String weaponName = reader.readLine();
    
            player.setX(x);
            player.setY(y);
            player.setHealth(health);
            player.setScore(score);
            player.setWeaponByName(weaponName);
    
            System.out.println("Oyun yüklendi.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Yükleme hatası: " + e.getMessage());
        }
    }
}
