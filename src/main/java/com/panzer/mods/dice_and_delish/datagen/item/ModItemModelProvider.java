package com.panzer.mods.dice_and_delish.datagen.item;

import com.panzer.mods.dice_and_delish.DiceAndDelish;
import com.panzer.mods.dice_and_delish.item.component.IronCupContent;
import com.panzer.mods.dice_and_delish.registry.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.registries.DeferredItem;

//? if <1.21.4 {
import com.panzer.mods.dice_and_delish.datagen.util.DatagenTracker;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

@SuppressWarnings("SameParameterValue")
public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DiceAndDelish.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Blocks
        applianceLit(ModItems.GRILL_TABLE, ModItems.GRILL_TABLE_SOUL);
        applianceBlock(ModItems.CUTTING_BOARD, ModItems.GRILL_TABLE_UNLIT);
        applianceBlockRenamed(ModItems.SKILLET, "skillet_unlit");
        applianceBlockRenamed(ModItems.GRILL_TABLE_SOUL_UNLIT, "grill_table_unlit");
        applianceBlockGenerated(ModItems.ORGANIC_SOIL);
        applianceBlockSuffixGenerated("_0", ModItems.FERTILE_FARMLAND);

        // 2D Items
        item2D("wild",
                ModItems.WILD_PURPLE_ONION, ModItems.WILD_LETTUCE, ModItems.WILD_TOMATO,
                ModItems.WILD_STRAWBERRY, ModItems.WILD_RICE);
        item2D("seed",
                ModItems.STRAWBERRY_SEEDS, ModItems.TOMATO_SEEDS, ModItems.LETTUCE_SEEDS,
                ModItems.PURPLE_ONION_SEEDS, ModItems.RICE_SEEDS
        );
        item2D("food",
                ModItems.RAW_CHICKEN_PIECES, ModItems.COOKED_CHICKEN_PIECES,
                ModItems.FRIED_EGG, ModItems.CHEESE, ModItems.CHEESE_SLICE, ModItems.GRILLED_CHEESE,
                ModItems.TORTILLA, ModItems.STRAWBERRY, ModItems.TOMATO, ModItems.LETTUCE, ModItems.PURPLE_ONION,
                ModItems.CUT_POTATO, ModItems.CUT_PURPLE_ONION, ModItems.ORGANIC_MIXTURE, ModItems.POTATO_TORTILLA,
                ModItems.RICE_BOWL, ModItems.PURPLE_ONION_TORTILLA, ModItems.RICE, ModItems.COOKED_RICE_BOWL
        );

        item2D("sandwich/raw", ModItems.RAW_SANDWICH_BREAD);
        item2D("sandwich/toasted", ModItems.TOASTED_SANDWICH_BREAD);

        // Tools & Extras
        item2D("knife/custom", ModItems.NETHERITE_KNIFE);

        sandwichPair(ModItems.CHEESE_RAW_SANDWICH, ModItems.CHEESE_TOASTED_SANDWICH, "cheese_sandwich_content");

        bowlItem(ModItems.RICE_BOWL, "rice_bowl_content");
        bowlItem(ModItems.COOKED_RICE_BOWL, "cooked_rice_bowl_content");
        bowlItem(ModItems.SALAD, "salad_bowl_content");

        tintableKnives(ModItems.STONE_KNIFE, ModItems.IRON_KNIFE, ModItems.GOLDEN_KNIFE, ModItems.DIAMOND_KNIFE, ModItems.OBSIDIAN_KNIFE);

        ironCup();
    }

    private void applianceLit(DeferredItem<?>... items) {
        for (DeferredItem<?> item : items) {
            String itemId = item.getId().getPath();
            blockItemModel(itemId, "appliance/" + itemId + "_lit");
        }
    }

    private void applianceBlock(DeferredItem<?>... items) {
        for (DeferredItem<?> item : items) {
            String itemId = item.getId().getPath();
            blockItemModel(itemId, "appliance/" + itemId);
        }
    }

    private void applianceBlockRenamed(DeferredItem<?> item, String blockPath) {
        blockItemModel(item.getId().getPath(), "appliance/" + blockPath);
    }

    private void applianceBlockGenerated(DeferredItem<?>... items) {
        for (DeferredItem<?> item : items) {
            String itemId = item.getId().getPath();
            blockItemModel(itemId, itemId);
        }
    }

    private void applianceBlockSuffixGenerated(String suffix, DeferredItem<?>... items) {
        for (DeferredItem<?> item : items) {
            String itemId = item.getId().getPath();
            blockItemModel(itemId, itemId + suffix);
        }
    }

    private void blockItemModel(String itemPath, String blockPath) {
        DatagenTracker.trackModel(existingFileHelper, "block/" + blockPath);
        withExistingParent(itemPath, modLoc("block/" + blockPath));
    }

    private void item2D(String folder, DeferredItem<?>... items) {
        for (DeferredItem<?> item : items) {
            generatedLayers(item.getId().getPath(), folder + "/" + item.getId().getPath());
        }
    }

    private void sandwichPair(DeferredItem<?> rawItem, DeferredItem<?> toastedItem, String contentTex) {
        sandwichItem(rawItem.getId().getPath(), "raw/raw_sandwich_bread", contentTex);
        sandwichItem(toastedItem.getId().getPath(), "toasted/toasted_sandwich_bread", contentTex);
    }

    private void sandwichItem(String modelName, String breadPrefix, String contentTex) {
        generatedLayers(modelName,
                "sandwich/" + breadPrefix + "_lower",
                "sandwich/content/" + contentTex,
                "sandwich/" + breadPrefix + "_upper"
        );
    }

    private void bowlItem(DeferredItem<?> item, String contentTex) {
        bowlItem(item.getId().getPath(), "recipient/bowl", contentTex);
    }

    private void bowlItem(String modelName, String bowlPrefix, String contentTex) {
        generatedLayers(modelName,
                "bowl/" + bowlPrefix,
                "bowl/content/" + contentTex
        );
    }

    private void tintableKnives(DeferredItem<?>... items) {
        for (DeferredItem<?> item : items) {
            generatedLayers(item.getId().getPath(), "knife/knife_handle", "knife/knife_blade", "knife/knife_highlight");
        }
    }

    private void ironCup() {
        String baseName = ModItems.IRON_CUP.getId().getPath();
        String baseTexture = baseName + "/" + baseName;

        generatedLayers(baseName, baseTexture);

        ItemModelBuilder cupBuilder = getBuilder(baseName);

        for (IronCupContent content : IronCupContent.values()) {
            String name = baseName + "_" + content.getSerializedName();
            DatagenTracker.trackModel(existingFileHelper, "item/" + name);

            generatedLayers(name, baseTexture, baseName + "/" + name);

            cupBuilder.override()
                    .predicate(modLoc("content"), content.modelIndex() + 1)
                    .model(getExistingFile(modLoc("item/" + name)))
                    .end();
        }
    }

    private void generatedLayers(String modelName, String... relativeTextures) {
        ItemModelBuilder builder = withExistingParent(modelName, mcLoc("item/generated"));
        for (int i = 0; i < relativeTextures.length; i++) {
            String fullPath = "item/" + relativeTextures[i];
            DatagenTracker.trackTexture(existingFileHelper, fullPath);
            builder.texture("layer" + i, modLoc(fullPath));
        }
    }
}

