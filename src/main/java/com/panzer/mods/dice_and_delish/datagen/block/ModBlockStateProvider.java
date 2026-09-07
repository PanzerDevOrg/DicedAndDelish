package com.panzer.mods.dice_and_delish.datagen.block;

import com.panzer.mods.dice_and_delish.DiceAndDelish;
import com.panzer.mods.dice_and_delish.block.FertileFarmlandBlock;
import com.panzer.mods.dice_and_delish.crop.RiceCropBlock;
import com.panzer.mods.dice_and_delish.crop.TomatoCropPoleBlock;
import com.panzer.mods.dice_and_delish.crop.TripleBlockHalf;
import com.panzer.mods.dice_and_delish.crop.TriplePlantBlock;
import com.panzer.mods.dice_and_delish.registry.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.BiFunction;
import java.util.function.Function;

//? <1.21.4 {
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import com.panzer.mods.dice_and_delish.datagen.util.DatagenTracker;
//?} else {
/*import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.Condition;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
*///?}

@SuppressWarnings("SameParameterValue")
//? <1.21.4 {
public class ModBlockStateProvider extends BlockStateProvider {
 //?} else {
/*public class ModBlockStateProvider extends ModelProvider {
*///?}

    private static final int VISUAL_AGE_4 = 4;
    private static final int VISUAL_AGE_5 = 5;
    private static final int VISUAL_AGE_6 = 6;
    private static final int POLE_THRESHOLD = 2;

    //? <1.21.4 {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DiceAndDelish.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
    //?} else {
    /*private BlockModelGenerators blockModels;

    public ModBlockStateProvider(PackOutput output) {
        super(output, DiceAndDelish.MOD_ID);
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        this.blockModels = blockModels;
        *///?}
        // Blocks
        litModel(ModBlocks.GRILL_TABLE, "appliance/grill_table_lit", "appliance/grill_table_unlit", false);
        litModel(ModBlocks.GRILL_TABLE_SOUL, "appliance/grill_table_soul_lit", "appliance/grill_table_unlit", false);
        litModel(ModBlocks.GRILL_TABLE_UNLIT, "appliance/grill_table_lit", "appliance/grill_table_unlit", false);
        litModel(ModBlocks.GRILL_TABLE_SOUL_UNLIT, "appliance/grill_table_soul_lit", "appliance/grill_table_unlit", false);

        applianceLits(ModBlocks.SKILLET);
        applianceHorizontals(ModBlocks.CUTTING_BOARD);

        applianceSimple(ModBlocks.ORGANIC_SOIL);
        fertileFarmland(ModBlocks.FERTILE_FARMLAND);

        // Wild Crops
        wildCrops(ModBlocks.WILD_TOMATO, ModBlocks.WILD_LETTUCE, ModBlocks.WILD_STRAWBERRY);

        wildCropTintable(ModBlocks.WILD_PURPLE_ONION);
        wildCropMulti(ModBlocks.WILD_RICE, TriplePlantBlock.HALF, TripleBlockHalf.values());

        // Crops
        existingCrop(ModBlocks.STRAWBERRY_CROP, BlockStateProperties.AGE_3, VISUAL_AGE_4,
                st -> "crop/strawberry/strawberry_crop_stage" + st);

        mixedLettuceCrop();

        setupCrop(ModBlocks.PURPLE_ONION_CROP, BlockStateProperties.AGE_3, VISUAL_AGE_4, (st, tex) ->
                cropModel("purple_onion/purple_onion_crop_stage" + st, tex, false));

        setupCrop(ModBlocks.TOMATO_CROP, BlockStateProperties.AGE_4, VISUAL_AGE_5, (st, tex) ->
                cropModel("tomato/tomato_crop_stage" + st, tex, true));

        crossCropMulti(ModBlocks.RICE_CROP, RiceCropBlock.HALF, DoubleBlockHalf.values(),
                BlockStateProperties.AGE_4, VISUAL_AGE_5,
                (stage, half) -> half == DoubleBlockHalf.UPPER && stage < 3);

