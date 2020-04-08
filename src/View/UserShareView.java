package View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class UserShareView extends JPanel {
    ArrayList<CompanyiesModel> dades; // a canviar per les dades reals de la taula
    final int MAX_HEIGHT = 700;
    final int MAX_WIDTH_USERS = 350;
    final int MAX_WIDTH_ACCIONS = 700;

   public UserShareView(){

       dades = new ArrayList<CompanyiesModel>();
       for (int i = 0; i < 50 ; i++) {
           dades.add(i,new CompanyiesModel("Telefonica S.A. "+i));
       }

       setLayout(new BorderLayout());

       //***************************************USUARIS********************************************************************************************

       JPanel jpUsuaris = new JPanel();




       JPanel jpllistaUsers = new JPanel();
       jpllistaUsers.setLayout(new BoxLayout(jpllistaUsers,BoxLayout.Y_AXIS));

       for (int i = 0; i < 20; i++) {       // caldra canviarho pels nomes dle users i tota l pesca
           JButton bottnet = new JButton("<NOM_USER> "+i);
           bottnet.setPreferredSize(new Dimension(300,50));
           bottnet.setMaximumSize(new Dimension(300,50));
           jpllistaUsers.add(bottnet);

       }



       JPanel jpcontainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
       jpcontainer.add(jpllistaUsers);
       JScrollPane jspUsers = new JScrollPane(jpcontainer,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

       jspUsers.setAlignmentX(Component.LEFT_ALIGNMENT);
       jspUsers.setPreferredSize(new Dimension(MAX_WIDTH_USERS, MAX_HEIGHT));
       jspUsers.setMaximumSize(new Dimension(MAX_WIDTH_USERS, MAX_HEIGHT));


       TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"Usuaris");// caldra posar el nom del usuari aqui
       title.setTitleJustification(TitledBorder.CENTER);
       title.setTitlePosition(TitledBorder.ABOVE_TOP);
       jspUsers.setBorder(title);


       jpUsuaris.add(jspUsers);



        //***********************CARTERA******************************************************************************************************


       JTable jtabla = new JTable();
       DefaultTableModel model = (DefaultTableModel)jtabla.getModel();
       model.addColumn("Companyia");
       model.addColumn("Accions");
       model.addColumn("Preu/Accio");
       model.addColumn("ValorTotal");

       for (int i = 0; i < dades.size() ; i++) {        // Aqui omplim amb les dades de les accions que volim CALDRA MODIFIFICAR AMB DADES REALS
           Object [] fila = new Object[4];
           fila[0] = dades.get(i).getNomCompanyia();
           fila[1] = dades.get(i).getnAccions();
           fila[2] = dades.get(i).getPreuAccio();
           fila[3] = dades.get(i).getValorTotal();
           model.addRow(fila);
       }


       JScrollPane jscrollCartera = new JScrollPane(jtabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       jscrollCartera.setPreferredSize(new Dimension(MAX_WIDTH_ACCIONS , MAX_HEIGHT));
       jscrollCartera.setMaximumSize(new Dimension(MAX_WIDTH_ACCIONS, MAX_HEIGHT));



       JPanel jpllistaAccions = new JPanel();
       jpllistaAccions.add(jscrollCartera);
       //jpllistaAccions.setAlignmentX(Component.LEFT_ALIGNMENT);
       jpllistaAccions.setPreferredSize(new Dimension(MAX_WIDTH_ACCIONS, MAX_HEIGHT));
       jpllistaAccions.setMaximumSize(new Dimension(MAX_WIDTH_ACCIONS, MAX_HEIGHT));

       title = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"Cartera d'accions - Usuari: "+"<NOM_USER>");// caldra posar el nom del usuari aqui
       title.setTitleJustification(TitledBorder.CENTER);
       title.setTitlePosition(TitledBorder.ABOVE_TOP);

        jpllistaAccions.setBorder(title);


       JPanel jpCartera = new JPanel();
       jpCartera.add(jpllistaAccions);

       //************************************************************************************************************


       this.add(jpUsuaris,BorderLayout.WEST);
       this.add(jpCartera,BorderLayout.CENTER);


   }
}
