package org.example.model;

import jakarta.persistence.*;

@Entity
public class Upis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int upisID;

    @ManyToOne
    @JoinColumn(name = "IDPolaznik", referencedColumnName = "polaznikID", nullable = false)
    private Polaznik polaznik;

    @ManyToOne
    @JoinColumn(name = "IDProgramObrazovanja", referencedColumnName = "programObrazovanjaID", nullable = false)
    private ProgramObrazovanja programObrazovanja;

    public Upis() {
    }

    public int getUpisID() {
        return upisID;
    }

    public void setUpisID(int upisID) {
        this.upisID = upisID;
    }

    public Polaznik getPolaznik() {
        return polaznik;
    }

    public void setPolaznik(Polaznik polaznik) {
        this.polaznik = polaznik;
    }

    public ProgramObrazovanja getProgramObrazovanja() {
        return programObrazovanja;
    }

    public void setProgramObrazovanja(ProgramObrazovanja programObrazovanja) {
        this.programObrazovanja = programObrazovanja;
    }
}