public class Ability {
    private String name;
    private String description;
    private int damage;

    Ability(String name, String description, int damage){
        this.name = name;
        this.description = description;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public int getDamage(){
        return damage;
    }
}