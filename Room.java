import java.io.Serializable;
import java.util.*;

public class Room implements Serializable {
    private String roomTitle;
    private Map<Dir, Room> exits;// Map direction to neighboring Room
    private Inventory inventory;
    private List<Character> characters = new ArrayList<>();
    private String description;
    private int x;
    private int y;
    private boolean playerHasBeen;

//    public Room(String title, String description, int x, int y){
//        this.roomTitle = title;
//        exits = new HashMap<>();
//        this.description = description;
//        this.inventory = new Inventory(this.roomTitle);
//        this.x = x;
//        this.y = y;
//        this.playerHasBeen = false;
//    }

    public Room(String title, String description, int x, int y, Item... items){
        this.roomTitle = title;
        exits = new HashMap<>();
        this.description = description;
        this.x = x;
        this.y = y;
        this.inventory = new Inventory(this.roomTitle, items);
        this.playerHasBeen = false;
    }

    public void describe(){
        Console.print(this.description);
        Console.print("Exits: " + this.getExitString());
        for(Character character : characters){
            Console.print(character.getName() + " is here.");
        }
    }

    public List<Character> getCharacters(){
        return characters;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
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
            if(character.getName().equalsIgnoreCase(characterName)){
                return true;
            }
        }
        return false;
    }

    public Character getCharacter(String characterName){
        for(Character character : characters){
            if(character.getName().equalsIgnoreCase(characterName)){
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
    }

    public boolean hasPlayerBeen(){
        return playerHasBeen;
    }

}