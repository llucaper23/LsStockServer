package Controller;

import Model.Company;
import Model.Database.DAO.CompanyDAO;

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





    public BotsBuyThread(int botid,float tempsActivacio, int percentatgeCompra, Company companyia) {
        this.tempsActivacio =  (int) tempsActivacio;
        this.percentatgeCompra = percentatgeCompra;
        this.companyia = companyia;
        this.botid = botid;
        random = new Random();
        funciona =true;
        isActive = true;
    }


    public void run(){

        while(funciona){

            if (isActive){
                //System.out.println("Soc el bot"+ this.botid+" i estic funcionant");
                int nombreRandom = random.nextInt(100);
                float preuactual = companyia.getSharePrice(); // Nose Rick, no l'ahuriem d'agar el meu actualitzat de la BBDD

                if(nombreRandom <= percentatgeCompra){      //cas que compra
                    preuactual = (float) (preuactual*1.01); // augmentem el preu un 1%
                    System.out.println("BOT: "+ this.botid + " - BUY: "+preuactual);
                }else{                                      // cas que ven
                    preuactual = (float) (preuactual*0.99); // decrementem el preu un 1%
                    System.out.println("BOT: "+ this.botid + " - SELL: "+preuactual);
                }

                companyia.setSharePrice(preuactual); // actualizem a preu a companyia?
                companyies.setSharePrice(companyia.getCompanyId(),preuactual);

            }else{      // cas descativat--> no fara res
                //System.out.println("Soc el bot"+ this.botid+" i estic APAGAT");
            }

            if(funciona){
                try {
                    TimeUnit.SECONDS.sleep(tempsActivacio);     // aqui dormim una estoneta
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }



        }
        System.out.println("BOT: " + this.botid + " - DELETED");


    }

    public synchronized void eliminaBot() {     // el bot no s'eliminara fins que hagui passt el seu temps , pero ens assegura que no tornara a comprar
        funciona = false;
    }

    public synchronized void activaBot() {
        isActive = true;
    }

    public synchronized void desactivaBot() {
        isActive = false;
    }

    public synchronized void setStateCarregaInicial(boolean isActive){
        this.isActive = isActive;
    }

    public int getBotid() {
        return botid;
    }
}
