package View;

import javax.swing.*;

public class MainView extends JFrame {


    public MainView(){
        //configurem finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
        setTitle("LS - Stocks");

        //fem les pestanyes --> TabbedPane
        JTabbedPane jTPpestanyes=new JTabbedPane();


        // Creem els diferents panells que els afegirem com a pestanyes

        // Pestanya Acciones per user
            JPanel jpAccionsXUser=new JPanel();

            //continguts del panell
            JLabel jlAccionsxUser=new JLabel("Accions X Usuari text");
            jpAccionsXUser.add(jlAccionsxUser);
            //l'afegim
            jTPpestanyes.addTab("Accions X Usuari", jpAccionsXUser);


        //Pestanya Top 10
            JPanel panel2=new JPanel();

            //continguts del panell
            JLabel et_p2=new JLabel("Estas en el panel 2");
            panel2.add(et_p2);

            jTPpestanyes.addTab("Panel 2", panel2);





        //Pestanya 3
            JPanel panel3=new JPanel();
             //continguts del panell
            JLabel et_p3=new JLabel("Estas en el panel 3");
            panel3.add(et_p3);

            jTPpestanyes.addTab("Panel 3", panel3);

        // ho posem al contenidor general
        getContentPane().add(jTPpestanyes);



    }
}
