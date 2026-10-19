package io.github.twister716.hbmutilities.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BombParts {

    private static final Map<Item, List<Block>> USED_IN = new HashMap<>();

    static {
        register(ModItems.GADGET_WIREING, ModBlocks.NUKE_GADGET.get());
        register(ModItems.GADGET_CORE, ModBlocks.NUKE_GADGET.get());
        register(ModItems.EARLY_EXPLOSIVE_LENSES, ModBlocks.NUKE_GADGET.get(), ModBlocks.NUKE_MAN.get());

        register(ModItems.BOY_SHIELDING, ModBlocks.NUKE_BOY.get());
        register(ModItems.BOY_TARGET, ModBlocks.NUKE_BOY.get());
        register(ModItems.BOY_BULLET, ModBlocks.NUKE_BOY.get());
        register(ModItems.BOY_PROPELLANT, ModBlocks.NUKE_BOY.get());
        register(ModItems.BOY_IGNITER, ModBlocks.NUKE_BOY.get());

        register(ModItems.MAN_IGNITER, ModBlocks.NUKE_MAN.get());
        register(ModItems.MAN_CORE, ModBlocks.NUKE_MAN.get(), ModBlocks.NUKE_MIKE.get(), ModBlocks.NUKE_TSAR.get());
        register(ModItems.EXPLOSIVE_LENSES, ModBlocks.NUKE_MIKE.get(), ModBlocks.NUKE_TSAR.get());

        register(ModItems.MIKE_CORE, ModBlocks.NUKE_MIKE.get());
        register(ModItems.MIKE_DEUT, ModBlocks.NUKE_MIKE.get());
        register(ModItems.MIKE_COOLING_UNIT, ModBlocks.NUKE_MIKE.get());
        register(ModItems.TSAR_CORE, ModBlocks.NUKE_TSAR.get());

        register(ModItems.EGG_BALEFIRE, ModBlocks.NUKE_FSTBMB.get());
        register(ModItems.BATTERY_SPARK, ModBlocks.NUKE_FSTBMB.get());
        register(ModItems.BATTERY_TRIXITE, ModBlocks.NUKE_FSTBMB.get());
    }

    private BombParts() {
    }

    public static void register(ItemLike part, Block... bombs) {
        USED_IN.put(part.asItem(), List.of(bombs));
    }

    public static List<Block> getBombs(ItemStack stack) {
        return USED_IN.getOrDefault(stack.getItem(), List.of());
    }
}