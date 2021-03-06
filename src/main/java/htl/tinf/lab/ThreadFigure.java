package htl.tinf.lab;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Collection;

public class ThreadFigure  extends Thread {

    //the 2 circles aka buttons on the table
    private Circle right;
    private Circle left;

    //the console on the bottom
    private TextArea console;
    //individual values of the thread
    private Color color;
    private String name;
    //visualzing the amount of pressed buttons
    private int pressedButtonCount =0;
    Text head;

    public double elapsedTime;

    ThreadFigure(Circle right, Circle left, TextArea console, Color color,String name,Text head) {
        this.right=right;
        this.left=left;
        this.console = console;
        this.color = color;
        this.name = name;
        this.head=head;
    }


    @Override
    public void run() {
        if(left.getFill().equals(Color.BLACK)) {
            left.setFill(color);
            console.appendText(name+" Hat den linken Knopf gedrückt\n");
            pressedButtonCount++;
        }else {
            console.appendText(name+" Konnte den linken Knopf NICHT drücken\n");
        }

        if(right.getFill().equals(Color.BLACK)) {
            right.setFill(color);
            console.appendText(name+" Hat den rechten Knopf gedrückt\n");
            pressedButtonCount++;
        }else {
            console.appendText(name+" Konnte den rechten Knopf NICHT drücken\n");
        }


    }


    //set the value of pressed buttons inside the head of the figure
    public void setButtonCount() {
        head.setText(pressedButtonCount+"");
    }

    //will be called when the deadlock button was pressed
    public void deadLock(){
        left.setFill(color);
        pressedButtonCount++;

        console.appendText("* "+name + " hat den linken Knopf gedürckt.\n");
    }

    public int getButtonCount(){
        return pressedButtonCount;
    }
    private void sleep(int milli){
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    public void reset() {
        right.setFill(Color.BLACK);
        left.setFill(Color.BLACK);
        pressedButtonCount=0;
        head.setText("0");
    }

    public void left(){
        if(left.getFill().equals(Color.BLACK)) {
            left.setFill(color);
            console.appendText(name+" Hat den linken Knopf gedrückt\n");
        }else {
            console.appendText(name+" Konnte den linken Knopf NICHT drücken\n");
        }
        pressedButtonCount++;
    }

    public void right(){
        if(right.getFill().equals(Color.BLACK)) {
            right.setFill(color);
            console.appendText(name+" Hat den rechten Knopf gedrückt\n");
        }else {
            console.appendText(name+" Konnte den rechten Knopf NICHT drücken\n");
        }

    }
}
