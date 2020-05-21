package Controller;

public class UserThread extends Thread {
    private MainViewController mainViewController;

    public UserThread(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void run(){
        while (isAlive()){
            mainViewController.actualitzaUser();
            try {
                sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
