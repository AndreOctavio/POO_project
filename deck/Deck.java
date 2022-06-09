package deck;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    public  List <Card> deck;
    public List<Integer> excluded;

    /**
     * Constructs the Deck. 
     */
    public Deck() {
        deck = new ArrayList<Card> ();
        excluded = new ArrayList<Integer>();
    }

    /**
     * Creates all the cards for the deck. 
     */
    public void createDeck () {
        char[] naipes = {'S', 'C', 'H', 'D'};
        int i = 0;

        while (i < 4) {
            for (char n = 50; n < 63; n++) {
                Card c = new Card(n, naipes[i]);
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
    public void getDeck (String d) {
        String [] cardsArray = d.split (" ");
        int i = 0;
        Card tmp;

        while (i < cardsArray.length) {
            tmp = new Card(cardsArray[i].charAt(0), cardsArray[i].charAt(1));
            deck.add(tmp);
            i++;
        }
    }

    /**
     * Search for an int s in list n. 
     * 
     * @param n list of integers
     * @param s integer we are looking for
     */
    public boolean search (List<Integer> n, int s) {
        for (int i: n) {
            if (i == s) {
                return true;
            }
        }
        return false;
    }
}
