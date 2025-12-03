import java.io.Serializable;

public class QuestManager implements Serializable {
    private Arrow arrow;
    private int stage;
    private Character firstEnemy;

    QuestManager(Arrow arrow){
        this.stage = 0;
        this.arrow = arrow;
    }

    public void update(){
        switch (stage){
            case 0:
                boolean playerHasAbilities = false;
                for(Ability ability : ZorkGame.getInstance().getPlayer().getActiveAbilities()){
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
                System.out.println("case 1");
                if(firstEnemy.getHealth() <= 0){
                    stage++;
                    arrow.setTargetRoom(Minimap.rooms.get("Home"));
                    System.out.println(Minimap.rooms.get("Home"));
                }
                break;
            case 2:

                break;
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
