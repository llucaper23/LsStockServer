package Model;

public class CompanyiesUnitaries {

    private String nomCompany;
    private float preu;
    private int vegades;

    public CompanyiesUnitaries(String nomCompany, float preu, int vegades) {
        this.nomCompany = nomCompany;
        this.preu = preu;
        this.vegades = vegades;
    }

    public String getNomCompany() {
        return nomCompany;
    }

    public void setNomCompany(String nomCompany) {
        this.nomCompany = nomCompany;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    public int getVegades() {
        return vegades;
    }

}
