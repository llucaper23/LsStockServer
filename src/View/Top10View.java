package View;

import Model.Company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Top10View extends JPanel {

    final int MAX_HEIGHT = 700;
    final int MAX_WIDTH = 1250;
    final int MAX_COMPANYIES = 9;
    ArrayList<Company> llistaCompany = new ArrayList<>();

    public Top10View() {

        // farem el set dels valors que tinguem en la nostre BD --> son inventants , tocara canviarlos despres per les dades reals, s'ha d'eliinar la classe

    }

    /**
     * Procediment que mostra les 10 companyies.
     * @param llistaCompany ArrayList amb les companyies a mostrar.
     */
    public void novesAccions(ArrayList<Company> llistaCompany){
        this.llistaCompany = llistaCompany;
        HistogramPanel panel = new HistogramPanel();

        // agefim les 10 comapnyies !!!WARNING nomes esta dimensionat per 10, si els volguessin posat mes o reduir tocaraia cnviar la capçalera de HistogrmPanel
        //
        for (int i = MAX_COMPANYIES; i > -1; i--) {

            panel.addHistogramColumn(this.llistaCompany.get(i).getCompanyName(),  this.llistaCompany.get(i).getSharePrice(), Color.RED);

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