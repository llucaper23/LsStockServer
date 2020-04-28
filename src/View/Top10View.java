package View;

import Model.Company;
import Model.Database.DAO.CompanyDAO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Top10View extends JPanel {

    final int MAX_HEIGHT = 700;
    final int MAX_WIDTH = 1250;
    CompanyDAO dadesCompany = new CompanyDAO();

    public Top10View() {

        // farem el set dels valors que tinguem en la nostre BD --> son inventants , tocara canviarlos despres per les dades reals, s'ha d'eliinar la classe
        HistogramPanel panel = new HistogramPanel();
        ArrayList<Company> llistaCompany = dadesCompany.getTop10Companies();
        // agefim les 10 comapnyies !!!WARNING nomes esta dimensionat per 10, si els volguessin posat mes o reduir tocaraia cnviar la capçalera de HistogrmPanel
        for (int i = 0; i < 10; i++) {


            panel.addHistogramColumn("Telefonica" + i, i * 10, Color.RED);

        }

        panel.layoutHistogram();

        this.add(panel);
    }

}