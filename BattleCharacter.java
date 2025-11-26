import java.util.Arrays;

//Wrapper class for characters when they enter a battle
public class BattleCharacter {
    Character character;
    Ability[] moveSet;

    BattleCharacter(Character character){
        this.character = character;
        this.moveSet = character.getActiveAbilities();
    }

    public Character getCharacter(){
        return character;
    }


    public boolean[] AbilityUsable(){
//        System.out.println("AbilityUsable() being called");
        boolean[] usable = new boolean[4];
        int i = 0;
        for(int num : character.getUses()){
            if(num>0){
                usable[i++] = true;
            }
        }
//        System.out.println(Arrays.toString(usable));
        return usable;
    }

    public void recieveAttack(Ability ability){
        Console.print(String.format("%s recieves %d dmg", character.getName(), ability.getDamage()));
        character.changeHealth(-ability.getDamage());
    }

    public boolean canAttack(){
//        System.out.println("canAttack() being called");
//        System.out.println(Arrays.toString(AbilityUsable()));
        return AbilityUsable()[0] || AbilityUsable()[1] || AbilityUsable()[2] || AbilityUsable()[3];
    }

    public Ability getAbility(int index){
        return moveSet[index];
    }

    public void useAbility(Ability ability, BattleCharacter target){
        ability.use();
        target.recieveAttack(ability);

    }
}
