/**
 * Displays the card images on a table (the Javafx stage)
 * @author Faisal Rezwan, Chris Loftus and Lynda Thomas, mrm19
 * @version 3.0
 */
package uk.ac.aber.dcs.cs12320_mrm19.GUI;

import uk.ac.aber.dcs.cs12320_mrm19.App;
import uk.ac.aber.dcs.cs12320_mrm19.Deck;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

public class Game extends GUI {
    Deck fullDeck = new Deck();
    private final ArrayList<Deck> cardsOnTable = new ArrayList<>();
    private ImageView cardFrame = new ImageView();
    private ArrayList<Deck> deck = null;

    private StackPane selectedPane;
    private boolean repeat;
    private Deck selected;

    /**
     *  Constructor
     * @param app
     */
    Game(App app) {
        this.app = app;
        fullDeck.shuffle();
        deck = fullDeck.getShuffledDeck();
        this.app.initialise();

    }

    /**
     *
     * @return
     */
    ArrayList<Deck> getCardsOnTable() {
        return cardsOnTable;
    }

    /**
     *
     * @return
     */
    private Deck getSelected() {
        return selected;
    }

    /**
     *
     * @param selected
     */
    private void setSelected(Deck selected) {
        this.selected = selected;
    }

    /**
     *
     * @return
     */
    public ArrayList<Deck> getDeck() {
        return deck;
    }

    /**
     * Deck
     * @param autoOn
     */
    private void draw(boolean autoOn) {
        if (deck.size() != 0) {
            cardsOnTable.add(deck.get(0));
            deck.remove(0);
            screenUpdate();
        } else {
            if (!autoOn)
                System.err.println("All the Cards are on the table");
        }
    }

    /**
     * Screen GUI
     */
    @Override
    public void screenUpdate() {

        VBox vBox = new VBox();
        name(vBox);
        addingCard(vBox);

        app.setRoot(vBox);
    }

    /**
     *
     * @param vBox
     */

