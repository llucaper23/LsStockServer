package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private static final long serialVersionUID = 12345L;
    private int requestType;
    private ArrayList<Company> companyList;
    private User user;
    private Company company;
    private UserCompany userCompany;
    private ArrayList<UserCompany> userCompanies;
    private boolean ok;

    public Message(int requestType, ArrayList<Company> companyList, User user, boolean ok, Company company, UserCompany userCompany, ArrayList<UserCompany> userCompanies) {
        this.requestType = requestType;
        this.companyList = companyList;
        this.user = user;
        this.ok = ok;
        this.company = company;
        this.userCompany = userCompany;
        this.userCompanies = userCompanies;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public ArrayList<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(ArrayList<Company> companyList) {
        this.companyList = companyList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public UserCompany getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(UserCompany userCompany) {
        this.userCompany = userCompany;
    }

    public ArrayList<UserCompany> getUserCompanies() {
        return userCompanies;
    }

    public void setUserCompanies(ArrayList<UserCompany> userCompanies) {
        this.userCompanies = userCompanies;
    }
}
