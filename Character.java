import java.util.ArrayList;
import java.util.List;

public class Character {
    private String name;
    private Room currentRoom;
    private Inventory inventory;
    private List<Ability> abilities = new ArrayList<>();
    private Ability[] selectedAbilities = new Ability[4];
    private int health;
    private int level;

    public Character(String name, Room startingRoom) {
        this.name = name;
        this.currentRoom = startingRoom;
        startingRoom.addCharacter(this);
        this.inventory = new Inventory("Inventory");
        this.health = 100;
        this.level = 1;
    }

    public Character(String name, Room startingRoom, List<Item> items) {
        //this(name, startingRoom);
        this.name = name;
        this.currentRoom = startingRoom;
        startingRoom.addCharacter(this);
        this.inventory = new Inventory("Inventory", items);
        this.health = 100;
        this.level = 1;
        //System.out.println(items);
    }

    public Character(String name, Room startingRoom, List<Item> items, int level){
        this.name = name;
        this.currentRoom = startingRoom;
        startingRoom.addCharacter(this);
        this.inventory = new Inventory("Inventory", items);
        this.health = 100;
        this.level = level;
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
            //System.out.println("You moved to: " + currentRoom.getTitle());
            Console.print("You moved to: " + currentRoom.getTitle());
        } else {
            //System.out.println("You can't go that way!");
            Console.print("You can't go that way!");
        }
    }

    public void addAbility(Ability ability){
        abilities.add(ability);
    }

    public List<Ability> getAbilities(){
        return abilities;
    }

    public void displayAbilities(){
        int i=1;
        for(Ability ability : abilities){
            System.out.printf("%d. %s\n",i++,ability.getName());
        }
    }

    public Ability[] getActiveAbilities(){
        return selectedAbilities;
    }

    public void setActiveAbilities(Ability[] abilities){
        this.selectedAbilities = abilities;
    }

    public void displayStatus(){
        Console.print(String.format("%s : %d HP", name, health));
    }

    public Ability getAbility(int index){
        return abilities.get(index);
    }

    public void useAbility(Ability ability, Character target){
        Console.print(String.format("%s uses %s",this.name, ability.getName()));
        target.recieveAttack(ability);
    }

    public void recieveAttack(Ability ability){
        Console.print(String.format("%s recieves %d dmg", this.name, ability.getDamage()));
        this.health -= ability.getDamage();
    }

    public int getHealth(){
        return health;
    }

    public int getLevel(){
        return level;
    }
}
