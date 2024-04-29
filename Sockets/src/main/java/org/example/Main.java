package org.example;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

class SocketTCP {
    private String ping;
    private int port;
    private volatile int value;
    private SocketTCP nextSocket;
    private ServerSocket serverSocket;

    public SocketTCP(String ping, int port) {
        this.ping = ping;
        this.port = port;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void sendValue(int value) {
        try {
            Socket clientSocket = new Socket(this.ping, nextSocket.getPort());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(value);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public void receiveValue(int value) {
        this.value = value;
        System.out.println("The socket with the ping " + ping + " received the value " + value);
        if (value < 100) {
            sendValue(value + 1);
        } else {
            System.out.println("Socket with the ping " + ping + " reached 100. Exiting...");
        }
    }

    public void setNextSocket(SocketTCP nextSocket) {
        this.nextSocket = nextSocket;
    }

    public void start() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    int value = Integer.parseInt(in.readLine());
                    receiveValue(value);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (serverSocket != null) {
                        serverSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

public class Main {
    public static void main(String[] args) {
        SocketTCP socket1 = new SocketTCP("127.0.0.15", 5001);
        SocketTCP socket2 = new SocketTCP("127.0.0.16", 5002);
        SocketTCP socket3 = new SocketTCP("127.0.0.17", 5003);
        socket1.setNextSocket(socket2);
        socket2.setNextSocket(socket3);
        socket3.setNextSocket(socket1);

        socket1.setValue(0);
        socket1.start();

        socket2.setValue(0);
        socket2.start();

        socket3.setValue(0);
        socket3.start();

        socket1.sendValue(1);
    }
}
