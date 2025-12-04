import java.util.ArrayList;
import java.util.List;

public class Player extends Character{
    private Ability abilityAwaitingIndex;
    private List<Room> roomsEntered;

    public Player(String name, Room startingRoom, Item... items) {
        super(name, null, 1, items);
        this.currentRoom = startingRoom;
        this.roomsEntered = new ArrayList<>();
        roomsEntered.add(startingRoom);
        this.xp = 0;
        this.setActiveAbilities(new Ability[]{null, null, null, null});
    }

    @Override
    public void setCurrentRoom(Room room){
        this.currentRoom = room;
        roomsEntered.add(room);
    }

    public boolean hasBeenTo(Room room){
        return roomsEntered.contains(room);
    }

    @Override
    public void loseBattle(){
        ZorkGame.getInstance().onPlayerDeath();
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

    public void rest(){
        for(int i=0;i<4;i++){
            if(selectedAbilities[i] != null) {
                selectedAbilities[i].resetUses();
            }
        }
        setHealth(100);
    }

    public int getXP(){
        return xp;
    }

    public void addXP(int xp) {
        this.xp += xp;
        while(this.xp >= 100) {
            levelUp();
            this.xp -= 100;
        }
    }

    public void resetTo(Player newPlayer){
        this.currentRoom = newPlayer.currentRoom;
        this.xp = newPlayer.xp;
        this.inventory = newPlayer.inventory;
        this.selectedAbilities = newPlayer.selectedAbilities;
        this.health = newPlayer.health;
        this.level = newPlayer.level;
    }

    public void levelUp(){
        this.level++;
    }

}
