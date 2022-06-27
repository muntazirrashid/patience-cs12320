package uk.ac.aber.dcs.cs12320_mrm19;

import uk.ac.aber.dcs.cs12320_mrm19.enums.Suit;
import uk.ac.aber.dcs.cs12320_mrm19.enums.nums;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Deck {
    private String cardNum;
    private String cardSuit;
    private ArrayList<Deck> deck;
    private ArrayList<Deck> shuffledDeck = new ArrayList<>();

    /**
     * Constructor
     */
    public Deck() {
        deck = new ArrayList<>();
        for (int i = 2; i < 10; i++) {
            for (Suit s : Suit.values()) {
                cardSuit = s.toString();
                cardNum = Integer.toString(i);
                deck.add(new Deck(cardNum, cardSuit));
            }
        }
        for (nums n : nums.values()) {
            for (Suit s : Suit.values()) {
                cardSuit = s.toString();
                cardNum = n.toString();
                deck.add(new Deck(cardNum, cardSuit));
            }
        }
    }

    /**
     *
     * @param num
     * @param suit
     */
    private Deck(String num, String suit) {
        cardNum = num;
        cardSuit = suit;
    }



    public void setCard(String url) {
        this.cardNum = Character.toString(url.charAt(0));
        this.cardSuit = Character.toString(url.charAt(1));
    }

    public ArrayList<Deck> getDeck() {
        return deck;
    }

    /**
     *
     * @return shuffeled deck
     */

    public ArrayList<Deck> getShuffledDeck() {
        return shuffledDeck;
    }

    /**
     * Shuffles
     */
    public void shuffle() {
        shuffledDeck = deck;
        Collections.shuffle(shuffledDeck);
        System.err.println("Shuffling completed");
    }

    /**
     *
     * @param filename
     * @throws IOException
     * load cards from file
     */
    void loadCard(String filename) throws IOException {
        deck = new ArrayList<>();

        try (FileReader fr = new FileReader(filename);
             Scanner infile = new Scanner(fr)) {
            while (deck.size() < 52) {
                this.cardNum = infile.nextLine();
                this.cardSuit = infile.nextLine();
                Deck newDeck = new Deck(cardNum, cardSuit);
                this.deck.add(newDeck);
            }
        }
    }

    /**
     *
     * @return card number
     */
    public String getCardNum() {
        return cardNum;
    }

    /**
     *
     * @return card suit
     */

    public String getCardSuit() {
        return cardSuit;
    }

    /**
     *
     * @return card
     */

    public String getCard() {
        return cardNum + cardSuit;
    }

    /**
     *
     * @param o
     * @return
     */

    @Override
    public boolean equals(Object o) {
        Deck deck = (Deck) o;
        return cardNum.equals(deck.cardNum) & cardSuit.equals(deck.cardSuit);
    }

    /**
     *
     * @return card value and suit .
     */
    @Override
    public String toString() {
        return cardNum + cardSuit;
    }
}
