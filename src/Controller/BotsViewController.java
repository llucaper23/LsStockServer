package Controller;

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

        if (e.getActionCommand().equals(botsView.CREATE_BOT_BUTTON_COMMAND)){

            System.out.println("pitjat");
        }




    }
}
