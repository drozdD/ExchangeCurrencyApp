package org.example.strategy.encoder;

/**
 * Interfejs strategii kodowania tekstu (np. konwersja znaków, ISO itp.)
 */
public interface Encoder {
    /**
     * Koduje (lub dekoduje) dany tekst według wybranej strategii
     * @param input tekst wejściowy
     * @return wynik kodowania
     */
    String encode(String input);
}