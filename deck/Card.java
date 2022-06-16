package deck;

public class Card {

    /* Put verifications for card */
    public char value;
    public char naipe;

    /**
     * Constructs a Card with value v and naipe n.
     * 
     * @param v value
     * @param n naipe
     */
    public Card(char v, char n) {

        switch (v) {
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
                if (v > 49 && v < 63) {
                    value = v;
                } else {
                    System.out.println("Invalid card in card-file, please use a valid file.");
                    System.exit(0);
                }
        }

        naipe = n;
    }

    /**
     * Reverses changes made to card a in the constructor.
     * 
     * @param a card
     * @return normal card
     */
    public Card reverse(Card a) {

        Card tmp = new Card(a.value, a.naipe);

        if (a.value == 58) {
            tmp.value = 84;
        } else if (a.value == 59) {
            tmp.value = 74;
        } else if (a.value == 60) {
            tmp.value = 81;
        } else if (a.value == 61) {
            tmp.value = 75;
        } else if (a.value == 62) {
            tmp.value = 65;
        }

        return tmp;
    }

    public String toString() {
        return String.valueOf(this.value) + String.valueOf(this.naipe);
    }
}
