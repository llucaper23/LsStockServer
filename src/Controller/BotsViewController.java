package Controller;

import Model.Bot;
import Model.Company;
import Model.Database.DAO.BotDAO;
import Model.Database.DAO.CompanyDAO;
import Model.Manager;
import Model.Network.Server;
import View.BotsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class BotsViewController implements ActionListener {

    private BotsView botsView;
    private CompanyDAO companyies = new CompanyDAO();
    private int botSelecionat = -1;
    private Manager llistBots = new Manager();
    private BotDAO botBBDD = new BotDAO();
    final float MIN_TEMPS = 0;
    final float MAX_TEMPS = 2000;
    private Server server;

    public BotsViewController(BotsView botsView, Server server) {
        this.botsView = botsView;
        this.server = server;
        actualitzaLlistatBots();
        carregaThreadsBots();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<BotsBuyThread> llistatThreadsBots = llistBots.getLlistatThreadsBots();

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

                            llistatThreadsBots.get(i).eliminaBot();                            // s'elimina aixins no em deixa ferho cridant  ales meves funcions
                            posbotEliminar = i;
                        }

                    }
                    llistatThreadsBots.remove(posbotEliminar);

                    llistBots.setLlistatThreadsBots(llistatThreadsBots);
                    botSelecionat = -1;
                    actualitzaLlistatBots();
                    botsView.esborraConfigurationBotView();// caldra cridar al clear de la vista de les adades de la
                }
                break;
            default:

                // si no han clicat els altres botons, vol dir que ha estat el del id del boto correponent

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
                String nomBot = e.getActionCommand();

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
                    int posUltimNouBot = llistatBots.size() -1;
                    BotsBuyThread nouThreadBotDades = new BotsBuyThread(llistatBots.get(posUltimNouBot).getBotId(),tempsActivacio,valueSlider,companyia,server);

                    nouThreadBotDades.start();

                    ArrayList<BotsBuyThread> llistatThreadsBots = llistBots.getLlistatThreadsBots();

                    llistatThreadsBots.add(nouThreadBotDades);    // afegim totes les dades del thread del bot (id bot, i thread a la llista de thread no eliminats)

                    llistBots.setLlistatThreadsBots(llistatThreadsBots);

                    actualitzaLlistatBots();
                }
            }


        } catch (NumberFormatException excepction) {
            JOptionPane.showMessageDialog(null, "Els decimals son and punt");
        }




        botsView.resetTextFieldNameCompanyia();
        botsView.resetTextFieldTempsActivacio();
        botsView.resetSliderPercetnCompra();





    }
    private void cambiarEstatBot(Boolean estat) throws InterruptedException {
        ArrayList<Bot> llistatBots = botBBDD.getAllBots();
        Bot botActual = llistatBots.get(botSelecionat);
        botActual.setActive(estat);
        botBBDD.changeBotStatus(botActual);
        ArrayList<BotsBuyThread> llistatThreadsBots = llistBots.getLlistatThreadsBots();




        // modifiquem thread bots--> es a dir canviem estat
        if(estat){      // cal el volguem a activar
            for (int i = 0; i <llistatThreadsBots.size() ; i++) {
                if (llistatThreadsBots.get(i).getBotid() == botActual.getBotId()){
                    llistatThreadsBots.get(i).activaBot();


                }
            }
        }else{      // cas el volguem posar en suspens

            for (int i = 0; i <llistatThreadsBots.size() ; i++) {
                if (llistatThreadsBots.get(i).getBotid() == botActual.getBotId()){
                    llistatThreadsBots.get(i).desactivaBot();

                }
            }
        }

        llistBots.setLlistatThreadsBots(llistatThreadsBots);
        actualitzaLlistatBots();

    }

    private void actualitzaLlistatBots(){

        botsView.refreshBotsListfromView(botBBDD.getAllBots());

    }

    private void carregaThreadsBots(){

        ArrayList<BotsBuyThread> llistatThreadsBots = llistBots.getLlistatThreadsBots();


        ArrayList<Bot> botsactuals = botBBDD.getAllBots();
        for (int i = 0; i < botsactuals.size(); i++) {

            Company companyia = companyies.getCompany(botsactuals.get(i).getCompanyId()); // comapnyia que li passarem al bot
            // codi per afegir bots

            BotsBuyThread nouThreadBotDades = new BotsBuyThread(botsactuals.get(i).getBotId(),botsactuals.get(i).getActivationTime(),(int) botsactuals.get(i).getBuyPercentage(),companyia,server);
            nouThreadBotDades.setStateCarregaInicial(botsactuals.get(i).isActive());
            nouThreadBotDades.start();

            llistatThreadsBots.add(nouThreadBotDades);    // afegim totes les dades del thread del bot (id bot, i thread a la llista de thread no eliminats)
        }


        llistBots.setLlistatThreadsBots(llistatThreadsBots);
    }

    public void refreshNewData(){ actualitzaLlistatBots(); }


}
