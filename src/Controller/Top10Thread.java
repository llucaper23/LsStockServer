package Controller;

import java.util.concurrent.TimeUnit;

public class Top10Thread extends Thread {

    private Top10ViewController controladortop10;
    final int TEMPS_ACTUALITZACIO_EN_SECS = 1;

    public Top10Thread(Top10ViewController controladortop10) {
        this.controladortop10 = controladortop10;
    }

    public void run(){

        while (true){
            controladortop10.actualitzaTop10();
            try {
                TimeUnit.SECONDS.sleep( TEMPS_ACTUALITZACIO_EN_SECS );     // aqui dormim una estoneta

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
