package deck;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    public  List <Card> deck;
    public int[] excluded;

    /**
     * Constructs the Deck. 
     */
    public Deck() {
        deck = new ArrayList<Card> ();
        excluded = new int [5];
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
}
