package main;

import game.DebugMode;
/*import game.SimulationMode;*/
import game.SimulationMode;

import java.util.Scanner;

import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors

public class Main {
    public static void main(String[] args) {

        String commands_input = "";
        String cards_input = "";
        int credit = 0;

        if (args[0].equals("-d")) { // DEBUG MODE
            try {
                credit = Integer.parseInt(args[1]); // verifies if the credit argument is an integer
            } catch (Exception e) {
                System.out.println("error in the credit value"); // a valid credit was not inserted
                System.exit(0);
            }

            if (!(args[2].equals("cmd-file")) || !(args[3].equals("card-file"))) { // detection of illegal file names
                System.out.println("error: wrong file name");
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
                System.out.println("error in the parameters");
                System.exit(0);
            }

            SimulationMode sm = new SimulationMode(credit, nbdeals, bet);

            for (int i = nbdeals; i > 0; i--) {
                sm.bet(bet);
                sm.deal();
                sm.hold = sm.advice(sm.player.hand);
                sm.doHold(sm.hold);
                sm.identifyHand(sm.player.hand, bet);
                sm.hold.removeAll(sm.hold);
                sm.excluded.removeAll(sm.excluded);
            }

            sm.statistics((sm.player.sum_of_all_gains/ sm.sum_of_all_bets) * 100);

        } 

    }

}
