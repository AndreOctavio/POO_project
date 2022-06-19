package src.main;

import java.util.Scanner;

import src.game.DebugMode;
import src.game.SimulationMode;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

        String commands_input = "";
        String cards_input = "";
        int credit = 0;

        if (args[0].equals("-d")) { // DEBUG MODE
            try {
                credit = Integer.parseInt(args[1]); // verifies if the credit argument is an integer
            } catch (Exception e) {
                System.out.println("error: wrong credit value"); // a valid credit was not inserted
                System.exit(0);
            }

            if (!(args[2].equals("cmd-file")) || !(args[3].equals("card-file"))) { // detection of illegal file names
                System.out.println("error: wrong file name");
                System.exit(0);
            } else {
                try {
                    File cmd_file = new File(args[2] + ".txt");
                    Scanner myReader = new Scanner(cmd_file);
                    while (myReader.hasNextLine()) {
                        commands_input = myReader.nextLine(); // commands_input saves the cmd-file commands
                    }
                    myReader.close();
                } catch (FileNotFoundException e) { // error reading from the cmd-file
                    System.out.println("error reading from command file");
                    System.exit(0);
                }

                try {
                    File card_file = new File(args[3] + ".txt");
                    Scanner myReader = new Scanner(card_file);
                    while (myReader.hasNextLine()) {
                        cards_input = myReader.nextLine(); // cards_input saves the card-file commands
                    }
                    myReader.close();
                } catch (FileNotFoundException e) { // error reading from the card-file
                    System.out.println("error reading from card file");
                    System.exit(0);
                }

                DebugMode dm = new DebugMode(credit, cards_input);
                dm.Commands(commands_input);
            }

        } else if (args[0].equals("-s")) { // SIMULATION MODE
            int bet = 0, nbdeals = 0;
            try {
                credit = Integer.parseInt(args[1]); // verifies if the credit argument is an integer
                bet = Integer.parseInt(args[2]); // verifies if the bet argument is an integer
                nbdeals = Integer.parseInt(args[3]); // verifies if the nbdeals argument is an integer

            } catch (Exception e) {
                System.out.println("error: wrong parameters");
                System.exit(0);
            }

            if (bet < 0 || bet > 5) {
                System.out.println("error: invalid bet amount " + bet);
                System.exit(0);
            }

            SimulationMode sm = new SimulationMode(credit, nbdeals, bet);

            for (int i = nbdeals; i > 0; i--) { //loop with all the plays we need to do
                sm.bet(bet);
                sm.deal();
                sm.hold = sm.MasterKey.advice(sm.player.hand); //use the hold returned by advice to make the correct play
                sm.doHold(sm.hold);

                switch (sm.MasterKey.identifyHand(sm.player.hand, bet)) {
                    case 11: // player has a ROYAL FLUSH
                        sm.hands_count[8]++;
                        break;
                    case 10: // player has a STRAIGHT FLUSH
                        sm.hands_count[7]++;
                        break;
                    case 9: // player has a FOUR ACES
                        sm.hands_count[6]++;
                        break;
                    case 8: // player has a FOUR 2-4
                        sm.hands_count[6]++;
                        break;
                    case 7: // player has a FOUR 5-K
                        sm.hands_count[6]++;
                        break;
                    case 6: // player has a FULL HOUSE
                        sm.hands_count[5]++;
                        break;
                    case 5: // player has a FLUSH
                        sm.hands_count[4]++;
                        break;
                    case 4: // player has a STRAIGHT
                        sm.hands_count[3]++;
                        break;
                    case 3: // player has a THREE OF A KIND
                        sm.hands_count[2]++;
                        break;
                    case 2: // player has a TWO PAIR
                        sm.hands_count[1]++;
                        break;
                    case 1: // player has a JACKS OR BETTER
                        sm.hands_count[0]++;
                        break;
                    default:
                        sm.hands_count[9]++;
                        break;
                }

                sm.player.hand.removeAll(sm.player.hand); //reset player.hand
                sm.hold.removeAll(sm.hold); //reset hold
                sm.excluded.removeAll(sm.excluded); //reset excluded
            }

            sm.statistics((sm.player.sum_of_all_gains / sm.sum_of_all_bets) * 100);

        } else { // wrong mode call
            System.out.println("error: invalid mode call");
            System.exit(0);
        }
    }
}
