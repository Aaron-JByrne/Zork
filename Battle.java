

public class Battle {
    private Character user;
    private Character npc;
    private BattleCharacter player1;
    private BattleCharacter player2;
    private boolean userPriority; //trash variable name
    private boolean isOver;
    private BattleCharacter loser;
    private BattleCharacter winner;

    private Ability struggle = new Ability("Struggle", "last resort", 5, 1){
        @Override
        public void use(){
            return;
        }
    };


    public Battle(Character user, Character npc) {
        userPriority = (user.getLevel() >= npc.getLevel());
        this.player1 = (userPriority) ? new BattleCharacter(user) : new BattleCharacter(npc);
        this.player2 = (userPriority) ? new BattleCharacter(npc) : new BattleCharacter(user);
        this.user = user;
        this.npc = npc;
        this.isOver = false;
        validateMoveSets();
//        System.out.println("BATTLE STARTED");
    }

    public boolean isFinished(){
        return isOver;
    }

    public Character getNPC(){
        return npc;
    }

    public BattleCharacter getPlayerBC(){
        return (userPriority) ? player1 : player2;
    }

    public Ability getNPCAbility(BattleCharacter npc){
        return npc.getAbility(0);
    }

    public void performTurn(int index){
        try {
            Ability player1Ability;
            Ability player2Ability;

            if(index == -1) {
                player1Ability = (userPriority) ? struggle : getNPCAbility(player1);
                player2Ability = (userPriority) ? getNPCAbility(player2) : struggle;
            }else{
                player1Ability = (userPriority) ? user.getActiveAbilities()[index] : getNPCAbility(player1);
                player2Ability = (userPriority) ? getNPCAbility(player2) : user.getActiveAbilities()[index];
            }

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
            player1.allowStruggle();
//            end(player1);
        }
        if(!player2.canAttack()){
            player2.allowStruggle();
//            end(player2);
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


    public boolean hasLost(BattleCharacter battleChar){
        if(battleChar.getCharacter().getHealth() <= 0){
            end(battleChar);
            return true;
        }else{
            return false;
        }
    }

    public void end(BattleCharacter loser){
        this.loser = loser;
        int xpRewards = loser.getCharacter().getLevel() * 10;
        this.winner = (loser == player2) ? player1 : player2;
        winner.getCharacter().addXP(xpRewards);
        loser.getCharacter().loseBattle();
        System.out.printf("winner - %s\nloser - %s\n",winner.getCharacter().getName(), loser.getCharacter().getName());
        isOver = true;

        ZorkGame.getInstance().onBattleEnd();
    }
}



