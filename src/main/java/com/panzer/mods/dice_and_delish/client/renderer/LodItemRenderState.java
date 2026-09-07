package com.panzer.mods.dice_and_delish.client.renderer;

//? >1.21.3 {
/*import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public final class LodItemRenderState {
    private LodItemRenderState() {}

    private static final ItemStackRenderState SCRATCH = new ItemStackRenderState();

    public static void render(ItemStack stack, ItemDisplayContext context, PoseStack poseStack,
                              MultiBufferSource bufferSource, int packedLight, int packedOverlay,
                              ClientLevel level, LivingEntity entity, int seed, boolean pastLod1) {
        SCRATCH.clear();
        ItemLodState.setPastLod1(pastLod1);
        Minecraft.getInstance().getItemModelResolver()
                .updateForTopItem(SCRATCH, stack, context, false, level, entity, seed);
        SCRATCH.render(poseStack, bufferSource, packedLight, packedOverlay);
    }
}
*///?}
