package Controller;

import Model.Bot;
import View.BotsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BotsViewController implements ActionListener {

    private BotsView botsView;

    public BotsViewController(BotsView botsView) {
        this.botsView = botsView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(botsView.CREATE_BOT_BUTTON_COMMAND)){ // boto de crear bot



            try {
                botsView.getTextFieldNameCompanyia();
                int idCompanyie = 0; // caldra obtenirlo amb el botsView.getTextFieldNameCompanyia();
                float tempsActivacio =  botsView.getTextFieldTempsActivacio();
                int ValueSlider = botsView.getSliderPercetnCompra();
                String nameCompanyie = botsView.getTextFieldNameCompanyia();
            } catch (NumberFormatException excepction) {
                JOptionPane.showMessageDialog(null, "Els decimals son and punt");
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Dades Incorrectes");
            }


            botsView.resetTextFieldNameCompanyia();
            botsView.resetTextFieldTempsActivacio();
            botsView.resetSliderPercetnCompra();




            //Bot nouBot = new Bot(0,botsView.getSliderPercetnCompra(),botsView.getTextFieldTempsActivacio(),idCompanyie);
            System.out.println("");
        }




    }
}
