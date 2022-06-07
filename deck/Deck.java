package deck;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    public  List <Card> deck;
    int[] excluded;

    public Deck() {
        int[] excluded = {0, 0, 0, 0, 0};
        deck = new ArrayList<Card> ();
    }

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
        
        System.out.println (deck);
    }
}
