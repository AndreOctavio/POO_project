package src.deck;

public class Card {

    public char value;
    public char suit;

    /**
     * Constructs a Card with value v and suit n.
     * 
     * @param v value
     * @param n suit
     */
    public Card(char v, char n) {

        switch (v) { //if it's a A, K, Q, J or T we change their values to the values of the ASCII table.
                     //So the value 9 is 57 in the ASCII table, so T will be 58 and so on.
            case 84:
                value = 58;
                break;
            case 74:
                value = 59;
                break;
            case 81:
                value = 60;
                break;
            case 75:
                value = 61;
                break;
            case 65:
                value = 62;
                break;
            default:
                if (v > 49 && v < 63) { //v < 63 in this condition because in simulation mode 
                    value = v;          //our deck has the values that we want already
                } else {
                    System.out.println("Invalid card in card-file, please use a valid file.");
                    System.exit(0);
                }
        }

        if (n == 'D' || n == 'C' || n == 'H' || n == 'S') { //check if the card has a valid suit
            suit = n;
        } else {
            System.out.println("Invalid card in card-file, please use a valid file.");
            System.exit(0);
        }
    }

    /**
     * Reverses changes made to card a in the constructor.
     * 
     * @param a card
     * @return a normal card
     */
    public Card reverse(Card a) {

        Card tmp = new Card(a.value, a.suit);

        switch (a.value) {
            case 58:
                tmp.value = 84;
                break;
            case 59:
                tmp.value = 74;
                break;
            case 60:
                tmp.value = 81;
                break;
            case 61:
                tmp.value = 75;
                break;
            case 62:
                tmp.value = 65;
                break;
        }

        return tmp;
    }

    public String toString() {
        return String.valueOf(this.value) + String.valueOf(this.suit);
    }
}
