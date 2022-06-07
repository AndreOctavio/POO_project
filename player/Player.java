package player;

import deck.Card;
import java.util.ArrayList;
import java.util.List;

public class Player {
    
    int money;
    public List<Card> hand; 

    /**
     * Constructs Player with money m and an empty array hand. 
     * 
     * @param m money
     */
    public Player (int m) {
        money = m;
        hand = new ArrayList <Card>();
    }
    
    /**
     * Adds a card c to the array hand. 
     * 
     * @param c card
     */
    public void addCard (Card c) {
        hand.add(c);
    }

    /**
     * Removes a card c from the array hand. 
     * 
     * @param c card
     */
    public void removeCard (Card c) {
        hand.remove(c);
    }

    /**
     * Adds money q to the player. 
     * 
     * @param q money
     */
    public void gain (int q) {
        money += q;
    }

    /**
     * Takes money q from the player. 
     * 
     * @param q money
     */
    public void loss (int q) {
        money -= q;
    }
}
