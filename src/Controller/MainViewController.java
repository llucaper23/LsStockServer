package Controller;

import Model.Company;
import Model.Database.DAO.CompanyDAO;
import Model.Database.DAO.UserCompanyDAO;
import Model.Database.DAO.UserDAO;
import Model.Manager;
import Model.Network.Server;
import View.MainView;
import View.UserShareView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainViewController implements ActionListener {

    private MainView mainView;
    private Server server;
    private UserShareView userShareWindow;
    private UserDAO userDAO;
    private UserCompanyDAO userCompanyDAO;
    private Manager manager;

    public MainViewController(MainView mainView, Server server, Manager manager, UserShareView userShareWindow) {
        this.mainView = mainView;
        this.server = server;
        this.userShareWindow = userShareWindow;
        userDAO = new UserDAO();
        userCompanyDAO = new UserCompanyDAO();
        this.manager = manager;
        this.userShareWindow = userShareWindow;
        userShareWindow.updateUsers(userDAO.getAllUsers(), this);
        userShareWindow.updateUserCompanies(manager.getUserCompanies(userCompanyDAO.getAllCompaniesFromUser(1), 1));

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("USER")) {
            int userId = Integer.parseInt(((JButton)e.getSource()).getClientProperty("user_id").toString());
            userShareWindow.updateUserCompanies(manager.getUserCompanies(userCompanyDAO.getAllCompaniesFromUser(userId), userId));
        }
    }
}
