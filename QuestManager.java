public class QuestManager {
    private Player player;
    private int stage;
    private Character firstEnemy;

    QuestManager(Player player){
        this.player = player;
        this.stage = 0;
    }

    public void update(){
        switch (stage){
            case 0:
                boolean playerHasAbilities = false;
                for(Ability ability : player.getActiveAbilities()){
                    if(ability != null){
                        playerHasAbilities = true;
                        break;
                    }
                }
                if(playerHasAbilities){
//                    Console.print("enemy has spawned to the west");
                    this.firstEnemy = spawnFirstEnemy();
                    stage++;
                }
                break;
            case 1:
                if(firstEnemy.getHealth() <= 0){
                    stage++;
                    break;
                }
         }
    }

    public Character spawnFirstEnemy(){
        Ability entombed = new Ability("entombed", "deftones", 10, 10);
        Disc entombedDisc = new Disc(entombed);
        Character firstEnemy = new Character("Thug", Minimap.rooms.get("vordhosbn street"), 1, entombedDisc);
        firstEnemy.setActiveAbilities(new Ability[]{entombed, null, null, null});
        return firstEnemy;
    }
}
