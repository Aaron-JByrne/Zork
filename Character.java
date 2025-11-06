import java.util.ArrayList;

public class Character {
    private String name;
    private Room currentRoom;
    private Inventory inventory;
    //private ArrayList<Item> inventory;

    public Character(String name, Room startingRoom) {
        this.name = name;
        this.currentRoom = startingRoom;
        this.inventory = new Inventory("Inventory");
    }

    public Character(String name, Room startingRoom, ArrayList<Item> items) {
        //this(name, startingRoom);
        this.name = name;
        this.currentRoom = startingRoom;
        this.inventory = new Inventory("Inventory", items);
        System.out.println("Character created with inventory");
        System.out.println(items);
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory(){
        return inventory;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public void move(String direction) {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            currentRoom = nextRoom;
            System.out.println("You moved to: " + currentRoom.getTitle());
        } else {
            System.out.println("You can't go that way!");
        }
    }
}
