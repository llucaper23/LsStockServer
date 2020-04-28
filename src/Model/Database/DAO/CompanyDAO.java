package Model.Database.DAO;

import Model.Company;
import Model.Database.DBConnector;
import Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentMap;

public class CompanyDAO {

    public Company getCompany(int companyId) {
        try {
            String query;
            query = "SELECT * FROM Company as c WHERE c.company_id = '" + companyId + "';";

            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            while (rs.next()) {
                companyId = rs.getInt("company_id");
                String companyName = rs.getString("companyName");
                float sharePrice = rs.getFloat("email");
                return new Company(companyId, companyName, sharePrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Syntax Error");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Company> getAllCompanies() {
        try {
            ArrayList <Company> companyList = new ArrayList<>();
            String query;
            query = "SELECT * FROM Company ;";

            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            while (rs.next()) {
                int companyId = rs.getInt("company_id");
                String companyName = rs.getString("company_name");
                float sharePrice = rs.getFloat("share_price");
                companyList.add(new Company(companyId, companyName, sharePrice));
            }

            return companyList;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Syntax Error");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Company> getTop10Companies() {
        try {
            ArrayList <Company> top10 = new ArrayList<>();
            String query;
            query = "SELECT * FROM Company ORDER BY share_price DESC LIMIT 10;";

            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            while (rs.next()) {
                int companyId = rs.getInt("company_id");
                String companyName = rs.getString("company_name");
                float sharePrice = rs.getFloat("share_price");
                top10.add(new Company(companyId, companyName, sharePrice));
            }
            return top10;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Syntax Error");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}