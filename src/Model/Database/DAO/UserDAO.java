package Model.Database.DAO;

import Model.Database.DBConnector;
import Model.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public boolean registerUser(User user){
        boolean exists = false;
        try{
            String query = "SELECT COUNT(user_id) from User WHERE nickname = '" + user.getNickName() + "' AND email = '" + user.getEmail() + "' AND password = '" + user.getPassword() + "';";
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            while (rs.next()){
                if (rs.getInt("COUNT") == 0){
                    exists = false;
                    String query2 = "INSERT INTO User (nickname, email, password, money, isLogged) VALUES ("+user.toString() + ");";
                    DBConnector.getInstance().insertQuery(query2);
                }else{
                    exists = true;
                }
            }
        }catch (SQLException e){
            System.out.println("Error connecting to the Database");
            return false;
        }
        return exists;
    }

    public boolean canUserLogin(User user){
        boolean logged = false;
        try{
            String query = "SELECT COUNT(user_id) from User WHERE nickname = '" + user.getNickName() + "' AND email = '" + user.getEmail() + "' AND password = '" + user.getPassword() + "';";
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            while (rs.next()){
                if (rs.getInt("COUNT") == 0){
                    logged = false;
                }else{
                    logged = true;
                    String query2 = "UPDATE User SET isLogged = true";
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
