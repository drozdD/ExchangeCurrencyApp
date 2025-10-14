package org.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Reprezentuje kompletną tabelę kursów walut z NBP
 */
public class ExchangeTable {
    private String id;                              // Identyfikator tabeli (np. "245/A/NBP/2024")
    private LocalDateTime timestamp;                 // Data publikacji
    private Map<String, ExchangeRate> rates;        // Mapa kursów (klucz: kod waluty)

    // Kurs PLN (dla przeliczania PLN na inne waluty)
    private static final ExchangeRate PLN_RATE = new ExchangeRate("PLN", "złoty polski", 1.0, 1.0);

    // Konstruktor domyślny
    public ExchangeTable() {
        this.rates = new HashMap<>();
        this.timestamp = LocalDateTime.now();
        // Dodaj PLN jako dostępną walutę
        rates.put("PLN", PLN_RATE);
    }

    // Konstruktor pełny
    public ExchangeTable(String id, LocalDateTime timestamp) {
        this.id = id;
        this.timestamp = timestamp;
        this.rates = new HashMap<>();
        // Dodaj PLN jako dostępną walutę
        rates.put("PLN", PLN_RATE);
    }

    // Gettery
    public String getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Collection<ExchangeRate> getRates() {
        return new ArrayList<>(rates.values());
    }

    // Settery
    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID tabeli nie może być puste");
        }
        this.id = id;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp nie może być null");
        }
        this.timestamp = timestamp;
    }

    /**
     * Dodaje kurs waluty do tabeli
     * @param rate kurs do dodania
     */
    public void addRate(ExchangeRate rate) {
        if (rate == null) {
            throw new IllegalArgumentException("Kurs nie może być null");
        }
        rates.put(rate.getId(), rate);
    }

    /**
     * Ustawia całą kolekcję kursów
     * @param rates kolekcja kursów
     */
    public void setRates(Collection<ExchangeRate> rates) {
        if (rates == null) {
            throw new IllegalArgumentException("Kolekcja kursów nie może być null");
        }
        this.rates.clear();
        this.rates.put("PLN", PLN_RATE); // Zawsze dodaj PLN

        for (ExchangeRate rate : rates) {
            this.rates.put(rate.getId(), rate);
        }
    }

    /**
     * Pobiera kurs dla danego kodu waluty
     * @param code kod waluty (np. "USD", "EUR")
     * @return kurs waluty lub null jeśli nie znaleziono
     */
    public ExchangeRate getRate(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        return rates.get(code.toUpperCase());
    }

    /**
     * Sprawdza czy tabela zawiera kurs dla danej waluty
     * @param code kod waluty
     * @return true jeśli kurs istnieje
     */
    public boolean hasRate(String code) {
        return rates.containsKey(code.toUpperCase());
    }

    /**
     * Zwraca liczbę walut w tabeli
     * @return liczba walut
     */
    public int getCount() {
        return rates.size();
    }

    /**
     * Zwraca listę kodów wszystkich dostępnych walut
     * @return lista kodów walut (posortowana alfabetycznie)
     */
    public List<String> getAvailableCurrencies() {
        List<String> currencies = new ArrayList<>(rates.keySet());
        Collections.sort(currencies);
        return currencies;
    }

    /**
     * Formatuje datę publikacji
     * @return sformatowana data
     */
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tabela kursów NBP\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Data: ").append(getFormattedTimestamp()).append("\n");
        sb.append("Liczba walut: ").append(getCount()).append("\n");
        sb.append("\n");
        sb.append(String.format("%-6s %-30s %12s %10s\n", "Kod", "Nazwa", "Kurs", "Mnożnik"));
        sb.append("=".repeat(65)).append("\n");

        List<String> currencies = getAvailableCurrencies();
        for (String code : currencies) {
            ExchangeRate rate = rates.get(code);
            sb.append(String.format("%-6s %-30s %12.4f %10.0f\n",
                    rate.getId(),
                    rate.getName(),
                    rate.getRate(),
                    rate.getMultiplier()));
        }

        return sb.toString();
    }
}
