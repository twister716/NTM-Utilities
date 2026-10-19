package io.github.twister716.hbmutilities.bomb;

import com.hbm.data.ExplosionData;
import java.util.function.IntSupplier;

public record BombTrait(ExplosionType type, IntSupplier radiusSupplier, boolean fallout, int falloutBonus) {

    public BombTrait(ExplosionType type, IntSupplier radiusSupplier, boolean fallout) {
        this(type, radiusSupplier, fallout, 0);
    }

    public int getRadius() {
        return radiusSupplier.getAsInt();
    }

    public boolean hasFallout() {
        return fallout && !ExplosionData.DISABLE_NUCLEAR.get();
    }

    public int getFalloutRadius() {
        return Math.max(1, (int) (getRadius() * 2.5 + falloutBonus) * ExplosionData.FALLOUT_RANGE.get() / 100);
    }
}