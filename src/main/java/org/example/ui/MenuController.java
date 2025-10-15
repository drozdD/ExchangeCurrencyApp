package org.example.ui;

import org.example.model.ExchangeTable;
import org.example.strategy.repository.IRemoteRepository;
import org.example.strategy.repository.RESTRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Klasa zarządzająca logiką menu.
 * Tworzy konkretne handlery i reaguje na wybory użytkownika.
 */
public class MenuController {

    private final Scanner scanner;
    private final IRemoteRepository repository;
    private ExchangeTable currentTable;
    private final Map<Integer, ConsoleInterface> handlers;

    public MenuController() {
        this.scanner = new Scanner(System.in);
        this.repository = new RESTRepository();
        this.handlers = new HashMap<>();
    }

    public void start() {
        System.out.println("=== APLIKACJA KURSÓW WALUT NBP ===");

        try {
            currentTable = repository.fetchExchangeTable();
            System.out.println("Pobrano aktualną tabelę: " + currentTable.getId());
        } catch (Exception e) {
            System.out.println("Błąd podczas pobierania kursów: " + e.getMessage());
            return;
        }

        // Tworzenie handlerów (dziedziczą po ConsoleInterface)
        handlers.put(1, new DisplayTableHandler(currentTable, scanner));
        handlers.put(2, new ExchangeCurrencyHandler(currentTable, scanner));
        handlers.put(3, new ExitHandler());

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Wybierz opcję: ");
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);
                ConsoleInterface handler = handlers.get(choice);

                if (handler != null) {
                    running = handler.handle();
                } else {
                    System.out.println("Nieprawidłowa opcja menu!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź numer opcji!");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("MENU:");
        System.out.println("1. Wyświetl tabelę kursów");
        System.out.println("2. Przelicz waluty");
        System.out.println("3. Wyjście");
    }
}
