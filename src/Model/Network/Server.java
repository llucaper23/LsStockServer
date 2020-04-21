package Model.Network;

import Model.Database.DBConnector;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{

    private ServerSocket serverSocket;
    private ArrayList<DedicatedServer> dedicatedServerList;
    private boolean isRunning;
    private boolean isOn;
    NetworkConfiguration nc;

    public Server() {
        Gson gson = new Gson();
        String path = "data/config.json";
        try {
            //llegim json
            JsonReader reader = new JsonReader(new FileReader(path));
            this.nc = gson.fromJson(reader, NetworkConfiguration.class);
            isOn = false;
            dedicatedServerList = new ArrayList<>();
            this.serverSocket = new ServerSocket(nc.getServerPort());
            DBConnector.init(nc);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

            isRunning = true;
            while (isRunning) {
                System.out.println("Waiting for a client...");
                Socket socket = serverSocket.accept();

                DedicatedServer dedicatedServer = new DedicatedServer(socket,this);
                dedicatedServerList.add(dedicatedServer);
                dedicatedServer.startDedicatedServer();
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

    void updateAllClients () {
        ObjectOutputStream outStream;
        for (DedicatedServer dServer : dedicatedServerList) {

        }
    }

    void remove (DedicatedServer dedicatedServer) {
        dedicatedServerList.remove(dedicatedServer);
        // invoquem el metode del servidor que mostra els servidors dedicats actuals
        showClients();
    }
}

