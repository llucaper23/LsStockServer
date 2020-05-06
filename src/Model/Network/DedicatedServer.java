package Model.Network;

import Model.*;
import Model.Database.DAO.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class DedicatedServer extends Thread {

    private boolean isOn;
    private UserDAO userDAO;
    private User user;
    private UserCompanyDAO userCompanyDAO;
    private HistoryDAO historyDAO;
    private CompanyDAO companyDAO;
    private BotDAO botDAO;
    private Socket sClient;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    private ObjectOutputStream updateClient;
    private Server server;
    private int port;
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
        companyDAO = new CompanyDAO();
        userCompanyDAO = new UserCompanyDAO();
        try {
            // creem els canals de comunicacio
            this.objectOut = new ObjectOutputStream(sClient.getOutputStream());
            this.objectIn = new ObjectInputStream(sClient.getInputStream());
            this.port = objectIn.readInt();
            sleep(100);
            Socket clientUpdates = new Socket("localhost", port);
            updateClient = new ObjectOutputStream(clientUpdates.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
                Message message = (Message) objectIn.readObject();
                if (message.getRequestType() == REGISTER_REQUEST){
                    message.setOk(userDAO.registerUser(message.getUser()));
                    objectOut.writeObject(message);
                    objectOut.flush();
                }
                if (message.getRequestType() == LOGIN_REQUEST){
                    message.setOk(userDAO.canUserLogin(message.getUser()));
                    if (message.isOk()){
                        this.user = userDAO.getUser(message.getUser().getNickName(), message.getUser().getEmail());
                        message.setUser(this.user);
                        objectOut.writeObject(message);
                        objectOut.flush();
                    }
                }
                if (message.getRequestType() == LOGOUT) {
                   userDAO.logOut(user);
                }

                if (message.getRequestType() == UPDATE_MONEY) {
                    User user = message.getUser();
                    userDAO.setMoney(user);
                    message.setOk(true);
                    objectOut.writeObject(message);
                    objectOut.flush();
                }

                if (message.getRequestType() == SELL_SHARES) {
                    UserCompany userCompany = message.getUserCompany();
                    userCompanyDAO.insertBuy(userCompany);
                    int auxId = userCompany.getCompanyId();
                    float auxPrice = userCompany.getBuyPrice();
                    auxPrice = (float) (auxPrice - auxPrice * 0.01);
                    companyDAO.setSharePrice(auxId, auxPrice);
                    message.setOk(true);
                    objectOut.writeObject(message);

                }

                if (message.getRequestType() == BUY_SHARES) {
                    UserCompany userCompany = message.getUserCompany();
                    userCompanyDAO.insertBuy(userCompany);
                    int auxId = userCompany.getCompanyId();
                    float auxPrice = userCompany.getBuyPrice();
                    auxPrice = (float) (auxPrice * 0.01 + auxPrice);
                    companyDAO.setSharePrice(auxId, auxPrice);
                    message.setOk(true);
                    objectOut.writeObject(message);
                    server.updateAllClients();
                }

                if (message.getRequestType() == ALL_COMPANIES){
                    ArrayList<Company> companies = companyDAO.getAllCompanies();
                    message.setCompanyList(companies);
                    message.setOk(true);
                    objectOut.writeObject(message);
                    objectOut.flush();
                }

            }
        } catch (IOException e1) {
            // en cas derror aturem el servidor dedicat
            userDAO.logOut(user);
            stopDedicatedServer();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateAllCompanies(){
        try {
            ArrayList<Company> companies = companyDAO.getAllCompanies();
            Message message = new Message(ALL_COMPANIES, companies,null, false, null, null, null);
            if (companies == null){
                message.setOk(false);
                updateClient.writeObject(message);
                updateClient.flush();
            }else{
                message.setCompanyList(companies);
                updateClient.writeObject(message);
                updateClient.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
