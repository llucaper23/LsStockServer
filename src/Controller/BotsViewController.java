package Controller;

import Model.Bot;
import Model.Company;
import Model.Database.DAO.CompanyDAO;
import View.BotsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BotsViewController implements ActionListener {

    private BotsView botsView;
    private CompanyDAO companyies;

    final float MIN_TEMPS = 0;
    final float MAX_TEMPS = 2000;
    public BotsViewController(BotsView botsView) {
        this.botsView = botsView;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(botsView.CREATE_BOT_BUTTON_COMMAND)){ // boto de crear bot
                afegeixBot();
        }




    }





    // funcio per afegir bots a la base de dades una vega ens han premut el boto crear bot, altrmaent tambe farem tota la logica ssiociada lacorrecio d'errors
    private void afegeixBot(){

        try {
            botsView.getTextFieldNameCompanyia();

            float tempsActivacio =  botsView.getTextFieldTempsActivacio();
            int ValueSlider = botsView.getSliderPercetnCompra();
            String nameCompanyie = botsView.getTextFieldNameCompanyia();
            if (tempsActivacio <= MIN_TEMPS || tempsActivacio >= MAX_TEMPS){

                JOptionPane.showMessageDialog(null, "Temps fora dels rangs possibles");

            }else{

                companyies = new CompanyDAO();

                ArrayList<Company> totesCompanyies = companyies.getAllCompanies(); // predelete

                int idCompanyie = companyies.getCompanyId(nameCompanyie);
                if (idCompanyie == -1){
                    JOptionPane.showMessageDialog(null, "Aquesta Companyia no existeix");
                }else{
                    // codi per afegir bots

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
}