    public void addingCard(VBox vBox) {

        int column = 1;
        HBox line = new HBox();
        Label count = new Label(Integer.toString(cardsOnTable.size()));
        count.setWrapText(true);
        count.setStyle("-fx-font-family: \"Verdana\"; -fx-font-size: 20; -fx-text-fill: #000000;");
        ImageView helpCard = new ImageView(new Image("cards/h.gif"));
        helpCard.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> rules());
        if (!(deck.size() == 0)) {
            ImageView backside = new ImageView(new Image("cards/b.gif"));
            backside.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                draw(false);
            });
            line.getChildren().add(backside);
        } else {
            line.getChildren().add(new ImageView());
        }
        line.getChildren().add(count);
        line.getChildren().add(helpCard);
        line.setSpacing(550);
        line.setPadding(new Insets(10));
        vBox.getChildren().add(line);

        if (cardsOnTable != null) {
            line = new HBox();
            for (Deck deck : (ArrayList<Deck>) cardsOnTable.clone()) {
                line.getChildren().add(drawCards(deck));
                if ((column % 13) == 0) {
                    vBox.getChildren().add(line);
                    line = new HBox();
                    column = 0;
                }
                column++;
            }
            vBox.getChildren().add(line);
        }
        if (!validMoves(cardsOnTable)) {
            Button button = new Button("Game Finished - Exit");

            vBox.getChildren().add(button);
            vBox.setAlignment(Pos.TOP_CENTER);
            button.setOnAction(event -> app.exit());
        }
        vBox.setAlignment(Pos.TOP_CENTER);


    }
    public void name(VBox vBox) {
        Label label = new Label("Patience - mrm19");
        label.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 50; -fx-text-fill: #008080;");
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.getChildren().addAll(label);
    }

    /**
     *
     * @param deck
     * @param sp
     */

    private void mouseClicks(Deck deck, StackPane sp) {
        if (selectedPane != null) {
            selectedPane.getChildren().remove(cardFrame);
        }
        sp.getChildren().add(cardFrame);
        selectedPane = sp;
        if (selected == null) {
            selected = deck;
        } else {
            cardMove(deck);
            selected = null;
            selectedPane.getChildren().remove(cardFrame);
        }
        System.err.println(selected);
        System.out.println(deck);

        if (this.deck.size() > 0) {
            if (deck.getCard().equals("b.gif")) {
                screenUpdate();
            }
        }
    }

    /**
     *
     * @param newSelected
     */
    private void cardMove(Deck newSelected) {

        int oldSel = cardsOnTable.indexOf(selected);
        int posSel = cardsOnTable.indexOf(newSelected);


        if (((oldSel > posSel) &
                ((oldSel - 1) == posSel) || ((oldSel - 3) == posSel))
                & (selected.getCardSuit().equals(newSelected.getCardSuit()))
                || ((selected.getCardNum().equals(newSelected.getCardNum())))) {
            cardsOnTable.remove(newSelected);
            cardsOnTable.remove(selected);
            cardsOnTable.add(posSel, selected);
            screenUpdate();

        } else {
            System.err.println("The Cards need to be of same suit or number");
        }

    }

    /**
     *
     * @param deck
     * @return
     */
    private StackPane drawCards(Deck deck) {
        StackPane stack = new StackPane();
        ImageView iv = new ImageView(new Image(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("cards/" + deck + ".gif")).toString(), true));
        iv.setFitWidth(100);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);

        iv.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> mouseClicks(deck, stack));

        stack.getChildren().addAll(iv);
        return stack;
    }



    /**
     * Move Validation
     * @param decks
     * @return
     */

    private boolean validMoves(ArrayList<Deck> decks) {

        if (getDeck().size() != 0) {
            return true;
        }
        for (Deck deck : cardsOnTable) {
            int cardIndex = decks.indexOf(deck);
            if (cardIndex > 2) {
                Deck fourthLeft = decks.get(decks.indexOf(deck) - 3);
                if (deck.getCardSuit().equals(fourthLeft.getCardSuit()) ||
                        deck.getCardNum().equals(fourthLeft.getCardNum())) {
                    return true;
                }
            }
            if (cardIndex != 0) {
                Deck firstLeft = decks.get(decks.indexOf(deck) - 1);
                if (deck.getCardSuit().equals(firstLeft.getCardSuit()) ||
                        deck.getCardNum().equals(firstLeft.getCardNum())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Help Menu
     */
    private void rules() {
        final Stage popUpWindow = new Stage();
        popUpWindow.setTitle("Rules");
        popUpWindow.initOwner(app.getMStage());
        VBox popUpVbox = new VBox(20);
        Label label = new Label(
                "-\t This is how it works:\n" +
                        "• The object of the game is to end up with one pile/stack of cards.\n" +
                        "• Shuffle a deck of cards.\n" +
                        "• Lay down the first card face up.\n" +
                        "• Lay down the next one next to it, and so on.\n" +
                        "• This would lead you to 52 cards all facing upwards, except that you can put one pile\n" +
                        "on another if any of the following apply:\n" +
                        "o. They are next to each other and the same number or suit.\n" +
                        "o. There are two piles between them, and they are the same number or suit..\n");

        label.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 20; -fx-text-fill: #008080;");
        popUpVbox.getChildren().add(label);

        Button oneMove = new Button("Play For Me!");
        oneMove.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> autoPlay());
        popUpVbox.getChildren().add(oneMove);

        popUpVbox.setAlignment(Pos.CENTER);


        Scene popUpScene = new Scene(popUpVbox, 800, 500);
        popUpWindow.setScene(popUpScene);
        popUpWindow.show();
    }

    private void moveCmnd(int i) {
        try{
            setSelected(cardsOnTable.get(cardsOnTable.size() - 1));
            Deck newDeck = cardsOnTable.get(cardsOnTable.size() - 1 - i);
            cardMove(newDeck);
            setSelected(cardsOnTable.get(cardsOnTable.size() - 1));
        }catch (IndexOutOfBoundsException e){
            System.err.println("Invalid");
        }


    }

    private void amalgamate() {
        Scanner sc = new Scanner(System.in);

        try {
                System.out.println("Which Card Do you want To Move:");
                int input_a = sc.nextInt() -1;
                sc.nextLine();
                System.out.println("On top of which card do u want to place it: ");
                int input_b = sc.nextInt();
                if (input_b>0) {
                    Deck rCard = cardsOnTable.get(input_a);
                    Deck lCard = cardsOnTable.get(input_b);
                    if (rCard.getCardSuit().equals(lCard.getCardSuit()) || rCard.getCardNum().equals(lCard.getCardNum())) {
                        cardsOnTable.remove(input_b);
                        cardsOnTable.add(input_b, rCard);
                        cardsOnTable.remove(input_a);
                    }
                    else {
                        System.err.println("Invalid");
                    }
                }
                else {
                    System.out.println("Invalid");
                }
        }catch (IndexOutOfBoundsException e){
            System.err.println("Invalid");
        }

    }

    /**
     * Auto Play
     */
    private void autoPlay() {
        ArrayList<Deck> possibleOne = new ArrayList<>();
        ArrayList<Deck> possibleTwo = new ArrayList<>();
        if (repeat) {
            for (Deck deck : cardsOnTable) {
                int cardIndex = cardsOnTable.indexOf(deck);
                if (cardIndex > 2) {
                    Deck fourthLeft = cardsOnTable.get(cardsOnTable.indexOf(deck) - 3);
                    if (deck.getCardSuit().equals(fourthLeft.getCardSuit()) ||
                            deck.getCardNum().equals(fourthLeft.getCardNum())) {
                        possibleTwo.add(cardsOnTable.get(cardsOnTable.indexOf(fourthLeft)));
                        possibleTwo.add(deck);
                    }
                }
                if (cardIndex != 0) {
                    Deck firstLeft = cardsOnTable.get(cardsOnTable.indexOf(deck) - 1);
                    if (deck.getCardSuit().equals(firstLeft.getCardSuit()) ||
                            deck.getCardNum().equals(firstLeft.getCardNum())) {
                        possibleOne.add(cardsOnTable.get(cardsOnTable.indexOf(firstLeft)));
                        possibleOne.add(deck);
                    }
                }
            }
            if (getDeck().size() == 0) {
                repeat = false;
            }

        }
        if (possibleTwo.size() != 0) {
            setSelected(possibleTwo.get(1));
            cardMove(possibleTwo.get(0));
        } else if (possibleOne.size() != 0) {
            setSelected(possibleOne.get(1));
            cardMove(possibleOne.get(0));
        } else repeat = true;
    }




    /**
     * CLI MENU
     */
    @Override
    public void menuList() {
        System.out.println("1 - Print the pack out (this is so you can check that it plays properly)");
        System.out.println("2 - Shuffle");
        System.out.println("3 - Deal a card");
        System.out.println("4 - Make a move, move last pile onto previous one");
        System.out.println("5 - Make a move, move last pile back over two piles");
        System.out.println("6 - Amalgamate piles");
        System.out.println("7 - Print the displayed Cards on the command line");
        System.err.println("8 - Play For Me!");
        System.out.println("9 - Exit");
    }

    /**
     * @param choice user inputs
     */
    @Override
    public void input(String choice) {
        switch (choice) {
            case "1":
                System.out.println(getDeck());
                break;
            case "2":
                Collections.shuffle(deck);
            case "3":
                draw(false);
                if (getSelected() != null) {
                    setSelected(null);
                }
                setSelected(cardsOnTable.get(cardsOnTable.size() - 1));
                screenUpdate();
                break;
            case "4":
                moveCmnd(1);
                break;
            case "5":
                moveCmnd(3);
                break;
            case "6":
                amalgamate();
                break;
            case "7":
                System.out.println(cardsOnTable);
                break;
            case "8":
                autoPlay();
                break;
            case "9":
                app.exit();
                break;
            default:
                System.err.println("INVALID OPTION");
        }
        menuList();
    }

}