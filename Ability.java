public class Ability {
    private String name;
    private String description;
    private int damage;
    private int uses;
    private int initialUses;

    Ability(String name, String description, int damage, int uses){
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.uses = uses;
        this.initialUses = uses;
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