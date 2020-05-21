package Model;

import Model.Database.DAO.CompanyDAO;
import Model.Database.DAO.HistoryDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class HistoryThread extends Thread {

    private HistoryDAO historyDAO;
    private CompanyDAO companyDAO;
    private ArrayList<Float> prices;
    private int counter;
    private Company company;

    public HistoryThread(Company company) {
        this.company = company;
        historyDAO = new HistoryDAO();
        companyDAO = new CompanyDAO();
    }

    public Company getCompany() {
        return company;
    }

    @Override
    public void run() {
        counter = 0;
        float min, max, open, close;
        prices = new ArrayList<>();
        while (isAlive()) {
            try {

                Company aux = companyDAO.getCompany(company.getCompanyId());
                prices.add(aux.getSharePrice());

                java.util.Date date = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                java.sql.Time sqlTime = new java.sql.Time(date.getTime());

                if (counter == 60) {
                    open = prices.get(0);
                    close = prices.get(60);
                    Collections.sort(prices);
                    min = prices.get(0);
                    max = prices.get(60);
                    historyDAO.insertPrice(new History(max, min, open, close, sqlDate, sqlTime, aux.getCompanyId()));
                    counter = 0;
                    prices = new ArrayList<>();
                    System.out.println("id: " + aux.getCompanyId() + " - Commpany: " + aux.getCompanyName() + " - Time: " + sqlTime);
                } else {
                    counter ++;
                }
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
