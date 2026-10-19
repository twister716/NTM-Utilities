package io.github.twister716.hbmutilities.bomb;

import io.github.twister716.hbmutilities.HbmUtilities;
import net.minecraft.ChatFormatting;

import java.util.Locale;

public enum ExplosionType {

    NUCLEAR(ChatFormatting.DARK_GREEN),
    THERMONUCLEAR(ChatFormatting.GOLD),
    EXTREME(ChatFormatting.DARK_RED),
    SCHRABIDIUM(ChatFormatting.AQUA),
    SOLINIUM(ChatFormatting.DARK_AQUA),
    BALEFIRE(ChatFormatting.GREEN),
    SALTED(ChatFormatting.DARK_GREEN),
    CONVENTIONAL(ChatFormatting.RED);

    private final ChatFormatting color;

    ExplosionType(ChatFormatting color) {
        this.color = color;
    }

    public ChatFormatting getColor() {
        return color;
    }

    // NUCLEAR → "trait.hbmutilities.explosion.nuclear"
    public String getTranslationKey() {
        return "trait." + HbmUtilities.MODID + ".explosion." + name().toLowerCase(Locale.ROOT);
    }
}
