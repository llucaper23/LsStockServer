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
            JPanel jpTop10=new JPanel();
            //continguts del panell
            JLabel jlTop10=new JLabel("Top 10 Text");
            jpTop10.add(jlTop10);

            jTPpestanyes.addTab("Top 10", jpTop10);





        //Pestanya 3
            JPanel jpBots=new JPanel();
             //continguts del panell
            JLabel jlBots=new JLabel("Bots");
            jpBots.add(jlBots);

            jTPpestanyes.addTab("Bots Text", jpBots);

        // ho posem al contenidor general
        getContentPane().add(jTPpestanyes);



    }

    public static void main(String[] args){

        MainView vista = new MainView();
    }
}
