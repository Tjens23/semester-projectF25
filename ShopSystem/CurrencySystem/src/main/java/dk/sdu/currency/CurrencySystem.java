package dk.sdu.currency;

public class CurrencySystem {
    private int balance; // Spillerens pengebeholdning

    // Constructor der sætter startbeløbet
    public CurrencySystem(int startingBalance) {
        this.balance = startingBalance;
    }

    // Henter den aktuelle balance
    public int getBalance() {
        return balance;
    }

    // Tilføjer penge til spillerens balance
    public void addCurrency(int amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Tilføjet " + amount + " til balancen. Ny balance: " + balance);
        }
    }

    // Fjerner penge fra spillerens balance (kun hvis der er nok penge)
    public boolean subtractCurrency(int amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Trukket " + amount + " fra balancen. Ny balance: " + balance);
            return true;
        }
        System.out.println("Ikke nok valuta. Transaktionen fejlede.");
        return false;
    }

    @Override
    public String toString() {
        return "Nuværende Balance: " + balance;
    }
}
