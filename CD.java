public class CD extends Item{
    private Ability ability;

    CD(Ability ability){
        super(String.format("%sCD",ability.getName()), ability.getDescription());
        this.ability = ability;
    }
}
