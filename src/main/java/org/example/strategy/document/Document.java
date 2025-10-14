package org.example.strategy.document;

import org.example.model.ExchangeTable;

/**
 * Interfejs strategii generowania dokumentów z danymi o kursach walut
 */
public interface Document {
    /**
     * Generuje reprezentację tabeli kursów w danym formacie (np. XML)
     * @param table tabela kursów
     * @return tekstowa reprezentacja dokumentu
     * @throws Exception w razie błędów generowania
     */
    String generate(ExchangeTable table) throws Exception;
}