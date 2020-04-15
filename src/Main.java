import Controller.MainViewController;
import Controller.UserShareViewController;
import Model.Network.Network;
import View.MainView;

public class Main {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        Network network = new Network();

        MainViewController mvc = new MainViewController(mainView, network);
    }
}
