package net.langlet.batch.model;

public class AdresseSociete {
    private String siret;
    private String adresse;
    private String codePostal;
    private String ville;

    public AdresseSociete() {
    }

    public AdresseSociete(String siret, String adresse, String codePostal, String ville) {
        this.siret = siret;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }
}
