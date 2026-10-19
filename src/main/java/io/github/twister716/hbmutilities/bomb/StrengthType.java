package io.github.twister716.hbmutilities.bomb;

import io.github.twister716.hbmutilities.HbmUtilities;
import java.util.Locale;

public enum StrengthType {
    RADIUS,
    POWER;

    public String getTranslationKey() {
        return "trait." + HbmUtilities.MODID + "." + name().toLowerCase(Locale.ROOT);
    }
}