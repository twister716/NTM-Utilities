package io.github.twister716.hbmutilities.compat;

import com.hbm.data.ExplosionData;
import com.hbmspace.blockentity.bomb.BlockEntityNukeAntimatter;
import com.hbmspace.blocks.bomb.BlockDetSalt;
import com.hbmspace.blocks.bomb.NukeAntimatter;
import io.github.twister716.hbmutilities.bomb.BombTraits;
import io.github.twister716.hbmutilities.bomb.ExplosionType;

public final class SpaceCompat {

    private SpaceCompat() {
    }

    public static void register() {
        BombTraits.register(NukeAntimatter.class, ExplosionType.ANTIMATTER,
                () -> BlockEntityNukeAntimatter.DESTRUCTION_RANGE, false);

        BombTraits.register(BlockDetSalt.class, ExplosionType.SALTED, ExplosionData.MISSILE_RADIUS::get, true);
    }
}