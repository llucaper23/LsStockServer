import Controller.BotsViewController;
import Controller.MainViewController;
import Controller.Top10ViewController;
import Model.Network.Server;
import View.MainView;
import View.Top10View;

public class Main {
    public static void main(String[] args) {
        Top10View top10View = new Top10View();
        MainView mainView = new MainView(top10View);
        Server server = new Server();
        server.startServer();
        MainViewController mvc = new MainViewController(mainView, server);
        mainView.setVisible(true);
        //BotsViewController botsController = new BotsViewController();
    }
}
