package model;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *  Stato del bagaglio.
 */
public enum StatoBagaglio {
    /**
     * Il bagaglio è caricato.
     */
    CARICATO,
    /**
     * Il bagaglio è ritirabile.
     */
    RITIRABILE,
    /**
     * Il bagaglio è smarrito.
     */
    SMARRITO;

    @Override
    public String toString() {
        return Arrays.stream(name().split("_"))
                .map(s -> s.charAt(0) + s.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
