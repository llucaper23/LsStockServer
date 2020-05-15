package Model.Network;

import Model.Company;
import Model.Database.DAO.CompanyDAO;
import Model.Database.DAO.UserDAO;
import Model.Database.DBConnector;
import Model.Manager;
import Model.User;
import View.MainView;
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
    private Manager manager;
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
            manager = new Manager();
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
                    UserDAO userDAO = new UserDAO();
                    userDAO.logOutAllUsers();
                    manager.stopAllHistories();
                    stopServer();
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateAllClients () {
        for (DedicatedServer dServer : dedicatedServerList) {
            dServer.updateAllCompanies();
        }
    }

    public void remove (DedicatedServer dedicatedServer) {
        dedicatedServerList.remove(dedicatedServer);
        // invoquem el metode del servidor que mostra els servidors dedicats actuals
        showClients();
    }
}

