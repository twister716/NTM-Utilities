package io.github.twister716.hbmutilities.bomb;

import io.github.twister716.hbmutilities.HbmUtilities;
import java.util.Locale;

public enum StrengthType {
    NONE,
    RADIUS,
    POWER,
    COUNT,
    DEPTH;


    public String getTranslationKey() {
        return "trait." + HbmUtilities.MODID + "." + name().toLowerCase(Locale.ROOT);
    }
}