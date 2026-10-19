package io.github.twister716.hbmutilities.bomb;

import java.util.function.DoubleSupplier;

public record BombStat(StrengthType type, DoubleSupplier valueSupplier) {

    public double getValue() {
        return valueSupplier.getAsDouble();
    }
}