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
    CONVENTIONAL(ChatFormatting.RED),

    MISSILE_HE(ChatFormatting.GRAY),
    MISSILE_IC(ChatFormatting.GOLD),
    MISSILE_CL(ChatFormatting.DARK_BLUE),
    MISSILE_BU(ChatFormatting.DARK_GRAY),
    MISSILE_NUCLEAR(ChatFormatting.DARK_GREEN),
    MISSILE_THERMONUCLEAR(ChatFormatting.GOLD),
    MISSILE_MICRO(ChatFormatting.DARK_GREEN),
    MISSILE_DOOMSDAY(ChatFormatting.DARK_PURPLE),
    MISSILE_SCHRABIDIUM(ChatFormatting.AQUA),
    MISSILE_STEALTH(ChatFormatting.GRAY),
    MISSILE_DECOY(ChatFormatting.GRAY),
    MISSILE_EMP(ChatFormatting.DARK_AQUA),
    MISSILE_SINGULARITY(ChatFormatting.DARK_GRAY),
    MISSILE_TAINT(ChatFormatting.LIGHT_PURPLE),
    MISSILE_TECTONIC(ChatFormatting.DARK_RED),
    MISSILE_ABM(ChatFormatting.DARK_GREEN);

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
