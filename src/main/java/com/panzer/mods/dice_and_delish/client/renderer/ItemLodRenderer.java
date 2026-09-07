package com.panzer.mods.dice_and_delish.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

//? <1.21.4 {
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.neoforged.neoforge.client.ClientHooks;
//?} else {
/*import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.client.model.data.ModelData;

import java.util.List;
*///?}

public final class ItemLodRenderer {

    private ItemLodRenderer() {
    }

    public static void renderItem(ItemRenderer itemRenderer, ItemStack stack, ItemDisplayContext context,
                                  PoseStack poseStack, MultiBufferSource bufferSource,
                                  int packedLight, int packedOverlay, ClientLevel level, int seed, boolean pastLod1) {
        if (stack.isEmpty()) return;

        //? if <1.21.4 {
        BakedModel baseModel = itemRenderer.getModel(stack, level, null, seed);

        if (pastLod1) {
            BakedModel flatModel = FlatItemModelCache.flatten(baseModel);

            if (flatModel != baseModel) {
                renderFlatModelDirect(itemRenderer, flatModel, baseModel, stack, context, false, poseStack, bufferSource, packedLight, packedOverlay);
                return;
            }
        }

        itemRenderer.render(
                stack,
                context,
                false,
                poseStack,
                bufferSource,
                packedLight,
                packedOverlay,
                baseModel
        );
        //?} else {
        /*BakedModel baseModel = ModelManager.getModel();

        if (pastLod1) {
            BakedModel flatModel = FlatItemModelCache.flatten(baseModel);

            if (flatModel != baseModel) {
                renderFlatModelDirect(itemRenderer, flatModel, baseModel, stack, context, false, poseStack, bufferSource, packedLight, packedOverlay);
                return;
            }
        }

        ItemModelResolver resolver = Minecraft.getInstance().getItemModelResolver();
        ItemStackRenderState renderState = new ItemStackRenderState();
        resolver.updateForTopItem(renderState, stack, context, false, level, null, seed);
        renderState.render(poseStack, bufferSource, packedLight, packedOverlay);
        *///?}
    }

    @SuppressWarnings({"SameParameterValue", "unused"})
    private static void renderFlatModelDirect(ItemRenderer itemRenderer, BakedModel flatModel, BakedModel baseModel,
                                              ItemStack stack, ItemDisplayContext context, boolean leftHand,
                                              PoseStack poseStack, MultiBufferSource bufferSource,
                                              int packedLight, int packedOverlay) {
        poseStack.pushPose();

        //? if <1.21.4 {
        BakedModel transformedModel = ClientHooks.handleCameraTransforms(
                poseStack, baseModel, context, leftHand
        );
        //?} else {
        /*baseModel.applyTransform(context, poseStack, leftHand);
        *///?}

        poseStack.translate(-0.5F, -0.5F, -0.5F);

        //? if <1.21.2 {
        boolean solid = true;

        for (BakedModel modelPass : flatModel.getRenderPasses(stack, solid)) {
            for (RenderType renderType : modelPass.getRenderTypes(stack, solid)) {
                VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(
                        bufferSource,
                        renderType,
                        true,
                        stack.hasFoil()
                );

                itemRenderer.renderModelLists(modelPass, stack, packedLight, packedOverlay, poseStack, vertexConsumer);
            }
        }
        //?} else if >=1.21.4 {
        /*RandomSource rand = RandomSource.create(42L);

        for (RenderType renderType : flatModel.getRenderTypes(stack, rand)) {
            List<BakedQuad> quads = flatModel.getQuads(null, null, rand, ModelData.EMPTY, renderType);

            ItemStackRenderState.FoilType foilType = stack.hasFoil()
                    ? ItemStackRenderState.FoilType.STANDARD
                    : ItemStackRenderState.FoilType.NONE;

            ItemRenderer.renderItem(
                    context,
                    poseStack,
                    bufferSource,
                    packedLight,
                    packedOverlay,
                    new int[]{ItemRenderer.NO_TINT},
                    (BakedModel) quads,
                    renderType,
                    foilType
            );
        }
        *///?} else {
        /*for (BakedModel modelPass : flatModel.getRenderPasses(stack)) {
            for (RenderType renderType : modelPass.getRenderTypes(stack)) {
                VertexConsumer vertexConsumer = ItemRenderer.getFoilBuffer(
                        bufferSource,
                        renderType,
                        true,
                        stack.hasFoil()
                );

                itemRenderer.renderModelLists(modelPass, stack, packedLight, packedOverlay, poseStack, vertexConsumer);
            }
        }
        *///?}

        poseStack.popPose();
    }

    public static int renderedItemCountFor(ItemStack stack, boolean pastLod1, double distanceSqr) {
        int baseCount = StackedItemCount.countFor(stack);
        baseCount = Math.min(baseCount, LodConfig.MAX_RENDERED_ITEMS_HARD_CAP);

        if (!pastLod1 || baseCount <= 1) {
            return baseCount;
        }

        double currentCoverage = ScreenSpaceLod.coverage(LodConfig.ITEM_RADIUS_SQR, distanceSqr);
        double maxLodCoverage = LodConfig.ITEM_LOD_COVERAGE_THRESHOLD;
        double minCullCoverage = ScreenSpaceLod.coverage(LodConfig.ITEM_RADIUS_SQR, LodConfig.CULL_DISTANCE_SQR);

        double factor = (currentCoverage - minCullCoverage) / (maxLodCoverage - minCullCoverage);
        factor = Math.clamp(factor, 0.0, 1.0);

        int targetCount = 1 + (int) Math.round((baseCount - 1) * factor);
        return Math.clamp(targetCount, 1, baseCount);
    }
}
