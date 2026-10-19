package io.github.twister716.hbmutilities.bomb;

import com.hbm.data.ExplosionData;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;

public record BombTrait(ExplosionType type, StrengthType strengthType, DoubleSupplier strengthSupplier,
                        boolean fallout, int falloutBonus, int tier, List<BombStat> extraStats) {

    public BombTrait(ExplosionType type, DoubleSupplier radiusSupplier, boolean fallout, int falloutBonus) {
        this(type, StrengthType.RADIUS, radiusSupplier, fallout, falloutBonus, 0, List.of());
    }

    public BombTrait(ExplosionType type, DoubleSupplier radiusSupplier, boolean fallout) {
        this(type, radiusSupplier, fallout, 0);
    }

    public static BombTrait power(ExplosionType type, DoubleSupplier powerSupplier) {
        return new BombTrait(type, StrengthType.POWER, powerSupplier, false, 0, 0, List.of());
    }

    public static BombTrait label(ExplosionType type) {
        return new BombTrait(type, StrengthType.NONE, () -> 0, false, 0, 0, List.of());
    }

    public BombTrait withTier(int newTier) {
        return new BombTrait(type, strengthType, strengthSupplier, fallout, falloutBonus, newTier, extraStats);
    }

    public BombTrait withStat(StrengthType statType, DoubleSupplier valueSupplier) {
        List<BombStat> stats = new ArrayList<>(extraStats);
        stats.add(new BombStat(statType, valueSupplier));
        return new BombTrait(type, strengthType, strengthSupplier, fallout, falloutBonus, tier, List.copyOf(stats));
    }

    public double getStrength() {
        return strengthSupplier.getAsDouble();
    }

    public boolean hasFallout() {
        return fallout && !ExplosionData.DISABLE_NUCLEAR.get();
    }

    public int getFalloutRadius() {
        return Math.max(1, (int) (getStrength() * 2.5 + falloutBonus) * ExplosionData.FALLOUT_RANGE.get() / 100);
    }
}