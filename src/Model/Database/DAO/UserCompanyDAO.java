package Model.Database.DAO;

import Model.Database.DBConnector;
import Model.User;
import Model.UserCompany;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserCompanyDAO {

    public void insertBuy(UserCompany userCompany){
        try {
            String query = "INSERT INTO User_Company (user_id, company_id, quantity, buy_price) VALUES (" + userCompany.toString() + ");";
            DBConnector.getInstance().insertQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<UserCompany> getAllCompaniesFromUser(int id){
        try {
            ArrayList<UserCompany> userCompanies = new ArrayList<>();
            String query = "SELECT * FROM User_Company WHERE user_id = " + id + ";";
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            while (rs.next()){
                int user_company_id = rs.getInt("user_company_id");
                int user_id = rs.getInt("user_id");
                int company_id = rs.getInt("company_id");
                int quantity = rs.getInt("quantity");
                float buy_price = rs.getFloat("buy_price");
                userCompanies.add(new UserCompany(user_company_id,user_id,company_id,quantity,buy_price));
            }
            return userCompanies;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
