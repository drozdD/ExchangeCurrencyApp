package org.example;

import org.example.ui.ConsoleInterface;

/**
 * Klasa główna aplikacji wymiany walut NBP
 */
public class ExchangeApp {

    public void start() {
        ConsoleInterface ui = new ConsoleInterface();
        ui.run();
    }
}