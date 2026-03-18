import java.util.*;
import java.io.*;

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 3);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<String, Integer> data) {
        inventory = data;
    }

    public void display() {
        System.out.println("Remaining Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + ": " + inventory.get(key));
        }
    }
}

class FilePersistenceService {
    public void saveInventory(RoomInventory inventory, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : inventory.getInventory().entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return;

        Map<String, Integer> data = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                data.put(parts[0], Integer.parseInt(parts[1]));
            }
            inventory.setInventory(data);
        } catch (Exception e) {
            System.out.println("Error loading inventory.");
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Data Persistence & System Recovery");

        String filePath = "inventory.txt";

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService service = new FilePersistenceService();

        service.saveInventory(inventory, filePath);

        RoomInventory recoveredInventory = new RoomInventory();
        service.loadInventory(recoveredInventory, filePath);

        recoveredInventory.display();
    }
}