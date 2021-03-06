package View;

import Model.Company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Top10View extends JPanel {

    final int MAX_COMPANYIES = 9;

    public Top10View() {

    }

    /**
     * Procediment que mostra les 10 companyies.
     * @param llistaCompany ArrayList amb les companyies a mostrar.
     */
    public void novesAccions(ArrayList<Company> llistaCompany){
        ArrayList<Company> llistatCompany = new ArrayList<>();
        llistatCompany = llistaCompany;
        HistogramPanel panel = new HistogramPanel();

        // agefim les 10 comapnyies !!!WARNING nomes esta dimensionat per 10, si els volguessin posat mes o reduir tocaraia cnviar la capçalera de HistogrmPanel
        //
        for (int i = MAX_COMPANYIES; i > -1; i--) {

            panel.addHistogramColumn(llistatCompany.get(i).getCompanyName(),  llistatCompany.get(i).getSharePrice(), Color.RED);

        }

        panel.layoutHistogram();

        panel.revalidate();
        panel.repaint();

        this.removeAll();
        this.add(panel);
        this.revalidate();
        this.repaint();


    }

}