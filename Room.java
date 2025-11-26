import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Room implements Serializable {
    private String roomTitle;
    private Map<Dir, Room> exits;// Map direction to neighboring Room
    private Inventory inventory;
    private ArrayList<Character> characters = new ArrayList<>();
    private String description;
    private int x;
    private int y;

    public Room(String title, String description, int x, int y){
        this.roomTitle = title;
        exits = new HashMap<>();
        this.description = description;
        this.inventory = new Inventory(this.roomTitle);
        this.x = x;
        this.y = y;
    }

    public Room(String title, String description, int x, int y, Item... item ){
        this.roomTitle = title;
        exits = new HashMap<>();
        this.description = description;
        List<Item> defaultInventory = new ArrayList<>();
        for(Item i : item){
            defaultInventory.add(i);
        }
        this.inventory = new Inventory(this.roomTitle, defaultInventory);
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

    public void setExit(Dir direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Room getExit(Dir direction) {
        return exits.get(direction);
    }

    public String getExitString() {
        StringBuilder sb = new StringBuilder();
        for (Dir direction : exits.keySet()) {
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