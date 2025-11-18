import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Room implements Serializable {
    private String roomTitle;
    private Map<String, Room> exits;// Map direction to neighboring Room
    private Inventory inventory;
    private ArrayList<Character> characters = new ArrayList<>();
    private String description;

    public Room(String title, String description) {
        this.roomTitle = title;
        exits = new HashMap<>();
        this.description = description;
        this.inventory = new Inventory(this.roomTitle);
    }

    public Room(String title, String description, Inventory defaultInventory) {
        this.roomTitle = title;
        exits = new HashMap<>();
        this.description = description;
        this.inventory = defaultInventory;
        //System.out.println("Room created with inventory");
    }

    public void describe(){
        Console.print(this.description);
        Console.print("Exits: " + this.getExitString());
        for(Character character : characters){
            Console.print(character.getName() + " is here.");
        }
    }

    public void displayInventory(){
        inventory.displayInventory();
    }

    public Inventory getInventory(){
        return inventory;
    }

    public void addCharacter(Character character){
        characters.add(character);
    }

    public boolean hasCharacter(String characterName){
        for(Character character : characters){
            if(character.getName().equals(characterName)){
                return true;
            }
        }
        return false;
    }

    public Character getCharacter(String characterName){
        for(Character character : characters){
            if(character.getName().equals(characterName)){
                return character;
            }
        }
        return null;
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

    public void removeCharacter(Character character){
        characters.remove(character);
        if (character.getHealth() <= 0){
            Inventory inventory = character.getInventory();
            for(int i = inventory.getItems().size()-1; i >= 0; i--){
                inventory.sendItem(this.inventory, inventory.getItems().get(i));
            }
        }
    }

}
