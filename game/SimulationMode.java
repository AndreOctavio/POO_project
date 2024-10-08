package game;

import java.util.ArrayList;
import java.util.List;

/**
 * SimulationMode has all the methods used for simulation mode of videopoker.
 */
public class SimulationMode extends Game {
    /**
     * keeps all the cards already taken from deck in one nbdeal
     * */
    public List<Integer> excluded; 
    private int numOfdeals, bet; 
    public double sum_of_all_bets; 

    /**
     * Initialisation of objects in Simulation Mode
     * 
     * @param m credit
     * @param d nbdeals
     * @param b bet
     */
    public SimulationMode(int m, int d, int b) {
        super(m); // call of Game constructor (Inheritance)
        deckOfcards.createDeck();
        excluded = new ArrayList<Integer>();

        numOfdeals = d;
        bet = b;

        sum_of_all_bets = numOfdeals * bet;
    }

    /**
     * 
     * Gives the player random cards from deck (shuffle)
     * 
     */
    public void deal() {
        int random_num;
        hands_count[10]++;

        for (int i = 0; i < 5; i++) {
            random_num = deckOfcards.getRandom(excluded);
            player.hand.add(deckOfcards.deck.get(random_num));
            excluded.add(random_num);
        }
    }

    /**
     * Replaces the cards that the player doesn't want.
     * 
     * @param h list with the index of the cards that player wants in his hand.
     */
    public void doHold(List<Integer> h) {
        int random_num;
        for (int n = 1; n < 6; n++) {
            if (!deckOfcards.search(h, n)) { // check if n is in hold
                random_num = deckOfcards.getRandom(excluded);
                player.hand.set(n - 1, deckOfcards.deck.get(random_num)); // replace the new card in players hand
                excluded.add(random_num);
            }
        }
    }
}
