package Controller;

import Model.Company;
import Model.Database.DAO.CompanyDAO;

import java.util.Random;

public class BotsBuyThread extends Thread{

    private int tempsActivacio;
    private int percentatgeCompra;
    private Company companyia;
    private Random random;
    private Boolean funciona;
    private Boolean isActive;
    private CompanyDAO companyies = new CompanyDAO();





    public BotsBuyThread(float tempsActivacio, int percentatgeCompra, Company companyia) {
        this.tempsActivacio =  (int) tempsActivacio;
        this.percentatgeCompra = percentatgeCompra;
        this.companyia = companyia;
        random = new Random();
        funciona =true;

    }


    public void Run() throws InterruptedException {

        while(funciona){

            if (isActive){
                int nombreRandom = random.nextInt(100);
                float preuactual = companyia.getSharePrice(); // Nose Rick, no l'ahuriem d'agar el meu actualitzat de la BBDD

                if(nombreRandom <= percentatgeCompra){      //cas que compra
                    preuactual = (float) (preuactual*1.01); // augmentem el preu un 1%
                }else{                                      // cas que ven
                    preuactual = (float) (preuactual*0.99); // decrementem el preu un 1%
                }

                companyia.setSharePrice(preuactual); // actualizem a preu a companyia? Nose Rick, no huria de ser a la BBDD
                companyies.setSharePrice(companyia);

            }else{      // cas descativat--> no fara res

            }

            if(funciona){
                Thread.sleep(tempsActivacio*1000);
            }



        }


    }

    public void EliminaBot() {
        funciona = false;
    }

    public void activaBot() {
        isActive = true;
    }

    public void desactivaBot() {
        isActive = false;
    }


}
