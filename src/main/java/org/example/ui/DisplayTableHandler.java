package org.example.ui;

import org.example.model.ExchangeTable;

/**
 * Handler wyświetlający tabelę kursów.
 * Dziedziczy po ConsoleInterface.
 */
public class DisplayTableHandler extends ConsoleInterface {

    public DisplayTableHandler(ExchangeTable table, java.util.Scanner scanner) {
        super(table, scanner);
    }

    @Override
    public boolean handle() {
        clearScreen();
        System.out.println("=== AKTUALNA TABELA KURSÓW ===\n");
        System.out.println(currentTable.toString());
        return true;
    }
}