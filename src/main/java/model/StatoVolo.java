package model;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Lo stato del volo.
 */
public enum StatoVolo {
    /**
     * Volo programmato.
     */
    PROGRAMMATO,
    /**
     * Volo in ritardo.
     */
    IN_RITARDO,
    /**
     * Volo atterrato.
     */
    ATTERRATO,
    /**
     * Volo decollato.
     */
    DECOLLATO,
    /**
     * Volo cancellato.
     */
    CANCELLATO;

    @Override
    public String toString() {
        return Arrays.stream(name().split("_"))
                .map(s -> s.charAt(0) + s.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
