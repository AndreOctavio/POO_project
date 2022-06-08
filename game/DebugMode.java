package game;

import deck.Card;
import java.util.ArrayList;
import java.util.List;

 public class DebugMode extends Game{

    public DebugMode (int m) {
        super(m);
    }

    public void Commands(String c) {
        
        String [] commandsArray = c.split (" ");
        List<Integer> hold = new ArrayList<Integer>();
        int i = 0, n, bet = 0, p_bet = 5,deal = -1;

        while (i < commandsArray.length) {
            if (commandsArray [i] == "b") {
                try {
                    bet = Integer.parseInt(commandsArray [i + 1]);
                }
                catch (NumberFormatException e) {
                    bet = p_bet;
                    i--;
                }

                if (deal == 1) {
                    System.out.println("\nb: illegal command");
                } else if (bet < 0 || bet > 5) {
                    System.out.print("\n-cmd b " + commandsArray[i + 1]+ "\nb: illegal amount");
                } else {
                    deal = 0;
                    p_bet = bet;
                    bet(bet);
                }
                i+=2;
            } else if (commandsArray [i] == "$") {
                credit();
                i++;
            } else if (commandsArray [i] == "d"){
                if (deal != 0) {
                    System.out.print("\n-cmd d\nd: illegal command");
                } else {
                    deal = 1;
                    deal(player.hand);
                    System.out.print("\n-cmd d\nplayer's hand ");
                    for (Card tmp:player.hand) {
                        System.out.print(tmp.reverse(tmp)+" ");
                    }
                }
                i++;
            } else if (commandsArray [i] == "h") {
                    int tmp;

                    for (n = 0; n < 5; n++) {
                        try {
                            tmp = Integer.parseInt(commandsArray [i + n]);
                        }
                        catch (NumberFormatException e) {
                            break;
                        }
                        hold.add(tmp);
                    }

                    i+=hold.size();

                    System.out.print("\n-cmd h ");

                    for (n = 0; n < hold.size(); n++) {
                        System.out.print(hold.get(n)+" ");
                    }
                    
                    if (deal == 1) {
                        doHold(hold);
                        deal = -1;
                    } else {
                        System.out.print("\nh: illegal command");
                    }
                    hold.clear();
            } else if (commandsArray [i] == "a"){
                System.out.print("\n-cmd a");
                if (deal == 1) {
                    hold = advice(player.hand);
                    System.out.print("\nplayer should hold cards ");

                    for (n = 0; n < hold.size(); n++) {
                        System.out.print(hold.get(n)+" ");
                    }
                } else {
                    System.out.print("\na: illegal command");
                }

                i++;
            } else if (commandsArray [i] == "s") {
                System.out.print("\n-cmd s");
                statistics();
            }
        }
    }
}
