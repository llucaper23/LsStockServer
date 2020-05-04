package Model.Network;

import Model.Company;
import Model.Database.DAO.*;
import Model.Message;
import Model.User;
import Model.UserCompany;

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
        companyDAO = new CompanyDAO();
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
                        message.setCompanyList(companyDAO.getAllCompanies());
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
                }

                if (message.getRequestType() == SELL_SHARES) {
                    UserCompany userCompany = message.getUserCompany();
                    userCompanyDAO.insertBuy(userCompany);
                    //que fem al vendre accions a la BBDD????
                }

                if (message.getRequestType() == BUY_SHARES) {
                    UserCompany userCompany = message.getUserCompany();
                    userCompanyDAO.insertBuy(userCompany);
                    message.setOk(true);
                    objectOut.writeObject(message);
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

    public void updateAllCompanies(Message message){
        try {
            ArrayList<Company> companies = companyDAO.getAllCompanies();
            if (companies == null){
                message.setOk(false);
                objectOut.writeObject(message);
                objectOut.flush();
            }else{
                message.setCompanyList(companies);
                objectOut.writeObject(message);
                objectOut.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
