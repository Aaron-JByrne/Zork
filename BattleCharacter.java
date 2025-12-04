//Wrapper class for characters when they enter a battle
public class BattleCharacter {
    private Character character;
    private Ability[] moveSet;
    private boolean canStruggle = false;

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

    public void receiveAttack(Ability ability){
        Console.print(String.format("%s %s", character.getName(), ability.getActionText()));
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

    public void allowStruggle(){
        canStruggle = true;
    }

    public boolean canStruggle(){
        return canStruggle;
    }

    public Ability[] getMoveset(){
        return moveSet;
    }

    public void useAbility(Ability ability, BattleCharacter target){
        ability.use();
        Console.print(String.format("%s uses %s!", character.getName(), ability.getName()));
        if(ability.getDamage() < 0){
            this.receiveAttack(ability);
        }else {
            target.receiveAttack(ability);
        }
    }
}
