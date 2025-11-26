public class GameController{
    private ZorkGame model;
    private GUI view;
    private Parser parser;
    private GameState state;

    public GameController(){
        this.parser = new Parser();

    }

    public void setModel(ZorkGame model){
        this.model = model;
    }
    public void setView(GUI view){
        this.view = view;
        refreshUI();
    }

    public void takeString(String input){
        Command command = parser.getCommand(input);
        GameState state = model.processCommand(command);
        updateState(state);

        refreshUI();
    }

    public void onAbilityClick(String abilityName) {
        if (model.getState() == GameState.FIGHT) {
            Battle activeBattle = model.getBattle();
            activeBattle.performTurn(abilityName);
        }

        refreshUI();
    }

    public void updateState(GameState gameState){
        view.updateState(gameState);
        this.state = gameState;
        if (state==GameState.FIGHT)
        {
            String[] abilityNames = new String[4];
            int i = 0;
            for(Ability ability : model.getPlayer().getActiveAbilities()) {
                if(ability != null){
                    abilityNames[i++] = ability.getName();
                }
                else{
                    abilityNames[i++] = "";
                }
            }
            view.setAbilityButtons(abilityNames);
        }

    }

    public void refreshUI(){
        view.updateHP(model.getPlayer().getHealth());
        view.updateRoom(model.getPlayer().getCurrentRoom().getTitle());
        if(state == GameState.FIGHT){
            boolean[] usableAbilites = new boolean[4];
            int i = 0;
            for(int count : model.getPlayer().getUses()){
                usableAbilites[i++] = count > 0;
//                System.out.println(usableAbilites[i-1]);
//                System.out.println(i);
            }
            view.updateAbilityButtons(usableAbilites);
        }
    }
}