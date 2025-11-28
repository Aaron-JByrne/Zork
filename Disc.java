public class Disc extends Item{
    private Ability ability;

    Disc(Ability ability){
        super(ability.getName(), ability.getDescription());
        this.ability = ability;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }


    public void use(){
        ZorkGame.getInstance().getPlayer().beginSelectAbilityIndex(this.ability);
    }
}
