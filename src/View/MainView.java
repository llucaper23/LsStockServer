package View;

import javax.swing.*;


public class MainView extends JFrame {


    public MainView(){
        //configurem finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(false);


        setResizable(false);
        setTitle("LS - Stocks");


        //fem les pestanyes --> TabbedPane
        JTabbedPane jTPpestanyes=new JTabbedPane();


        // Creem els diferents panells que els afegirem com a pestanyes

        // Pestanya Acciones per user
            JPanel jpAccionsXUser=new UserShareView();

            //l'afegim
            jTPpestanyes.addTab("Accions X Usuari", jpAccionsXUser);


        //Pestanya Top 10
            JPanel jpTop10=new Top10View();
            //continguts del panell


            jTPpestanyes.addTab("Top 10", jpTop10);





        //Pestanya 3
            JPanel jpBots=new BotsView();
             //continguts del panell


            jTPpestanyes.addTab("Bots", jpBots);

        // ho posem al contenidor general
        getContentPane().add(jTPpestanyes);

        //Dimension mida = this.getSize();
        setSize(1300,800);// fixem el temany de la finestra

    }

}
