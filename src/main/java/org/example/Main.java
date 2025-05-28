package org.example;

import org.example.model.Polaznik;
import org.example.model.ProgramObrazovanja;
import org.example.model.Upis;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.lang.module.Configuration;
import java.util.List;
import java.util.Scanner;


public class Main {
    private static final SessionFactory factory;

    static {
        try {
            Configuration configuration = new Configuration().configure();
            factory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n1. Unesi novog polaznika");
            System.out.println("2. Unesi novi program obrazovanja");
            System.out.println("3. Upisi polaznika na program obrazovanja");
            System.out.println("4. Prebaci polaznika u drugi program");
            System.out.println("5. Ispisi polaznike i upise");
            System.out.println("0. Izlaz");
            System.out.print("Odaberi opciju: ");

            int opcija = Integer.parseInt(scanner.nextLine());

            switch (opcija) {
                case 1:
                    unesiPolaznika();
                    break;
                case 2:
                    unesiProgram();
                    break;
                case 3:
                    upisiPolaznika();
                    break;
                case 4:
                    prebaciPolaznika();
                    break;
                case 5:
                    ispisiPolaznike();
                    break;
                case 0:
                    factory.close();
                    return;
                default:
                    System.out.println("Nepoznata opcija. Pokušajte ponovo!");
                    break;
            }
        }
    }

    static void unesiPolaznika() {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Polaznik p = new Polaznik();
        System.out.print("Ime: ");
        p.setIme(scanner.nextLine());
        System.out.print("Prezime: ");
        p.setPrezime(scanner.nextLine());

        session.save(p);
        tx.commit();
        session.close();
    }

    static void unesiProgram() {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        ProgramObrazovanja po = new ProgramObrazovanja();
        System.out.print("Naziv: ");
        po.setNaziv(scanner.nextLine());
        System.out.print("CSVET: ");
        po.setCSVET(Integer.parseInt(scanner.nextLine()));

        session.save(po);
        tx.commit();
        session.close();
    }

    static void upisiPolaznika() {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("ID polaznika: ");
        int idPolaznik = Integer.parseInt(scanner.nextLine());
        System.out.print("ID programa: ");
        int idProgram = Integer.parseInt(scanner.nextLine());

        Polaznik p = session.get(Polaznik.class, idPolaznik);
        ProgramObrazovanja pr = session.get(ProgramObrazovanja.class, idProgram);

        if (p == null || pr == null) {
            System.out.println("Polaznik ili program nije pronađen.");
            session.close();
            return;
        }

        Upis u = new Upis();
        u.setPolaznik(p);
        u.setProgramObrazovanja(pr);
        session.save(u);

        tx.commit();
        session.close();
    }

    static void prebaciPolaznika() {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("ID upisa: ");
        int idUpis = Integer.parseInt(scanner.nextLine());
        System.out.print("Novi ID programa: ");
        int noviIDProgram = Integer.parseInt(scanner.nextLine());

        Upis u = session.get(Upis.class, idUpis);
        ProgramObrazovanja novi = session.get(ProgramObrazovanja.class, noviIDProgram);

        if (u == null || novi == null) {
            System.out.println("Upis ili novi program nije pronađen.");
            session.close();
            return;
        }

        u.setProgramObrazovanja(novi);
        session.update(u);

        tx.commit();
        session.close();
    }

    static void ispisiPolaznike() {
        Session session = factory.openSession();
        List<Upis> upisi = session.createQuery("FROM Upis", Upis.class).list();

        for (Upis u : upisi) {
            System.out.println("Polaznik: " + u.getPolaznik().getIme() + " " + u.getPolaznik().getPrezime() +
                    ", Program: " + u.getProgramObrazovanja().getNaziv() +
                    ", CSVET: " + u.getProgramObrazovanja().getCSVET());
        }

        session.close();
    }
}
