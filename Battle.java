

public class Battle {
    private Character user;
    private Character npc;
    private boolean userPriority; //trash variable name
    private boolean isOver;


    public Battle(Character user, Character npc) {
        this.user = user;
        this.npc = npc;
        this.isOver = false;
        userPriority = (user.getLevel() >= npc.getLevel());
        System.out.println(userPriority);
    }

    public void end(){

    }

    public Ability getNPCAbility(){
        return npc.getAbility(0);
    }

    public void performTurn(String abilityName){
        Ability selectedAbility = getAbilityByName(abilityName);
        Ability npcAbility = getNPCAbility();

        if (userPriority) {
            user.useAbility(selectedAbility, npc);
            if(hasLost(npc)){
                isOver = true;
                Console.print("BATTLE OVER");
                return;
            }
            npc.useAbility(npcAbility, user);
            if(hasLost(user)){
                isOver = true;
                Console.print("BATTLE OVER");
                return;
            }
        }
        else{
            npc.useAbility(selectedAbility, user);
            if(hasLost(user)){
                isOver = true;
                Console.print("BATTLE OVER");
                return;
            }
            user.useAbility(npcAbility, npc);
            if(hasLost(npc)){
                isOver = true;
                Console.print("BATTLE OVER");
                return;
            }
        }
    }

    public Ability getAbilityByName(String abilityName){
        for(Ability ability : user.getAbilities()){
            if(ability.getName().equals(abilityName)){
                return ability;
            }
        }
        return null;
    }

    public boolean hasLost(Character player){
        if(player.getHealth() <= 0){
            player.getCurrentRoom().removeCharacter(player);
            return true;
        }else{
            return false;
        }
    }
}



