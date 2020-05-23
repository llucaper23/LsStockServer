package Controller;

import Model.Company;
import Model.Database.DAO.CompanyDAO;
import Model.Network.Server;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BotsBuyThread extends Thread   {

    private int tempsActivacio;
    private int percentatgeCompra;
    private Company companyia;
    private Random random;
    private Boolean funciona;
    private Boolean isActive;
    private CompanyDAO companyies = new CompanyDAO();
    private int botid;
    private Server server;

    public BotsBuyThread(int botid,float tempsActivacio, int percentatgeCompra, Company companyia, Server server) {
        this.tempsActivacio =  (int) tempsActivacio;
        this.percentatgeCompra = percentatgeCompra;
        this.companyia = companyia;
        this.botid = botid;
        random = new Random();
        funciona =true;
        isActive = true;
        this.server = server;
    }

    /**
     * Prodeciment run del thread que controla la compra/venta de un dels bots
     */
    public void run(){

        while(funciona){

            if (isActive) {
                int nombreRandom = random.nextInt(100);
                float preuactual = companyies.getCompany(companyia.getCompanyId()).getSharePrice();

                if (nombreRandom <= percentatgeCompra) {      //cas que compra
                    preuactual = (float) (preuactual * 1.01); // augmentem el preu un 1%
                    System.out.println("BOT: " + this.botid + " - BUY: " + preuactual);
                } else {                                      // cas que ven
                    preuactual = (float) (preuactual * 0.99); // decrementem el preu un 1%
                    System.out.println("BOT: " + this.botid + " - SELL: " + preuactual);
                }

                companyia.setSharePrice(preuactual); // actualizem a preu a companyia
                companyies.setSharePrice(companyia.getCompanyId(), preuactual);
                server.updateAllClients();
            }

            if(funciona){
                try {
                    TimeUnit.SECONDS.sleep(tempsActivacio);     // aqui dormim una estoneta
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    /**
     * Procediment que elimina un bot.
     */
    public synchronized void eliminaBot() {     // el bot no s'eliminara fins que hagui passt el seu temps , pero ens assegura que no tornara a comprar
        funciona = false;
    }

    /**
     * Procediment que activa un bot ja creat.
     */
    public synchronized void activaBot() {
        isActive = true;
    }

    /**
     * Procediment que desactiva un bot
     */
    public synchronized void desactivaBot() {
        isActive = false;
    }

    /**
     * Procediment que posa el estat inicial d'un bot.
     * @param isActive
     */
    public synchronized void setStateCarregaInicial(boolean isActive){
        this.isActive = isActive;
    }

    public int getBotid() {
        return botid;
    }
}
