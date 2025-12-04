import java.io.Serializable;

public class Ability implements Serializable {
    private String name;
    private String description;
    private int damage;
    private int uses;
    private int initialUses;
    private String actionText;
    private String abilityInfo;

    public Ability(String name, String description, int damage, int uses){
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.uses = uses;

        initialUses = uses;
        if(damage < 0){
            actionText = String.format("heals %d hp", -damage);
            abilityInfo = actionText;
        }else{
            actionText = String.format("receives %d damage", damage);
            abilityInfo = String.format("deals %d damage", damage);
        }
    }

    public Ability(Ability otherAbility){
        //Constructor for cloning an instance of an ability so uses arent global
        this(otherAbility.name, otherAbility.description, otherAbility.damage, otherAbility.initialUses);
    }


    public String getAbilityInfo(){
        return abilityInfo;
    }

    public String getActionText(){
        return actionText;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDamage(){
        return damage;
    }

    public int getCurrentUses(){
        return uses;
    }

    public void use(){
        uses--;
    }

    public int getInitialUses(){
        return initialUses;
    }

    public void resetUses(){
        uses = initialUses;
    }
}