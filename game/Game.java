package game;

import deck.Card;
import player.Player;
import deck.Deck;

import java.util.ArrayList;
import java.util.List;

abstract class Game {

    public Player player;
    public Deck deckOfcards;
    protected int[] hands_count = new int[11];
    protected int low_pair;

    /**
     * Constructs Game, player with money m.
     * 
     * @param m money
     */
    public Game(int m) {
        player = new Player(m);
        deckOfcards = new Deck();
        /* deckOfcards.createDeck(); for simulation mode */
    }

    /**
     * Takes the money for the bet from the player.
     * 
     * @param m money
     */
    public void bet(int m) {
        player.loss(m);
    }

    /**
     * Prints the players credit.
     */
    public void credit() {
        System.out.println("\n-cmd $\nplayer's credit is " + player.money);
    }

    /**
     * Gets 5 cards from deckOfcards and gives them to players hand.
     */
    public abstract void deal();

    /**
     * Replaces the cards that the player doesn't want.
     * 
     * @param h list with the index of the cards that player wants in his hand.
     */
    public abstract void doHold(List<Integer> h);

    /**
     * Identifies the type of hand that the player has and
     * gives the credit that he won, if he has a good hand.
     * 
     * @param h hand of the player.
     * @param b amout of the bet.
     */
    public int identifyHand(List<Card> hand, int b) {
        List<Card> h = new ArrayList<Card>();

        h = player.organiseHand(hand);
        boolean s = straight(h);
        int[] cont = { 1, 0, 0, 0 }; // index 0 cont of equal cards, index 1 cont of pairs,
                                     // index 2 cont of triples, index 3 cont of four of a kind
        int goodPair = 0;
        int badPair = 0;
        if (flush(h)) {
            if (h.get(2).value == 60 && s) {
                if (b < 5) { // player bet 1/2/3/4 credits
                    player.gain(b * 250);
                } else { // player bet 5 credits
                    player.gain(4000);
                }
                hands_count[8]++;
                return 11;// Royal FLush
            } else if (s) {
                player.gain(b * 50);
                hands_count[7]++;
                return 10;// Straight Flush
            } else {
                player.gain(b * 7);
                hands_count[4]++;
                return 5;// Flush
            }
        } else {
            if (s) {
                player.gain(b * 5);
                hands_count[3]++;
                return 4;// Straight
            }
            for (int i = 0; i < 4; i++) {
                if (h.get(i).value == h.get(i + 1).value) { // Check if a card is equal to the next
                    cont[0]++;
                } else if (cont[0] > 1) {
                    if (cont[0] == 2) { // Pair
                        if (h.get(i).value > 58) { // Check if it's a good pair
                            goodPair++;
                        } else {
                            badPair++;
                            low_pair = h.get(i).value;
                        }
                    }
                    cont[cont[0] - 1]++; // increment counters
                    cont[0] = 1; // reset card counter
                }
            }
            cont[cont[0] - 1]++;
            if (cont[1] == 1 && cont[2] == 1) { // Full House
                player.gain(b * 10);
                hands_count[5]++;
                return 6;
            } else if (cont[1] == 2) { // Two Pair
                player.gain(b);
                hands_count[1]++;
                return 2;
            } else if (cont[1] == 1 && goodPair == 1) { // Jacks or Better
                player.gain(b);
                hands_count[0]++;
                return 1;
            } else if (cont[1] == 1 && badPair == 1) { // LowPair
                return -1;
            } else if (cont[2] == 1) { // Three of a kind
                player.gain(b * 3);
                hands_count[2]++;
                return 3;
            } else if (cont[3] == 1) { // Four of a kind
                if (h.get(2).value < 53) { // Four 2–4
                    player.gain(b * 80);
                    hands_count[6]++;
                    return 8;
                } else if (h.get(2).value == 62) { // Four Aces
                    player.gain(b * 160);
                    hands_count[6]++;
                    return 9;
                } else { // Four 5-K
                    player.gain(b * 50);
                    hands_count[6]++;
                    return 7;
                }
            } else {
                hands_count[9]++;
                return 0; // nothing special about the hand
            }
        }
    }

