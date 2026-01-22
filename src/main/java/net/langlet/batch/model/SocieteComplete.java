// src/main/java/net/langlet/batch/model/SocieteComplete.java

package net.langlet.batch.model;

public class SocieteComplete {
    private String siret;
    private String nom;
    private String adresse;
    private String codePostal;
    private String ville;

    public SocieteComplete(String siret, String nom, String adresse, String codePostal, String ville) {
        this.siret = siret;
        this.nom = nom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public static SocieteComplete fromInputData(Societe societe, AdresseSociete enrichissement) {
        return new SocieteComplete(
            societe.getSiret(),
            societe.getNom(),
            enrichissement.getAdresse(),
            enrichissement.getCodePostal(),
            enrichissement.getVille()
        );
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}