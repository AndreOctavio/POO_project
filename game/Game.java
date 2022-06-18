package game;

import player.Player;
import deck.Deck;

import java.util.ArrayList;
import java.util.List;

abstract class Game {

    public Player player;
    public Deck deckOfcards;
    public HandManager MasterKey;
    public List<Integer> hold;
    public int[] hands_count = new int[11];

    /**
     * Constructs Game, player with money m.
     * 
     * @param m money
     */
    public Game(int m) {
        player = new Player(m);
        deckOfcards = new Deck();
        MasterKey = new HandManager(player);
        hold = new ArrayList<Integer>(); // hold is used to know which cards to keep
    }

    /**
     * Takes the money for the bet from the player.
     * 
     * @param m money
     */
    public void bet(int m) {
        player.loss(m);
    }

    /**
     * Prints the players credit.
     */
    public void credit() {
        System.out.println("\n-cmd $\nplayer's credit is " + player.money);
    }

    /**
     * Gets 5 cards from deckOfcards and gives them to players hand.
     */
    public abstract void deal();

    /**
     * Replaces the cards that the player doesn't want.
     * 
     * @param h list with the index of the cards that player wants in his hand.
     */
    public abstract void doHold(List<Integer> h);

    /**
     * prints the statistics table.
     * 
     * @param N13 "theoretical returned"
     */
    public void statistics(double N13) {
        // System.out.println("Hand Nb");
        // System.out.println("______________________");
        // System.out.println("Jacks or Better " + hands_count[0]);
        // System.out.println("Two Pair " + hands_count[1]);
        // System.out.println("Three of a kind " + hands_count[2]);
        // System.out.println("Straight " + hands_count[3]);
        // System.out.println("Flush " + hands_count[4]);
        // System.out.println("Full House " + hands_count[5]);
        // System.out.println("Four of a Kind " + hands_count[6]);
        // System.out.println("Straight Flush " + hands_count[7]);
        // System.out.println("Royal Flush " + hands_count[8]);
        // System.out.println("Other " + hands_count[9]);
        // System.out.println("______________________");
        // System.out.println("Total " + hands_count[10]);
        // System.out.println("Credit " + player.money + " (" + N13 + "%)");
    }
}
