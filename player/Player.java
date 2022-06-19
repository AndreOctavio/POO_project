package player;

import deck.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Player {

    public int money;
    public List<Card> hand;
    public double sum_of_all_gains;

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

    /**
     * Organises from lowest to highest value the cards order.
     * 
     * @param h cards given to the player
     * @return order organised array
     */
    public List<Card> organiseHand(List<Card> h) {

        List<Card> order = new ArrayList<Card>(); //were we keep the organised cards.
        List<Card> aux = new ArrayList<Card>(); //aux hand, we remove cards from here 
                                                //to not mess with the players real hand.
        char[] hand = new char[5]; //we use this char array to organise the values.
        int n = 0, i = 0;

        for (Card tmp : h) { //add tmp to aux and the tmp.value to hand
            aux.add(tmp);
            hand[n] = tmp.value;
            n++;
        }

        n = 0;
        Arrays.sort(hand); //sort the values

        while (n < 5) { //go through each value in hand
            while (hand[n] != aux.get(i).value) { //look for the value in aux
                i++;
            }
            order.add(aux.get(i)); //add the card to order
            aux.remove(aux.get(i)); //remove the card from aux, we do this because there could be repeated values
            n++;
            i = 0; //reset i;
        }

        return order;
    }

}
