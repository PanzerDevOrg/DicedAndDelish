package com.panzer.mods.dice_and_delish.datagen;

import com.panzer.mods.dice_and_delish.datagen.advancement.ModAdvancementProvider;
import com.panzer.mods.dice_and_delish.datagen.block.ModBlockStateProvider;
import com.panzer.mods.dice_and_delish.datagen.data.ModDataMapProvider;
import com.panzer.mods.dice_and_delish.datagen.data.ModLootTableProvider;
import com.panzer.mods.dice_and_delish.datagen.item.ModItemModelProvider;
import com.panzer.mods.dice_and_delish.datagen.lang.ModEnUsLanguageProvider;
import com.panzer.mods.dice_and_delish.datagen.lang.ModEsEsLanguageProvider;
import com.panzer.mods.dice_and_delish.datagen.recipe.ModRecipeProvider;
import com.panzer.mods.dice_and_delish.datagen.sound.ModSoundDefinitionsProvider;
import com.panzer.mods.dice_and_delish.datagen.tags.ModBiomeTagsProvider;
import com.panzer.mods.dice_and_delish.datagen.tags.ModBlockTagsProvider;
import com.panzer.mods.dice_and_delish.datagen.tags.ModDamageTypeTagsProvider;
import com.panzer.mods.dice_and_delish.datagen.tags.ModItemTagsProvider;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

//? <1.21.4 {
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;
//?}

//? >1.21.2 {
/*import com.panzer.mods.dice_and_delish.DiceAndDelish;
import com.panzer.mods.dice_and_delish.datagen.data.ModDamageTypeProvider;
import com.panzer.mods.dice_and_delish.datagen.worldgen.ModBiomeModifiers;
import com.panzer.mods.dice_and_delish.datagen.worldgen.ModConfiguredFeatures;
import com.panzer.mods.dice_and_delish.registry.world.worldgen.ModPlacedFeatures;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
*///?}

//? >1.21.2 && <1.21.4 {
/*import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.RecipeProvider;
*///?}


@SuppressWarnings("CommentedOutCode")
public final class DataGenerators {

    private DataGenerators() {
    }

    public static void register(IEventBus eventBus) {
        eventBus.addListener(DataGenerators::gatherData);
    }

    private static void gatherData(GatherDataEvent event) {
        //? <1.21.4 {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        //?}

        //? if >1.21.2 && <1.21.4 {
        /*RegistrySetBuilder datapackBuilder = new RegistrySetBuilder()
                .add(Registries.DAMAGE_TYPE, ModDamageTypeProvider::bootstrap)
                .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
                .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);
        *///?}

        //? if <1.21.2 {
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModEnUsLanguageProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModEsEsLanguageProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModSoundDefinitionsProvider(packOutput, existingFileHelper));

        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ModLootTableProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ModAdvancementProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModDataMapProvider(packOutput, lookupProvider));

        if (event.includeServer()) {
            event.createBlockAndItemTags(
                    (output, lookup) -> new ModBlockTagsProvider(output, lookup, existingFileHelper),
                    (output, lookup, blockTags) -> new ModItemTagsProvider(output, lookup, blockTags, existingFileHelper)
            );
            generator.addProvider(event.includeServer(), new ModDamageTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));
            generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        }
        //?} <1.21.4 {
        /*DataProvider.Factory<ModBlockStateProvider> blockStateFactory = output -> new ModBlockStateProvider(output, existingFileHelper);
        generator.addProvider(event.includeClient(), blockStateFactory);

        DataProvider.Factory<ModItemModelProvider> itemModelFactory = output -> new ModItemModelProvider(output, existingFileHelper);
        generator.addProvider(event.includeClient(), itemModelFactory);

        DataProvider.Factory<ModEnUsLanguageProvider> enUsLangFactory = ModEnUsLanguageProvider::new;
        generator.addProvider(event.includeClient(), enUsLangFactory);

        DataProvider.Factory<ModEsEsLanguageProvider> esEsLangFactory = ModEsEsLanguageProvider::new;
        generator.addProvider(event.includeClient(), esEsLangFactory);

        DataProvider.Factory<ModSoundDefinitionsProvider> soundFactory = output -> new ModSoundDefinitionsProvider(output, existingFileHelper);
        generator.addProvider(event.includeClient(), soundFactory);

        DataProvider.Factory<RecipeProvider.Runner> recipeFactory = output -> new ModRecipeProvider.Runner(output, lookupProvider);
        generator.addProvider(event.includeServer(), recipeFactory);

        DataProvider.Factory<ModLootTableProvider> lootTableFactory = output -> new ModLootTableProvider(output, lookupProvider);
        generator.addProvider(event.includeServer(), lootTableFactory);

        DataProvider.Factory<ModAdvancementProvider> advancementFactory = output -> new ModAdvancementProvider(output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), advancementFactory);

        DataProvider.Factory<ModDataMapProvider> dataMapFactory = output -> new ModDataMapProvider(output, lookupProvider);
        generator.addProvider(event.includeServer(), dataMapFactory);

        DataProvider.Factory<DatapackBuiltinEntriesProvider> datapackFactory = output -> new DatapackBuiltinEntriesProvider(output, lookupProvider, datapackBuilder, Set.of(DiceAndDelish.MOD_ID));
        generator.addProvider(event.includeServer(), datapackFactory);

        if (event.includeServer()) {
            ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
            generator.addProvider(true, (DataProvider.Factory<ModBlockTagsProvider>) output -> blockTagsProvider);

            DataProvider.Factory<ModItemTagsProvider> itemTagsFactory = output -> new ModItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper);
            generator.addProvider(true, itemTagsFactory);

            DataProvider.Factory<ModDamageTypeTagsProvider> damageTypeTagsFactory = output -> new ModDamageTypeTagsProvider(output, lookupProvider, existingFileHelper);
            generator.addProvider(true, damageTypeTagsFactory);

            DataProvider.Factory<ModBiomeTagsProvider> biomeTagsFactory = output -> new ModBiomeTagsProvider(output, lookupProvider, existingFileHelper);
            generator.addProvider(true, biomeTagsFactory);
        }
        *///?} else {
        /*event.createProvider(ModBlockStateProvider::new);
        event.createProvider(ModItemModelProvider::new);
        event.createProvider(ModEnUsLanguageProvider::new);
        event.createProvider(ModEsEsLanguageProvider::new);
        event.createProvider(ModSoundDefinitionsProvider::new);

        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider(ModLootTableProvider::new);
        event.createProvider(ModAdvancementProvider::new);
        event.createProvider(ModDataMapProvider::new);

        RegistrySetBuilder datapackBuilder = new RegistrySetBuilder()
                .add(Registries.DAMAGE_TYPE, ModDamageTypeProvider::bootstrap)
                .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
                .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

        event.createProvider((output, lookup) -> new DatapackBuiltinEntriesProvider(output, lookup, datapackBuilder, Set.of(DiceAndDelish.MOD_ID)));

        // Tags
        event.createProvider((output, lookup) -> {
            ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(output, lookup);
            event.createProvider((out, l) -> new ModItemTagsProvider(out, l, blockTagsProvider.contentsGetter()));
            return blockTagsProvider;
        });

        event.createProvider(ModDamageTypeTagsProvider::new);
        event.createProvider(ModBiomeTagsProvider::new);
        *///?}
    }
}
