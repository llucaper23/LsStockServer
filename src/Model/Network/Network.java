package Model.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Network {
    private static final int PORT = 34568;
    private ServerSocket serverSocket;

    private ArrayList<NetworkThread> networkThreadList;
    private boolean isRunning;

    public Network() {
        networkThreadList = new ArrayList<>();
        serverSocket = null;
    }

    public void run() {
        try {

            ServerSocket serverSocket = new ServerSocket(PORT);
            isRunning = true;

            while (isRunning) {
                System.out.println("Waiting for a client...");
                Socket socket = serverSocket.accept();

                NetworkThread networkThread = new NetworkThread(socket, networkThreadList);
                networkThreadList.add(networkThread);
                networkThread.start();
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
