package server;

import client.model.Player;
import client.model.map.GameMap;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to represent server answer contains map and flags for communication
 */
public class Answer implements Serializable {
    private GameMap map;
    private boolean hasSendMove = false; //reconnect variable
    private boolean WrongNickPassword = false; //reconnect flag if true then server refused connection wrong nick
    private boolean gameWon = false;
    private Player Winner = null;
    private ArrayList<String> serverComunicates=new ArrayList<>();

    public Answer(GameMap map, ArrayList<String> coms) {
        this.serverComunicates =coms;
        this.map = map;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public void setServerComunicates(ArrayList<String> s){
        this.serverComunicates=s;
    }

    public Player getWinner() {
        return Winner;
    }

    public void setWinner(Player winner) {
        Winner = winner;
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public boolean isWrongNickPassword() {
        return WrongNickPassword;
    }

    public void setWrongNickPassword(boolean wrongNickPassword) {
        WrongNickPassword = wrongNickPassword;
    }

    public boolean hasSendMove() {
        return hasSendMove;
    }

    public void setHasSendMove(boolean hasSendMove) {
        this.hasSendMove = hasSendMove;
    }

}
