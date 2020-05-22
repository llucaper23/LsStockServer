package Controller;

public class UserThread extends Thread {
    private MainViewController mainViewController;

    public UserThread(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    /**
     * Procediment run del thread que actualitza la info dels usuaris a la finestra principal.
     */
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
