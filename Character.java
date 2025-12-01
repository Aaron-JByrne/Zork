import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Character implements Nameable{
    protected String name;
    protected Room currentRoom;
    protected Inventory inventory;
    protected Ability[] selectedAbilities = new Ability[4];
    protected int health;
    protected int level;
    protected int xp;

    public Character(String name, Room startingRoom) {
        this.name = name;
        this.currentRoom = startingRoom;
        startingRoom.addCharacter(this);
        this.inventory = new Inventory("Inventory");
        this.health = 100;
        this.level = 1;
    }

    public Character(String name, Room startingRoom, int level, Item... items){
        this.name = name;
        this.currentRoom = startingRoom;
        if(startingRoom != null) {
            startingRoom.addCharacter(this);
        }
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

    public int[] getUses(){
        int[] uses = new int[4];
        int i=0;
        for(Ability ability : selectedAbilities){
            if(ability != null){
//                System.out.printf("%s has %d uses left\n",ability.getName(),ability.getUses());
                uses[i++] = ability.getCurrentUses();
            }
            else{
                uses[i++] = 0;
            }
        }
//        System.out.println(Arrays.toString(uses));
        return uses;
    }

//    public void move(String direction) {
//        Room nextRoom = currentRoom.getExit(direction);
//        if (nextRoom != null) {
//            currentRoom = nextRoom;
//            //System.out.println("You moved to: " + currentRoom.getTitle());
//            Console.print("You moved to: " + currentRoom.getTitle());
//        } else {
//            //System.out.println("You can't go that way!");
//            Console.print("You can't go that way!");
//        }
//    }

//    public void addAbility(Ability ability){
//        abilities.add(ability);
//    }

//    public List<Ability> getAbilities(){
//        return abilities;
//    }

//    public void displayAbilities(){
//        int i=1;
//        for(Ability ability : abilities){
//            System.out.printf("%d. %s\n",i++,ability.getName());
//        }
//    }

    public Ability[] getActiveAbilities(){
        return selectedAbilities;
    }

    public void setActiveAbilities(Ability[] abilities){
        this.selectedAbilities = abilities;
    }

    public void displayStatus(){
        Console.print(String.format("%s : %d HP", name, health));
    }

//    public void useAbility(Ability ability, Character target){
//        Console.print(String.format("%s uses %s",this.name, ability.getName()));
//        ability.use();
//        target.recieveAttack(ability);
//    }

    public int getHealth(){
        return health;
    }

    public void changeHealth(int amount){
        health += amount;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getLevel(){
        return level;
    }

    public int getXP(){
        return xp;
    }

    public void addXP(int xp) {
        this.xp += xp;
        if (xp >= 100) {
            levelUp();
            this.xp -= 100;
        }
    }

    public void levelUp(){
        this.level++;
    }

    public void loseBattle(){
        inventory.sendAllItems(currentRoom.getInventory());
        currentRoom.removeCharacter(this);
    }
}
