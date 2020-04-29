package Model;

public class CompanyiesModel {

    private String nomCompanyia;
    private float nAccions;
    private float preuAccio;
    private float valorTotal;

    public CompanyiesModel(String nomCompanyia) {
        this.nomCompanyia = nomCompanyia;
        this.nAccions = 100;
        this.preuAccio = 98;
        this.valorTotal = 3000;

    }

    public String getNomCompanyia() {
        return nomCompanyia;
    }

    public float getnAccions() {
        return nAccions;
    }

    public float getPreuAccio() {
        return preuAccio;
    }

    public float getValorTotal() {
        return valorTotal;
    }
}
