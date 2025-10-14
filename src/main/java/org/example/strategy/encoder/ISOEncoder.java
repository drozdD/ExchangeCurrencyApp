package org.example.strategy.encoder;

import java.nio.charset.StandardCharsets;

/**
 * Implementacja kodera konwertująca tekst na ISO-8859-2
 * (typowe dla polskich znaków)
 */
public class ISOEncoder implements Encoder {

    @Override
    public String encode(String input) {
        if (input == null) {
            return "";
        }
        // Konwertuj do bajtów UTF-8, a następnie odczytaj jako ISO-8859-2
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        return new String(bytes, StandardCharsets.ISO_8859_1);
    }
}
