import java.util.ArrayList;
import java.util.List;

public class Character {
    protected String name;
    protected Room currentRoom;
    protected Inventory inventory;
    protected List<Ability> abilities = new ArrayList<>();
    protected Ability[] selectedAbilities = new Ability[4];
    protected int health;
    protected int level;

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

    public int[] getUses(){
        int[] uses = new int[4];
        int i=0;
        for(Ability ability : selectedAbilities){
            if(ability != null){
                uses[i++] = ability.getUses();
            }
            else{
                uses[i++] = 0;
            }
        }
        return uses;
    }

    public boolean[] AbilityUsable(){
        boolean[] usable = new boolean[4];
        int i = 0;
        for(int num : getUses()){
            if(num>0){
                usable[i++] = true;
            }
        }
        return usable;
    }

    public boolean canAttack(){
        return AbilityUsable()[0] && AbilityUsable()[1] && AbilityUsable()[2] && AbilityUsable()[3];
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
        ability.use();
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
