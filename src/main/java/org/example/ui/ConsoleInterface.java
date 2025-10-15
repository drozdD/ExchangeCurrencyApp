package org.example.ui;

import org.example.model.ExchangeTable;
import java.util.Scanner;

/**
 * Klasa bazowa dla interfejsu konsolowego i wszystkich handlerów menu.
 * Udostępnia wspólne pola i metodę abstrakcyjną handle().
 */
public abstract class ConsoleInterface {

    protected Scanner scanner;
    protected ExchangeTable currentTable;

    public ConsoleInterface() {}

    public ConsoleInterface(ExchangeTable currentTable, Scanner scanner) {
        this.currentTable = currentTable;
        this.scanner = scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setCurrentTable(ExchangeTable currentTable) {
        this.currentTable = currentTable;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public ExchangeTable getCurrentTable() {
        return currentTable;
    }

    /**
     * Każdy handler definiuje swoją logikę w tej metodzie.
     * @return true jeśli aplikacja ma działać dalej, false jeśli zakończyć.
     */
    public abstract boolean handle();

    /**
     * Pomocnicza metoda do czyszczenia konsoli.
     */
    protected void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
