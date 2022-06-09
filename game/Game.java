package game;

import deck.Card;
import player.Player;
import deck.Deck;

import java.util.ArrayList;
import java.util.List;

abstract class Game {

    public Player player;
    public Deck deckOfcards;

    /**
     * Constructs Game, player with money m. 
     * 
     * @param m money
     */
    public Game (int m){
        player = new Player(m);
        deckOfcards = new Deck();   
        /*deckOfcards.createDeck();     for simulation mode */
    }

    /**
     * Takes the money for the bet from the player. 
     * 
     * @param m money
     */
    public void bet(int m){
        player.loss(m);
    }

    /**
     * Prints the players credit.
     */
    public void credit() {
        System.out.println("\n-cmd $\nplayer's credit is "+player.money);
    }

    /**
     * Gets 5 random cards from deckOfcards and gives them to players hand. 
     */
    public void deal() {
        int random_num;

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
     */
    public void doHold (List <Integer> h) {
        int random_num;

        for (int n = 1; n < 6; n++) {
            if (!deckOfcards.search(h, n)) { //check if n is in hold 
                random_num = deckOfcards.getRandom(deckOfcards.excluded, deckOfcards.deck.size());
                player.hand.set(n - 1, deckOfcards.deck.get(random_num)); //replace the new card in players hand
                deckOfcards.excluded.add(random_num); //add index to the array with the already used cards from deck
            }
        }
    }
    
    public List<Integer> advice (List<Card> h) {
        List<Integer> a = new ArrayList<Integer>();
        return a;
    }


    public void statistics () {};

    public void strat () {}
}
