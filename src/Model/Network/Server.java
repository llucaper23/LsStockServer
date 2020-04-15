package Model.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
    private static final int PORT = 34568;
    private ServerSocket serverSocket;

    private ArrayList<DedicatedServer> dedicatedServerList;
    private boolean isRunning;
    private boolean isOn;

    public Server() {
        isOn = false;
        dedicatedServerList = new ArrayList<>();
        serverSocket = null;
    }

    public void startServer() {
        // iniciem el thread del servidor
        isOn = true;
        this.start();
    }

    public void stopServer() {
        // aturem el thread del servidor
        isOn = false;
        this.interrupt();
    }

    public void showClients() {
        System.out.println("***** SERVER ***** (" + dedicatedServerList.size() +" clients / dedicated servers running)");
    }

    public void run() {
        try {

            ServerSocket serverSocket = new ServerSocket(PORT);
            isRunning = true;

            while (isRunning) {
                System.out.println("Waiting for a client...");
                Socket socket = serverSocket.accept();

                DedicatedServer dedicatedServer = new DedicatedServer(socket, dedicatedServerList);
                dedicatedServerList.add(dedicatedServer);
                dedicatedServer.start();
                System.out.println("Client connected");

            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
}
