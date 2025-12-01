import java.util.List;

public class Player extends Character{
    private Ability abilityAwaitingIndex;

    public Player(String name, Room startingRoom, Item... items) {
        super(name, null, 1, items);
        this.currentRoom = startingRoom;
        this.xp = 0;
        this.setActiveAbilities(new Ability[]{null, null, null, null});
    }

    @Override
    public void loseBattle(){
        //lose battle
    }

    public void beginSelectAbilityIndex(Ability ability, Disc disc){
        abilityAwaitingIndex = ability;
        ZorkGame.getController().selectAbilityIndexRequest(ability);
        inventory.removeItem(disc);
    }

    public void setAbilityByIndex(int index){
        selectedAbilities[index] = abilityAwaitingIndex;
        abilityAwaitingIndex = null;
        Console.print("The disc vanishes");
    }




//    public String getName() {
//        return name;
//    }

//    public Inventory getInventory(){
//        return inventory;
//    }

//    public Room getCurrentRoom() {
//        return currentRoom;
//    }

//    public void setCurrentRoom(Room room) {
//        this.currentRoom = room;
//    }

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

//    public Ability[] getActiveAbilities(){
//        return selectedAbilities;
//    }

//    public void setActiveAbilities(Ability[] abilities){
//        this.selectedAbilities = abilities;
//    }

//    public void displayStatus(){
//        Console.print(String.format("%s : %d HP", name, health));
//    }

//    public Ability getAbility(int index){
//        return abilities.get(index);
//    }

//    public void useAbility(Ability ability, Character target){
//        Console.print(String.format("%s uses %s",this.name, ability.getName()));
//        target.recieveAttack(ability);
//    }

//    public void recieveAttack(Ability ability){
//        Console.print(String.format("%s recieves %d dmg", this.name, ability.getDamage()));
//        this.health -= ability.getDamage();
//    }

//    public int getHealth(){
//        return health;
//    }

//    public int getLevel(){
//        return level;
//    }
}
