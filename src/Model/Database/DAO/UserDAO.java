package Model.Database.DAO;

import Model.Database.DBConnector;
import Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public boolean registerUser(User user){
        boolean ok = false;
        try{
            String query = "SELECT COUNT(user_id) as num_users FROM User WHERE nickname = '" + user.getNickName() + "' AND email = '" + user.getEmail() + "';";
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            while (rs.next()){
                if (rs.getInt("num_users") == 0){
                    String query2 = "INSERT INTO User (nickname, email, password, money, is_logged) VALUES ("+user.toString() +");";
                    DBConnector.getInstance().insertQuery(query2);
                    ok = true;
                }else{
                    ok = false;
                }
            }
        }catch (SQLException e){
            System.out.println("SQL Syntax Error");
            return false;
        }
        return ok;
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