//?} else {
/*import com.panzer.mods.dice_and_delish.client.renderer.FlatItemModelUnbaked;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.CustomModelDataProperty;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SameParameterValue", "unused"})
public class ModItemModelProvider extends ModelProvider {

    private static final int DEFAULT_IRON = 0xFFECF5F5;

    public ModItemModelProvider(PackOutput output) {
        super(output, DiceAndDelish.MOD_ID);
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        // Blocks
        applianceLit(itemModels, ModItems.GRILL_TABLE, ModItems.GRILL_TABLE_SOUL, ModItems.SKILLET);
        applianceBlock(itemModels, ModItems.CUTTING_BOARD, ModItems.GRILL_TABLE_UNLIT);
        applianceBlockRenamed(itemModels, ModItems.GRILL_TABLE_SOUL_UNLIT, "grill_table_unlit");
        applianceBlockGenerated(itemModels, ModItems.ORGANIC_SOIL);
        applianceBlockSuffixGenerated(itemModels, "_0", ModItems.FERTILE_FARMLAND);

        // 2D Items
        item2D(itemModels, "wild",
                ModItems.WILD_PURPLE_ONION, ModItems.WILD_LETTUCE, ModItems.WILD_TOMATO,
                ModItems.WILD_STRAWBERRY, ModItems.WILD_RICE);
        item2D(itemModels, "seed",
                ModItems.STRAWBERRY_SEEDS, ModItems.TOMATO_SEEDS, ModItems.LETTUCE_SEEDS,
                ModItems.PURPLE_ONION_SEEDS, ModItems.RICE_SEEDS
        );
        item2D(itemModels, "food",
                ModItems.RAW_CHICKEN_PIECES, ModItems.COOKED_CHICKEN_PIECES,
                ModItems.FRIED_EGG, ModItems.CHEESE, ModItems.CHEESE_SLICE, ModItems.GRILLED_CHEESE,
                ModItems.TORTILLA, ModItems.STRAWBERRY, ModItems.TOMATO, ModItems.LETTUCE, ModItems.PURPLE_ONION,
                ModItems.CUT_POTATO, ModItems.CUT_PURPLE_ONION, ModItems.ORGANIC_MIXTURE, ModItems.POTATO_TORTILLA,
                ModItems.RICE_BOWL, ModItems.PURPLE_ONION_TORTILLA, ModItems.RICE, ModItems.COOKED_RICE_BOWL
        );

        item2D(itemModels, "sandwich/raw", ModItems.RAW_SANDWICH_BREAD);
        item2D(itemModels, "sandwich/toasted", ModItems.TOASTED_SANDWICH_BREAD);

        // Tools & Extras
        item2D(itemModels, "knife/custom", ModItems.NETHERITE_KNIFE);

        sandwichPair(itemModels, ModItems.CHEESE_RAW_SANDWICH, ModItems.CHEESE_TOASTED_SANDWICH, "cheese_sandwich_content");

        bowlItem(itemModels, ModItems.RICE_BOWL, "rice_bowl_content");
        bowlItem(itemModels, ModItems.COOKED_RICE_BOWL, "cooked_rice_bowl_content");
        bowlItem(itemModels, ModItems.SALAD, "salad_bowl_content");

        // Knives con sus tintes integrados
        knife(itemModels, ModItems.STONE_KNIFE, 0xFFB1AFAD, 0xFFB1AFAD);
        knife(itemModels, ModItems.IRON_KNIFE, DEFAULT_IRON, DEFAULT_IRON);
        knife(itemModels, ModItems.GOLDEN_KNIFE, 0xFFFAEB0F, DEFAULT_IRON);
        knife(itemModels, ModItems.DIAMOND_KNIFE, 0xFF72F7E4, 0xFFC9FFF8);
        knife(itemModels, ModItems.OBSIDIAN_KNIFE, 0xFF865FBF, 0xFFD2BCF7);

        ironCup(itemModels);
    }

    private void applianceLit(ItemModelGenerators generators, DeferredItem<?>... items) {
        for (DeferredItem<?> item : items) {
            String itemId = item.getId().getPath();
            blockItemModel(generators, item.get(), "appliance/" + itemId + "_lit");
        }
    }

    private void applianceBlock(ItemModelGenerators generators, DeferredItem<?>... items) {
        for (DeferredItem<?> item : items) {
            String itemId = item.getId().getPath();
            blockItemModel(generators, item.get(), "appliance/" + itemId);
        }
    }

    private void applianceBlockRenamed(ItemModelGenerators generators, DeferredItem<?> item, String blockPath) {
        blockItemModel(generators, item.get(), "appliance/" + blockPath);
    }

    private void applianceBlockGenerated(ItemModelGenerators generators, DeferredItem<?>... items) {
        for (DeferredItem<?> item : items) {
            String itemId = item.getId().getPath();
            blockItemModel(generators, item.get(), itemId);
        }
    }

    private void applianceBlockSuffixGenerated(ItemModelGenerators generators, String suffix, DeferredItem<?>... items) {
        for (DeferredItem<?> item : items) {
            String itemId = item.getId().getPath();
            blockItemModel(generators, item.get(), itemId + suffix);
        }
    }

    private void blockItemModel(ItemModelGenerators generators, Item item, String blockPath) {
        generators.itemModelOutput.accept(
                item,
                ItemModelUtils.plainModel(ResourceLocation.fromNamespaceAndPath(DiceAndDelish.MOD_ID, "block/" + blockPath))
        );
    }

    private void item2D(ItemModelGenerators generators, String folder, DeferredItem<?>... items) {
        for (DeferredItem<?> item : items) {
            generatedLayers(generators, item.get(), folder + "/" + item.getId().getPath());
        }
    }

    private void sandwichPair(ItemModelGenerators generators, DeferredItem<?> rawItem, DeferredItem<?> toastedItem, String contentTex) {
        sandwichItem(generators, rawItem.get(), "raw/raw_sandwich_bread", contentTex);
        sandwichItem(generators, toastedItem.get(), "toasted/toasted_sandwich_bread", contentTex);
    }

    private void sandwichItem(ItemModelGenerators generators, Item item, String breadPrefix, String contentTex) {
        generatedLayers(generators, item,
                "sandwich/" + breadPrefix + "_lower",
                "sandwich/content/" + contentTex,
                "sandwich/" + breadPrefix + "_upper"
        );
    }

    private void bowlItem(ItemModelGenerators generators, DeferredItem<?> item, String contentTex) {
        bowlItem(generators, item.get(), "recipient/bowl", contentTex);
    }

    private void bowlItem(ItemModelGenerators generators, Item item, String bowlPrefix, String contentTex) {
        generatedLayers(generators, item,
                "bowl/" + bowlPrefix,
                "bowl/content/" + contentTex
        );
    }

    private void knife(ItemModelGenerators generators, DeferredItem<?> item, int bladeColor, int highlightColor) {
        ResourceLocation baseModel = generatedLayersModel(
                generators,
                item.getId().getPath(),
                "knife/knife_handle",
                "knife/knife_blade",
                "knife/knife_highlight"
        );

        generators.itemModelOutput.accept(
                item.get(),
                ItemModelUtils.tintedModel(
                        baseModel,
                        new Constant(-1),
                        new Constant(bladeColor),
                        new Constant(highlightColor)
                )
        );
    }

    private void ironCup(ItemModelGenerators generators) {
        Item cupItem = ModItems.IRON_CUP.get();
        String baseName = ModItems.IRON_CUP.getId().getPath();
        String baseTexture = baseName + "/" + baseName;

        ResourceLocation emptyCupModel = generatedLayersModel(generators, baseName, baseTexture);

        List<SelectItemModel.SwitchCase<String>> cases = new ArrayList<>();

        for (IronCupContent content : IronCupContent.values()) {
            String name = baseName + "_" + content.getSerializedName();
            ResourceLocation contentModel = generatedLayersModel(generators, name, baseTexture, baseName + "/" + name);

            cases.add(ItemModelUtils.when(content.getSerializedName(), ItemModelUtils.plainModel(contentModel)));
        }

        generators.itemModelOutput.accept(
                cupItem,
                ItemModelUtils.select(
                        new CustomModelDataProperty(0),
                        ItemModelUtils.plainModel(emptyCupModel),
                        cases
                )
        );
    }

    private void generatedLayers(ItemModelGenerators generators, Item item, String... relativeTextures) {
        ResourceLocation modelLoc = generatedLayersModel(generators, BuiltInRegistries.ITEM.getKey(item).getPath(), relativeTextures);
        generators.itemModelOutput.accept(item, wrapFlat(modelLoc));
    }

    private ItemModel.Unbaked wrapFlat(ResourceLocation baseModel) {
        return new FlatItemModelUnbaked(baseModel);
    }

    private ResourceLocation generatedLayersModel(ItemModelGenerators generators, String modelName, String... relativeTextures) {
        TextureMapping mapping = new TextureMapping();

        if (relativeTextures.length > 0) {
            mapping.put(TextureSlot.LAYER0, ResourceLocation.fromNamespaceAndPath(DiceAndDelish.MOD_ID, "item/" + relativeTextures[0]));
        }
        if (relativeTextures.length > 1) {
            mapping.put(TextureSlot.LAYER1, ResourceLocation.fromNamespaceAndPath(DiceAndDelish.MOD_ID, "item/" + relativeTextures[1]));
        }
        if (relativeTextures.length > 2) {
            mapping.put(TextureSlot.LAYER2, ResourceLocation.fromNamespaceAndPath(DiceAndDelish.MOD_ID, "item/" + relativeTextures[2]));
        }

        return ModelTemplates.FLAT_ITEM.create(
                ResourceLocation.fromNamespaceAndPath(DiceAndDelish.MOD_ID, "item/" + modelName),
                mapping,
                generators.modelOutput
        );
    }
}
*///?}
