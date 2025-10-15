package org.example.business;

import org.example.model.ExchangeRate;

/**
 * Klasa statyczna zawierająca logikę przeliczania walut
 */
public class Exchange {

    // Prywatny konstruktor - klasa statyczna
    private Exchange() {
        throw new UnsupportedOperationException("Nie można tworzyć instancji klasy Exchange");
    }

    /**
     * @param amount kwota do przeliczenia
     * @param r1 kurs waluty źródłowej
     * @param r2 kurs waluty docelowej
     * @return przeliczona kwota w walucie docelowej
     * @throws IllegalArgumentException jeśli parametry są nieprawidłowe
     */
    public static double exchange(double amount, ExchangeRate r1, ExchangeRate r2) {
        // Walidacja parametrów
        if (r1 == null) {
            throw new IllegalArgumentException("Kurs waluty źródłowej nie może być null");
        }
        if (r2 == null) {
            throw new IllegalArgumentException("Kurs waluty docelowej nie może być null");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Kwota nie może być ujemna");
        }
        if (r1.getRate() <= 0 || r1.getMultiplier() <= 0) {
            throw new IllegalArgumentException("Nieprawidłowy kurs waluty źródłowej");
        }
        if (r2.getRate() <= 0 || r2.getMultiplier() <= 0) {
            throw new IllegalArgumentException("Nieprawidłowy kurs waluty docelowej");
        }

        // Obliczenie efektywnych kursów (w PLN)
        double effectiveRate1 = r1.getRate() / r1.getMultiplier();
        double effectiveRate2 = r2.getRate() / r2.getMultiplier();

        // Przeliczenie przez PLN jako walutę pośrednią
        // 1. Przelicz amount z waluty źródłowej na PLN
        double amountInPLN = amount * effectiveRate1;

        // 2. Przelicz z PLN na walutę docelową
        double result = amountInPLN / effectiveRate2;

        return result;
    }

    /**
     * Przelicza kwotę z jednej waluty na drugą i formatuje wynik
     *
     * @param amount kwota do przeliczenia
     * @param r1 kurs waluty źródłowej
     * @param r2 kurs waluty docelowej
     * @return sformatowany string z wynikiem
     */
    public static String exchangeFormatted(double amount, ExchangeRate r1, ExchangeRate r2) {
        double result = exchange(amount, r1, r2);

        return String.format("%.2f %s = %.2f %s",
                amount, r1.getId(),
                result, r2.getId());
    }

    /**
     * Oblicza wartość kwoty w PLN
     *
     * @param amount kwota
     * @param rate kurs waluty
     * @return wartość w PLN
     */
    public static double toPLN(double amount, ExchangeRate rate) {
        if (rate == null) {
            throw new IllegalArgumentException("Kurs nie może być null");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Kwota nie może być ujemna");
        }

        double effectiveRate = rate.getRate() / rate.getMultiplier();
        return amount * effectiveRate;
    }

    /**
     * Oblicza ile waluty można kupić za daną kwotę PLN
     *
     * @param amountPLN kwota w PLN
     * @param rate kurs waluty
     * @return ilość waluty
     */
    public static double fromPLN(double amountPLN, ExchangeRate rate) {
        if (rate == null) {
            throw new IllegalArgumentException("Kurs nie może być null");
        }
        if (amountPLN < 0) {
            throw new IllegalArgumentException("Kwota nie może być ujemna");
        }

        double effectiveRate = rate.getRate() / rate.getMultiplier();
        return amountPLN / effectiveRate;
    }
}
