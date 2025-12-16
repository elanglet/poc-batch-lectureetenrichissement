// src/main/java/net/langlet/batch/model/Societe.java

package net.langlet.batch.model;

public class Societe {
    private String siret;
    private String nom;

    public Societe() {
    }

    public Societe(String siret, String nom) {
        this.siret = siret;
        this.nom = nom;
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
}