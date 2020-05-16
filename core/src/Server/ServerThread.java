package Server;

import Client.Controller.Turn;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * test
 */
public class ServerThread extends Thread {
    public final Object lock = new Object();
    public ObjectOutputStream os;
    public String name;
    public boolean reciever;
    public Turn recieved;
    public ObjectInputStream is;
    boolean exit;
    private Socket socket;

    public ServerThread(Socket sock, ObjectInputStream is, ObjectOutputStream os, String name) throws IOException {
        System.out.println("Creating thread");
        socket = sock;
        this.is = is;
        this.os = os;
        this.name = name;
        this.start();
    }

    @Override
    public void run() {
        System.out.println("Running");
        while (!exit) {
            reciever = false;
            try {
                this.recieved = (Turn) is.readObject();
                System.out.println("received object from " + name);
                reciever = true;
                synchronized (lock) {
                    {
                        System.out.println("lock " + name);
                        if (!Server.check())
                            lock.wait();
                    }
                }

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                Server.removeClient(this);
                System.out.println("disconnect " + name);
                this.dispose();
            }

        }
    }

    public void dispose() {
        exit = true;
    }
}