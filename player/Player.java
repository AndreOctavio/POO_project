package player;

import deck.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Player {

    public int money;
    public List<Card> hand;
    public int sum_of_all_gains;

    /**
     * Constructs Player with money m and an empty array hand.
     * 
     * @param m money
     */
    public Player(int m) {
        money = m;
        hand = new ArrayList<Card>();
        sum_of_all_gains = 0;
    }

    /**
     * Adds a card c to the array hand.
     * 
     * @param c card
     */
    public void addCard(Card c) {
        hand.add(c);
    }

    /**
     * Removes a card c from the array hand.
     * 
     * @param c card
     */
    public void removeCard(Card c) {
        hand.remove(c);
    }

    /**
     * Adds money q to the player.
     * 
     * @param q money
     */
    public void gain(int q) {
        money += q;
        sum_of_all_gains += q;
    }

    /**
     * Takes money q from the player.
     * 
     * @param q money
     */
    public void loss(int q) {
        money -= q;
    }

    public List<Card> organiseHand(List<Card> h) {
        List<Card> order = new ArrayList<Card>();
        char[] hand = new char[5];
        int n = 0, i = 0;

        for (Card tmp : h) {
            hand[n] = tmp.value;
            n++;
        }

        Arrays.sort(hand, 0, 4);

        while (n < 5) {
            while (hand[n] != h.get(i).value) {
                i++;
            }
            order.add(h.get(i));
            h.remove(h.get(i));
            n++;
            i = 0;
        }

        return order;
    }

}
