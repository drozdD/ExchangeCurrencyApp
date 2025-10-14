package org.example.strategy.repository;

import org.example.model.ExchangeTable;

/**
 * Interfejs strategii do pobierania danych z zewnętrznego źródła (np. API NBP)
 */
public interface IRemoteRepository {
    /**
     * Pobiera aktualną tabelę kursów walut.
     * @return obiekt ExchangeTable zawierający kursy walut
     * @throws Exception w razie błędów połączenia lub parsowania
     */
    ExchangeTable fetchExchangeTable() throws Exception;
}