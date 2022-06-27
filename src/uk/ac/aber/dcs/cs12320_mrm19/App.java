/**
 * The Game of patience main class
 * @author Faisal Rezwan, Chris Loftus and Lynda Thomas, mrm19
 * @version 3.0
 */

package uk.ac.aber.dcs.cs12320_mrm19;

import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import uk.ac.aber.dcs.cs12320_mrm19.GUI.GUI;
import uk.ac.aber.dcs.cs12320_mrm19.GUI.Menu;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends Application {
    private Stage mStage;
    private GUI gui;
    private static Deck deck;

    /**
     *
     * @param gui
     */
    public void setScene(GUI gui) {
        Platform.runLater(() -> {
            this.gui = gui;
            gui.menuList();
            gui.screenUpdate();
        });

    }

    /**
     *
     * @return
     */
    public Stage getMStage() {
        return mStage;
    }

    /**
     * takes in commands
     */
    private void cmnd() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            String answer = scan.nextLine();
            gui.input(answer);
        }
    }

    /**
     *
     * @param root
     */
    public void setRoot(Parent root) {
        Platform.runLater(() -> {
            mStage.getScene().setRoot(root);
            mStage.show();
        });
    }

    /**
     * initialise
     */

    public void initialise() {

        deck = new Deck();

    }


    /**
     * To play sound effects
     * @param fileName
     * @throws MalformedURLException
     * @throws LineUnavailableException
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */

    public static void playSound(String fileName) throws MalformedURLException, LineUnavailableException, UnsupportedAudioFileException, IOException {
        File url = new File(fileName);
        Clip clip = AudioSystem.getClip();
        AudioInputStream ais = AudioSystem.getAudioInputStream( url );
        clip.open(ais);
        clip.start();
    }

    /**
     * ends the game
     */
    public void exit() {
        try {
            playSound("src/sound/sound.wav");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.err.println("Good Bye!");
        System.exit(0);
    }

    /**
     * Starting the game
     * @param mStage
     */
    @Override
    public void start(Stage mStage){
        this.mStage = mStage;
        initialise();
        Menu menu = new Menu(this);
        StackPane sp = new StackPane();
        Scene primaryScene = new Scene(sp, 1350, 800);
        mStage.setScene(primaryScene);
        mStage.setTitle("Patience - mrm19");
        mStage.show();
        setScene(menu);

        Thread commandLineThread = new Thread(() -> cmnd());
        commandLineThread.setName("CMND Thread");
        commandLineThread.start();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}


