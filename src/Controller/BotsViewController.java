package Controller;

import Model.Bot;
import View.BotsView;

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




            botsView.getTextFieldNameCompanyia();
            int idCompanyie = 0; // caldra obtenirlo amb el botsView.getTextFieldNameCompanyia();
            float tempsActivacio = botsView.getTextFieldTempsActivacio();
            int ValueSlider = botsView.getSliderPercetnCompra();
            String nameCompanyie = botsView.getTextFieldNameCompanyia();
            //Bot nouBot = new Bot(0,botsView.getSliderPercetnCompra(),botsView.getTextFieldTempsActivacio(),idCompanyie);
            System.out.println("pitjat");
        }




    }
}
