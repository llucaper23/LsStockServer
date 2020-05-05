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
                    String query2 = "INSERT INTO User (nickname, email, password, money, is_logged) VALUES ("+ user.toString() +");";
                    DBConnector.getInstance().insertQuery(query2);
                    ok = true;
                }else{
                    ok = false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("SQL Syntax Error");
            return false;
        }
        return ok;

    }

    public boolean canUserLogin(User user){
        boolean logged = false;
        try{
            ResultSet rs;
            if (user.getNickName().isEmpty()) {
                String query = "SELECT COUNT(user_id) as num_users FROM User WHERE email = '" + user.getEmail() + "' AND password = '" + user.getPassword() + "' AND is_logged = 0;";
                rs = DBConnector.getInstance().selectQuery(query);
            } else {
                String query = "SELECT COUNT(user_id) as num_users FROM User WHERE nickname = '" + user.getNickName() + "' AND password = '" + user.getPassword() + "' AND is_logged = 0;";
                rs = DBConnector.getInstance().selectQuery(query);
            }

            while (rs.next()){
                if (rs.getInt("num_users") == 0){
                    logged = false;
                }else{
                    logged = true;
                    String query2;
                    if (user.getNickName().isEmpty()){
                        query2 = "UPDATE User SET is_logged = 1 WHERE email = '" + user.getEmail() + "';";
                    }else {
                        query2 = "UPDATE User SET is_logged = 1 WHERE nickname = '" + user.getNickName() + "';";
                    }
                    DBConnector.getInstance().updateQuery(query2);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("SQL Syntax Error");
            return false;
        }
        return logged;
    }

    public void logOut(User user){
        if (user != null){
            String query2 = "UPDATE User SET is_logged = 0 WHERE nickname = '" + user.getNickName() + "';";
            DBConnector.getInstance().updateQuery(query2);
        }
    }

    public User getUser(String nickName, String email) {
        try {
            String query;
            if (nickName.isEmpty()) {
                query = "SELECT u.user_id, u.nickName, u.email, u.password, u.money, u.is_logged FROM User as u WHERE email = '" + email + "';";

            } else {
                query = "SELECT u.user_id, u.nickName, u.email, u.password, u.money, u.is_logged FROM User as u WHERE nickname = '" + nickName + "';";

            }
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                nickName = rs.getString("nickName");
                email = rs.getString("email");
                String password = rs.getString("password");
                int money = rs.getInt("money");
                boolean isLogged = rs.getBoolean("is_logged");
                User user = new User(user_id, nickName, email, password, money, isLogged);
                return user;
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("SQL Syntax Error");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setMoney(User user){
        try {
            String query = "UPDATE User SET money = " + user.getMoney() + " WHERE nickname = " + user.getNickName() + ";";
            DBConnector.getInstance().updateQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
