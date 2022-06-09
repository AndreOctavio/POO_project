package game;

import deck.Card;
import java.util.ArrayList;
import java.util.List;

 public class DebugMode extends Game{

    /**
     * Constructs DebugMode, player with money m. 
     * 
     * @param m money
     * @param d String with all the cards from card-file
     */
    public DebugMode (int m, String d) {
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

        String [] commandsArray = c.split (" "); //commandsArray has all the strings from cmd-file
        List<Integer> hold = new ArrayList<Integer>(); //hold is used to know which cards to keep
        int i = 0, n, bet = 0, p_bet = 5,deal = -1, illegal = 0; 

        while (i < commandsArray.length) {
            if (commandsArray [i].equals("b")) { //command bet
                try {
                    bet = Integer.parseInt(commandsArray [i + 1]); //convert the string next to b into an int
                }
                catch (NumberFormatException e) {   //if it can't do it, then it's another command, bet amout is equal to last bet (5 if first bet)
                    bet = p_bet;
                    i--;
                }

                System.out.print("\n-cmd b ");

                if (deal != -1) {  //check if bet command is llegal
                    System.out.println("\nb: illegal command\n");
                } else if (bet < 0 || bet > 5) { //check if bet amout is between [1,5]
                    System.out.print(commandsArray[i + 1]+ "\nb: illegal amount\n");
                } else {
                    deal = 0;
                    p_bet = bet;
                    bet(bet);
                    System.out.println("\nplayer is betting " + bet);
                }
                i+=2;
            } else if (commandsArray [i].equals("$")) { //command credit
                credit();
                i++;
            } else if (commandsArray [i].equals("d")){ //command deal
                if (deal == 0) { //check if deal is llegal
                    deal = 1;
                    deal();
                    System.out.print("\n-cmd d\nplayer's hand ");
                    for (Card tmp:player.hand) { //print player's hand
                        System.out.print(tmp.reverse(tmp)+" ");
                    }
                } else {
                    System.out.print("\n-cmd d\nd: illegal command\n");
                }
                i++;
            } else if (commandsArray [i].equals("h")) { //command hold
                    int tmp;

                    for (n = 0; n < 5; n++) { //get the cards the player wants to hold
                        try {
                            tmp = Integer.parseInt(commandsArray [i + n]);
                        }
                        catch (NumberFormatException e) {
                            break;
                        }
                        if (tmp < 1 || tmp > 5) {
                            illegal = 1;
                        }
                        hold.add(tmp); //add integers to hold
                    }

                    i+=hold.size() + 1;

                    System.out.print("\n-cmd h ");

                    for (n = 0; n < hold.size(); n++) { //print the integers read next to h command
                        System.out.print(hold.get(n)+" "); 
                    }
                    
                    if (deal == 1 && illegal != 1) { //check if command h is llegal
                        doHold(hold);
                        System.out.print("\nplayer's hand ");

                        for (Card tmp2:player.hand) { //print player's hand
                            System.out.print(tmp2.reverse(tmp2)+" ");
                        }

                        deal = -1;
                        strat();
                        player.hand.clear();
                        deckOfcards.excluded.clear();
                    } else {
                        System.out.println("\nh: illegal command");
                        illegal = 0;
                    }
                    hold.clear();
            } else if (commandsArray [i].equals("a")){ //command advice
                System.out.print("\n-cmd a");
                if (deal == 1) { //check if command a is llegal
                    hold = advice(player.hand);
                    System.out.print("\nplayer should hold cards ");

                    for (n = 0; n < hold.size(); n++) { //print which cards the player needs to hold
                        System.out.print(hold.get(n)+" ");
                    }
                } else {
                    System.out.print("\na: illegal command\n");
                }

                i++;
            } else if (commandsArray [i].equals("s")) { //command statistics
                System.out.print("\n-cmd s");
                statistics();
            } else {
                System.out.print("\nillegal command");
            }
        } 
    }
}
