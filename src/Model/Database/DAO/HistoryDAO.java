package Model.Database.DAO;

import Model.Database.DBConnector;
import Model.History;

import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class HistoryDAO {
    public void insertPrice(History history){
        try {
            String query = "SELECT COUNT(history_id) as numHistory FROM History WHERE company_id = " + history.getCompanyId() + ";";
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            while (rs.next()){
                if (rs.getInt("numHistory") < 10){
                    String query2 = "INSERT INTO History (max_share_price, min_share_price, open_share_price, close_share_price, date, time, company_id) VALUES (" + history.toString() + ");";
                    DBConnector.getInstance().insertQuery(query2);
                }else{
                    String query2 = "SELECT * FROM History WHERE company_id = " + history.getCompanyId() + " ORDER BY date, time ASC LIMIT 1;";
                    ResultSet rs1 = DBConnector.getInstance().selectQuery(query2);
                    while (rs1.next()){
                        int id = rs1.getInt("history_id");
                        String query3 = "DELETE FROM History WHERE history_id = " + id + ";";
                        DBConnector.getInstance().deleteQuery(query3);
                        String query4 = "INSERT INTO History (max_share_price, min_share_price, open_share_price, close_share_price, date, time, company_id) VALUES (" + history.toString() + ");";
                        DBConnector.getInstance().insertQuery(query4);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<History> getHistoricFromCompany(int companyId){
        try{
            String query = "SELECT * FROM History WHERE company_id = " + companyId + ";";
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            ArrayList<History> historicPrices = new ArrayList<>();
            while (rs.next()){
                int historyId = rs.getInt("history_id");
                float max = rs.getFloat("max_share_price");
                float min = rs.getFloat("min_share_price");
                float open = rs.getFloat("open_share_price");
                float close = rs.getFloat("close_share_price");
                Date date = rs.getDate("date");
                Time time = rs.getTime("time");
                companyId = rs.getInt("company_id");
                historicPrices.add(new History(historyId, max, min, open, close, date, time, companyId));
            }
            return historicPrices;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public History get5MinBeforePrice(int companyId){
        try{
            String query = "SELECT * FROM History WHERE company_id = " + companyId + ";";
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            ArrayList<History> historicPrices = new ArrayList<>();
            while (rs.next()){
                int historyId = rs.getInt("history_id");
                float max = rs.getFloat("max_share_price");
                float min = rs.getFloat("min_share_price");
                float open = rs.getFloat("open_share_price");
                float close = rs.getFloat("close_share_price");
                Date date = rs.getDate("date");
                Time time = rs.getTime("time");
                companyId = rs.getInt("company_id");
                historicPrices.add(new History(historyId, max, min, open, close, date, time, companyId));
            }
            return historicPrices.get(5);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<History> getAllHistories() {
        try{
            String query = "SELECT * FROM History;";
            ResultSet rs = DBConnector.getInstance().selectQuery(query);
            ArrayList<History> historicPrices = new ArrayList<>();
            while (rs.next()){
                int historyId = rs.getInt("history_id");
                float max = rs.getFloat("max_share_price");
                float min = rs.getFloat("min_share_price");
                float open = rs.getFloat("open_share_price");
                float close = rs.getFloat("close_share_price");
                Date date = rs.getDate("date");
                Time time = rs.getTime("time");
                int companyId = rs.getInt("company_id");
                historicPrices.add(new History(historyId, max, min, open, close, date, time, companyId));
            }
            return historicPrices;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
