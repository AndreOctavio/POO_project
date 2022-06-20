package deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Deck keeps all the cards needed for game.
 */
public class Deck {
    /** 
     * Array that keeps all the cards.
     * */
    public List<Card> deck; 

    /**
     * Creates an empty deck.
     */
    public Deck() {
        deck = new ArrayList<Card>();
    }

    /**
     * Creates all the cards for the deck.
     */
    public void createDeck() {
        char[] suits = { 'S', 'C', 'H', 'D' };
        int i = 0;

        while (i < 4) {
            for (char n = 50; n < 63; n++) {
                Card c = new Card(n, suits[i]);
                deck.add(c);
            }
            i++;
        }
    }

    /**
     * Gets deck from card-file.
     * 
     * @param d card-file
     */
    public void getDeck(String d) {
        String[] cardsArray = d.split(" ");
        int i = 0;
        Card tmp;

        while (i < cardsArray.length) {
            tmp = new Card(cardsArray[i].charAt(0), cardsArray[i].charAt(1));
            deck.add(tmp);
            i++;
        }
    }

    /**
     * Gets a random number from 0 to 52 (excluding) and checks if it is in n
     * list of integers.
     * 
     * @param n list of integers
     * 
     * @return random number
     */
    public int getRandom(List<Integer> n) {
        Random rand = new Random();
        int random;

        random = rand.nextInt(52);

        while (search(n, random)) {
            random = rand.nextInt(52);
        }

        return random;
    }

    /**
     * Searches for an int s in list n.
     * 
     * @param n list of integers
     * @param s integer we are looking for
     * @return true/false
     */
    public boolean search(List<Integer> n, int s) {
        for (int i : n) {
            if (i == s) {
                return true;
            }
        }
        return false;
    }
}
