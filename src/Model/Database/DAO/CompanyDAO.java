package Model.Database.DAO;

import Model.Company;
import Model.Database.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyDAO {

    public Company getCompany(int companyId) {
        try {
            String query;
            query = "SELECT * FROM Company as c WHERE c.company_id = '" + companyId + "';";

            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            while (rs.next()) {
                companyId = rs.getInt("company_id");
                String companyName = rs.getString("companyName");
                float sharePrice = rs.getFloat("share_price");
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
            query = "SELECT * FROM Company;";

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

    public void setSharePrice(Company company){
        try {
            String query = "UPDATE Company SET share_price = " + company.getSharePrice() + " WHERE company_id = " + company.getCompanyId() + ";";
            DBConnector.getInstance().updateQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCompanyId(String companyName){
        int companyId = -1;
        try{
            String query = "SELECT c.company_id FROM Company as c WHERE c.company_name = '" + companyName + "';";
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            while (rs.next()){
                companyId = rs.getInt("company_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return companyId;
    }

    public void insertCompany(Company company){
        try {
            String query = "INSERT INTO Company (company_name, share_price) VALUES (" + company.toString() + ");";
            DBConnector.getInstance().insertQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}