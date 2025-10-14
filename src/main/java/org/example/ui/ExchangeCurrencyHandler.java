package org.example.ui;

import org.example.business.Exchange;
import org.example.model.ExchangeRate;
import org.example.model.ExchangeTable;

import java.util.Scanner;

/**
 * Handler odpowiedzialny za przeliczanie walut
 */
public class ExchangeCurrencyHandler implements MenuHandler {

    private final ExchangeTable table;
    private final Scanner scanner;

    public ExchangeCurrencyHandler(ExchangeTable table, Scanner scanner) {
        this.table = table;
        this.scanner = scanner;
    }

    @Override
    public boolean handle() {
        System.out.println("\n=== PRZELICZANIE WALUT ===");

        System.out.print("Podaj kod waluty źródłowej (np. USD): ");
        String fromCode = scanner.nextLine().trim().toUpperCase();

        System.out.print("Podaj kod waluty docelowej (np. EUR): ");
        String toCode = scanner.nextLine().trim().toUpperCase();

        System.out.print("Podaj kwotę: ");
        String amountStr = scanner.nextLine();

        try {
            double amount = Double.parseDouble(amountStr);

            ExchangeRate fromRate = table.getRate(fromCode);
            ExchangeRate toRate = table.getRate(toCode);

            if (fromRate == null || toRate == null) {
                System.out.println("Nie znaleziono jednej z walut w tabeli!");
                return true;
            }

            String result = Exchange.exchangeFormatted(amount, fromRate, toRate);
            System.out.println(result);

        } catch (NumberFormatException e) {
            System.out.println("Niepoprawna kwota!");
        } catch (IllegalArgumentException e) {
            System.out.println("Błąd: " + e.getMessage());
        }

        return true;
    }
}
