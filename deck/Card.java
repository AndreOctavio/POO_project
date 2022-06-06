package deck;

public class Card {
    
    char value;
    char naipe;

    /**
     * Constructs a Card with value v and naipe n. 
     * 
     * @param v value
     * @param n naipe
     */

    public Card(char v, char n){

        if (v == 84) {
            value = 58;
        } else if (v == 74) {
            value = 59;
        } else if (v == 81) {
            value = 60;
        } else if (v == 75) {
            value = 61;
        }

        naipe = n;
    }
}
