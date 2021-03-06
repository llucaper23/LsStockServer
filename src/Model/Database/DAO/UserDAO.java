package Model.Database.DAO;

import Model.Database.DBConnector;
import Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {

    /**
     * registra un usuari dins la bbdd
     * @param user usuari a registrar
     * @return true o false segons hagi sigut satisfactoria la funcio o no
     */
    public synchronized boolean registerUser(User user){
        boolean ok = false;
        try{
            String query = "SELECT COUNT(user_id) as num_users FROM User WHERE nickname = '" + user.getNickName() + "' OR email = '" + user.getEmail() + "';";
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

    /**
     * Comprova si el usuari pot fer login o no
     * @param user usuari a loguejar
     * @return true o false segons hagi sigut satisfactoria la funcio o no
     */
    public synchronized boolean canUserLogin(User user){
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

    /**
     * fa logout d'un usuari
     * @param user usuari a fer logout
     */
    public void logOut(User user){
        if (user != null){
            String query2 = "UPDATE User SET is_logged = 0 WHERE nickname = '" + user.getNickName() + "';";
            DBConnector.getInstance().updateQuery(query2);
        }
    }

    /**
     * fa logout de tots els usuaris
     */
    public void logOutAllUsers () {
        String query = "UPDATE user SET is_logged = 0;";
        DBConnector.getInstance().updateQuery(query);
    }

    /**
     * Agafa tota la info d'un usuari
     * @param nickName nickname del usuari
     * @param email email del usuari
     * @return usuari buscat
     */
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

    /**
     * actualitza el diner d'un usuari
     * @param user usuari a actualitzar
     */
    public synchronized void setMoney(User user){
        try {
            String query = "UPDATE User SET money = " + user.getMoney() + " WHERE nickname = '" + user.getNickName() + "';";
            DBConnector.getInstance().updateQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * retorna tots els usuaris
     * @return arraylist de usuaris
     */
    public synchronized ArrayList<User> getAllUsers() {
        try {
            ArrayList <User> userList = new ArrayList<>();
            String query;
            query = "SELECT * FROM User;";

            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String nickName = rs.getString("nickName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int money = rs.getInt("money");
                boolean isLogged = rs.getBoolean("is_logged");
                userList.add(new User(user_id, nickName, email, password, money, isLogged));
            }

            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Syntax Error");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * retorna un usuari segons un id
     * @param id id del usuari dessitjat
     * @return usuari dessitjat
     */
    public synchronized User getUserById (int id) {
        try {
            String query;
            query = "SELECT * FROM User WHERE user_id = " + id + ";";

            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String nickName = rs.getString("nickName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int money = rs.getInt("money");
                boolean isLogged = rs.getBoolean("is_logged");
                return new User(user_id, nickName, email, password, money, isLogged);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Syntax Error");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
