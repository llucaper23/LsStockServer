package Model.Database.DAO;

import Model.Database.DBConnector;
import Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public boolean registerUser(User user){
        boolean exists = false;
        try{
            String query = "SELECT COUNT(user_id) as num_users FROM User WHERE nickname = '" + user.getNickName() + "' AND email = '" + user.getEmail() + "';";
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            while (rs.next()){
                if (rs.getInt("num_users") == 0){
                    exists = false;
                    String query2 = "INSERT INTO User (nickname, email, password, money, is_logged) VALUES ("+user.toString() +");";
                    DBConnector.getInstance().insertQuery(query2);
                }else{
                    exists = true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error connecting to the Database");
            return false;
        }
        return exists;
    }

    public boolean canUserLogin(User user){
        boolean logged = false;
        try{
            String query = "SELECT COUNT(user_id) as num_users FROM User WHERE nickname = '" + user.getNickName() + "' AND email = '" + user.getEmail() + "' AND password = '" + user.getPassword() + "';";
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            while (rs.next()){
                if (rs.getInt("num_users") == 0){
                    logged = false;
                }else{
                    logged = true;
                    String query2 = "UPDATE User SET is_logged = true";
                    DBConnector.getInstance().updateQuery(query2);
                }
            }
        }catch (SQLException e){
            System.out.println("Error connecting to the Database");
            return false;
        }
        return logged;
    }
}
