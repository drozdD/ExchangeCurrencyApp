package org.example.strategy.repository;

import org.example.model.ExchangeTable;
import org.example.model.ExchangeRate;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacja repozytorium pobierająca kursy z API NBP w formacie XML
 * Przykład użycia:
 *     IRemoteRepository repo = new RESTRepository();
 *     ExchangeTable table = repo.fetchExchangeTable();
 */
public class RESTRepository implements IRemoteRepository {

    private static final String NBP_URL = "http://static.nbp.pl/dane/kursy/xml/LastA.xml";

    @Override
    public ExchangeTable fetchExchangeTable() throws Exception {
        URL url = new URL(NBP_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (InputStream stream = connection.getInputStream()) {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
            doc.getDocumentElement().normalize();

            // Pobierz identyfikator tabeli
            String tableId = doc.getElementsByTagName("numer_tabeli").item(0).getTextContent();

            ExchangeTable table = new ExchangeTable();
            table.setId(tableId);
            table.setTimestamp(LocalDateTime.now());

            NodeList positions = doc.getElementsByTagName("pozycja");
            List<ExchangeRate> rates = new ArrayList<>();

            for (int i = 0; i < positions.getLength(); i++) {
                Element el = (Element) positions.item(i);
                String currencyName = el.getElementsByTagName("nazwa_waluty").item(0).getTextContent();
                String converter = el.getElementsByTagName("przelicznik").item(0).getTextContent();
                String currencyCode = el.getElementsByTagName("kod_waluty").item(0).getTextContent();
                String rate = el.getElementsByTagName("kurs_sredni").item(0).getTextContent();

                // Zamień przecinek na kropkę i konwertuj
                double rateValue = Double.parseDouble(rate.replace(",", "."));
                double multiplier = Double.parseDouble(converter.replace(",", "."));

                ExchangeRate exRate = new ExchangeRate(currencyCode, currencyName, rateValue, multiplier);
                rates.add(exRate);
            }

            table.setRates(rates);
            return table;
        }
    }
}
