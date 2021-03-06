package Model.Database.DAO;

import Model.Database.DBConnector;
import Model.UserCompany;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserCompanyDAO {

    /**
     * Insereix una compra a la bbdd
     * @param userCompany informacio que ha de inserir
     */
    public void insertBuy(UserCompany userCompany){
        try {
            String query = "INSERT INTO User_Company (user_id, company_id, quantity, buy_price) VALUES (" + userCompany.toString() + ");";
            DBConnector.getInstance().insertQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Insereix una venta a la bbdd
     * @param userCompany informacio que ha de inserir
     */
    public void sell(UserCompany userCompany){
        try{
            String query = "SELECT * FROm User_Company WHERE user_company_id = " + userCompany.getUserCompanyId() + ";";
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            int quantity = 0;
            while (rs.next()){
                quantity = rs.getInt("quantity");
            }
            if (userCompany.getQuantity()-quantity == 0){
                String query2 = "DELETE FROM User_Company WHERE user_company_id = " + userCompany.getUserCompanyId() + ";";
                DBConnector.getInstance().deleteQuery(query2);
            }else{
                String query2 = "UPDATE User_Company SET quantity = " + userCompany.getQuantity() + " WHERE user_company_id = " + userCompany.getUserCompanyId() + ";";
                DBConnector.getInstance().updateQuery(query2);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Actualitza o esborra la venta d'accions
     * @param idCompany id de la companyia
     * @param idUser id del usuari
     * @param numShares nombre d'accions a vendre
     */
    public synchronized void sellSomeShares(int idCompany, int  idUser, int numShares){
        try {
            String query = "SELECT * FROm User_Company WHERE company_id = " + idCompany + " AND user_id = " + idUser + ";";
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            int quantity;
            while (rs.next()) {
                    quantity = rs.getInt("quantity");
                    int auxId = rs.getInt("user_company_id");
                    if (numShares >= quantity) {
                        String query2 = "DELETE FROM User_Company WHERE user_company_id = " + auxId + ";";
                        DBConnector.getInstance().deleteQuery(query2);
                    } else {
                        if (numShares > 0){
                            String query2 = "UPDATE User_Company SET quantity = " + (quantity - numShares) + " WHERE user_company_id = " + auxId + ";";
                            DBConnector.getInstance().updateQuery(query2);
                        }
                    }
                    numShares = numShares - quantity;
                }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Agafa totes les compres i ventes d'un usuari
     * @param id id del usuari
     * @return arraylist de usercompany
     */
    public synchronized ArrayList<UserCompany> getAllCompaniesFromUser(int id){
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
                userCompanies.add(new UserCompany(user_company_id, user_id, company_id, quantity, buy_price));
            }
            return userCompanies;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
