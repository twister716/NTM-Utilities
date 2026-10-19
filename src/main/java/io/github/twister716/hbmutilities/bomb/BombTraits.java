package io.github.twister716.hbmutilities.bomb;

import com.hbm.blockentity.bomb.BlockEntityNukeBalefire;
import com.hbm.blocks.bomb.*;
import com.hbm.data.ExplosionData;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.items.ModItems;
import io.github.twister716.hbmutilities.compat.SpaceCompat;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleSupplier;
import java.util.function.Function;

public final class BombTraits {

    private static final Map<Class<? extends Block>, Function<Block, List<BombTrait>>> TRAITS = new HashMap<>();

    private static final Map<Item, List<BombTrait>> ITEM_TRAITS = new HashMap<>();

    static {
        register(NukeGadget.class, ExplosionType.NUCLEAR, ExplosionData.GADGET_RADIUS::get, true);
        register(NukeBoy.class, ExplosionType.NUCLEAR, ExplosionData.BOY_RADIUS::get, true);
        register(NukeMan.class, ExplosionType.NUCLEAR, ExplosionData.MAN_RADIUS::get, true);
        register(NukeMike.class, ExplosionType.THERMONUCLEAR, ExplosionData.MIKE_RADIUS::get, true);
        register(NukeTsar.class, ExplosionType.THERMONUCLEAR, ExplosionData.TSAR_RADIUS::get, true);
        register(NukeN2.class, ExplosionType.EXTREME, ExplosionData.N2_RADIUS::get, false);
        register(NukePrototype.class, BombTrait.folkvangr(ExplosionType.SCHRABIDIUM, ExplosionData.PROTOTYPE_RADIUS::get));
        register(NukeFleija.class, BombTrait.folkvangr(ExplosionType.SCHRABIDIUM, ExplosionData.FLEIJA_RADIUS::get));
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
                () -> ExplosionNukeSmall.PARAMS_MEDIUM.blastRadius));


        registerItem(ModItems.MISSILE_GENERIC, BombTrait.power(ExplosionType.MISSILE_HE, () -> 15).withTier(1));
        registerItem(ModItems.MISSILE_STRONG, BombTrait.power(ExplosionType.MISSILE_HE, () -> 30).withTier(2));
        registerItem(ModItems.MISSILE_BURST, BombTrait.power(ExplosionType.MISSILE_HE, () -> 50).withTier(3));

        registerItem(ModItems.MISSILE_INCENDIARY, BombTrait.power(ExplosionType.MISSILE_IC, () -> 15).withTier(1));
        registerItem(ModItems.MISSILE_INCENDIARY_STRONG, BombTrait.power(ExplosionType.MISSILE_IC, () -> 30).withTier(2));
        registerItem(ModItems.MISSILE_INFERNO, BombTrait.power(ExplosionType.MISSILE_IC, () -> 50).withTier(3));

        registerItem(ModItems.MISSILE_CLUSTER, BombTrait.power(ExplosionType.MISSILE_CL, () -> 5).withTier(1)
                .withStat(StrengthType.COUNT, () -> 25));
        registerItem(ModItems.MISSILE_CLUSTER_STRONG, BombTrait.power(ExplosionType.MISSILE_CL, () -> 15).withTier(2)
                .withStat(StrengthType.COUNT, () -> 50));
        registerItem(ModItems.MISSILE_RAIN, BombTrait.power(ExplosionType.MISSILE_CL, () -> 25).withTier(3)
                .withStat(StrengthType.COUNT, () -> 100));

        registerItem(ModItems.MISSILE_BUSTER, BombTrait.power(ExplosionType.MISSILE_BU, () -> 5).withTier(1)
                .withStat(StrengthType.DEPTH, () -> 15));
        registerItem(ModItems.MISSILE_BUSTER_STRONG, BombTrait.power(ExplosionType.MISSILE_BU, () -> 7.5).withTier(2)
                .withStat(StrengthType.DEPTH, () -> 20));
        registerItem(ModItems.MISSILE_DRILL, BombTrait.power(ExplosionType.MISSILE_BU, () -> 10).withTier(3)
                .withStat(StrengthType.DEPTH, () -> 30));

        registerItem(ModItems.MISSILE_NUCLEAR,
                new BombTrait(ExplosionType.MISSILE_NUCLEAR, ExplosionData.MISSILE_RADIUS::get, true));
        registerItem(ModItems.MISSILE_NUCLEAR_CLUSTER,
                new BombTrait(ExplosionType.MISSILE_THERMONUCLEAR, () -> ExplosionData.MISSILE_RADIUS.get() * 2, true));
        registerItem(ModItems.MISSILE_DOOMSDAY,
                new BombTrait(ExplosionType.MISSILE_DOOMSDAY, () -> ExplosionData.MISSILE_RADIUS.get() * 2, true, 100));
        registerItem(ModItems.MISSILE_DOOMSDAY_RUSTED,
                new BombTrait(ExplosionType.MISSILE_DOOMSDAY, ExplosionData.MISSILE_RADIUS::get, true, 100));
        registerItem(ModItems.MISSILE_MICRO,
                new BombTrait(ExplosionType.MISSILE_MICRO, () -> (int) ExplosionNukeSmall.PARAMS_HIGH.blastRadius, true));
        registerItem(ModItems.MISSILE_SCHRABIDIUM,
                BombTrait.folkvangr(ExplosionType.MISSILE_SCHRABIDIUM, ExplosionData.A_SCHRAB_RADIUS::get));

        registerItem(ModItems.MISSILE_STEALTH, BombTrait.power(ExplosionType.MISSILE_STEALTH, () -> 20));
        registerItem(ModItems.MISSILE_DECOY, BombTrait.power(ExplosionType.MISSILE_DECOY, () -> 4));

        registerItem(ModItems.MISSILE_EMP, BombTrait.label(ExplosionType.MISSILE_EMP));
        registerItem(ModItems.MISSILE_EMP_STRONG, BombTrait.label(ExplosionType.MISSILE_EMP));
        registerItem(ModItems.MISSILE_BHOLE, BombTrait.label(ExplosionType.MISSILE_SINGULARITY));
        registerItem(ModItems.MISSILE_TAINT, BombTrait.label(ExplosionType.MISSILE_TAINT));
        registerItem(ModItems.MISSILE_VOLCANO, BombTrait.label(ExplosionType.MISSILE_TECTONIC));
        registerItem(ModItems.MISSILE_ANTI_BALLISTIC, BombTrait.label(ExplosionType.MISSILE_ABM));

        if (ModList.get().isLoaded("hbmspace")) {
            SpaceCompat.register();
        }
    }

    private BombTraits() {
    }

    public static void register(Class<? extends Block> blockClass, ExplosionType type, DoubleSupplier radius, boolean fallout) {
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

    public static void registerItem(ItemLike item, BombTrait... traits) {
        ITEM_TRAITS.put(item.asItem(), List.of(traits));
    }

    // ブロックのアイテムならブロックの表、それ以外はアイテムの表から探す
    public static List<BombTrait> get(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem blockItem) {
            return get(blockItem.getBlock());
        }
        return ITEM_TRAITS.getOrDefault(stack.getItem(), List.of());
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