package com.panzer.mods.dice_and_delish.client.renderer;

//? >1.21.3 {
/*import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.BakedModel;
import org.jetbrains.annotations.NotNull;

import javax.naming.spi.Resolver;

public record FlatItemModelUnbaked(ItemModel.Unbaked base) implements ItemModel.Unbaked {

    public static final MapCodec<FlatItemModelUnbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    getType().fieldOf("base").forGetter(FlatItemModelUnbaked::base)
            ).apply(instance, FlatItemModelUnbaked::new)
    );

    @Override
    public void resolveDependencies(@NotNull Resolver resolver) {
        base.resolveDependencies(resolver);
    }

    @Override
    public @NotNull MapCodec<? extends ItemModel.Unbaked> type() {
        return MAP_CODEC;
    }

    public static @NotNull MapCodec<? extends ItemModel.Unbaked> getType() {
        return MAP_CODEC;
    }

    @Override
    public @NotNull ItemModel bake(ItemModel.@NotNull BakingContext context) {
        ItemModel bakedBase = base.bake(context);
        return new FlatItemModel((BakedModel) bakedBase);
    }
}
*///?}
