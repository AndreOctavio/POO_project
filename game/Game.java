package game;

import deck.Card;
import java.util.ArrayList;

public interface Game {
    
    public void Commands(String c);
    public void deal();
    public void bet(int m);
    public void credit();
    public String advice (ArrayList<Card> h);
    public String statistics ();

}
