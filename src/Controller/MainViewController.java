package Controller;

import Model.Network.Network;
import View.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainViewController implements ActionListener {

    private MainView mainView;
    private Network network;

    public MainViewController(MainView mainView, Network network) {
        this.mainView = mainView;
        this.network = network;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
