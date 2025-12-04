public class Console{
    //Console class used to easily get system.out.println messages from game also directly to a console that connects to a GUI textField (Hopefully)
//    public Console() {
//
//    }

    public static void print(String message){
        GUI.writeTo(message);
        //System.out.println(message);
    }

    public static void clear(){
        GUI.consoleDisplay.setText("");
    }

}
