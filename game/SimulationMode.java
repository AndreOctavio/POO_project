package game;

/*import java.util.ArrayList;
import java.util.List;*/

public class SimulationMode /*extends Game */{
    /*
    public List<Integer> excluded;

    public void deal() {
        int random_num;
        hands_count[10]++;

        for (int i = 0; i < 5; i++) {
            random_num = deckOfcards.getRandom(deckOfcards.excluded, deckOfcards.deck.size());
            player.hand.add(deckOfcards.deck.get(random_num));
            deckOfcards.excluded.add(random_num);
        }
    }

    /**
     * Replaces the cards that the player doesn't want.
     * 
     * @param h list with the index of the cards that player wants in his hand.
     *//* 
    public void doHold(List<Integer> h) {
        int random_num;

        for (int n = 1; n < 6; n++) {
            if (!deckOfcards.search(h, n)) { // check if n is in hold
                random_num = deckOfcards.getRandom(deckOfcards.excluded, deckOfcards.deck.size());
                player.hand.set(n - 1, deckOfcards.deck.get(random_num)); // replace the new card in players hand
                // deckOfcards.excluded.add(random_num); // NÃO É PRECISO!!!!!!!!!!!!
            }
        }
    }*/
}
