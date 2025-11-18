import java.util.Scanner;

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
        this.start();
    }

    public void start() {
        while (!isOver) {
            user.displayStatus();
            npc.displayStatus();
            user.displayAbilities();
            Ability currentability = selectAbility();
            if (userPriority) {
                user.useAbility(currentability, npc);
                if(hasLost(npc)){
                    isOver = true;
                    System.out.println("You win A");
                    break;
                }
                npc.useAbility(npc.getAbility(0), user);
                if(hasLost(user)){
                    isOver = true;
                    System.out.println("You lose A");
                    break;
                }
            } else{
                npc.useAbility(npc.getAbility(0), user);
                if(hasLost(user)){
                    isOver = true;
                    System.out.println("You lose B");
                    break;
                }
                user.useAbility(currentability, npc);
                if(hasLost(npc)){
                    isOver = true;
                    System.out.println("You win B");
                    break;
                }
            }
        }

    }

    public Ability selectAbility() {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt() - 1;
        return user.getAbility(choice);
    }

    public boolean hasLost(Character player){
        if(player.getHealth() <= 0){
            player.getCurrentRoom().removeCharacter(player);
            return true;
        }else{
            return false;
        }

    }

    public void finish(){

    }
}
