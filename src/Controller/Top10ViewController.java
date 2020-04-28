package Controller;

import Model.Company;
import Model.Database.DAO.CompanyDAO;
import View.Top10View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Top10ViewController implements ActionListener {

    private CompanyDAO dadesCompany = new CompanyDAO();
    private Top10View top10View;

    public Top10ViewController(Top10View top10View) {
        this.top10View = top10View;
    }




    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
