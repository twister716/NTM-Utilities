package io.github.twister716.hbmutilities.client;

import io.github.twister716.hbmutilities.HbmUtilities;
import io.github.twister716.hbmutilities.bomb.BombTrait;
import io.github.twister716.hbmutilities.bomb.BombTraits;
import java.util.List;

import io.github.twister716.hbmutilities.bomb.StrengthType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = HbmUtilities.MODID, value = Dist.CLIENT)
public final class BombTooltipHandler {

    private BombTooltipHandler() {
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (!(event.getItemStack().getItem() instanceof BlockItem blockItem)) {
            return;
        }

        List<Component> tooltip = event.getToolTip();
        List<BombTrait> traits = BombTraits.get(blockItem.getBlock());

        for (int i = 0; i < traits.size(); i++) {
            if (i > 0) {
                tooltip.add(Component.empty());
            }

            BombTrait trait = traits.get(i);
            tooltip.add(heading(trait.type().getTranslationKey(), trait.type().getColor()));
            tooltip.add(strengthLine(trait.strengthType(), trait.getStrength()));

            if (trait.hasFallout()) {
                tooltip.add(heading("trait." + HbmUtilities.MODID + ".fallout", ChatFormatting.DARK_GREEN));
                tooltip.add(strengthLine(StrengthType.RADIUS, trait.getFalloutRadius()));
            }
        }
    }

    private static Component heading(String translationKey, ChatFormatting color) {
        return Component.literal("[")
                .append(Component.translatable(translationKey))
                .append("]")
                .withStyle(color);
    }

    private static Component strengthLine(StrengthType strengthType, int value) {
        return Component.translatable(strengthType.getTranslationKey(), value)
                .withStyle(ChatFormatting.YELLOW);
    }
}