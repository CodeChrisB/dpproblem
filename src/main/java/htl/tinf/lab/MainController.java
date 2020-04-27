package htl.tinf.lab;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class MainController implements Initializable {

    @FXML
    Button button_algoRandom;
    @FXML
    Button stop;
    @FXML
    Button button_algoSoluotion;
    @FXML
    Button button_algoDeadLock;

    @FXML
    ImageView bluePhilosopher;
    @FXML
    ImageView greenPhilosopher;
    @FXML
    ImageView yellowPhilosopher;
    @FXML
    ImageView purplePhilosopher;
    @FXML
    ImageView redPhilosopher;

    @FXML
    AnchorPane philoPane;

    @FXML
    Circle buttonTop;
    @FXML
    Circle buttonRight;
    @FXML
    Circle buttonLeft;
    @FXML
    Circle buttonDownLeft;
    @FXML
    Circle buttonDownRight;
    @FXML
    TextArea console;


    @FXML
    Text redTf;
    @FXML
    Text yellowTf;
    @FXML
    Text purpleTf;
    @FXML
    Text greenTf;
    @FXML
    Text blueTf;


    ConsoleUpdater updater;

    //normal Threads no sync
    ThreadFigure red;
    ThreadFigure yellow;
    ThreadFigure purple;
    ThreadFigure green;
    ThreadFigure blue;
    ThreadFigure[] threads;

    //Synced Threads
    ThreadFigureSync[] threadsSync;
    ThreadFigureSync redSync;
    ThreadFigureSync yellowSync;
    ThreadFigureSync purpleSync;
    ThreadFigureSync greenSync;
    ThreadFigureSync blueSync;

    private boolean coustomFont = true;

    public void onClose() {
        System.exit(1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        consoleHeader();
        bluePhilosopher.setImage(startImage(new File("images/blue.jpeg")));
        greenPhilosopher.setImage(startImage(new File("images/green.jpeg")));
        yellowPhilosopher.setImage(startImage(new File("images/yellow.jpeg")));
        purplePhilosopher.setImage(startImage(new File("images/purple.jpeg")));
        redPhilosopher.setImage(startImage(new File("images/red.jpeg")));

        console.setEditable(false);
        console.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        redTf.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        redTf.setFill(Color.RED);
        blueTf.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        blueTf.setFill(Color.BLUE);
        greenTf.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        greenTf.setFill(Color.GREEN);
        yellowTf.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        yellowTf.setFill(Color.YELLOW);
        purpleTf.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        purpleTf.setFill(Color.PURPLE);

        console.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: #00ff00; ");

        //shuffle thread array
        threads = createThreads();
        shuffleThreads(threads);


        stop.setOnAction(a -> {
            reset();
            consoleHeader();
        });

        button_algoSoluotion.setOnAction(a -> {
            threadSync();
        });

        button_algoRandom.setOnAction(a -> {
            rndm();

        });

        button_algoDeadLock.setOnAction(a -> {
            deadlock();
        });

    }

    private void deadlock() {
        reset();
        shuffleThreads(threads);
        for (ThreadFigure thread : threads) {
            thread.left();
        }

        shuffleThreads(threads);
        for (ThreadFigure thread : threads) {
            thread.right();
        }

        waitAllThreads();

        for (ThreadFigure thread : threads) {
            thread.setButtonCount();
        }

        console.appendText("============================================================================\n" +
                "Deadlock : Red,Yellow,Purple,Green,Blue warten.");
    }

    private void threadSync() {

        waitAllSyncedThreads(threadsSync);
        TextArea con = console;
        con.setText("");
        updater = new ConsoleUpdater(con);
        threadsSync = new ThreadFigureSync[5];

        threadsSync[0] = new ThreadFigureSync(buttonLeft, buttonTop, updater, Color.RED, "Red   :", redTf);
        threadsSync[1] = new ThreadFigureSync(buttonTop, buttonRight, updater, Color.YELLOW, "Yellow:", yellowTf);
        threadsSync[2] = new ThreadFigureSync(buttonRight, buttonDownRight, updater, Color.PURPLE, "Purple:", purpleTf);
        threadsSync[3] = new ThreadFigureSync(buttonDownRight, buttonDownLeft, updater, Color.LIGHTGREEN, "Green :", greenTf);
        threadsSync[4] = new ThreadFigureSync(buttonDownLeft, buttonLeft, updater, Color.BLUE, "Blue  :", blueTf);

        resetSync();
//        console.setText("Mhh, die Threads haben bisher noch keine Friedliche\nLösung gefunden dieses Problem zu lösen.\n");
        // TODO: 08/03/2020

        waitAllSyncedThreads(threadsSync);

        for (ThreadFigureSync thread : threadsSync) {
            thread.start();
        }

        waitAllSyncedThreads(threadsSync);
        for (ThreadFigureSync thread : threadsSync) {
            thread.setButtonCount();
        }

        waitAllSyncedThreads(threadsSync);

        updater.updateConsole();

        console.appendText("============================================================================\n\n\n");
    }

    private void resetSync() {
        for (ThreadFigureSync thread : threadsSync) {
            thread.reset();
        }

        consoleHeader();
    }

    private void waitAllSyncedThreads(ThreadFigureSync[] threads) {
        if (threads == null)
            return;

        for (ThreadFigureSync thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void rndm() {
        reset();
        consoleHeader();
        shuffleThreads(threads);
        //make random shuffled thread array

        for (ThreadFigure thread : threads) {
            thread.run();
        }

        boolean everyThreadPressed = true;
        for (ThreadFigure thread : threads) {
            if (!(thread.getButtonCount() == 1)) {
                everyThreadPressed = false;
            }
        }

        if (everyThreadPressed) {
            console.appendText("============================================================================\n" +
                    "Deadlock : Red,Yellow,Purple,Green,Blue warten.\n\n\n");
        } else {
            console.appendText("============================================================================\n\n\n");
        }
        for (ThreadFigure thread : threads) {
            thread.setButtonCount();
        }
    }


    private Image startImage(File file) {
        Image image = new Image(file.toURI().toString());
        return image;
    }

    private ThreadFigure[] createThreads() {
        /*
        ThreadFigure red;
        ThreadFigure yellow;
        ThreadFigure purple;
        ThreadFigure green;
        ThreadFigure blue;
        */

        red = new ThreadFigure(buttonLeft, buttonTop, console, Color.RED, "Red   :", redTf);
        yellow = new ThreadFigure(buttonTop, buttonRight, console, Color.YELLOW, "Yellow:", yellowTf);
        purple = new ThreadFigure(buttonRight, buttonDownRight, console, Color.PURPLE, "Purple:", purpleTf);
        green = new ThreadFigure(buttonDownRight, buttonDownLeft, console, Color.LIGHTGREEN, "Green :", greenTf);
        blue = new ThreadFigure(buttonDownLeft, buttonLeft, console, Color.BLUE, "Blue  :", blueTf);

        return new ThreadFigure[]{red, yellow, purple, green, blue};
    }

    private void reset() {
        for (ThreadFigure thread : threads) {
            thread.reset();
        }
        consoleHeader();
    }

    private void shuffleThreads(ThreadFigure[] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            ThreadFigure a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }


    public void consoleHeader() {
        try {
            waitAllSyncedThreads(threadsSync);
        } catch (Exception e) {
        }
        console.setText("");
        console.appendText("Research Button Problem\n");
        console.appendText("***********************\n");
    }

    public void changeFont() {
        if (coustomFont) {
            //no font
            console.setStyle("");
        } else {
            //font
            console.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: #00ff00; ");
        }
        coustomFont = !coustomFont;
    }


    private void waitAllThreads() {
        for (ThreadFigure thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sleep(int milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setVisibilityOfAllTexts() {
        boolean state = !purpleTf.isVisible();
        purpleTf.setVisible(state);
        yellowTf.setVisible(state);
        blueTf.setVisible(state);
        greenTf.setVisible(state);
        redTf.setVisible(state);
    }


}

