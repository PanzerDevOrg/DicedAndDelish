package com.panzer.mods.dice_and_delish.client.renderer;
//? >1.21.3 {
/*import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public record FlatItemModel(BakedModel baseModel) implements ItemModel {

    @Override
    public void update(ItemStackRenderState renderState, @NotNull ItemStack stack, @NotNull ItemModelResolver resolver,
                       @NotNull ItemDisplayContext displayContext, @Nullable ClientLevel level,
                       @Nullable LivingEntity entity, int seed) {

        BakedModel finalModel = ItemLodState.isPastLod1()
                ? FlatItemModelCache.flatten(baseModel)
                : baseModel;

        ItemStackRenderState.LayerRenderState layerState = renderState.newLayer();
        layerState.setupBlockModel(finalModel, net.minecraft.client.renderer.RenderType.cutout()); // ajustar RenderType según tu caso
    }
}
*///?}
