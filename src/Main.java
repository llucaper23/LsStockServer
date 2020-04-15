import Controller.MainViewController;
import Model.Network.Server;
import View.MainView;

public class Main {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        Server server = new Server();
        server.startServer();
        MainViewController mvc = new MainViewController(mainView, server);
        mainView.setVisible(true);
    }
}
