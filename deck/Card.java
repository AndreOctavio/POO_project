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
        } else if (v == 65) {
            value = 62;
        } else {
            value = v;
        }

        naipe = n;
    }

    /**
     * Reverses changes made to card a in the constructor. 
     * 
     * @param a card
     * @return normal card
     */
    public Card reverse (Card a) {

        Card tmp = new Card (a.value, a.naipe); 

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
