package atm_damianlesiewicz;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Damion
 */
public class HelloWorldThreadExt extends Thread {

    private String threadName;
    public Account acc = new Account("1234", "1234");

    public HelloWorldThreadExt(String name) {
        this.threadName = name;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.print(this.threadName);
        }
    }

    //uruchomienie 2 rownoleglych watkow
    public static void main(String[] args) throws InterruptedException {
        HelloWorldThreadExt thread1 = new HelloWorldThreadExt("A");
        HelloWorldThreadExt thread2 = new HelloWorldThreadExt("B");
        thread1.start();
        thread2.start();
        for (int i = 0; i < 100; i++) {
            System.out.print("M");
        }

    }
}

class Atm {

    /**
     * Baza kont to mapa, ponieważ przechowuje elementy z przypisanymi kluczami.
     * Kluczem jest unikalny numer konta.
     */
    private Map<String, Account> database;

    public Atm() {
        database = new HashMap<>();
        addAccount(new Account("12345","1234"));
        addAccount(new Account("00000","5801"));
    }

    public void login() {
        System.out.println("Witaj!");
        getAccessToAccount();
    }

    public void getAccessToAccount() {

        System.out.println("Podaj numer konta...");
        Scanner sc = new Scanner(System.in);
        String input = sc.toString();
        sc.close();

        if (database.containsKey(input)) {
            Account account = database.get(input);
            while(!account.isAccess()){
                database.get(input).checkPin();
            }
            
        } else {
            System.out.println("Numer nie istnieje. ");
            getAccessToAccount();
        }
    }

    /**
     * Dodaje konto do bazy danych
     *
     * @param acc obiekt konto do dodania
     */
    public void addAccount(Account acc) {
        database.put(acc.getNumber(), acc);
    }



    /**
     * Sprawdza czy konto o podanym numerze istnieje. Czy to void czy bool? Jak
     * korzystać z tego bankomatu?
     */
}

class Account {

    private boolean access;
    private int saldo;
    private String number;
    private String pin;

    /**
     * Konstruktor obiektu Account Domyślnie access = false, saldo = 0
     *
     * @param num numer konta
     * @param pin numer pin
     */
    public Account(String num, String pin) {
        access = false;
        this.number = num;
        this.pin = pin;
        this.saldo = 0;
        System.out.println("Numer konta: " + number + "\n Saldo konta: " + saldo);
        /*Dodawanie do bazy danych?*/
    }

    public boolean isAccess() {
        return this.access;
    }

    public String getNumber() {
        return this.number;
    }

    public void putMoneyIn() {
        if (isAccess()) {
            System.out.println("Jaką kwotę deklarujesz wpłacić?");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            sc.close();
            this.saldo += input;
            System.out.println("Wpłacono pomyślnie!");
            checkSaldo();
        } else {
            this.checkPin();
        }
    }

    public void checkSaldo() {
        if (isAccess()) {
            System.out.println("Saldo wynosi: " + saldo);
        } else {
            this.checkPin();
        }
    }

    public void getMoneyOut() {
        if (isAccess()) {
            System.out.println("Jaką kwotę chcesz wypłacić?");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            sc.close();
            if (input > saldo) {
                System.out.println("Niewystarczająca ilość środków na koncie.");
                checkSaldo();
                getMoneyOut();
            }
        } else {
            this.checkPin();
        }
    }

    public void checkNumber() {

        System.out.println("Podaj numer konta...");
        Scanner sc = new Scanner(System.in);
        String input = sc.toString();
        sc.close();

        if (input == this.number) {
            this.checkPin();
        } else {
            System.out.println("Numer nie istnieje. ");
            checkNumber();
        }
    }

    public void checkPin() {

        System.out.println("Podaj pin...");
        Scanner sc = new Scanner(System.in);
        String input = sc.toString();

        if (input == this.pin) {
            access = true;
        } else {
            System.out.println("Pin niepoprawny. ");
            checkPin();
        }

    }

}
