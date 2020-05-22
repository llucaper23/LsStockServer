package Controller;

import java.util.concurrent.TimeUnit;

public class Top10Thread extends Thread {

    private Top10ViewController controladortop10;

    public Top10Thread(Top10ViewController controladortop10) {
        this.controladortop10 = controladortop10;
    }


    /**
     * Procediment run del thread que controla el mostratge del Top 10 copanyies.
     */
    public void run(){

        while (true){
            controladortop10.actualitzaTop10();
            try {
                sleep(1000);     // aqui dormim una estoneta

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
