package game;

import deck.Card;
import player.Player;
import java.util.List;

abstract class Game {

    public Player player;

    public Game (int m){
        player = new Player(m);
    }

    public void deal(List<Card> hand) 
    {}
    public void bet(int m){};
    public void credit() {
        System.out.println("-cmd $\nplayer's credit is"+player.money+"\n");
    }
    public List<Integer> advice (List<Card> h) {
    }
    public void statistics () {};
    public void doHold (List <Integer> h) {
    }

}
