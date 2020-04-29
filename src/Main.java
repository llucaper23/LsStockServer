import Controller.BotsViewController;
import Controller.MainViewController;
import Controller.Top10ViewController;
import Model.Network.Server;
import View.MainView;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
        MainView mainView = new MainView();
        MainViewController mvc = new MainViewController(mainView, server);
        mainView.setVisible(true);
        //BotsViewController botsController = new BotsViewController();
    }
}
