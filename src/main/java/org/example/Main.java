package org.example;

import org.example.model.Polaznik;
import org.example.model.ProgramObrazovanja;
import org.example.model.Upis;
import org.example.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public class Main {
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
                    unesiPolaznika(scanner);
                    break;
                case 2:
                    unesiProgram(scanner);
                    break;
                case 3:
                    upisiPolaznika(scanner);
                    break;
                case 4:
                    prebaciPolaznika(scanner);
                    break;
                case 5:
                    ispisiPolaznike(scanner);
                    break;
                case 0:
                    HibernateUtil.shutdown();
                    return;
                default:
                    System.out.println("Nepoznata opcija. Pokušajte ponovo!");
                    break;
            }
        }
    }

    private static void unesiPolaznika(Scanner scanner) {
        System.out.print("Ime: ");
        String ime = scanner.nextLine();
        System.out.print("Prezime: ");
        String prezime = scanner.nextLine();

        Polaznik p = new Polaznik(ime, prezime);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(p);
            session.getTransaction().commit();
            System.out.println("Polaznik uspješno unesen.");
        }
    }

    private static void unesiProgram(Scanner scanner) {
        System.out.print("Naziv programa: ");
        String naziv = scanner.nextLine();
        System.out.print("CSVET: ");
        int csvet = Integer.parseInt(scanner.nextLine());

        ProgramObrazovanja po = new ProgramObrazovanja(naziv, csvet);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(po);
            session.getTransaction().commit();
            System.out.println("Program uspješno unesen.");
        }
    }

    private static void upisiPolaznika(Scanner scanner) {
        System.out.print("ID polaznika: ");
        int polaznikID = Integer.parseInt(scanner.nextLine());
        System.out.print("ID programa: ");
        int programID = Integer.parseInt(scanner.nextLine());

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Polaznik p = session.get(Polaznik.class, polaznikID);
            ProgramObrazovanja prog = session.get(ProgramObrazovanja.class, programID);

            if (p != null && prog != null) {
                session.persist(new Upis(p, prog));
                session.getTransaction().commit();
                System.out.println("Polaznik uspješno upisan.");
            } else {
                System.out.println("Neispravan ID.");
            }
        }
    }

    private static void prebaciPolaznika(Scanner scanner) {
        System.out.print("ID upisa: ");
        int upisID = Integer.parseInt(scanner.nextLine());
        System.out.print("Novi ID program: ");
        int noviProgID = Integer.parseInt(scanner.nextLine());

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Upis upis = session.get(Upis.class, upisID);
            ProgramObrazovanja novi = session.get(ProgramObrazovanja.class, noviProgID);

            if (upis != null && novi != null) {
                upis.setProgramObrazovanja(novi);
                session.merge(upis);
                session.getTransaction().commit();
                System.out.println("Prebacivanje je uspješno.");
            } else {
                System.out.println("Neispravan ID.");
            }
        }
    }

    private static void ispisiPolaznike(Scanner scanner) {
        System.out.print("ID programa: ");
        int programID = Integer.parseInt(scanner.nextLine());

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Upis> popis = session.createQuery(
                            "FROM Upis u WHERE u.programObrazovanja.programObrazovanjaID = :id", Upis.class)
                    .setParameter("id", programID)
                    .list();

            if (popis.isEmpty()) {
                System.out.println("Nema upisanih polaznika.");
            } else {
                popis.forEach(u -> System.out.println(u.getPolaznik()));
            }
        }
    }
}