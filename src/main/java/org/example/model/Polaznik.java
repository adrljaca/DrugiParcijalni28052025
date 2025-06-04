package org.example.model;

import jakarta.persistence.*;

@Entity
public class Polaznik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int polaznikID;

    @Column(nullable = false, length = 100)
    private String ime;

    @Column(nullable = false, length = 100)
    private String prezime;

    public Polaznik() {
    }

    public Polaznik(String ime, String prezime) {
        this.ime = ime;
        this.prezime = prezime;
    }

    public int getPolaznikID() {
        return polaznikID;
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
}