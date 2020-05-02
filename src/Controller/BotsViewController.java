package Controller;

import Model.Bot;
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

    private BotDAO botBBDD = new BotDAO();

    final float MIN_TEMPS = 0;
    final float MAX_TEMPS = 2000;
    public BotsViewController(BotsView botsView) {
        this.botsView = botsView;
        actualitzaLlistatBots();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(botsView.CREATE_BOT_BUTTON_COMMAND)){ // boto de crear bot
                afegeixBot();
        }else{ // si no han clicat els altres botons, vol dir que ha estat el del id del boto correponent
            System.out.println("Boto BOts premut amb ID "+e.getActionCommand());

            ArrayList<Bot> llistatBots = botBBDD.getAllBots();
            int posBot = 0;

            for (int i = 0; i < llistatBots.size(); i++) {
                if (e.getActionCommand().equals(String.valueOf(llistatBots.get(i).getBotId()))){
                    posBot = i;
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


                    // codi per afegir bots
                    Bot bot = new Bot(0,valueSlider,tempsActivacio,idCompanyie);
                    botBBDD.insertBot(bot);
                    ArrayList<Bot> llistatBots = botBBDD.getAllBots();

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

    private void actualitzaLlistatBots(){

        botsView.refreshBotsListfromView(botBBDD.getAllBots());

    }

    public void refreshNewData(){ actualitzaLlistatBots(); }
}
