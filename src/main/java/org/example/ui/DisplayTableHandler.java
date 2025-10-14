package org.example.ui;

import org.example.model.ExchangeTable;

/**
 * Handler odpowiedzialny za wyświetlenie tabeli kursów
 */
public class DisplayTableHandler implements MenuHandler {

    private final ExchangeTable table;

    public DisplayTableHandler(ExchangeTable table) {
        this.table = table;
    }

    @Override
    public boolean handle() {
        System.out.println();
        System.out.println(table.toString());
        return true;
    }
}