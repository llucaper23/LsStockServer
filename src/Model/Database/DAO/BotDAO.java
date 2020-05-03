package Model.Database.DAO;

import Model.Bot;
import Model.Database.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class BotDAO {

    public void insertBot(Bot bot){
        try {
            String query = "INSERT INTO BOT (buy_percentage, activation_time, company_id, isActive) VALUES (" + bot.toString() + ");";
            DBConnector.getInstance().insertQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Bot> getAllBots(){
        try{
            String query = "SELECT * FROM BOT;";
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            ArrayList<Bot> bots = new ArrayList<>();
            while (rs.next()){
                int bot_id = rs.getInt("bot_id");
                float buy_percentage = rs.getFloat("buy_percentage");
                float activation_time = rs.getFloat("activation_time");
                int company_id = rs.getInt("company_id");
                boolean isActive = rs.getBoolean("isActive");
                bots.add(new Bot(bot_id,buy_percentage,activation_time,company_id, isActive));
            }
            return bots;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Bot> getAllBotsFromCompany(int company_id){
        try{
            String query = "SELECT * FROM BOT WHERE company_id = " + company_id + ";";
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            ArrayList<Bot> bots = new ArrayList<>();
            while (rs.next()){
                int bot_id = rs.getInt("bot_id");
                float buy_percentage = rs.getFloat("buy_percentage");
                float activation_time = rs.getFloat("activation_time");
                boolean isActive = rs.getBoolean("isActive");
                bots.add(new Bot(bot_id,buy_percentage,activation_time,company_id, isActive));
            }
            return bots;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void changeBotStatus(Bot bot){
        try{
            String query = "UPDATE BOT SET isActive = " + bot.isActive() + "WHERE company_id = " + bot.getBotId() + ";";
            DBConnector.getInstance().updateQuery(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteBot(Bot bot){
        try {
            String query = "DELETE FROM BOT WHERE bot_id = " + bot.getBotId();
            DBConnector.getInstance().deleteQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
