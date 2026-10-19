package io.github.twister716.hbmutilities.bomb;

import com.hbm.blockentity.bomb.BlockEntityNukeBalefire;
import com.hbm.blocks.bomb.*;
import com.hbm.data.ExplosionData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntSupplier;
import net.minecraft.world.level.block.Block;

public final class BombTraits {

    private static final Map<Class<? extends Block>, Function<Block, List<BombTrait>>> TRAITS = new HashMap<>();

    static {
        register(NukeGadget.class, ExplosionType.NUCLEAR, ExplosionData.GADGET_RADIUS::get, true);
        register(NukeBoy.class, ExplosionType.NUCLEAR, ExplosionData.BOY_RADIUS::get, true);
        register(NukeMan.class, ExplosionType.NUCLEAR, ExplosionData.MAN_RADIUS::get, true);
        register(NukeMike.class, ExplosionType.THERMONUCLEAR, ExplosionData.MIKE_RADIUS::get, true);
        register(NukeTsar.class,
                new BombTrait(ExplosionType.THERMONUCLEAR, ExplosionData.TSAR_RADIUS::get, true),
                new BombTrait(ExplosionType.NUCLEAR, ExplosionData.MAN_RADIUS::get, true));
        register(NukeN2.class, ExplosionType.EXTREME, ExplosionData.N2_RADIUS::get, false);
        register(NukePrototype.class, ExplosionType.SCHRABIDIUM, ExplosionData.PROTOTYPE_RADIUS::get, false);
        register(NukeFleija.class, ExplosionType.SCHRABIDIUM, ExplosionData.FLEIJA_RADIUS::get, false);
        register(NukeSolinium.class, ExplosionType.SOLINIUM, ExplosionData.SOLINIUM_RADIUS::get, false);
        register(NukeBalefire.class, ExplosionType.BALEFIRE, () -> BlockEntityNukeBalefire.DESTRUCTION_RANGE, false);
        register(BlockCrashedBomb.class, BombTraits::getDudTraits);
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
            case CONVENTIONAL -> List.of();
        };
    }
}