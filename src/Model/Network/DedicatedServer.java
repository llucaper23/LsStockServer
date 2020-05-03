package Model.Network;

import Model.Company;
import Model.Database.DAO.*;
import Model.Message;
import Model.User;

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
                if (message.getRequestType() == LOGIN_REQUEST) {
                    message.setOk(userDAO.canUserLogin(message.getUser()));
                    if (message.isOk()){
                        this.user = userDAO.getUser(message.getUser().getNickName(), message.getUser().getEmail());
                        message.setUser(this.user);
                        objectOut.writeObject(message);
                        objectOut.flush();
                    }
                }
                if (message.getRequestType() == ALL_COMPANIES){
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
                }
                if (message.getRequestType() == LOGOUT) {
                   userDAO.logOut(user);
                }
                /*
                if (option == UPDATE_MONEY) {
                    User actualUser = (User) objectIn.readObject();
                    float updatedMoney = objectIn.readFloat();

                    //??? - actualitzar el valor de updatedMoney a actualUser. Buscar-lo entre tots users registrats

                }
                if (option == SELL_SHARES) {
                    User actualUser = (User) objectIn.readObject();
                    Company actualCompany = (Company) objectIn.readObject();
                    float soldShares = objectIn.readFloat();

                    //??? - actualitzar info a bbdd
                }
                if (option == BUY_SHARES) {
                    User actualUser = (User) objectIn.readObject();
                    Company actualCompany = (Company) objectIn.readObject();
                    float boughtShares = objectIn.readFloat();

                    //??? - actualitzar info a bbdd
                }
                 */
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
            objectOut.writeInt(ALL_COMPANIES);
            objectOut.flush();
            objectOut.writeInt(companies.size());
            objectOut.flush();
            for (Company c : companies) {
                objectOut.writeObject(c);
                objectOut.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
