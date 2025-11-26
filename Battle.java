

public class Battle {
    private Character user;
    private Character npc;
    private BattleCharacter player1;
    private BattleCharacter player2;
    private boolean userPriority; //trash variable name
    private boolean isOver;
    private BattleCharacter loser;
    private BattleCharacter winner;


    public Battle(Character user, Character npc) {
        userPriority = (user.getLevel() >= npc.getLevel());
        this.player1 = (userPriority) ? new BattleCharacter(user) : new BattleCharacter(npc);
        this.player2 = (userPriority) ? new BattleCharacter(npc) : new BattleCharacter(user);
        this.user = user;
        this.npc = npc;
        this.isOver = false;
        validateMoveSets();
    }

    public boolean isFinished(){
        return isOver;
    }

    public Ability getNPCAbility(){
        return npc.getActiveAbilities()[0];
    }

    public void performTurn(String abilityName){
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\TURN BEGAN////////////////////");

        try {
            Ability player1Ability = (userPriority) ? getAbilityByName(abilityName) : getNPCAbility();
            Ability player2Ability = (userPriority) ? getNPCAbility() : getAbilityByName(abilityName);

            player1.useAbility(player1Ability, player2);
            if(hasLost(player2)){
                return;
            }
            player2.useAbility(player2Ability, player1);
            if(hasLost(player1)){
                return;
            }
        } finally {
            finishTurn();
        }
    }

    public void finishTurn(){
        validateMoveSets();
    }

    public void validateMoveSets(){
        if(!player1.canAttack()) {
            end(player1);
        }
        if(!player2.canAttack()){
            end(player2);
        }
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
        for(Ability ability : user.getActiveAbilities()){
            if(ability.getName().equals(abilityName)){
                if(ability.getUses() > 0){
                    return ability;
                }
            }
        }
        return null;
    }


    public boolean hasLost(BattleCharacter battleChar){
        if(battleChar.getCharacter().getHealth() <= 0){
            end(battleChar);
            return true;
        }else{
            return false;
        }
    }

    public void end(BattleCharacter loser){
        this.winner = (loser == player2) ? player1 : player2;
        System.out.printf("winner - %s\nloser - %s\n",winner.getCharacter().getName(), loser.getCharacter().getName());
        isOver = true;
        ZorkGame.getInstance().onBattleEnd();
    }
}



