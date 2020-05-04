package Controller;

import Model.Bot;
import Model.BotThread;
import Model.Company;
import Model.Database.DAO.BotDAO;
import Model.Database.DAO.CompanyDAO;
import View.BotsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class BotsViewController implements ActionListener {

    private BotsView botsView;
    private CompanyDAO companyies = new CompanyDAO();
    private int botSelecionat = -1;
    private ArrayList<BotThread> llistatThreadsBots;


    private BotDAO botBBDD = new BotDAO();

    final float MIN_TEMPS = 0;
    final float MAX_TEMPS = 2000;
    public BotsViewController(BotsView botsView) {
        this.botsView = botsView;
        actualitzaLlistatBots();
        llistatThreadsBots = new ArrayList<BotThread>();
        //CarregaThreadsBots();                                     // sha comentat pqfalla

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
        switch(e.getActionCommand()) {
            case BotsView.CREATE_BOT_BUTTON_COMMAND:
                afegeixBot();
                break;
            case BotsView.ACTIVAR_BOT_BUTTON_COMMAND:
                if (botSelecionat != -1){
                    cambiarEstatBot(true);
                }

                break;
            case BotsView.DESACTIVAR_BOT_BUTTON_COMMAND:
                if (botSelecionat != -1){
                    cambiarEstatBot(false);
                }
                break;
            case BotsView.DELETE_BOT_BUTTON_COMMAND:
                if (botSelecionat != -1){

                        cambiarEstatBot(false); // primer el desactivem i despres l'eliminem

                    ArrayList<Bot> llistatBots = botBBDD.getAllBots();
                    Bot botActual = llistatBots.get(botSelecionat);
                    botBBDD.deleteBot(botActual);
                    int posbotEliminar = -1;
                    for (int i = 0; i <llistatThreadsBots.size() ; i++) {
                        if (llistatThreadsBots.get(i).getBotid() == botActual.getBotId()){

                            llistatThreadsBots.get(i).getIdThread().interrupt();                            // s'elimina aixins no em deixa ferho cridant  ales meves funcions
                            posbotEliminar = i;
                        }

                    }
                    llistatThreadsBots.remove(posbotEliminar);




                    botSelecionat = -1;
                    actualitzaLlistatBots();
                    botsView.esborraConfigurationBotView();// caldra cridar al clear de la vista de les adades de la
                }
                break;
            default:
                // si no han clicat els altres botons, vol dir que ha estat el del id del boto correponent
                System.out.println("Boto BOts premut amb ID "+e.getActionCommand());

                ArrayList<Bot> llistatBots = botBBDD.getAllBots();
                int posBot = 0;

                for (int i = 0; i < llistatBots.size(); i++) {
                    if (e.getActionCommand().equals(String.valueOf(llistatBots.get(i).getBotId()))){
                        posBot = i;
                        botSelecionat = i;
                    }
                }

                String nomCompanyia = new String();
                ArrayList<Company> listnomCompanyia = companyies.getAllCompanies();

                for (int i = 0; i < listnomCompanyia.size() ; i++) {
                    if (listnomCompanyia.get(i).getCompanyId() == llistatBots.get(posBot).getCompanyId()){
                        nomCompanyia = listnomCompanyia.get(i).getCompanyName();
                    }
                }


                float percentCompra = llistatBots.get(posBot).getBuyPercentage();
                float tempsActivacio = llistatBots.get(posBot).getActivationTime();
                String nomBot = e.getActionCommand(); // caldra cnaviar pel nom del bot corresponent

                botsView.refreshConfigurationBotView(nomCompanyia,percentCompra,tempsActivacio,nomBot);
        }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }





    // funcio per afegir bots a la base de dades una vega ens han premut el boto crear bot, altrmaent tambe farem tota la logica ssiociada lacorrecio d'errors
    private void afegeixBot(){

        try {
            botsView.getTextFieldNameCompanyia();

            float tempsActivacio =  botsView.getTextFieldTempsActivacio();
            int valueSlider = botsView.getSliderPercetnCompra();
            String nameCompanyie = botsView.getTextFieldNameCompanyia();
            if (tempsActivacio <= MIN_TEMPS || tempsActivacio >= MAX_TEMPS){

                JOptionPane.showMessageDialog(null, "Temps fora dels rangs possibles");

            }else{



                ArrayList<Company> totesCompanyies = companyies.getAllCompanies(); // predelete

                int idCompanyie = companyies.getCompanyId(nameCompanyie);
                if (idCompanyie == -1){
                    JOptionPane.showMessageDialog(null, "Aquesta Companyia no existeix");
                }else{

                    Company companyia = companyies.getCompany(idCompanyie); // comapnyia que li passarem al bot
                    // codi per afegir bots
                    Bot bot = new Bot(0,valueSlider,tempsActivacio,idCompanyie,true);  // calcdra caniar en true per false si volem que en crear el bot estigui desactivat
                    botBBDD.insertBot(bot);
                    ArrayList<Bot> llistatBots = botBBDD.getAllBots();

                    // creeem Thread del Bot amb totes les seves dades

                    BotThread nouThreadBotDades = new BotThread(bot.getBotId(),new BotsBuyThread(tempsActivacio,valueSlider,companyia));

                    nouThreadBotDades.getIdThread().run();
                    llistatThreadsBots.add(nouThreadBotDades);    // afegim totes les dades del thread del bot (id bot, i thread a la llista de thread no eliminats)


                    actualitzaLlistatBots();



                    System.out.println("");
                }
            }


            System.out.println("");

        } catch (NumberFormatException excepction) {
            JOptionPane.showMessageDialog(null, "Els decimals son and punt");
        }
        //catch (Exception ex){
        //  JOptionPane.showMessageDialog(null, "Dades Incorrectes");
        //}


        botsView.resetTextFieldNameCompanyia();
        botsView.resetTextFieldTempsActivacio();
        botsView.resetSliderPercetnCompra();




        //Bot nouBot = new Bot(0,botsView.getSliderPercetnCompra(),botsView.getTextFieldTempsActivacio(),idCompanyie);
        System.out.println("");


    }
    private void cambiarEstatBot(Boolean estat) throws InterruptedException {
        ArrayList<Bot> llistatBots = botBBDD.getAllBots();
        System.out.println("F");
        Bot botActual = llistatBots.get(botSelecionat);
        botActual.setActive(estat);
        botBBDD.changeBotStatus(botActual);


        // modifiquem thread bots
        if(estat){      // cal el volguem a activar
            for (int i = 0; i <llistatThreadsBots.size() ; i++) {
                if (llistatThreadsBots.get(i).getBotid() == botActual.getBotId()){
                   // llistatThreadsBots.get(i).getIdThread().wait();                       // aixo es el que falla
                }
            }
        }else{      // cas el volguem posar en suspens

            for (int i = 0; i <llistatThreadsBots.size() ; i++) {
                if (llistatThreadsBots.get(i).getBotid() == botActual.getBotId()){
                    //llistatThreadsBots.get(i).getIdThread().notify();                       // aixo es el que falla
                }
            }
        }


        actualitzaLlistatBots();

        // falta cridar a la funcio que getiona tots el threads del bots
    }

    private void actualitzaLlistatBots(){

        botsView.refreshBotsListfromView(botBBDD.getAllBots());

    }

    private void CarregaThreadsBots(){

        ArrayList<Bot> botsactuals = botBBDD.getAllBots();
        for (int i = 0; i < botsactuals.size(); i++) {

            Company companyia = companyies.getCompany(botsactuals.get(i).getCompanyId()); // comapnyia que li passarem al bot
            // codi per afegir bots


            // creeem Thread del Bot amb totes les seves dades

            BotThread nouThreadBotDades = new BotThread(botsactuals.get(i).getBotId(),new BotsBuyThread(botsactuals.get(i).getActivationTime(),(int) botsactuals.get(i).getBuyPercentage(),companyia));

            nouThreadBotDades.getIdThread().run();
            llistatThreadsBots.add(nouThreadBotDades);    // afegim totes les dades del thread del bot (id bot, i thread a la llista de thread no eliminats)


        }


    }

    public void refreshNewData(){ actualitzaLlistatBots(); }
}
