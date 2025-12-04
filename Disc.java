public class Disc extends Item{
    private Ability ability;

    Disc(Ability ability){
        super(ability.getName(), ability.getDescription());
        this.ability = ability;
    }

    public String readDisc(){
        return String.format("%s \uD83D\uDCBF :\n%s\n%d use(s)\n%s is etched onto the back of the disc",ability.getName(),ability.getAbilityInfo(),ability.getInitialUses(), ability.getDescription());
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
        Ability abilityCopy = new Ability(this.ability);
        ZorkGame.getInstance().getPlayer().beginSelectAbilityIndex(abilityCopy, this);
    }
}
