package org.example.model;

import jakarta.persistence.*;

@Entity
public class ProgramObrazovanja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int programObrazovanjaID;

    @Column(nullable = false, length = 100)
    private String naziv;

    @Column(nullable = false)
    private int csvet;

    public ProgramObrazovanja() {
    }

    public ProgramObrazovanja(String naziv, int csvet) {
        this.naziv = naziv;
        this.csvet = csvet;
    }

    public int getProgramObrazovanjaID() {
        return programObrazovanjaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getCsvet() {
        return csvet;
    }

    public void setCsvet(int csvet) {
        this.csvet = csvet;
    }
}