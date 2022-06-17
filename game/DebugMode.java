package game;

import deck.Card;
import java.util.List;
import java.util.Collections;

public class DebugMode extends Game {

    /**
     * Constructs DebugMode, player with money m.
     * 
     * @param m money
     * @param d String with all the cards from card-file
     */
    public DebugMode(int m, String d) {
        super(m);
        deckOfcards.getDeck(d);
    }

    /**
     * Creates a String array with all the commands from cmd-file and
     * calls the methods necessary to execute them.
     * 
     * if deal = 1, player made deal so command bet is invalid, so is deal again;
     * if deal = 0, player made bet so command advice, bet and hold are invalid;
     * if deal = -1, player made hold so command advice, hold and deal are invalid;
     * 
     * @param c String with all the commands from cmd-file
     */
    public void Commands(String c) {

        String[] commandsArray = c.split(" "); // commandsArray has all the strings from cmd-file
        int i = 0, n, bet = 0, p_bet = 5, deal = -1, illegal = 0;
        double sum_of_all_bets = 0;

        while (i < commandsArray.length) {
            if (commandsArray[i].equals("b")) { // command bet
                if (i + 1 != commandsArray.length) {
                    try {
                        bet = Integer.parseInt(commandsArray[i + 1]); // convert the string next to b into an int
                    } catch (NumberFormatException e) { // if it can't do it, then it's another command, bet amout is
                                                        // equal
                                                        // to last bet (5 if first bet)
                        bet = p_bet;
                        i--;
                    }
                }

                System.out.print("\n-cmd b ");

                if (deal != -1) { // check if bet command is llegal
                    System.out.println("\nb: illegal command\n");
                } else if (bet < 0 || bet > 5) { // check if bet amout is between [1,5]
                    System.out.print(commandsArray[i + 1] + "\nb: illegal amount\n");
                } else {
                    deal = 0;
                    p_bet = bet;
                    sum_of_all_bets += bet; // to keep track of the sum of bets
                    bet(bet);
                    System.out.println("\nplayer is betting " + bet);
                }
                i += 2;
            } else if (commandsArray[i].equals("$")) { // command credit
                credit();
                i++;
            } else if (commandsArray[i].equals("d")) { // command deal
                if (deal == 0) { // check if deal is llegal
                    deal = 1;
                    deal();
                    System.out.print("\n-cmd d\nplayer's hand ");
                    for (Card tmp : player.hand) { // print player's hand
                        System.out.print(tmp.reverse(tmp) + " ");
                    }
                    System.out.print("\n");
                } else {
                    System.out.print("\n-cmd d\nd: illegal command\n");
                }
                i++;
            } else if (commandsArray[i].equals("h")) { // command hold
                int tmp;

                for (n = 1; n < 6; n++) { // get the cards the player wants to hold
                    if (i + n == commandsArray.length) {
                        break;
                    }
                    try {
                        tmp = Integer.parseInt(commandsArray[i + n]);
                    } catch (NumberFormatException e) {
                        break;
                    }
                    if (tmp < 1 || tmp > 5) {
                        illegal = 1;
                    }
                    hold.add(tmp); // add integers to hold
                }

                i += hold.size() + 1;

                System.out.print("\n\n-cmd h ");

                for (n = 0; n < hold.size(); n++) { // print the integers read next to h command
                    System.out.print(hold.get(n) + " ");
                }

                if (deal == 1 && illegal != 1) { // check if command h is legal
                    doHold(hold);
                    System.out.print("\nplayer's hand ");

                    for (Card tmp2 : player.hand) { // print player's hand
                        System.out.print(tmp2.reverse(tmp2) + " ");
                    }

                    deal = -1;
                    result(MasterKey.identifyHand(player.hand, bet));
                    player.hand.clear();
                } else {
                    System.out.println("\nh: illegal command");
                    illegal = 0;
                }
                hold.clear();
            } else if (commandsArray[i].equals("a")) { // command advice
                System.out.print("\n-cmd a");
                if (deal == 1) { // check if command a is llegal
                    hold = MasterKey.advice(player.hand);
                    Collections.sort(hold);
                    System.out.print("\nplayer should hold cards ");

                    for (n = 0; n < hold.size(); n++) { // print which cards the player needs to hold
                        System.out.print(hold.get(n) + " ");
                    }
                } else {
                    System.out.print("\na: illegal command\n");
                }
                hold.removeAll(hold);
                i++;
            } else if (commandsArray[i].equals("s")) { // command statistics
                System.out.println("\n-cmd s");
                statistics((player.sum_of_all_gains / sum_of_all_bets) * 100);
                i++;
            } else {
                System.out.println("\n" + commandsArray[i] + ": illegal command");
                i++;
            }
        }
    }

