package game;

import deck.Card;
import player.Player;
import deck.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
     * Prints the player's credit.
     */
    public void credit() {
        System.out.println("\n-cmd $\nplayer's credit is "+player.money);
    }

    /**
     * Gets 5 random cards from deckOfcards and gives them to player's hand. 
     */
    public void deal() {
        Random rand = new Random();
        int random_num;

        for (int i = 0; i < 5; i++) {
            random_num = rand.nextInt(52);
            
            while (deckOfcards.search(deckOfcards.excluded, random_num) != true) {
                random_num = rand.nextInt(52);
            }

            player.hand.add(deckOfcards.deck.get(random_num));
            deckOfcards.excluded.add(random_num);
        }
    }

    public void doHold (List <Integer> h) {
    }
    

    
    public List<Integer> advice (List<Card> h) {
        List<Integer> a = new ArrayList<Integer>();
        return a;
    }
    public void statistics () {};
}
