package org.example.model;

import java.util.Objects;

/**
 * Reprezentuje kurs pojedynczej waluty
 */
public class ExchangeRate {
    private double rate;           // Wartość kursu
    private double multiplier;     // Mnożnik (np. 100 dla JPY)
    private String name;           // Nazwa waluty (np. "dolar amerykański")
    private String id;             // Kod waluty (np. "USD")

    // Konstruktor domyślny
    public ExchangeRate() {
        this.multiplier = 1.0; // Domyślny mnożnik
    }

    // Konstruktor pełny
    public ExchangeRate(String id, String name, double rate, double multiplier) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.multiplier = multiplier;
    }

    // Gettery
    public double getRate() {
        return rate;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    // Settery
    public void setRate(double rate) {
        if (rate <= 0) {
            throw new IllegalArgumentException("Kurs musi być większy od 0");
        }
        this.rate = rate;
    }

    public void setMultiplier(double multiplier) {
        if (multiplier <= 0) {
            throw new IllegalArgumentException("Mnożnik musi być większy od 0");
        }
        this.multiplier = multiplier;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nazwa waluty nie może być pusta");
        }
        this.name = name;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Kod waluty nie może być pusty");
        }
        this.id = id.toUpperCase(); // Zawsze wielkie litery dla kodów walut
    }

    /**
     * Porównuje dwa kursy walut
     * @param other drugi kurs do porównania
     * @return true jeśli kursy są identyczne
     */
    public boolean equals(ExchangeRate other) {
        if (other == null) return false;
        return this.id.equals(other.id) &&
                this.name.equals(other.name) &&
                Math.abs(this.rate - other.rate) < 0.0001 &&
                Math.abs(this.multiplier - other.multiplier) < 0.0001;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ExchangeRate that = (ExchangeRate) obj;
        return equals(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rate, multiplier);
    }

    /**
     * Oblicza efektywny kurs (rate/multiplier)
     * @return efektywny kurs waluty
     */
    public double getEffectiveRate() {
        return rate / multiplier;
    }

    @Override
    public String toString() {
        return String.format("%s (%s): %.4f (x%.0f)", name, id, rate, multiplier);
    }
}