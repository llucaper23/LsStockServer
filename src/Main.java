import Controller.BotsViewController;
import Controller.MainViewController;
import Controller.Top10ViewController;
import Model.Bot;
import Model.Network.Server;
import View.BotsView;
import View.MainView;
import View.Top10View;

public class Main {
    public static void main(String[] args) {
        Top10View top10View = new Top10View();
        BotsView botsView = new BotsView();

        MainView mainView = new MainView(top10View,botsView);
        Server server = new Server();
        server.startServer();
        MainViewController mvc = new MainViewController(mainView, server);
        Top10ViewController t10c = new Top10ViewController(top10View);
        BotsViewController botsVC = new BotsViewController(botsView);
        botsView.registerController(botsVC);
        botsVC.refreshNewData();        // necesari per a l'incorporacio dels listneres al ultim lllistat de bots creats



        mainView.setVisible(true);
        //BotsViewController botsController = new BotsViewController();
    }
}
