package org.example.ui;

/**
 * Handler odpowiedzialny za zakończenie działania programu.
 */
public class ExitHandler extends ConsoleInterface {

    public ExitHandler() {}

    @Override
    public boolean handle() {
        System.out.println("Zamykanie aplikacji... Do zobaczenia!");
        return false;
    }
}