    /**
     * Gets 5 cards from deckOfcards and gives them to players hand.
     *
     * It's a different method then the one in SimulationMode
     * because in this mode you dont need to shuffle.
     */
    public void deal() {
        hands_count[10]++;

        for (int i = 0; i < 5; i++) {
            player.hand.add(deckOfcards.deck.get(0));
            deckOfcards.deck.remove(0);
        }

    }

    /**
     * Replaces the cards that the player doesn't want.
     * 
     * It's a different method then the one in SimulationMode
     * because in this mode you dont take random cards from the deck.
     * 
     * @param h list with the index of the cards that player wants in his hand.
     */
    public void doHold(List<Integer> h) {

        for (int n = 1; n < 6; n++) {
            if (!deckOfcards.search(h, n)) { // check if n is in hold
                player.hand.set(n - 1, deckOfcards.deck.get(0)); // replace the new card in players hand
                deckOfcards.deck.remove(0);
            }
        }
    }

    /**
     * prints the final result of the play and the player's credit.
     * 
     * @param name_hand integer which identifies the hand of cards the player owns
     */
    public void result(int name_hand) {

        switch (name_hand) {
            case 11: // player has a ROYAL FLUSH
                System.out.println("\nplayer wins with a ROYAL FLUSH and his credit is " + (player.money));
                hands_count[8]++;
                break;
            case 10: // player has a STRAIGHT FLUSH
                System.out.println("\nplayer wins with a STRAIGHT FLUSH and his credit is " + (player.money));
                hands_count[7]++;
                break;
            case 9: // player has a FOUR ACES
                System.out.println("\nplayer wins with a FOUR ACES and his credit is " + (player.money));
                hands_count[6]++;
                break;
            case 8: // player has a FOUR 2-4
                System.out.println("\nplayer wins with a FOUR 2-4 and his credit is " + (player.money));
                hands_count[6]++;
                break;
            case 7: // player has a FOUR 5-K
                System.out.println("\nplayer wins with a FOUR 5-K and his credit is " + (player.money));
                hands_count[6]++;
                break;
            case 6: // player has a FULL HOUSE
                System.out.println("\nplayer wins with a FULL HOUSE and his credit is " + (player.money));
                hands_count[5]++;
                break;
            case 5: // player has a FLUSH
                System.out.println("\nplayer wins with a FLUSH and his credit is " + (player.money));
                hands_count[4]++;
                break;
            case 4: // player has a STRAIGHT
                System.out.println("\nplayer wins with a STRAIGHT and his credit is " + (player.money));
                hands_count[3]++;
                break;
            case 3: // player has a THREE OF A KIND
                System.out.println("\nplayer wins with a THREE OF A KIND and his credit is " + (player.money));
                hands_count[2]++;
                break;
            case 2: // player has a TWO PAIR
                System.out.println("\nplayer wins with a TWO PAIR and his credit is " + (player.money));
                hands_count[1]++;
                break;
            case 1: // player has a JACKS OR BETTER
                System.out.println("\nplayer wins with a JACKS OR BETTER and his credit is " + (player.money));
                hands_count[0]++;
                break;
            default:
                System.out.println("\nplayer loses and his credit is " + (player.money));
                hands_count[9]++;
                break;
        }

    }

}
