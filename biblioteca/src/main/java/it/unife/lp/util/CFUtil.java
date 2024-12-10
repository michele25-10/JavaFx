
package it.unife.lp.util;

public class CFUtil {

    /**
     * Controlla se un codice fiscale è valido.
     *
     * @param codiceFiscale Il codice fiscale da validare.
     * @return true se il codice fiscale è valido, false altrimenti.
     */
    public static boolean isCodiceFiscaleValido(String codiceFiscale) {
        // Controlla se il codice fiscale è null o non ha la lunghezza corretta (16
        // caratteri)
        if (codiceFiscale == null || codiceFiscale.length() != 16) {
            return false;
        }

        // Espressione regolare per il formato del codice fiscale italiano:
        // - 6 lettere (cognome e nome)
        // - 2 cifre (anno di nascita)
        // - 1 lettera (mese di nascita)
        // - 2 cifre (giorno di nascita e sesso)
        // - 4 lettere/numeri (codice del comune di nascita)
        // - 1 lettera (carattere di controllo)
        String regex = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z0-9]{4}[A-Z]$";

        return codiceFiscale.matches(regex);
    }
}
