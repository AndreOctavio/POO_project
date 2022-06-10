package game;

import deck.Card;
import player.Player;
import deck.Deck;

import java.util.ArrayList;
import java.util.List;

abstract class Game {

    public Player player;
    public Deck deckOfcards;
    protected int[] hands_count = new int[11];

    /**
     * Constructs Game, player with money m.
     * 
     * @param m money
     */
    public Game(int m) {
        player = new Player(m);
        deckOfcards = new Deck();
        /* deckOfcards.createDeck(); for simulation mode */
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

    public List<Integer> advice(List<Card> h) {
        List<Integer> a = new ArrayList<Integer>();
        return a;
    }

    /**
     * Identifies the type of hand that the player has and 
     * gives the credit that he won, if he has a good hand.
     * 
     * @param h hand of the player.
     * @param b amout of the bet.
     */
    public int identifyHand(List<Card> h, int b) {
        h = player.organiseHand(h);
        boolean s = straight(h);
        int[] cont = { 1, 0, 0, 0 }; // index 0 cont of equal cards, index 1 cont of pairs,
                                     // index 2 cont of triples, index 3 cont of four of a kind
        int goodPair = 0;
        if (flush(h)) {
            if (h.get(2).value == 60 && s) {
                if (b < 5) { // player bet 1/2/3/4 credits
                    player.gain(b * 250);
                } else { // player bet 5 credits
                    player.gain(4000);
                }
                hands_count[8]++;
                return 11;// Royal FLush
            } else if (s) {
                player.gain(b * 50);
                hands_count[7]++;
                return 10;// Straight Flush
            } else {
                player.gain(b * 7);
                hands_count[4]++;
                return 5;// Flush
            }
        } else {
            if (s) {
                player.gain(b * 5);
                hands_count[3]++;
                return 4;// Straight
            }
            for (int i = 0; i < 4; i++) {
                if (h.get(i).value == h.get(i + 1).value) { // Check if a card is equal to the next
                    cont[0]++;
                } else if (cont[0] > 1) {
                    if (cont[0] == 2) { // Pair
                        if (h.get(i).value > 58) { // Check if it's a good pair
                            goodPair++;
                        }
                    }
                    cont[cont[0] - 1]++; // increment counters
                    cont[0] = 1; // reset card counter
                }
            }
            cont[cont[0] - 1]++;
            if (cont[1] == 1 && cont[2] == 1) { // Full House
                player.gain(b * 10);
                hands_count[5]++;
                return 6;
            } else if (cont[1] == 2) { // Two Pair
                player.gain(b);
                hands_count[1]++;
                return 2;
            } else if (cont[1] == 1 && goodPair == 1) { // Jacks or Better
                player.gain(b);
                hands_count[0]++;
                return 1;
            } else if (cont[2] == 1) { // Three of a kind
                player.gain(b * 3);
                hands_count[2]++;
                return 3;
            } else if (cont[3] == 1) { // Four of a kind
                if (h.get(2).value < 53) { // Four 2–4
                    player.gain(b * 80);
                    hands_count[6]++;
                    return 8;
                } else if (h.get(2).value == 62) { // Four Aces
                    player.gain(b * 160);
                    hands_count[6]++;
                    return 9;
                } else { // Four 5-K
                    player.gain(b * 50);
                    hands_count[6]++;
                    return 7;
                }
            } else {
                hands_count[9]++;
                return 0; // nothing special about the hand
            }
        }
    }

    /**
     * Checks if the if the h hand is a flush.
     * 
     * @param h hand of the player.
     * @return true/false.
     */
    public boolean flush(List<Card> h) {

        for (Card tmp : h) {
            if (tmp.naipe != h.get(4).naipe) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the if the h hand is a straight.
     * 
     * @param h hand of the player.
     * @return true/false.
     */
    public boolean straight(List<Card> h) {
        int cont = 1;

        for (int i = 0; i < 4; i++) {
            if (h.get(i).value == h.get(i + 1).value + 1) {
                cont++;
            } else if (h.get(0).value == '2' && h.get(4).value == 62 && cont == 4) {
                return true;
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * prints the statistics table.
     * 
     * @param N13 "theoretical returned"
     */
    public void statistics(int N13) {
        System.out.println("Hand               Nb");
        System.out.println("______________________");
        System.out.println("Jacks or Better      " + hands_count[0]);
        System.out.println("Two Pair             " + hands_count[1]);
        System.out.println("Three of a kind      " + hands_count[2]);
        System.out.println("Straight             " + hands_count[3]);
        System.out.println("Flush                " + hands_count[4]);
        System.out.println("Full House           " + hands_count[5]);
        System.out.println("Four of a Kind       " + hands_count[6]);
        System.out.println("Straight Flush       " + hands_count[7]);
        System.out.println("Royal Flush          " + hands_count[8]);
        System.out.println("Other                " + hands_count[9]);
        System.out.println("______________________");
        System.out.println("Total                 " + hands_count[10]);
        System.out.println("Credit                " + player.money + " (" + N13 + "%)");
    }
}
