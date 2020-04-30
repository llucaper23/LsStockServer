package View;

import Model.Bot;
import Model.CompanyiesModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class BotsView extends JPanel {

    final int MAX_HEIGHT = 700;
    final int MAX_WIDTH_BOTS = 350;
    final int MAX_WIDTH_PAGE = 700;
    final int MAX_HEIGHT_BOT = 75;
    final int BOTONS_CENTRALS_WIDTH = 100;
    final int BOTONS_CENTRALS_HEIGHT = 80;
    final String ACTIVAR_BOT = "ACTIVAR BOT";
    final String DESACTIVAR_BOT = "DESACTIVAR BOT";
    final String DELETE_BOT = "EL·LIMINAR BOT";

    //Action's Listenners
    public static final String CREATE_BOT_BUTTON_COMMAND = "CREATE_BOT_BUTTON_COMMAND";



    //Vars
    private ActionListener listener;
    //list of buttons -> els fem globals per a poder introduir a posteriori els acctions listeners
    private JButton jbCrearBot;
    private JLabel jlbCompanyia;
    private JLabel jlbTempsActivacio;
    private JSlider jsliPercentCompra;

    public BotsView() {

        setLayout(new BorderLayout());
        //***************************************BOTS LIST********************************************************************************************

        JPanel jpUsuaris = new JPanel();




        JPanel jpllistaUsers = new JPanel();
        jpllistaUsers.setLayout(new BoxLayout(jpllistaUsers,BoxLayout.Y_AXIS));

        for (int i = 0; i < 20; i++) {       // caldra canviarho pels size de bots que tinguem i fer els acctions listeneres
            JButton bottnet = new JButton("<NOM_BOT>");
            bottnet.setPreferredSize(new Dimension(300,50));
            bottnet.setMaximumSize(new Dimension(300,50));
            jpllistaUsers.add(bottnet);

        }

        JPanel jpcontainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        jpcontainer.add(jpllistaUsers);
        JScrollPane jspUsers = new JScrollPane(jpcontainer,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        jspUsers.setAlignmentX(Component.RIGHT_ALIGNMENT);
        jspUsers.setPreferredSize(new Dimension(MAX_WIDTH_BOTS, MAX_HEIGHT));
        jspUsers.setMaximumSize(new Dimension(MAX_WIDTH_BOTS, MAX_HEIGHT));


        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"Bots");// caldra posar el nom del usuari aqui
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.ABOVE_TOP);
        jspUsers.setBorder(title);


        jpUsuaris.add(jspUsers);
        //******************************************CONFIG BOT******************************************************************************

        JPanel jpBotInfo = new JPanel();
        jpBotInfo.setLayout(new BoxLayout(jpBotInfo,BoxLayout.Y_AXIS));

        //>>>>>>>>>Panlls intermdedis<<<<<<<

// Panell blanc superior

        //>>> Espai Blanc
        JPanel jpEnBlancSup = new JPanel();
        jpEnBlancSup.setPreferredSize(new Dimension(800, 115));
        jpEnBlancSup.setMaximumSize(new Dimension(800, 115));

        //Taula

            JTable jtabla = new JTable();
            DefaultTableModel model = (DefaultTableModel)jtabla.getModel();
            model.addColumn("Companyia");
            model.addColumn("Percentatge de Compra");
            model.addColumn("Temps d'activacio");

            // omplim amb els dades del bot que tindrime guardades a la BD

            Object [] fila = new Object[3];
            fila[0] = "<NOM COMPANYIA>";
            fila[1] = "<PERCENTANT COMPRA>"+ "%";
            fila[2] = "<TEMPS ACTIVACIO>" + "sec";

            model.addRow(fila);

                // creeem scroll per a que surtin les titols sombrejats
            JScrollPane jscrollInfooBot = new JScrollPane(jtabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            jscrollInfooBot.setPreferredSize(new Dimension(MAX_WIDTH_PAGE, MAX_HEIGHT_BOT));
            jscrollInfooBot.setMaximumSize(new Dimension(MAX_WIDTH_PAGE, MAX_HEIGHT_BOT));

            // configurem mides i etc..
            JPanel jpTaula = new JPanel();
            jpTaula.setLayout(new BorderLayout());
            jpTaula.add(jscrollInfooBot,BorderLayout.CENTER);
            jpTaula.setPreferredSize(new Dimension(MAX_WIDTH_PAGE, MAX_HEIGHT_BOT));
            jpTaula.setMaximumSize(new Dimension(MAX_WIDTH_PAGE, MAX_HEIGHT_BOT));

            title = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"Confguracio - Bot:"+"<NOM_BOT>");// caldra posar el nom del usuari aqui
            title.setTitleJustification(TitledBorder.CENTER);
            title.setTitlePosition(TitledBorder.ABOVE_TOP);

            jpTaula.setBorder(title);

        //>>> botons intermedis>>>>>>>

            JPanel jpBotonsIntermedis = new JPanel();

            jpBotonsIntermedis.setLayout(new BoxLayout(jpBotonsIntermedis,BoxLayout.X_AXIS));

            JButton jbActivarBot = new JButton(ACTIVAR_BOT);
            jbActivarBot.setPreferredSize(new Dimension(BOTONS_CENTRALS_WIDTH,BOTONS_CENTRALS_HEIGHT));
            JButton jbDesactivarBot = new JButton(DESACTIVAR_BOT);
            jbDesactivarBot.setPreferredSize(new Dimension(BOTONS_CENTRALS_WIDTH,BOTONS_CENTRALS_HEIGHT));
            JButton jbDeleteBot = new JButton(DELETE_BOT);
            jbDeleteBot.setPreferredSize(new Dimension(BOTONS_CENTRALS_WIDTH,BOTONS_CENTRALS_HEIGHT));


            jpBotonsIntermedis.add(jbActivarBot);
            jpBotonsIntermedis.add(jbDesactivarBot);
            jpBotonsIntermedis.add(jbDeleteBot);



          //>>> Espai Blanc
        JPanel jpEnBlanc = new JPanel();
        jpEnBlanc.setPreferredSize(new Dimension(800, 135));
        jpEnBlanc.setMaximumSize(new Dimension(800, 135));





        //>>>Creacio Bot

            JPanel jpCreacioBot = new JPanel();
            jpCreacioBot.setLayout(new BoxLayout(jpCreacioBot,BoxLayout.Y_AXIS));
            jpCreacioBot.setPreferredSize(new Dimension(800, 275));
            jpCreacioBot.setMaximumSize(new Dimension(800, 275));
            title = BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()),"Creacio de Bot");// caldra posar el nom del usuari aqui
            title.setTitleJustification(TitledBorder.CENTER);
            title.setTitlePosition(TitledBorder.ABOVE_TOP);
            jpCreacioBot.setBorder(title);


            // Companyia Section
            final JPanel jpCompanyia = new JPanel(new GridLayout(1,2));
            jpCreacioBot.add(jpCompanyia);

            jlbCompanyia = new JLabel("Companyia");
            jlbCompanyia.setHorizontalAlignment(JLabel.LEFT);  //JPasswordField
            jpCompanyia.add(jlbCompanyia);


            JTextField jtfCompanyia = new JTextField("");
            jtfCompanyia.setEditable(true);
            jpCompanyia.add(jtfCompanyia);
            jpCompanyia.setPreferredSize(new Dimension(800, 40));
            jpCompanyia.setMaximumSize(new Dimension(800, 40));

            // percentage compra
            final JPanel jpPercentCompra = new JPanel(new GridLayout(1,2));
            jpCreacioBot.add(jpPercentCompra);

            final JLabel jlbPercentatgeCompra = new JLabel("Percentatge de Compra");
            jlbPercentatgeCompra.setHorizontalAlignment(JLabel.LEFT);  //JPasswordField
            jpPercentCompra.add(jlbPercentatgeCompra);


            jsliPercentCompra = new JSlider(JSlider.HORIZONTAL,0,100,50);
            jsliPercentCompra.setMinorTickSpacing(5);
            jsliPercentCompra.setMajorTickSpacing(20);
            jsliPercentCompra.setPaintTicks(true);
            jsliPercentCompra.setPaintLabels(true);

            jpPercentCompra.add(jsliPercentCompra);
            jpPercentCompra.setPreferredSize(new Dimension(800, 125));
            jpPercentCompra.setMaximumSize(new Dimension(800, 125));



            // Companyia Section
            final JPanel jpTempsAct = new JPanel(new GridLayout(1,2));
            jpCreacioBot.add(jpTempsAct);

            jlbTempsActivacio = new JLabel("Temps d'activacio");
            jlbTempsActivacio.setHorizontalAlignment(JLabel.LEFT);  //JPasswordField
            jpTempsAct.add(jlbTempsActivacio);


            JTextField jtfTempsActivacio = new JTextField("");
            jtfTempsActivacio.setEditable(true);
            jpTempsAct.add(jtfTempsActivacio);
            jpTempsAct.setPreferredSize(new Dimension(800, 40));
            jpTempsAct.setMaximumSize(new Dimension(800, 40));




         // Boto Crear Bot
            JPanel jpBotoBot = new JPanel();
            jpBotoBot.setLayout(new BorderLayout());

            jbCrearBot = new JButton("CREAR BOT");
            jbCrearBot.setActionCommand(CREATE_BOT_BUTTON_COMMAND);
            jbCrearBot.addActionListener(listener);

            jpBotoBot.add(jbCrearBot,BorderLayout.CENTER);
            jpBotoBot.setPreferredSize(new Dimension(200, 60));
            jpBotoBot.setMaximumSize(new Dimension(200, 60));





        jpBotInfo.add(jpEnBlancSup);
        jpBotInfo.add(jpTaula);
        jpBotInfo.add(jpBotonsIntermedis);
        jpBotInfo.add(jpEnBlanc);
        jpBotInfo.add(jpCreacioBot);
        jpBotInfo.add(jpBotoBot);

        //*********************************************************************************************************************************

        this.add(jpUsuaris,BorderLayout.EAST);
        this.add(jpBotInfo,BorderLayout.CENTER);
    }
    public void registerController(ActionListener listener){
        jbCrearBot.addActionListener(listener);
    }
    public  String getTextFieldNameCompanyia(){
        return  jlbCompanyia.getText();
    }
    public  float getTextFieldTempsActivacio(){ return  Float.parseFloat(jlbTempsActivacio.getText()); }
    public  int getSliderPercetnCompra(){ return jsliPercentCompra.getValue()  ;}


}
