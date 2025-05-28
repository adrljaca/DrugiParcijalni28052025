package org.example.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Polaznik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int polaznikID;

    @Column(nullable = false, length = 100)
    private String ime;

    @Column(nullable = false, length = 100)
    private String prezime;

    @OneToMany(mappedBy = "polaznik", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Upis> upisi = new HashSet<>();

    public Polaznik() {
    }

    public int getPolaznikID() {
        return polaznikID;
    }

    public void setPolaznikID(int polaznikID) {
        this.polaznikID = polaznikID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Set<Upis> getUpisi() {
        return upisi;
    }

    public void setUpisi(Set<Upis> upisi) {
        this.upisi = upisi;
    }
}