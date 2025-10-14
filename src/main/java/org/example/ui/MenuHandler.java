package org.example.ui;

/**
 * Interfejs dla wszystkich obsługiwaczy opcji menu
 */
public interface MenuHandler {
    /**
     * Obsługuje wybraną akcję w menu.
     * @return true jeśli aplikacja ma dalej działać, false jeśli zakończyć
     */
    boolean handle();
}