    /**
     * Checks if the if the h hand is a flush.
     * 
     * @param h hand of the player.
     * @return true/false.
     */
    public boolean flush(List<Card> h) {
        for (Card tmp : h) {
            if (tmp.naipe != h.get(4).naipe) {
                return false;
            }
        }
        return true;
    }
    /**
     * Checks if the if the h hand is a flush.
     * 
     * @param h hand of the player.
     * @return integer with the values 3 or 4, which indicates the number of cards
     *         with the same suit in the players' hand
     */
    public int flush_count(List<Card> h) {

        int naipe_counter[] = { 0, 0, 0, 0};

        for (Card tmp : h) {
            switch (tmp.naipe) {
                case ('C'):
                    naipe_counter[0]++;
                    break;
                case ('D'):
                    naipe_counter[1]++;
                    break;
                case ('H'):
                    naipe_counter[2]++;
                    break;
                case ('S'):
                    naipe_counter[3]++;
                    break;
            }
        }

        for (int aux : naipe_counter) {
            switch (aux) {
                case (3):
                    return 3;

                case (4):
                    return 4;
            }
        }
        return 0;
    }

    /**
     * Checks if the h hand is a straight.
     * 
     * @param h hand of the player.
     * @return true/false.
     */
    public boolean straight(List<Card> h) {
        int cont = 1;

        for (int i = 0; i < 4; i++) {

            if ((h.get(i).value + 1) == h.get(i + 1).value) {
                cont++;
            } else if (h.get(0).value == '2' && h.get(4).value == 62 && cont == 4) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean isRoyal(Card c) {
        for (int i = 58; i <= 62; i++){
            if(c.value == i){
                return true;
            }
        }
        return false;
    }

    public List<Integer> straight_count(List<Card> orig_hand) {
        
        int new_count = 0;

        // [number of straight cards, in_straight, out_straight, index of those cards in hand]
        List<Integer> str_index = new ArrayList<Integer>();
        str_index.add(0);
        str_index.add(0);
        str_index.add(1);

        List<Card> h = player.organiseHand(orig_hand);

        // max value of a card in order to be part of the straight currently being
        // evaluated in the loop
        int max_val = 0;
        int in_straight = 0;
        int out_straight = 1;
        int j;

        // loop through first 3 cards (i is the index of the straight start card)
        for (int i = 0; i < 5; i++) {

            j = i;
            
            // add current card as if it was part of a possible straight
            str_index.add(orig_hand.indexOf(h.get(i)) + 1);

            // the max value of a card that is part of the current straight
            max_val = h.get(i).value + 4;
            new_count++;

            if(h.get(i).value == 62){
                max_val = 53;
                j = 0;
            }

            // loop cards in front
            for(int n = j; n < 4; n++){
                // check if next card is the continuation of a straight
                if (((h.get(n).value + 1) == h.get(n + 1).value) && h.get(n + 1).value <= max_val) {
                    str_index.add(orig_hand.indexOf(h.get(n + 1)) + 1);
                    new_count++;

                // in this case we have a low Ace and it´s the first iteration
                } else if (j != i && n == j) {
                    if(h.get(n + 1).value == '2'){
                        str_index.add(orig_hand.indexOf(h.get(n + 1)) + 1);
                        new_count++;
                    }

                // check if the next card is part of a possible straight
                // if we get to this point, then the card isn´t the next possible
                // value which means we have an inside straight
                } else if (h.get(n + 1).value <= max_val && h.get(n + 1).value != h.get(n).value) { // inside straight
                    new_count++;
                    in_straight = 1;
                    out_straight = 0;
                    str_index.add(orig_hand.indexOf(h.get(n + 1)) + 1);
                }
            }

            // if there isn´t a 3 or 4 to a straight we clear the list and
            // reset it to its original state

            /* DIDN´T FIND SEMI STRAIGHT */
            if (new_count < 3){

                for (int inad = str_index.size() - 1; inad > str_index.get(0) + 2; inad--){
                    str_index.remove(inad);
                }

                in_straight = 0;
                out_straight = 1;
                new_count++;
            
            /* FOUND SEMI STRAIGHT */
            } else {

                /* Remove old semi straight if (old is 3, new is any) or (old is 4, new is 4) */
                if (str_index.get(0) == 3 || (str_index.get(0) == 4 && new_count == 4)){
                    for (int inad = str_index.size() - new_count - 1; inad > 2; inad--){
                        str_index.remove(inad);
                    }
                    str_index.set(0, new_count);
                    str_index.set(1, in_straight);
                    str_index.set(2, out_straight);
                }

                /* Remove new semi straight if (old is 4, new is 3) */
                if (str_index.get(0) == 4 && new_count == 3){
                    for (int a = str_index.size() - 1; a > 4 + 2; a--){
                        str_index.remove(a);
                    }
                } else {
                    str_index.set(0, new_count);
                    str_index.set(1, in_straight);
                    str_index.set(2, out_straight);
                }


                in_straight = 0;
                out_straight = 1;
            }
            new_count = 0;
            
        }

        return str_index;

    }
    /**
     * Will tell you what cards to hold
     * 
     * @param orig_hand player hand
     * @return indexes cards to hold
     */
    public List<Integer> advice (List<Card> orig_hand) {

        int hand_value = 0;
        List<Integer> hold = new ArrayList<Integer>();
        int i = 0;
        int aux = 0;

        List<Card> changed_hand = player.organiseHand(orig_hand);
        List<Integer> str_index = new ArrayList<Integer>();

        int  id_hand = identifyHand(changed_hand, 0);

        // Value 1  <--
        /* Straight flush, four of a kind, royal flush */
        if (id_hand > 6){

            /* add all cards to hold */
            for(i = 1; i < 6; i++){
                hold.add(i);
            }

            return hold;
        }
        
        /* checks how many cards there are to a straight */
        str_index = straight_count(orig_hand);


        // Value 2  <--
        /* 4 to Royal Flush */
        if(str_index.get(0) == 4) {
            aux = 1;
            /* Check if first card from possible straight are part of a Royal */
            if(!isRoyal(orig_hand.get(str_index.get(3) - 1))){
                aux = 0;
            }
            for(i = 3; i < 6 && aux != 0; i++){
                /* Check if all cards from possible straight are part of a Royal */
                if(!isRoyal(orig_hand.get(str_index.get(i + 1) - 1))){
                    aux = 0;
                    break;
                }

                /* check if all of them have the same suit */
                if(orig_hand.get(str_index.get(i) - 1).naipe != orig_hand.get(str_index.get(i + 1) - 1).naipe){
                    aux = 0;
                    break;
                }
            }

            /* we have a 4 to Royal Flush */
            if (aux == 1){
                for(i = 3; i < 7 && aux != 0; i++){
                    hold.add(str_index.get(i));
                }
                return hold;
            }
        }

        
        // Value 3  <--
        /* Three aces */
        if(id_hand == 3){

            /* in the ordered hand, the middle card will always be part of the three of a kind */
            if (changed_hand.get(2).value == 62){

                i = 1;
                for(Card temp : orig_hand){
                    if(temp.value == 62){
                        hold.add(i);
                    }
                    i++;
                }
                return hold;
            }
        }
        
        // Value 4
        /* Straight, flush, full house */
        if (id_hand == 4 || id_hand == 5 || id_hand == 6){

            /* add all cards to hold */
            for(i = 1; i < 6; i++){
                hold.add(i);
            }

            return hold;
        }
        
        // Value 5  <--
        /* Three aces */
        if(id_hand == 3){

            /* in the ordered hand, the middle card will always be part of the three of a kind */
            i = 1;
            for(Card temp : orig_hand){
                if(temp.value == changed_hand.get(2).value){
                    hold.add(i);
                }
                i++;
            }
            return hold;
        }

        // Value 10  <--
        /* 3 to Royal Flush */
        if(str_index.get(0) == 3) {
            aux = 1;
            
            if(!isRoyal(orig_hand.get(str_index.get(3)))){
                aux = 0;
            }

            for(i = 3; i < 5 && aux != 0; i++){
                /* Check if all cards from possible straight are part of a Royal */
                if(!isRoyal(orig_hand.get(str_index.get(i + 1)))){
                    aux = 0;
                    break;
                }

                /* check if all of them have the same suit */
                if(orig_hand.get(str_index.get(i)).naipe != orig_hand.get(str_index.get(i + 1)).naipe){
                    aux = 0;
                    break;
                }
            }

            /* we have a 4 to Royal Flush */
            if (aux == 1){
                for(i = 3; i < 7 && aux != 0; i++){
                    hold.add(str_index.get(i));
                }
                return hold;
            }
        }

        // Value 12 <--
        /* Low Pair */
        if(id_hand == -1){
            for(Card temp : orig_hand){
                if(temp.value == low_pair){
                    hold.add(i);
                }
                i++;
            }
            return hold;
        }

        return hold;
    }

    /**
     * prints the statistics table.
     * 
     * @param N13 "theoretical returned"
     */
    public void statistics(double N13) {
        System.out.println("Hand Nb");
        System.out.println("______________________");
        System.out.println("Jacks or Better " + hands_count[0]);
        System.out.println("Two Pair " + hands_count[1]);
        System.out.println("Three of a kind " + hands_count[2]);
        System.out.println("Straight " + hands_count[3]);
        System.out.println("Flush " + hands_count[4]);
        System.out.println("Full House " + hands_count[5]);
        System.out.println("Four of a Kind " + hands_count[6]);
        System.out.println("Straight Flush " + hands_count[7]);
        System.out.println("Royal Flush " + hands_count[8]);
        System.out.println("Other " + hands_count[9]);
        System.out.println("______________________");
        System.out.println("Total " + hands_count[10]);
        System.out.println("Credit " + player.money + " (" + N13 + "%)");
    }
}
