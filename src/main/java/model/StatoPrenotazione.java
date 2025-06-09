package model;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum StatoPrenotazione {
    CONFERMATA,
    IN_ATTESA,
    CANCELLATA;

    @Override
    public String toString() {
        return Arrays.stream(name().split("_"))
                .map(s -> s.charAt(0) + s.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
