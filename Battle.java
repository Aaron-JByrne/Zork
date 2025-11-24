

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

    public boolean isFinished(){
        return isOver;
    }

    public Ability getNPCAbility(){
        return npc.getAbility(0);
    }

    public boolean performTurn(String abilityName){
        Ability selectedAbility = getAbilityByName(abilityName);
        Ability npcAbility = getNPCAbility();

        if (userPriority) {
            user.useAbility(selectedAbility, npc);
            if(hasLost(npc)){
                Console.print("BATTLE OVER");
                return true;
            }
            npc.useAbility(npcAbility, user);
            if(hasLost(user)){
                Console.print("BATTLE OVER");
                return true;
            }
        }
        else{
            npc.useAbility(selectedAbility, user);
            if(hasLost(user)){
                Console.print("BATTLE OVER");
                return true;
            }
            user.useAbility(npcAbility, npc);
            if(hasLost(npc)){
                Console.print("BATTLE OVER");
                return true;
            }
        }
        return false;
    }

//    public boolean performtestturn(String abilityName){
//        Ability selectedAbility = getAbilityByName(abilityName);
//        Ability npcAbility = getNPCAbility();
//
//        if (userPriority) {
//            user.useAbility(selectedAbility, npc);
//            if(hasLost(npc)){
//                Console.print("BATTLE OVER");
//                return true;
//            }
//            npc.useAbility(npcAbility, user);
//            if(hasLost(user)){
//                Console.print("BATTLE OVER");
//                return true;
//            }
//        }
//        else{
//            npc.useAbility(selectedAbility, user);
//            if(hasLost(user)){
//                Console.print("BATTLE OVER");
//                return true;
//            }
//            user.useAbility(npcAbility, npc);
//            if(hasLost(npc)){
//                Console.print("BATTLE OVER");
//                return true;
//            }
//        }
//        return false;
//    }



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
            isOver = true;
            ZorkGame.getInstance().onBattleEnd();
            return true;
        }else{
            return false;
        }
    }
}



