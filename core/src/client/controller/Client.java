package client.controller;

import client.model.Player;
import client.model.heroes.*;
import client.model.map.Field;
import client.model.skills.Walk;
import client.view.SwordGame;
import server.Answer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class to implement client server communication on client side
 */
public class Client {
    final Object lock = new Object();
    public ObjectInputStream is;
    public ObjectOutputStream os;
    public Socket sock;
    public boolean isReceived = false;
    boolean exit = false;
    private Turn send;
    private Answer received;
    private boolean isSend = false;
    public boolean wrongPass;

    public Client(SwordGame game, final boolean init) throws Exception {

        Socket s = new Socket("127.0.0.1", 1701);
        sock = s;
        is = new ObjectInputStream(s.getInputStream());
        os = new ObjectOutputStream(s.getOutputStream());
        Player player = new Player(game.nick, game.password);
        game.player = player;

        send = new Turn(player);

        receive();

        if (init) {
            createTurn(send, game);
        }

        send();

        Thread t = new Thread(() -> {
            try {
                receive();
                isReceived = true;
                isSend = received.hasSendMove();
                wrongPass = received.isWrongNickPassword();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            while (!exit) { //TODO stop this while from running whole time
                synchronized (lock) {
                    try {
                        if (send != null && !isSend && send.getMoves().size() == 4) {
                            send();
                            isReceived = false;
                        }
                        if (isSend) {
                            receive();
                            isReceived = true;
                            isSend = false;
                            send.clearMoves();


                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    public Turn getSend() {
        return send;
    }

    public boolean isSend() {
        return isSend;
    }

    public Answer getReceived() {
        return this.received;
    }

    private void createTurn(Turn turn, SwordGame game) {
        if (game.chosen[0]) {
            Archer hero = new Archer(turn.getOwner(), 5, 5);
            turn.addMove((new Move(hero, new Field(1, 1), new Field(1, 1), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if (game.chosen[1]) {
            Necromancer hero = new Necromancer(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(2, 2), new Field(2, 2), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if (game.chosen[2]) {
            Paladin hero = new Paladin(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(3, 3), new Field(3, 3), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if (game.chosen[3]) {
            Priest hero = new Priest(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(4, 4), new Field(4, 4), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if (game.chosen[4]) {
            Warrior hero = new Warrior(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(5, 5), new Field(5, 5), new Walk(5))));
            turn.getOwner().addHero(hero);
        }
        if (game.chosen[5]) {
            Wizard hero = new Wizard(turn.getOwner(), 3, 4);
            turn.addMove((new Move(hero, new Field(6, 6), new Field(6, 6), new Walk(5))));
            turn.getOwner().addHero(hero);
        }

    }

    public void dispose() {
        exit = true;
    }

    public synchronized void send() throws IOException {
        System.out.println("Sending...");
        os.reset();
        os.writeObject(send);
        send.clearMoves();
        isSend = true;
        os.flush();
    }

    public synchronized void receive() throws IOException, ClassNotFoundException {
        received = (Answer) is.readObject();
        System.out.println("Reading...");
    }


}