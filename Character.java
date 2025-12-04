import java.io.Serializable;

public class Character implements Nameable, Serializable {
    protected String name;
    protected Room currentRoom;
    protected Inventory inventory;
    protected Ability[] selectedAbilities = new Ability[4];
    protected int health;
    protected int level;
    protected int xp;

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
                uses[i++] = ability.getCurrentUses();
            }
            else{
                uses[i++] = 0;
            }
        }
        return uses;
    }

    public void addXP(int xp){
        return;
    }

    public Ability[] getActiveAbilities(){
        return selectedAbilities;
    }

    public boolean canAttack(){
        return new BattleCharacter(this).canAttack();
    }

    public void setActiveAbilities(Ability[] abilities){
        this.selectedAbilities = abilities;
    }

    public int getHealth(){
        return health;
    }

    public void changeHealth(int amount){
        health += amount;
        health = (health > 100) ? 100 : health;
        health = (health < 0) ? 0 : health;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getLevel(){
        return level;
    }

    public void loseBattle(){
        inventory.sendAllItems(currentRoom.getInventory());
        currentRoom.removeCharacter(this);
    }
}
