public class Console{

    public static void print(String message){
        GUI.writeTo(message);
    }

    public static void clear(){
        GUI.consoleDisplay.setText("");
    }

}
