import Controller.*;
import Model.Bot;
import Model.Manager;
import Model.Network.Server;
import View.BotsView;
import View.MainView;
import View.Top10View;
import View.UserShareView;

public class Main {
    public static void main(String[] args) {
        Top10View top10View = new Top10View();
        BotsView botsView = new BotsView();
        UserShareView userShareView = new UserShareView();
        Manager manager = new Manager();
        MainView mainView = new MainView(top10View,botsView, userShareView);
        Server server = new Server();
        server.startServer();
        MainViewController mvc = new MainViewController(mainView, server, manager, userShareView);
        Top10ViewController t10c = new Top10ViewController(top10View);
        Top10Thread actualitzacioTop10Time = new Top10Thread(t10c);     // actualitzacio cada X temps de la pestanya top10
        actualitzacioTop10Time.start();
        UserThread userThread = new UserThread(mvc);
        userThread.start();
        BotsViewController botsVC = new BotsViewController(botsView, server);
        botsView.registerController(botsVC);
        botsVC.refreshNewData();        // necesari per a l'incorporacio dels listneres al ultim lllistat de bots creats
        manager.initHistories();
        mainView.setVisible(true);
        //BotsViewController botsController = new BotsViewController();
    }
}
