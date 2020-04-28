import Controller.BotsViewController;
import Controller.MainViewController;
import Controller.Top10ViewController;
import Model.Network.Server;
import View.MainView;
import View.Top10View;

public class Main {
    public static void main(String[] args) {
        Top10View t10v = new Top10View();

        MainView mainView = new MainView(t10v);
        Server server = new Server();
        server.startServer();
        MainViewController mvc = new MainViewController(mainView, server);
        Top10ViewController t10c = new Top10ViewController(t10v);
        mainView.setVisible(true);
        //BotsViewController botsController = new BotsViewController();
    }
}
