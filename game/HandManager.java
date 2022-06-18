package game;

import deck.Card;
import player.Player;

import java.util.ArrayList;
import java.util.List;

public class HandManager {

    protected int[] equal_cards = new int[5];
    protected int low_pair;
    protected char flush_naipe;
    Player player;

    HandManager(Player p) {
        player = p;
    }

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
                return 11;// Royal FLush
            } else if (s) {
                player.gain(b * 50);
                return 10;// Straight Flush
            } else {
                player.gain(b * 7);
                return 5;// Flush
            }
        } else {
            if (s) {
                player.gain(b * 5);
                return 4;// Straight
            }
            for (int i = 0; i < 4; i++) {
                if (h.get(i).value == h.get(i + 1).value) { // Check if a card is equal to the next
                    cont[0]++;
                }

                if (cont[0] > 1 && (h.get(i).value != h.get(i + 1).value || i == 3)) {
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
                    equal_cards[cont[1]] = h.get(i).value;
                    // if we have a four of a kind, save its suit
                    if (cont[0] == 4) {
                        equal_cards[3] = h.get(i).value;
                    }

                }

            }
            cont[cont[0] - 1]++;
            equal_cards[cont[1]] = h.get(4).value;
            if (cont[1] == 1 && cont[2] == 1) { // Full House
                player.gain(b * 10);
                return 6;
            } else if (cont[1] == 2) { // Two Pair
                player.gain(b);
                return 2;
            } else if (cont[1] == 1 && goodPair == 1) { // Jacks or Better
                player.gain(b);
                return 1;
            } else if (cont[1] == 1 && badPair == 1) { // LowPair
                return -1;
            } else if (cont[2] == 1) { // Three of a kind
                player.gain(b * 3);
                return 3;
            } else if (cont[3] == 1) { // Four of a kind
                if (h.get(2).value < 53) { // Four 2–4
                    player.gain(b * 80);
                    return 8;
                } else if (h.get(2).value == 62) { // Four Aces
                    player.gain(b * 160);
                    return 9;
                } else { // Four 5-K
                    player.gain(b * 50);
                    return 7;
                }
            } else {
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
     * Checks if the hand is a flush.
     * 
     * @param h hand of the player.
     * @return integer with the values 3 or 4, which indicates the number of cards
     *         with the same suit in the players' hand
     */
    public int flush_count(List<Card> h) {
        int i = 0;
        int naipe_counter[] = { 0, 0, 0, 0 };

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
            if (aux >= 3) {
                switch (i) {
                    case (0):
                        flush_naipe = 'C';
                        break;
                    case (1):
                        flush_naipe = 'D';
                        break;
                    case (2):
                        flush_naipe = 'H';
                        break;
                    case (3):
                        flush_naipe = 'S';
                        break;
                }
                return aux;
            }
            i++;
        }
        return 0;
    }

    /**
     * Checks if the hand h is a straight.
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

    /**
     * Checks if the card's value is a Royal Card.
     * 
     * @param c card.
     * @return true/false.
     */
    public boolean isRoyal(Card c) {
        for (int i = 58; i <= 62; i++) {
            if (c.value == i) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the card's value is a High Card.
     * 
     * @param c card.
     * @return true/false.
     */
    public boolean isHighCard(Card c) {
        for (int i = 59; i <= 62; i++) {
            if (c.value == i) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if cards in h have values given in the charArray value
     * and tests to see if they are suited or unsuited.
     * 
     * @param h     hand.
     * @param value values of the cards we are looking for.
     * @param aux   Suited/Unsuited.
     * @return List of integers (hold).
     */
    public List<Integer> CheckSuit(List<Card> organised_h, List<Card> true_h, int[] value, String aux) {

        // value -> [num of cards, [values]] 4, j, q, k, a
        int cont = value[0], diffCards = 0, i, j;
        char tmp_naipe = 'n'; // null

        List<Integer> indexOf = new ArrayList<Integer>();

        for (i = 4; i >= 0; i--) {
            // if value of card is equal to value we are looking for
            // cont only decrement if we find the value we are looking for
            if (organised_h.get(i).value == value[cont]) {
                if (tmp_naipe != 'n' && organised_h.get(i).naipe != tmp_naipe) {
                    if (aux.equals("Suited")) {
                        return indexOf;
                    }
                    diffCards++;
                }
                tmp_naipe = organised_h.get(i).naipe;
                if (cont > 0) {
                    cont--;
                }
            }
        }
        if (cont == 0) {
            if (aux.equals("Suited") ^ diffCards > 0) {
                for (i = 0; i <= 4; i++) {
                    for (j = value[0]; j > 0; j--) {
                        if (true_h.get(i).value == value[j]) {
                            indexOf.add(i + 1);
                        }
                    }
                }
                return indexOf;
            }
        }

        return indexOf;
    }

    /**
     * Checks if the value of the hand is either a A,J,Q,K according to the flag
     * value.
     * 
     * @param hand hand of the player.
     * @param flag 0 or 1 if we are searching for a A or J/Q/K, respectively
     * @return list of integers with the indexes of the cards found
     */
    public List<Integer> hand_values(List<Card> hand, int flag) {

        ArrayList<Integer> index = new ArrayList<Integer>();

        if (flag == 1) {
            for (int i = 0; i < 5; i++) {
                if (hand.get(i).value == 59) { // J
                    index.add(i);
                } else if (hand.get(i).value == 60) { // Q
                    index.add(i);
                } else if (hand.get(i).value == 61) { // K
                    index.add(i);
                }
            }
            return index;
        } else if (flag == 0) {
            for (int i = 0; i < 5; i++) {
                if (flag == 0 && hand.get(i).value == 62) {
                    index.add(i);
                }
            }
            return index;
        }
        return index;
    }

    /**
     * Verifies if the hand is a 4 to a straight or a 3 to a straight and
     * if it is an inside or an outside straight
     * 
     * @param orig_hand
     * @return str_index, an ArrayList with:
     *         [number of straight cards, in_straight (count of gaps), out_straight,
     *         [index of those cards in the original hand]]
     */

    public List<Integer> straight_count(List<Card> orig_hand) {

        int new_count = 0;

        // [number of straight cards, in_straight, out_straight, index of those cards in
        // hand]
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
        boolean first = true;

        // loop through cards (i is the index of the straight start card)
        for (int i = 0; i < 5; i++) {

            j = i;
            first = true;

            // add current card as if it was part of a possible straight
            str_index.add(orig_hand.indexOf(h.get(i)) + 1);

            // the max value of a card that is part of the current straight
            max_val = h.get(i).value + 4;
            new_count++;

            if (h.get(i).value == 62) {
                max_val = 53;
                j = 0;
            }

            // loop cards in front of i card (Inside Loop)
            for (int n = j; n < 4; n++) {
                // check if next card is the continuation of a straight
                if (((h.get(n).value + 1) == h.get(n + 1).value) && h.get(n + 1).value <= max_val
                        && !(j != i && first)) {
                    str_index.add(orig_hand.indexOf(h.get(n + 1)) + 1);
                    new_count++;
                    if (h.get(n).value == 62 || h.get(n + 1).value == 62) {
                        // if there is an Ace in the straight wannabe, it will always be an inside
                        // straight
                        in_straight = 1;
                        out_straight = 0;
                    }

                    // in this case we have a low Ace and it´s the first iteration
                } else if (j != i && first) {
                    if (h.get(n).value == '2') {
                        str_index.add(orig_hand.indexOf(h.get(n)) + 1);
                        new_count++;
                        // if there is an Ace in the straight wannabe, it will always be an inside
                        // straight
                        in_straight = 1;
                        out_straight = 0;
                        n--;
                    }

                    // check if the next card is part of a possible straight
                    // if we get to this point, then the card isn´t the next possible
                    // value which means we have an inside straight
                } else if (h.get(n + 1).value <= max_val && h.get(n + 1).value != h.get(n).value) { // inside straight
                    new_count++;
                    in_straight++;
                    out_straight = 0;
                    str_index.add(orig_hand.indexOf(h.get(n + 1)) + 1);
                }
                first = false;
            }

            // if there isn't a 3 or 4 to a straight we clear the list and
            // reset it to its original state

            /* DIDN'T FIND SEMI STRAIGHT */
            if (new_count < 3) {

                for (int inad = str_index.size() - 1; inad > str_index.get(0) + 2; inad--) {
                    str_index.remove(inad);
                }

                in_straight = 0;
                out_straight = 1;
                new_count++;

                /* FOUND A SEMI STRAIGHT */
            } else {

                /* Remove old semi straight if (old is 3, new is any) or (old is 4, new is 4) */
                if (str_index.get(0) == 3 || (str_index.get(0) == 4 && new_count == 4)) {
                    for (int inad = str_index.size() - new_count - 1; inad > 2; inad--) {
                        str_index.remove(inad);
                    }
                    str_index.set(0, new_count);
                    str_index.set(1, in_straight);
                    str_index.set(2, out_straight);
                }

                /* Remove new semi straight if (old is 4, new is 3) */
                if (str_index.get(0) == 4 && new_count == 3) {
                    for (int a = str_index.size() - 1; a > 4 + 2; a--) {
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
     * Studies the best strategy for the player according to the cards given
     * 
     * @param orig_hand player's hand
     * @return list of integers with the indexes of the cards the player must hold
     */
    public List<Integer> advice(List<Card> orig_hand) {

        List<Integer> hold = new ArrayList<Integer>();
        int i = 0;
        int aux = 0;
        int fls_cnt = 0;
        int high_straight = 0;
        int highcards_counter = 0; // counter of High Cards in 3 to a Flush

        List<Card> changed_hand = player.organiseHand(orig_hand);

        List<Integer> str_index = new ArrayList<Integer>();
        List<Integer> index_values = new ArrayList<Integer>();

        int[] Values = { 4, 59, 60, 61, 62 }; // index 0 is the number of positions i need to go in CheckSuit

        int id_hand = identifyHand(changed_hand, 0);

        // Value 1 <--
        /* Straight flush, royal flush */
        if (id_hand > 9) {
            /* add all cards to hold */
            for (i = 1; i < 6; i++) {
                hold.add(i);
            }
            System.out.println("advice: STRAIGHT FLUSH/ROYAL FLUSH");
            return hold;

            /* Four of a kind */
        } else if (id_hand > 6) {
            i = 1;
            for (Card temp : orig_hand) {
                if (temp.value == changed_hand.get(2).value) {
                    hold.add(i);
                }
                i++;
            }
            System.out.println("advice: FOUR OF A KIND");
            return hold;
        }

        /* checks how many cards there are to a straight */
        str_index = straight_count(orig_hand);
        fls_cnt = flush_count(orig_hand);

        // Value 2 <--
        /* 4 to Royal Flush */
        if (str_index.get(0) == 4) {
            if (fls_cnt >= 4) {
                for (i = 0; i < 5; i++) {
                    if (orig_hand.get(i).naipe == flush_naipe && isRoyal(orig_hand.get(i))) {
                        aux++;
                        hold.add(i + 1);
                    }
                }
                if (aux != 4) {
                    hold.removeAll(hold);
                } else {
                    System.out.println("advice: 4 TO A ROYAL FLUSH");
                    return hold;
                }
            }
        }

        // Value 3 <--
        /* Three aces */
        if ((id_hand == 3 || id_hand == 6) && changed_hand.get(2).value == 62) {

            /*
             * in the ordered hand, the middle card will always be part of the three of a
             * kind
             */
            i = 1;
            for (Card temp : orig_hand) {
                if (temp.value == 62) {
                    hold.add(i);
                }
                i++;
            }
            System.out.println("advice: 3 ACES");
            return hold;
        }

        // Value 4
        /* Straight, flush, full house */
        if (id_hand == 4 || id_hand == 5 || id_hand == 6) {
            /* add all cards to hold */
            for (i = 1; i < 6; i++) {
                hold.add(i);
            }
            System.out.println("advice: STRAIGHT/FLUSH/FULL HOUSE");
            return hold;
        }

        // Value 5 <--
        /* Three of a kind */
        if (id_hand == 3) {

            /*
             * in the ordered hand, the middle card will always be part of the three of a
             * kind
             */
            i = 1;
            for (Card temp : orig_hand) {
                if (temp.value == changed_hand.get(2).value) {
                    hold.add(i);
                }
                i++;
            }
            System.out.println("advice: 3 OF A KIND");
            return hold;
        }

        // Value 6 <--
        /* 4 to a Straight Flush */
        if (str_index.get(0) == 4) {
            if (fls_cnt >= 4) {
                aux = 1;
                for (i = 3; i < 7; i++) {
                    if (orig_hand.get(str_index.get(i) - 1).naipe != flush_naipe) {
                        aux = 0;
                        hold.add(i + 1);
                    }
                }
                if (aux == 1) {
                    for (i = 3; i < 7; i++) {
                        hold.add(str_index.get(i));
                    }
                } else {
                    return hold;
                }
            }
        }

        // Value 7 <--
        /* Two pair */
        if (id_hand == 2) {
            i = 1;
            for (Card temp : orig_hand) {
                if (temp.value == equal_cards[1] || temp.value == equal_cards[2]) {
                    hold.add(i);
                }
                i++;
            }
            System.out.println("advice: TWO PAIR");
            return hold;
        }

        // Value 8 <--
        /* High Pair (Jacks or Better) */
        if (id_hand == 1) {
            i = 1;
            for (Card temp : orig_hand) {
                if (temp.value == equal_cards[1]) {
                    hold.add(i);
                }
                i++;
            }
            System.out.println("advice: HIGH PAIR (JACKS OR BETTER)");
            return hold;
        }

        // Value 9 <--
        /* 4 to a Flush */
        if (fls_cnt == 4) {
            i = 0;
            for (Card tmp : orig_hand) {
                if (tmp.naipe == flush_naipe) {
                    hold.add(i + 1);
                }
                i++;
            }
            System.out.println("advice: 4 TO A FLUSH");
            return hold;
        }

        // Value 10 <--
        /* 3 to Royal Flush */
        if (str_index.get(0) == 3) {
            if (fls_cnt >= 3) {
                for (i = 0; i < 5; i++) {
                    if (orig_hand.get(i).naipe == flush_naipe && isRoyal(orig_hand.get(i))) {
                        aux++;
                        hold.add(i + 1);
                    }
                }
                if (aux != 3) {
                    hold.removeAll(hold);
                } else {
                    System.out.println("advice: 3 TO A ROYAL FLUSH");
                    return hold;
                }
            }
        }

        // Value 11 <--
        // 4 to an Outside Straight
        if ((str_index.get(0) == 4 && str_index.get(2) == 1)) {
            for (i = 3; i < 7; i++) {
                hold.add(str_index.get(i));
            }
            System.out.println("advice: 4 TO AN OUTSIDE STRAIGHT");
            return hold;
        }

        // Value 12 <--
        /* Low Pair */
        if (id_hand == -1) {
            i = 0;
            for (Card temp : orig_hand) {
                if (temp.value == low_pair) {
                    hold.add(i + 1);
                }
                i++;
            }
            System.out.println("advice: LOW PAIR");
            return hold;
        }

        // value 13
        /* AKQJ unsuited */
        hold = CheckSuit(changed_hand, orig_hand, Values, "Unsuited");
        if (hold.size() != 0) {
            System.out.println("advice: AKQJ UNSUITED");
            return hold;
        }

        // Value 14 <--
        /* 3 to a Straight Flush (Type 1 - High cards exceed or equal Gaps) */
        if (str_index.get(0) == 3) {
            aux = 1;
            for (i = 3; i < 6; i++) {
                // checks if all wannabe straight cards have the flush suit
                if (orig_hand.get(str_index.get(i) - 1).naipe != flush_naipe) {
                    aux = 0;
                }

                // count how many high cards there are in the wannabe straight
                if (isHighCard(orig_hand.get(str_index.get(i) - 1))) {
                    high_straight++;
                }
            }
            if (aux == 1 && high_straight >= str_index.get(1)) {
                for (i = 3; i < 6; i++) {
                    hold.add(str_index.get(i));
                }
                System.out.println("advice: 3 to STRAIGHT FLUSH (type 1)");
                return hold;
            }
        }

        // Value 15
        /* 4 to an inside Straight with 3 High Cards */
        if ((str_index.get(0) == 4 && str_index.get(1) >= 1)) {

            for (i = 3; i < 7; i++) {
                hold.add(str_index.get(i));
                if (isHighCard(orig_hand.get(str_index.get(i) - 1))) {
                    high_straight++;
                }
            }
            if (high_straight != 3) {
                hold.removeAll(hold);
            } else {
                System.out.println("advice: 4 TO AN INSIDE STRAIGHT WITH 3 HIGH CARDS");
                return hold;
            }
        }

        // Value 16
        /* QJ suited */
        Values[0] = 2;
        hold = CheckSuit(changed_hand, orig_hand, Values, "Suited");
        if (hold.size() != 0) {
            System.out.println("advice: QJ SUITED");
            return hold;
        }

        // Value 17
        /* 3 to a Flush with 2 high cards */
        if (fls_cnt == 3) {

            for (i = 0; i < 5; i++) {
                if (orig_hand.get(i).naipe == flush_naipe) { // aux_hold saves the cards with the Flush's suit
                    hold.add(i + 1);
                    if (isHighCard(orig_hand.get(i))) { // checks if the card is a High Card
                        highcards_counter++; // counter of High Cards in the hand
                    }
                }
            }

            if (highcards_counter != 2) { // no 2 High Cards were detected
                hold.removeAll(hold); // the player doesn't hold any card
            } else {
                System.out.println("advice: 3 TO A FLUSH WITH 2 HIGH CARDS");
                return hold; // the player holds the Flush cards
            }
        }

        // Value 19
        /* 4 to an inside Straight with 2 High Cards */
        if ((str_index.get(0) == 4 && str_index.get(1) >= 1 && high_straight == 2)) {

            for (i = 3; i < 7; i++) {
                hold.add(str_index.get(i));
            }
            System.out.println("advice: 4 TO AN INSIDE STRAIGHT WITH 2 HIGH CARDS");
            return hold;
        }

        // Value 20 <--
        /*
         * 3 to a Straight Flush (Type 2 - 1 gap || 2 gaps & 1 High || Ace-low || 234
         * suited)
         */
        if (str_index.get(0) == 3) {
            aux = 1;
            for (i = 3; i < 6; i++) {
                // checks if all wannabe straight cards have the flush suit
                if (orig_hand.get(str_index.get(i) - 1).naipe != flush_naipe) {
                    aux = 0;
                }

                // count how many high cards there are in the wannabe straight
                if (isHighCard(orig_hand.get(str_index.get(i) - 1))) {
                    high_straight++;
                }
            }
            if (aux == 1 && high_straight >= str_index.get(1)) {
                for (i = 3; i < 6; i++) {
                    hold.add(str_index.get(i));
                }
                System.out.println("advice: TYPE 2(???)");
                return hold;
            }
        }

        // Value 21
        /* 4 to an inside Straight with 1 High Card */
        if ((str_index.get(0) == 4 && str_index.get(1) >= 1 && high_straight == 1)) {

            for (i = 3; i < 7; i++) {
                hold.add(str_index.get(i));
            }
            System.out.println("advice: 4 TO AN INSIDE STRAIGHT WITH 1 HIGH CARD");
            return hold;
        }

        // Value 22
        /* KQJ unsuited */
        Values[0] = 3;
        hold = CheckSuit(changed_hand, orig_hand, Values, "Unsuited");
        if (hold.size() != 0) {
            System.out.println("advice: KQJ UNSUITED");
            return hold;
        }

        // Value 23
        /* JT suited */
        Values[0] = 2;
        Values[1] = 58;
        Values[2] = 59;
        hold = CheckSuit(changed_hand, orig_hand, Values, "Suited");
        if (hold.size() != 0) {
            System.out.println("advice: JT SUITED");
            return hold;
        }

        // Value 24
        /* QJ unsuited */
        Values[1] = 59;
        Values[2] = 60;
        hold = CheckSuit(changed_hand, orig_hand, Values, "Unsuited");
        if (hold.size() != 0) {
            System.out.println("advice: QJ SUITED");
            return hold;
        }

        // Value 25
        /* 3 to a Flush with 1 high card */
        if (fls_cnt == 3) {

            if (highcards_counter != 1) { // no High Card was detected
                hold.removeAll(hold); // the player doesn't hold any card
            } else {
                System.out.println("advice: 3 TO A FLUSH WITH 1 HIGH CARD");
                return hold; // the player holds the Flush cards
            }
        }

        // Value 26
        /* QT suited */
        Values[1] = 58;
        Values[2] = 60;
        hold = CheckSuit(changed_hand, orig_hand, Values, "Suited");
        if (hold.size() != 0) {
            System.out.println("advice: QT SUITED");
            return hold;
        }

        // Value 28
        /* KQ, KJ unsuited */
        Values[1] = 60;
        Values[2] = 61;
        hold = CheckSuit(changed_hand, orig_hand, Values, "Unsuited");
        if (hold.size() != 0) {
            System.out.println("advice: KQ UNSUITED");
            return hold;
        }

        Values[1] = 59;
        hold = CheckSuit(changed_hand, orig_hand, Values, "Unsuited");
        if (hold.size() != 0) {
            System.out.println("advice: KJ UNSUITED");
            return hold;
        }

        // Value 30
        /* KT suited */
        Values[1] = 58;
        hold = CheckSuit(changed_hand, orig_hand, Values, "Suited");
        if (hold.size() != 0) {
            System.out.println("advice: KJ SUITED");
            return hold;
        }

        // Value 29
        /* Ace */
        index_values = hand_values(orig_hand, 0);
        if (index_values.size() == 1) { // if there is an "Ace" in the player's hand
            for (i = 0; i < index_values.size(); i++) {
                hold.add(index_values.get(i) + 1); // hold of the cards
            }
            System.out.println("advice: ACE");
            return hold;
        }

        // Value 31
        /* Jack, Queen or King */
        index_values = hand_values(orig_hand, 1);
        if (index_values.size() == 1) { // if there is a J/Q/K, in the player's hand
            for (i = 0; i < index_values.size(); i++) {
                hold.add(index_values.get(i) + 1); // hold of the cards
            }
            System.out.println("advice: JACK, QUEEN, KING");
            return hold;
        }

        // Value 32
        /* 4 to an inside Straight with no High Cards */
        if ((str_index.get(0) == 4 && str_index.get(1) >= 1 && high_straight == 0)) {

            for (i = 3; i < 7; i++) {
                hold.add(str_index.get(i));
            }
            System.out.println("advice: 4 TO AN INSIDE STRAIGHT WITH NO HIGH CARDS");
            return hold;
        }

        // Value 33
        /* 3 to a Flush with 0 high cards */
        if (fls_cnt == 3) {

            if (highcards_counter != 0) { // no High Card was detected
                hold.removeAll(hold); // the player doesn't hold any card
            } else {
                System.out.println("advice: 3 TO A FLUSH WITH NO HIGH CARDS");
                return hold; // the player holds the Flush cards
            }
        }

        return hold; // Discard everything
    }
}