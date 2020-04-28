package Controller;

import Model.Company;
import Model.Database.DAO.CompanyDAO;
import Model.Network.Server;
import View.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainViewController implements ActionListener {

    private MainView mainView;
    private Server server;

    public MainViewController(MainView mainView, Server server) {
        this.mainView = mainView;
        this.server = server;

    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }
}
