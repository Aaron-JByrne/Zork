import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Room {
    private String roomTitle;
    private String description;
    private Map<String, Room> exits;// Map direction to neighboring Room
    private Inventory inventory;

    public Room(String title, String description) {
        this.description = description;
        this.roomTitle = title;
        exits = new HashMap<>();
        this.inventory = new Inventory(this.roomTitle);
    }

    public Room(String title, String description, Inventory defaultInventory) {
        this.description = description;
        this.roomTitle = title;
        exits = new HashMap<>();
        this.inventory = defaultInventory;
        System.out.println("Room created with inventory");
    }

    public String getDescription() {
        return description;
    }

    public void displayInventory(){
        inventory.displayInventory();
    }

    public Inventory getInventory(){
        return inventory;
    }

    public String getTitle() {
        return roomTitle;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getExitString() {
        StringBuilder sb = new StringBuilder();
        for (String direction : exits.keySet()) {
            sb.append(direction).append(" ");
        }
        return sb.toString().trim();
    }

    public String getLongDescription() {
        return "You are " + description + ".\nExits: " + getExitString();
    }
}
