package Controller;

import Model.CompanyiesModel;
import View.UserShareView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserShareViewController implements ActionListener {


    private UserShareView userShareWindow;

    public UserShareViewController(UserShareView userShareWindow) {
        this.userShareWindow = userShareWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("x")){  // caldra comprovar amb el nom de tots els usuaris que tenim, sera el Action listener que li haurem posat
            // haurem de fer un set a les dades que ens mostrara la vista per pantalla->

        }

    }
}
