package htl.tinf.lab;

import javafx.scene.control.TextArea;

public class ConsoleUpdater {
    private TextArea console;
    String text ="";

    public ConsoleUpdater(TextArea console) {
        this.console = console;
    }

    public String getText() {
        return text;
    }

    public void appendText(String text){
        this.text +=text;
    }

    public void updateConsole(){

       console.setText("");
        console.appendText("Research Button Problem\n");
        console.appendText("***********************\n");
        console.appendText( text);
    }

    public void resetText(){
        text="";
    }
}
