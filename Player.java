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
}
