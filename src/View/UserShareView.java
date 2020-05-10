package View;

import Controller.MainViewController;
import Controller.UserShareViewController;
import Model.*;
import Model.Database.DAO.UserCompanyDAO;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;


public class UserShareView extends JPanel {
    ArrayList<CompanyiesModel> dades; // aqui no pots tenir dades  trenca el MVC
    final int MAX_HEIGHT = 700;
    final int MAX_WIDTH_USERS = 350;
    final int MAX_WIDTH_ACCIONS = 700;
    JPanel jpllistaUsers = new JPanel();
    ArrayList<JButton> userButtons;
    private static final String USER = "USER";
    private JPanel jpCartera;

   public UserShareView(){
       userButtons = new ArrayList<>();
       dades = new ArrayList<CompanyiesModel>();
       for (int i = 0; i < 50 ; i++) {
           dades.add(i,new CompanyiesModel("Telefonica S.A. "+i));      // aqui obtindrem  les adades que ha
       }
       setLayout(new BorderLayout());

       //***************************************USUARIS********************************************************************************************
       JPanel jpUsuaris = new JPanel();
       jpllistaUsers.setLayout(new BoxLayout(jpllistaUsers,BoxLayout.Y_AXIS));

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
           fila[0] = "NULL";
           fila[1] = "NULL";
           fila[2] = "NULL";
           fila[3] = "NULL";
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

       jpCartera =  new JPanel();
       jpCartera.add(jpllistaAccions);

       //************************************************************************************************************
       this.add(jpUsuaris,BorderLayout.WEST);
       this.add(jpCartera,BorderLayout.CENTER);
   }

   public void updateUsers (ArrayList<User> usersList, MainViewController mvc) {
       jpllistaUsers.removeAll();
       for (User u : usersList) {
           JButton userButton = new JButton(u.getNickName());
           userButton.putClientProperty("user_id", u.getUserId());
           userButton.setPreferredSize(new Dimension(300,50));
           userButton.setMaximumSize(new Dimension(300,50));
           userButton.setActionCommand(USER);
           userButton.addActionListener(mvc);
           jpllistaUsers.add(userButton);
       }
       jpllistaUsers.setLayout(new BoxLayout(jpllistaUsers,BoxLayout.Y_AXIS));
   }

   public void updateUserCompanies (ArrayList<Company> companies, ArrayList<UserCompany> userCompanies,String nomUser) {

       ArrayList<CompanyiesUnitaries> companyiesUnitaries = new ArrayList<>();
        /////////***************ORDENEM TOTES LES COMAPNYES QUE TE EL USUARI/********************************///////////

        if (companies.size() > 0){      // mirem si el user te companyies

            CompanyiesUnitaries comapnyiaSimple = new CompanyiesUnitaries(companies.get(0).getCompanyName(), companies.get(0).getSharePrice(), userCompanies.get(0).getQuantity());

            companyiesUnitaries.add(comapnyiaSimple);
            for (int i = 0; i < companies.size(); i++) {     // recorrem tot l'array de companyes desordenades que ens passen
                int posTrobat = -1;
                for (int j = 0; j < companyiesUnitaries.size(); j++) {       // mirem si aquesta companyia ja esta afegida amb aquest preu
                    if (companies.get(i).getCompanyName().equalsIgnoreCase(companyiesUnitaries.get(j).getNomCompany()) && (companies.get(i).getSharePrice() == companyiesUnitaries.get(j).getPreu())){
                        posTrobat = j;
                    }
                }
                if (posTrobat == -1){// cal afegirla
                    comapnyiaSimple = new CompanyiesUnitaries(companies.get(i).getCompanyName(),companies.get(i).getSharePrice(), userCompanies.get(i).getQuantity());
                    companyiesUnitaries.add(comapnyiaSimple);
                }
            }
        }

       JTable jtabla = new JTable();
       DefaultTableModel model = (DefaultTableModel)jtabla.getModel();
       model.addColumn("Companyia");
       model.addColumn("Accions");
       model.addColumn("Preu/Accio");
       model.addColumn("ValorTotal");

       for (int i = 0; i < companyiesUnitaries.size() ; i++) {        // Aqui omplim amb les dades de les accions que volim CALDRA MODIFIFICAR AMB DADES REALS
           Object [] fila = new Object[4];

           fila[0] = companyiesUnitaries.get(i).getNomCompany();
           fila[1] = companyiesUnitaries.get(i).getVegades(); // nombre accions--> caldra recorrer tot per mirar si tenen el mateix preu
           fila[2] = companyiesUnitaries.get(i).getPreu();
           fila[3] =  companyiesUnitaries.get(i).getVegades() * companyiesUnitaries.get(i).getPreu(); // caldra fer el calcul
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

       TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"Cartera d'accions - Usuari: "+nomUser);// caldra posar el nom del usuari aqui
       title.setTitleJustification(TitledBorder.CENTER);
       title.setTitlePosition(TitledBorder.ABOVE_TOP);

       jpllistaAccions.setBorder(title);

       jpCartera.removeAll();
       jpCartera.add(jpllistaAccions);

       jpCartera.revalidate();
       jpCartera.repaint();
   }

   public void initPanellUSV(){
       jpCartera =  new JPanel();
       jpCartera.removeAll();
   }
}
