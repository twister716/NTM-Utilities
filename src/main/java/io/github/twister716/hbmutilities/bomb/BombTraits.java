package io.github.twister716.hbmutilities.bomb;

import com.hbm.blockentity.bomb.BlockEntityNukeBalefire;
import com.hbm.blocks.bomb.*;
import com.hbm.data.ExplosionData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntSupplier;

import com.hbm.explosion.ExplosionNukeSmall;
import net.minecraft.world.level.block.Block;

public final class BombTraits {

    private static final Map<Class<? extends Block>, Function<Block, List<BombTrait>>> TRAITS = new HashMap<>();

    static {
        register(NukeGadget.class, ExplosionType.NUCLEAR, ExplosionData.GADGET_RADIUS::get, true);
        register(NukeBoy.class, ExplosionType.NUCLEAR, ExplosionData.BOY_RADIUS::get, true);
        register(NukeMan.class, ExplosionType.NUCLEAR, ExplosionData.MAN_RADIUS::get, true);
        register(NukeMike.class, ExplosionType.THERMONUCLEAR, ExplosionData.MIKE_RADIUS::get, true);
        register(NukeTsar.class, ExplosionType.THERMONUCLEAR, ExplosionData.TSAR_RADIUS::get, true);
        register(NukeN2.class, ExplosionType.EXTREME, ExplosionData.N2_RADIUS::get, false);
        register(NukePrototype.class, ExplosionType.SCHRABIDIUM, ExplosionData.PROTOTYPE_RADIUS::get, false);
        register(NukeFleija.class, ExplosionType.SCHRABIDIUM, ExplosionData.FLEIJA_RADIUS::get, false);
        register(NukeSolinium.class, ExplosionType.SOLINIUM, ExplosionData.SOLINIUM_RADIUS::get, false);
        register(NukeBalefire.class, ExplosionType.BALEFIRE, () -> BlockEntityNukeBalefire.DESTRUCTION_RANGE, false);
        register(BlockCrashedBomb.class, BombTraits::getDudTraits);

        register(BlockDynamite.class, BombTrait.power(ExplosionType.CONVENTIONAL, () -> 8));
        register(BlockTNT.class, BombTrait.power(ExplosionType.CONVENTIONAL, () -> 10));
        register(BlockSemtex.class, BombTrait.power(ExplosionType.CONVENTIONAL, () -> 12));
        register(BlockC4.class, BombTrait.power(ExplosionType.CONVENTIONAL, () -> 15));

        register(BlockChargeDynamite.class, BombTrait.power(ExplosionType.CONVENTIONAL, () -> 4));
        register(BlockChargeMiner.class, BombTrait.power(ExplosionType.CONVENTIONAL, () -> 4));
        register(BlockChargeSemtex.class, BombTrait.power(ExplosionType.CONVENTIONAL, () -> 10));
        register(BlockChargeC4.class, BombTrait.power(ExplosionType.CONVENTIONAL, () -> 15));

        register(BlockLandmineAP.class, BombTrait.power(ExplosionType.CONVENTIONAL, () -> 3));
        register(BlockLandmineShrap.class, BombTrait.power(ExplosionType.CONVENTIONAL, () -> 3));
        register(BlockLandmineHE.class, BombTrait.power(ExplosionType.CONVENTIONAL, () -> 4));
        register(BlockLandmineFat.class, BombTrait.power(ExplosionType.NUCLEAR, () -> 10));
        register(BlockLandmineNaval.class, BombTrait.power(ExplosionType.CONVENTIONAL, () -> 25));

        register(BlockDetCharge.class, BombTrait.power(ExplosionType.CONVENTIONAL, () -> 15));
        register(BlockDetMiner.class, BombTrait.power(ExplosionType.CONVENTIONAL, () -> 4));
        register(BlockDetNuke.class, ExplosionType.NUCLEAR, ExplosionData.MISSILE_RADIUS::get, true);

        register(BlockFissureBomb.class, BombTrait.power(ExplosionType.NUCLEAR,
                () -> (int) ExplosionNukeSmall.PARAMS_MEDIUM.blastRadius));
    }

    private BombTraits() {
    }

    public static void register(Class<? extends Block> blockClass, ExplosionType type, IntSupplier radius, boolean fallout) {
        register(blockClass, new BombTrait(type, radius, fallout));
    }

    public static void register(Class<? extends Block> blockClass, BombTrait... traits) {
        List<BombTrait> list = List.of(traits);
        register(blockClass, block -> list);
    }

    public static void register(Class<? extends Block> blockClass, Function<Block, List<BombTrait>> resolver) {
        TRAITS.put(blockClass, resolver);
    }

    public static List<BombTrait> get(Block block) {
        Function<Block, List<BombTrait>> resolver = TRAITS.get(block.getClass());
        return resolver == null ? List.of() : resolver.apply(block);
    }

    // 範囲の数字は BlockCrashedBomb#explode に直接書かれている値
    private static List<BombTrait> getDudTraits(Block block) {
        if (!(block instanceof BlockCrashedBomb dud)) {
            return List.of();
        }

        return switch (dud.type) {
            case BALEFIRE -> List.of(new BombTrait(ExplosionType.BALEFIRE,
                    () -> (int) (ExplosionData.FATMAN_RADIUS.get() * 1.25), false));
            case NUKE -> List.of(new BombTrait(ExplosionType.NUCLEAR, () -> 35, true));
            case SALTED -> List.of(new BombTrait(ExplosionType.SALTED, () -> 25, true, 25));
            case CONVENTIONAL -> List.of(BombTrait.power(ExplosionType.CONVENTIONAL, () -> 35));
        };
    }
}