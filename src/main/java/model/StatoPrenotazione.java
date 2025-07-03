package model;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Lo stato della prenotazione.
 */
public enum StatoPrenotazione {
    /**
     * Prenotazione confermata.
     */
    CONFERMATA,
    /**
     * Prenotazione in attesa.
     */
    IN_ATTESA,
    /**
     * Prenotazione cancellata.
     */
    CANCELLATA;

    @Override
    public String toString() {
        return Arrays.stream(name().split("_"))
                .map(s -> s.charAt(0) + s.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
