package org.example.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class ProgramObrazovanja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int programObrazovanjaID;

    @Column(nullable = false, length = 100)
    private String naziv;

    @Column(nullable = false)
    private int CSVET;

    @OneToMany(mappedBy = "programObrazovanja", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Upis> upisi = new HashSet<>();

    public ProgramObrazovanja() {
    }

    public int getProgramObrazovanjaID() {
        return programObrazovanjaID;
    }

    public void setProgramObrazovanjaID(int programObrazovanjaID) {
        this.programObrazovanjaID = programObrazovanjaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getCSVET() {
        return CSVET;
    }

    public void setCSVET(int CSVET) {
        this.CSVET = CSVET;
    }

    public Set<Upis> getUpisi() {
        return upisi;
    }

    public void setUpisi(Set<Upis> upisi) {
        this.upisi = upisi;
    }
}