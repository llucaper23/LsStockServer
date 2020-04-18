package Model.Network;

import Model.Database.DAO.*;
import Model.User;

import java.io.*;
import java.net.Socket;

public class DedicatedServer extends Thread {

    private boolean isOn;
    private UserDAO userDAO;
    private UserCompanyDAO userCompanyDAO;
    private HistoryDAO historyDAO;
    private CompanyDAO companyDAO;
    private BotDAO botDAO;
    private Socket sClient;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    private Server server;
    private static final int REGISTER_REQUEST = 1;
    private static final int LOGIN_REQUEST = 2;
    private static final int UPDATE_MONEY = 3;
    private static final int BUY_SHARES = 4;
    private static final int SELL_SHARES = 5;
    private static final int USER_COMPANIES = 6;
    private static final int ALL_COMPANIES = 7;
    private static final int COMPANY_DETAIL = 8;
    private static final int LOGOUT = 9;

    public DedicatedServer(Socket sClient, Server server) {
        this.isOn = false;
        this.sClient = sClient;
        userDAO = new UserDAO();
        try {
            // creem els canals de comunicacio
            this.objectOut = new ObjectOutputStream(sClient.getOutputStream());
            this.objectIn = new ObjectInputStream(sClient.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.server = server;
    }


    public void startDedicatedServer() {
        // iniciem el servidor dedicat
        isOn = true;
        this.start();
    }

    public void stopDedicatedServer() {
        // aturem el servidor dedicat
        this.isOn = false;
        this.interrupt();
        // eliminem el servidor dedicat del conjunt de servidors dedicats
        server.remove(this);
    }

    public void run() {
        try {
            while (isOn) {
                int option = objectIn.read();
                if (option == REGISTER_REQUEST){
                    User registerUser = (User) objectIn.readObject();
                    boolean registerOk = userDAO.registerUser(registerUser);
                    objectOut.writeBoolean(registerOk);
                    objectOut.flush();
                }
                if (option == LOGIN_REQUEST) {
                    User userLogin = (User) objectIn.readObject();
                    boolean loginOk = userDAO.canUserLogin(userLogin);
                    objectOut.writeBoolean(loginOk);
                    objectOut.flush();
                    objectOut.writeObject(userLogin);
                }
            }
        } catch (IOException e1) {
            // en cas derror aturem el servidor dedicat
            stopDedicatedServer();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
