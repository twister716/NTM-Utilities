package io.github.twister716.hbmutilities.client;

import io.github.twister716.hbmutilities.HbmUtilities;
import io.github.twister716.hbmutilities.bomb.*;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = HbmUtilities.MODID, value = Dist.CLIENT)
public final class BombTooltipHandler {

    private static final String[] ROMAN_NUMERALS = {"", "I", "II", "III", "IV", "V"};

    private static final String MISSILE_TIER_KEY_PREFIX = "item.hbm.missile.tier.";

    private BombTooltipHandler() {
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onItemTooltip(ItemTooltipEvent event) {
        addUsedIn(event);
        addBombTraits(event);
    }

    private static void addUsedIn(ItemTooltipEvent event) {
        List<Block> bombs = BombParts.getBombs(event.getItemStack());
        if (bombs.isEmpty()) {
            return;
        }

        List<Component> tooltip = event.getToolTip();
        tooltip.add(Component.translatable("item.hbm.bomb_part.used_in").withStyle(ChatFormatting.GRAY));
        for (Block bomb : bombs) {
            tooltip.add(bomb.getName().withStyle(ChatFormatting.GRAY));
        }
    }

    private static void addBombTraits(ItemTooltipEvent event) {
        List<BombTrait> traits = BombTraits.get(event.getItemStack());
        if (traits.isEmpty()) {
            return;
        }

        List<Component> lines = new ArrayList<>();
        for (int i = 0; i < traits.size(); i++) {
            if (i > 0) {
                lines.add(Component.empty());
            }

            BombTrait trait = traits.get(i);
            lines.add(typeHeading(trait));
            if (trait.strengthType() != StrengthType.NONE) {
                lines.add(strengthLine(trait.strengthType(), trait.getStrength()));
            }
            for (BombStat stat : trait.extraStats()) {
                lines.add(strengthLine(stat.type(), stat.getValue()));
            }

            if (trait.hasFallout()) {
                lines.add(heading("trait." + HbmUtilities.MODID + ".fallout", ChatFormatting.DARK_GREEN));
                lines.add(strengthLine(StrengthType.RADIUS, trait.getFalloutRadius()));
            }
        }

        List<Component> tooltip = event.getToolTip();
        tooltip.addAll(findInsertIndex(tooltip), lines);
    }

    // DEのミサイルは「段階」の行のすぐ下に入れる。見つからなければ一番下
    private static int findInsertIndex(List<Component> tooltip) {
        for (int i = 0; i < tooltip.size(); i++) {
            if (tooltip.get(i).getContents() instanceof TranslatableContents contents
                    && contents.getKey().startsWith(MISSILE_TIER_KEY_PREFIX)) {
                return i + 1;
            }
        }
        return tooltip.size();
    }

    private static Component typeHeading(BombTrait trait) {
        ExplosionType type = trait.type();
        Component name = trait.tier() > 0
                ? Component.translatable(type.getTranslationKey(), ROMAN_NUMERALS[trait.tier()])
                : Component.translatable(type.getTranslationKey());
        return Component.literal("[").append(name).append("]").withStyle(type.getColor());
    }

    private static Component heading(String translationKey, ChatFormatting color) {
        return Component.literal("[")
                .append(Component.translatable(translationKey))
                .append("]")
                .withStyle(color);
    }

    private static Component strengthLine(StrengthType strengthType, double value) {
        return Component.translatable(strengthType.getTranslationKey(), formatNumber(value))
                .withStyle(ChatFormatting.YELLOW);
    }

    private static String formatNumber(double value) {
        return value == Math.rint(value) ? Long.toString((long) value) : Double.toString(value);
    }
}