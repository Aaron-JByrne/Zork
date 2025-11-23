import java.util.ArrayList;

public class NPC extends Character{
    private String name;
    private Room currentRoom;
    private Inventory inventory;
    private ArrayList<Ability> abilities = new ArrayList<>();
    private int health;
    private int level;

    NPC(String name, Room currentRoom, ArrayList<Item> items, int level){
        super(name, currentRoom, items, level);
    }
    //what am i doing
}
