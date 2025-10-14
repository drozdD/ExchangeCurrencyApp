package org.example.strategy.document;

import org.example.model.ExchangeRate;
import org.example.model.ExchangeTable;
import java.time.format.DateTimeFormatter;

/**
 * Generuje dokument XML z kursami walut (bez bibliotek zewnętrznych)
 */
public class XMLDocument implements Document {

    @Override
    public String generate(ExchangeTable table) throws Exception {
        if (table == null) {
            throw new IllegalArgumentException("Tabela kursów nie może być null");
        }

        StringBuilder sb = new StringBuilder();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<ExchangeTable id=\"").append(table.getId()).append("\" ")
                .append("timestamp=\"").append(table.getTimestamp().format(fmt)).append("\">\n");

        for (ExchangeRate rate : table.getRates()) {
            sb.append("  <Currency>\n");
            sb.append("    <Code>").append(rate.getId()).append("</Code>\n");
            sb.append("    <Name>").append(rate.getName()).append("</Name>\n");
            sb.append("    <Rate>").append(rate.getRate()).append("</Rate>\n");
            sb.append("    <Multiplier>").append(rate.getMultiplier()).append("</Multiplier>\n");
            sb.append("  </Currency>\n");
        }

        sb.append("</ExchangeTable>");
        return sb.toString();
    }
}
