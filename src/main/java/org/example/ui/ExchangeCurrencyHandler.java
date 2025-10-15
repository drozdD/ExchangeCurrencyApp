package org.example.ui;

import org.example.business.Exchange;
import org.example.model.ExchangeRate;
import org.example.model.ExchangeTable;

/**
 * Handler do przeliczania walut.
 * Dziedziczy po ConsoleInterface.
 */
public class ExchangeCurrencyHandler extends ConsoleInterface {

    public ExchangeCurrencyHandler(ExchangeTable table, java.util.Scanner scanner) {
        super(table, scanner);
    }

    @Override
    public boolean handle() {
        clearScreen();
        System.out.println("=== PRZELICZANIE WALUT ===\n");

        System.out.print("Podaj kod waluty źródłowej (np. USD): ");
        String from = scanner.nextLine().trim().toUpperCase();

        System.out.print("Podaj kod waluty docelowej (np. EUR): ");
        String to = scanner.nextLine().trim().toUpperCase();

        System.out.print("Podaj kwotę: ");
        String amountStr = scanner.nextLine().replace(',', '.');;

        try {
            double amount = Double.parseDouble(amountStr);
            ExchangeRate r1 = currentTable.getRate(from);
            ExchangeRate r2 = currentTable.getRate(to);

            if (r1 == null || r2 == null) {
                System.out.println("Nie znaleziono waluty!");
                return true;
            }

            String result = Exchange.exchangeFormatted(amount, r1, r2);
            System.out.println("\n" + result);

        } catch (NumberFormatException e) {
            System.out.println("Niepoprawna kwota!");
        } catch (IllegalArgumentException e) {
            System.out.println("Błąd: " + e.getMessage());
        }

        return true;
    }
}