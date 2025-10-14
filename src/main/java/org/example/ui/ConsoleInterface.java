package org.example.ui;

import org.example.model.ExchangeTable;
import org.example.strategy.repository.IRemoteRepository;
import org.example.strategy.repository.RESTRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Główna klasa odpowiedzialna za obsługę interfejsu użytkownika (CLI)
 */
public class ConsoleInterface {
    private final Scanner scanner;
    private final IRemoteRepository repository;
    private ExchangeTable currentTable;

    // Mapowanie opcji menu na ich "handlerów"
    private final Map<Integer, MenuHandler> handlers;

    public ConsoleInterface() {
        this.scanner = new Scanner(System.in);
        this.repository = new RESTRepository();
        this.handlers = new HashMap<>();
    }

    /**
     * Uruchamia aplikację konsolową
     */
    public void run() {
        System.out.println("=== APLIKACJA KURSÓW WALUT NBP ===");

        try {
            this.currentTable = repository.fetchExchangeTable();
            System.out.println("Pobrano aktualną tabelę kursów (" + currentTable.getId() + ")");
        } catch (Exception e) {
            System.out.println("Błąd podczas pobierania kursów: " + e.getMessage());
            return;
        }

        // Rejestracja opcji menu
        handlers.put(1, new DisplayTableHandler(currentTable));
        handlers.put(2, new ExchangeCurrencyHandler(currentTable, scanner));
        handlers.put(3, new ExitHandler());

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Wybierz opcję: ");
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);
                MenuHandler handler = handlers.get(choice);

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