        tomatoCropPole(ModBlocks.TOMATO_CROP_POLE);
    }

    private ResourceLocation blockLoc(String relativePath) {
        //? <1.21.4 {
        return modLoc("block/" + relativePath);
         //?} else {
        /*return this.modLocation("block/" + relativePath);
        *///?}
    }

    //? <1.21.4 {
    private ModelFile getExistingModel(String relativePath) {
        DatagenTracker.trackModel(models().existingFileHelper, "block/" + relativePath);
        return models().getExistingFile(blockLoc(relativePath));
    }
    //?} else {
    /*private ResourceLocation getExistingModel(String relativePath) {
        return blockLoc(relativePath);
    }
    *///?}

    //? <1.21.4 {
    private ModelFile cropModel(String name, ResourceLocation tex, boolean cross) {
        String cropName = "crop/" + name;
        return (cross ? models().cross(cropName, tex) : models().crop(cropName, tex))
                .renderType("minecraft:cutout");
    }
    //?} else {
    /*@SuppressWarnings("unused")
    private ResourceLocation cropModel(String name, ResourceLocation tex) {
        return cropModel(name, tex, true);
    }

    private ResourceLocation cropModel(String name, ResourceLocation tex, boolean cross) {
        String cropName = "crop/" + name;
        ModelTemplate base = cross ? ModelTemplates.CROSS : ModelTemplates.CROP;
        ModelTemplate template = base.extend().renderType("minecraft:cutout").build();
        ResourceLocation id = this.modLocation("block/" + cropName);
        TextureMapping mapping = new TextureMapping()
                .put(cross ? TextureSlot.CROSS : TextureSlot.CROP, tex);
        return template.create(id, mapping, blockModels.modelOutput);
    }
    *///?}

    // block/appliance/

    //? <1.21.4 {
    private void applianceSimple(DeferredBlock<?> block) {
        String name = block.getId().getPath();
        DatagenTracker.trackTexture(models().existingFileHelper, "block/appliance/" + name);
        var model = models().cubeAll(name, blockLoc("appliance/" + name));
        simpleBlock(block.get(), model);
    }
    //?} else {
    /*private void applianceSimple(DeferredBlock<?> block) {
        String name = block.getId().getPath();
        ResourceLocation tex = blockLoc("appliance/" + name);
        ResourceLocation modelLoc = ModelTemplates.CUBE_ALL.create(
                block.get(), new TextureMapping().put(TextureSlot.ALL, tex), blockModels.modelOutput);
        simpleBlockState(block.get(), modelLoc);
    }
    *///?}

    private void applianceHorizontals(DeferredBlock<?>... blocks) {
        for (DeferredBlock<?> block : blocks) {
            String path = "appliance/" + block.getId().getPath();
            //? <1.21.4 {
            horizontalBlock(block.get(), getExistingModel(path));
             //?} else {
            /*horizontalBlockState(block.get(), getExistingModel(path));
            *///?}
        }
    }

    private void applianceLits(DeferredBlock<?>... blocks) {
        for (DeferredBlock<?> block : blocks) {
            String basePath = "appliance/" + block.getId().getPath();
            litModel(block, basePath + "_lit", basePath + "_unlit", false);
        }
    }

    //? <1.21.4 {
    private void litModel(DeferredBlock<?> block, String litPath, String unlitPath, boolean ignoreLit) {
        ModelFile lit = getExistingModel(litPath);
        ModelFile unlit = unlitPath != null ? getExistingModel(unlitPath) : lit;

        Property<?>[] ignored = ignoreLit
                ? new Property<?>[]{BlockStateProperties.WATERLOGGED, BlockStateProperties.LIT}
                : new Property<?>[]{BlockStateProperties.WATERLOGGED};

        getVariantBuilder(block.get()).forAllStatesExcept(st -> {
            boolean isLit = !ignoreLit && st.getValue(BlockStateProperties.LIT);
            int yRot = ((int) st.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 360) % 360;
            return ConfiguredModel.builder().modelFile(isLit ? lit : unlit).rotationY(yRot).build();
        }, ignored);
    }
    //?} else {
    /*private void litModel(DeferredBlock<?> block, String litPath, String unlitPath, boolean ignoreLit) {
        ResourceLocation lit = getExistingModel(litPath);
        ResourceLocation unlit = unlitPath != null ? getExistingModel(unlitPath) : lit;

        PropertyDispatch.C2<net.minecraft.core.Direction, Boolean> dispatch = PropertyDispatch.properties(
                BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.LIT);

        for (net.minecraft.core.Direction facing : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            int yRot = ((int) facing.toYRot() + 360) % 360;
            for (boolean litState : new boolean[]{true, false}) {
                boolean isLit = !ignoreLit && litState;
                ResourceLocation modelLoc = isLit ? lit : unlit;
                dispatch.select(facing, litState,
                        Variant.variant()
                                .with(VariantProperties.MODEL, modelLoc)
                                .with(VariantProperties.Y_ROT,
                                        rotationFromDegrees(yRot)));
            }
        }

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(block.get()).with(dispatch));
    }
    *///?}

    //? <1.21.4 {
    private void fertileFarmland(DeferredBlock<?> block) {
        String name = block.getId().getPath();
        ModelFile[] models = new ModelFile[FertileFarmlandBlock.MAX_FERTILITY + 1];

        for (int i = 0; i <= FertileFarmlandBlock.MAX_FERTILITY; i++) {
            String topTexture = "appliance/" + (i > 0 ? "fertile_farmland_moist" : "fertile_farmland");
            DatagenTracker.trackTexture(models().existingFileHelper, "block/" + topTexture);

            models[i] = models().withExistingParent(name + "_" + i, mcLoc("block/template_farmland"))
                    .texture("dirt", mcLoc("minecraft:block/dirt"))
                    .texture("top", blockLoc(topTexture))
                    .renderType("minecraft:cutout");
        }

        getVariantBuilder(block.get()).forAllStates(st -> ConfiguredModel.builder()
                .modelFile(models[st.getValue(FertileFarmlandBlock.FERTILITY)]).build());
    }
    //?} else {
    /*private void fertileFarmland(DeferredBlock<?> block) {
        String name = block.getId().getPath();
        ResourceLocation[] models = new ResourceLocation[FertileFarmlandBlock.MAX_FERTILITY + 1];

        ModelTemplate farmlandTemplate = new ModelTemplate(
                Optional.of(this.mcLocation("block/template_farmland")),
                Optional.empty(),
                TextureSlot.create("dirt"), TextureSlot.create("top"))
                .extend().renderType("minecraft:cutout").build();

        for (int i = 0; i <= FertileFarmlandBlock.MAX_FERTILITY; i++) {
            String topTexture = "appliance/" + (i > 0 ? "fertile_farmland_moist" : "fertile_farmland");
            TextureMapping mapping = new TextureMapping()
                    .put(TextureSlot.create("dirt"), this.mcLocation("block/dirt"))
                    .put(TextureSlot.create("top"), blockLoc(topTexture));
            ResourceLocation id = this.modLocation("block/" + name + "_" + i);
            models[i] = farmlandTemplate.create(id, mapping, blockModels.modelOutput);
        }

        PropertyDispatch.C1<Integer> dispatch = PropertyDispatch.property(FertileFarmlandBlock.FERTILITY);
        for (int fertility : FertileFarmlandBlock.FERTILITY.getPossibleValues()) {
            dispatch.select(fertility, Variant.variant()
                    .with(VariantProperties.MODEL, models[fertility]));
        }

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(block.get()).with(dispatch));
    }
    *///?}

    // block/wild/

    private void wildCrops(DeferredBlock<?>... blocks) {
        for (DeferredBlock<?> block : blocks) {
            //? <1.21.4 {
            simpleBlock(block.get(), getExistingModel("wild/" + block.getId().getPath()));
             //?} else {
            /*simpleBlockState(block.get(), getExistingModel("wild/" + block.getId().getPath()));
            *///?}
        }
    }

    //? <1.21.4 {
    private void wildCropTintable(DeferredBlock<?> block) {
        String name = block.getId().getPath();
        var builder = getMultipartBuilder(block.get());

        DatagenTracker.trackTexture(models().existingFileHelper, "block/wild/" + name + "_tint");
        DatagenTracker.trackTexture(models().existingFileHelper, "block/wild/" + name + "_overlay");

        ModelFile tintModel = models().withExistingParent("wild/" + name + "_tint", mcLoc("block/tinted_cross"))
                .texture("cross", blockLoc("wild/" + name + "_tint"))
                .renderType("minecraft:cutout");

        ModelFile overlayModel = models().cross("wild/" + name + "_overlay", blockLoc("wild/" + name + "_overlay"))
                .renderType("minecraft:cutout");

        builder.part().modelFile(tintModel).addModel().end();
        builder.part().modelFile(overlayModel).addModel().end();
    }
    //?} else {
    /*private void wildCropTintable(DeferredBlock<?> block) {
        String name = block.getId().getPath();

        ModelTemplate tintTemplate = new ModelTemplate(
                Optional.of(this.mcLocation("block/tinted_cross")),
                Optional.empty(), TextureSlot.CROSS)
                .extend().renderType("minecraft:cutout").build();
        ResourceLocation tintModel = tintTemplate.create(
                this.modLocation("block/wild/" + name + "_tint"),
                new TextureMapping().put(TextureSlot.CROSS, blockLoc("wild/" + name + "_tint")),
                blockModels.modelOutput);

        ModelTemplate overlayTemplate = ModelTemplates.CROSS.extend().renderType("minecraft:cutout").build();
        ResourceLocation overlayModel = overlayTemplate.create(
                this.modLocation("block/wild/" + name + "_overlay"),
                new TextureMapping().put(TextureSlot.CROSS, blockLoc("wild/" + name + "_overlay")),
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                MultiPartGenerator.multiPart(block.get())
                        .with(Variant.variant().with(VariantProperties.MODEL, tintModel))
                        .with(Variant.variant().with(VariantProperties.MODEL, overlayModel)));
    }
    *///?}

    //? <1.21.4 {
    private <E extends Enum<E> & StringRepresentable> void wildCropMulti(
            DeferredBlock<?> block, Property<E> halfProp, E[] values) {
        var builder = getMultipartBuilder(block.get());
        for (E half : values) {
            String texName = block.getId().getPath() + "_" + half.getSerializedName();
            DatagenTracker.trackTexture(models().existingFileHelper, "block/wild/" + texName);

            ModelFile model = models().singleTexture("wild/" + texName, mcLoc("block/cross"), "cross", blockLoc("wild/" + texName))
                    .renderType("minecraft:cutout");
            builder.part().modelFile(model).addModel().condition(halfProp, half).end();
        }
    }
    //?} else {
    /*private <E extends Enum<E> & StringRepresentable> void wildCropMulti(
            DeferredBlock<?> block, Property<E> halfProp, E[] values) {
        MultiPartGenerator generator = MultiPartGenerator.multiPart(block.get());

        ModelTemplate template = new ModelTemplate(
                Optional.of(this.mcLocation("block/cross")),
                Optional.empty(), TextureSlot.CROSS)
                .extend().renderType("minecraft:cutout").build();

        for (E half : values) {
            String texName = block.getId().getPath() + "_" + half.getSerializedName();
            ResourceLocation model = template.create(
                    this.modLocation("block/wild/" + texName),
                    new TextureMapping().put(TextureSlot.CROSS, blockLoc("wild/" + texName)),
                    blockModels.modelOutput);

            generator = generator.with(
                    Condition.condition().term(halfProp, half),
                    Variant.variant().with(VariantProperties.MODEL, model));
        }

        blockModels.blockStateOutput.accept(generator);
    }
    *///?}

    /**
     * Crop whose models are ALL hand-made. Only generates the blockstate mapping.
     */
    //? <1.21.4 {
    private void existingCrop(DeferredBlock<?> block, IntegerProperty ageProp, int visualStages,
                              Function<Integer, String> modelPath) {
        int maxAge = ageProp.getPossibleValues().size() - 1;
        ModelFile[] models = new ModelFile[visualStages];

        for (int i = 0; i < visualStages; i++) {
            models[i] = getExistingModel(modelPath.apply(i));
        }

        getVariantBuilder(block.get()).forAllStates(state -> {
            int stage = Math.min(visualStages - 1, (state.getValue(ageProp) * visualStages) / Math.max(1, maxAge));
            return ConfiguredModel.builder().modelFile(models[stage]).build();
        });
    }
    //?} else {
    /*private void existingCrop(DeferredBlock<?> block, IntegerProperty ageProp, int visualStages,
                              Function<Integer, String> modelPath) {
        int maxAge = ageProp.getPossibleValues().size() - 1;
        ResourceLocation[] models = new ResourceLocation[visualStages];

        for (int i = 0; i < visualStages; i++) {
            models[i] = getExistingModel(modelPath.apply(i));
        }

        applyAgeDispatch(block.get(), ageProp, visualStages, maxAge, models);
    }

    private void applyAgeDispatch(Block block, IntegerProperty ageProp, int visualStages, int maxAge,
                                  ResourceLocation[] modelsByStage) {
        PropertyDispatch.C1<Integer> dispatch = PropertyDispatch.property(ageProp);
        for (int age : ageProp.getPossibleValues()) {
            int stage = Math.min(visualStages - 1, (age * visualStages) / Math.max(1, maxAge));
            dispatch.select(age, Variant.variant()
                    .with(VariantProperties.MODEL, modelsByStage[stage]));
        }
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(dispatch));
    }
    *///?}

    /**
     * Lettuce is mixed: stages 0-1 generated by datagen, stages 2-3 are hand-made.
     * If you later make stage0/1 by hand, replace this call with existingCrop().
     */
    //? <1.21.4 {
    private void mixedLettuceCrop() {
        DeferredBlock<?> block = ModBlocks.LETTUCE_CROP;
        IntegerProperty ageProp = BlockStateProperties.AGE_3;
        int visualStages = VISUAL_AGE_4;
        int maxAge = ageProp.getPossibleValues().size() - 1;
        ModelFile[] models = new ModelFile[visualStages];

        // Generated stages 0-1
        for (int i = 0; i < 2; i++) {
            ResourceLocation tex = blockLoc("crop/lettuce/lettuce_crop_stage" + i);
            DatagenTracker.trackTexture(models().existingFileHelper, tex.getPath());
            models[i] = cropModel("lettuce/lettuce_crop_stage" + i, tex, true);
        }

        // Hand-made stages 2-3
        models[2] = getExistingModel("crop/lettuce/lettuce_crop_stage2");
        models[3] = getExistingModel("crop/lettuce/lettuce_crop_stage3");

        getVariantBuilder(block.get()).forAllStates(state -> {
            int stage = Math.min(visualStages - 1, (state.getValue(ageProp) * visualStages) / Math.max(1, maxAge));
            return ConfiguredModel.builder().modelFile(models[stage]).build();
        });
    }
    //?} else {
    /*private void mixedLettuceCrop() {
        DeferredBlock<?> block = ModBlocks.LETTUCE_CROP;
        IntegerProperty ageProp = BlockStateProperties.AGE_3;
        int visualStages = VISUAL_AGE_4;
        int maxAge = ageProp.getPossibleValues().size() - 1;
        ResourceLocation[] models = new ResourceLocation[visualStages];

        // Generated stages 0-1
        for (int i = 0; i < 2; i++) {
            ResourceLocation tex = blockLoc("crop/lettuce/lettuce_crop_stage" + i);
            models[i] = cropModel("lettuce/lettuce_crop_stage" + i, tex, true);
        }

        // Hand-made stages 2-3
        models[2] = getExistingModel("crop/lettuce/lettuce_crop_stage2");
        models[3] = getExistingModel("crop/lettuce/lettuce_crop_stage3");

        applyAgeDispatch(block.get(), ageProp, visualStages, maxAge, models);
    }
    *///?}

    //? <1.21.4 {
    private void setupCrop(DeferredBlock<?> block, IntegerProperty ageProp, int visualStages,
                           BiFunction<Integer, ResourceLocation, ModelFile> provider) {
        String name = block.getId().getPath().replace("_crop", "");
        setupCrop(block, ageProp, visualStages, provider,
                st -> blockLoc("crop/" + name + "/" + name + "_crop_stage" + st));
    }

    private void setupCrop(DeferredBlock<?> block, IntegerProperty ageProp, int visualStages,
                           BiFunction<Integer, ResourceLocation, ModelFile> provider,
                           Function<Integer, ResourceLocation> textureMapper) {
        int maxAge = ageProp.getPossibleValues().size() - 1;
        ModelFile[] models = new ModelFile[visualStages];

        for (int i = 0; i < visualStages; i++) {
            ResourceLocation texLoc = textureMapper.apply(i);
            DatagenTracker.trackTexture(models().existingFileHelper, texLoc.getPath());
            models[i] = provider.apply(i, texLoc);
        }

        getVariantBuilder(block.get()).forAllStates(state -> {
            int stage = Math.min(visualStages - 1, (state.getValue(ageProp) * visualStages) / Math.max(1, maxAge));
            return ConfiguredModel.builder().modelFile(models[stage]).build();
        });
    }
    //?} else {
    /*private void setupCrop(DeferredBlock<?> block, IntegerProperty ageProp, int visualStages,
                           BiFunction<Integer, ResourceLocation, ResourceLocation> provider) {
        String name = block.getId().getPath().replace("_crop", "");
        setupCrop(block, ageProp, visualStages, provider,
                st -> blockLoc("crop/" + name + "/" + name + "_crop_stage" + st));
    }

    private void setupCrop(DeferredBlock<?> block, IntegerProperty ageProp, int visualStages,
                           BiFunction<Integer, ResourceLocation, ResourceLocation> provider,
                           Function<Integer, ResourceLocation> textureMapper) {
        int maxAge = ageProp.getPossibleValues().size() - 1;
        ResourceLocation[] models = new ResourceLocation[visualStages];

        for (int i = 0; i < visualStages; i++) {
            ResourceLocation texLoc = textureMapper.apply(i);
            models[i] = provider.apply(i, texLoc);
        }

        applyAgeDispatch(block.get(), ageProp, visualStages, maxAge, models);
    }
    *///?}

    //? <1.21.4 {
    private <E extends Enum<E> & StringRepresentable> void crossCropMulti(
            DeferredBlock<?> block, Property<E> halfProp, E[] halfValues,
            IntegerProperty ageProp, int visualStages, BiFunction<Integer, E, Boolean> skip) {
        crossCropMulti(getMultipartBuilder(block.get()), block, halfProp, halfValues, ageProp, visualStages, skip);
    }

    private <E extends Enum<E> & StringRepresentable> void crossCropMulti(
            MultiPartBlockStateBuilder builder, DeferredBlock<?> block, Property<E> halfProp, E[] halfValues,
            IntegerProperty ageProp, int visualStages, BiFunction<Integer, E, Boolean> skip) {

        String base = block.getId().getPath().replace("_crop", "");
        int maxAge = ageProp.getPossibleValues().size() - 1;

        for (int age : ageProp.getPossibleValues()) {
            int stage = Math.min(visualStages - 1, (age * visualStages) / Math.max(1, maxAge));

            for (E half : halfValues) {
                if (skip != null && skip.apply(stage, half)) continue;

                String texName = base + "_crop_" + half.getSerializedName() + "_stage" + stage;
                String relativeTex = "crop/" + base + "/" + texName;
                DatagenTracker.trackTexture(models().existingFileHelper, "block/" + relativeTex);

                ModelFile model = models().singleTexture("crop/" + base + "/" + texName,
                                mcLoc("block/cross"), "cross", blockLoc(relativeTex))
                        .renderType("minecraft:cutout");
                builder.part().modelFile(model).addModel()
                        .condition(ageProp, age).condition(halfProp, half).end();
            }
        }
    }
    //?} else {
    /*private <E extends Enum<E> & StringRepresentable> void crossCropMulti(
            DeferredBlock<?> block, Property<E> halfProp, E[] halfValues,
            IntegerProperty ageProp, int visualStages, BiFunction<Integer, E, Boolean> skip) {
        MultiPartGenerator generator = MultiPartGenerator.multiPart(block.get());
        generator = crossCropMulti(generator, block, halfProp, halfValues, ageProp, visualStages, skip);
        blockModels.blockStateOutput.accept(generator);
    }

    private <E extends Enum<E> & StringRepresentable> MultiPartGenerator crossCropMulti(
            MultiPartGenerator generator, DeferredBlock<?> block, Property<E> halfProp, E[] halfValues,
            IntegerProperty ageProp, int visualStages, BiFunction<Integer, E, Boolean> skip) {

        String base = block.getId().getPath().replace("_crop", "");
        int maxAge = ageProp.getPossibleValues().size() - 1;

        ModelTemplate template = new ModelTemplate(
                Optional.of(this.mcLocation("block/cross")),
                Optional.empty(), TextureSlot.CROSS)
                .extend().renderType("minecraft:cutout").build();

        for (int age : ageProp.getPossibleValues()) {
            int stage = Math.min(visualStages - 1, (age * visualStages) / Math.max(1, maxAge));

            for (E half : halfValues) {
                if (skip != null && skip.apply(stage, half)) continue;

                String texName = base + "_crop_" + half.getSerializedName() + "_stage" + stage;
                String relativeTex = "crop/" + base + "/" + texName;

                ResourceLocation model = template.create(
                        this.modLocation("block/crop/" + base + "/" + texName),
                        new TextureMapping().put(TextureSlot.CROSS, blockLoc(relativeTex)),
                        blockModels.modelOutput);

                generator = generator.with(
                        Condition.and(
                                Condition.condition().term(ageProp, age),
                                Condition.condition().term(halfProp, half)),
                        Variant.variant().with(VariantProperties.MODEL, model));
            }
        }
        return generator;
    }
    *///?}

    //? <1.21.4 {
    private void tomatoCropPole(DeferredBlock<?> block) {
        var builder = getMultipartBuilder(block.get());
        crossCropMulti(builder, block, TomatoCropPoleBlock.HALF, DoubleBlockHalf.values(),
                BlockStateProperties.AGE_5, VISUAL_AGE_6,
                (stage, half) -> half == DoubleBlockHalf.UPPER && stage < POLE_THRESHOLD);

        ModelFile upperPole = getExistingModel("crop/pole_crop_upper_template");
        ModelFile lowerPole = getExistingModel("crop/pole_crop_lower_template");

        Integer[] earlyAges = {0, 1};
        builder.part()
                .modelFile(upperPole)
                .addModel()
                .condition(TomatoCropPoleBlock.HALF, DoubleBlockHalf.LOWER)
                .condition(BlockStateProperties.AGE_5, earlyAges)
                .end();

        Integer[] lateAges = {2, 3, 4, 5};
        builder.part()
                .modelFile(lowerPole)
                .addModel()
                .condition(TomatoCropPoleBlock.HALF, DoubleBlockHalf.LOWER)
                .condition(BlockStateProperties.AGE_5, lateAges)
                .end();

        builder.part()
                .modelFile(upperPole)
                .addModel()
                .condition(TomatoCropPoleBlock.HALF, DoubleBlockHalf.UPPER)
                .condition(BlockStateProperties.AGE_5, lateAges)
                .end();
    }
    //?} else {
    /*private void tomatoCropPole(DeferredBlock<?> block) {
        MultiPartGenerator generator = MultiPartGenerator.multiPart(block.get());
        generator = crossCropMulti(generator, block, TomatoCropPoleBlock.HALF, DoubleBlockHalf.values(),
                BlockStateProperties.AGE_5, VISUAL_AGE_6,
                (stage, half) -> half == DoubleBlockHalf.UPPER && stage < POLE_THRESHOLD);

        ResourceLocation upperPole = getExistingModel("crop/pole_crop_upper_template");
        ResourceLocation lowerPole = getExistingModel("crop/pole_crop_lower_template");

        Condition earlyCondition = Condition.condition().term(BlockStateProperties.AGE_5,0 ,1);
        generator = generator.with(
                Condition.and(
                        Condition.condition().term(TomatoCropPoleBlock.HALF, DoubleBlockHalf.LOWER),
                        earlyCondition),
                Variant.variant().with(VariantProperties.MODEL, upperPole));

        Condition lateCondition = Condition.condition().term(BlockStateProperties.AGE_5, 2, 3, 4, 5);
        generator = generator.with(
                Condition.and(
                        Condition.condition().term(TomatoCropPoleBlock.HALF, DoubleBlockHalf.LOWER),
                        lateCondition),
                Variant.variant().with(VariantProperties.MODEL, lowerPole));

        generator = generator.with(
                Condition.and(
                        Condition.condition().term(TomatoCropPoleBlock.HALF, DoubleBlockHalf.UPPER),
                        lateCondition),
                Variant.variant().with(VariantProperties.MODEL, upperPole));

        blockModels.blockStateOutput.accept(generator);
    }

    private void simpleBlockState(Block block, ResourceLocation modelLoc) {
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(block,
                        Variant.variant().with(VariantProperties.MODEL, modelLoc)));
    }

    private void horizontalBlockState(Block block, ResourceLocation modelLoc) {
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(block,
                        Variant.variant().with(VariantProperties.MODEL, modelLoc)
                ).with(BlockModelGenerators.createHorizontalFacingDispatch())
        );
    }

    private static VariantProperties.Rotation rotationFromDegrees(int degrees) {
        return switch (((degrees % 360) + 360) % 360) {
            case 90 -> VariantProperties.Rotation.R90;
            case 180 -> VariantProperties.Rotation.R180;
            case 270 -> VariantProperties.Rotation.R270;
            default -> VariantProperties.Rotation.R0;
        };
    }
    *///?}
}
