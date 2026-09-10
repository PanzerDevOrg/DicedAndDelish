package com.panzer.mods.dice_and_delish.datagen.sound;

import com.panzer.mods.dice_and_delish.DiceAndDelish;
import com.panzer.mods.dice_and_delish.registry.sound.ModSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import java.util.function.Supplier;

//? <1.21.4 {
import com.panzer.mods.dice_and_delish.datagen.util.DatagenTracker;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
//?}

public class ModSoundDefinitionsProvider extends SoundDefinitionsProvider {

    //? <1.21.4 {
    private final ExistingFileHelper existingFileHelper;

    public ModSoundDefinitionsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DiceAndDelish.MOD_ID, existingFileHelper);
        this.existingFileHelper = existingFileHelper;
    }
    //?} else {
    /*public ModSoundDefinitionsProvider(PackOutput output) {
        super(output, DiceAndDelish.MOD_ID);
    }
    *///?}

    @Override
    public void registerSounds() {
        addSound(ModSounds.GRILL_PLACE_FOOD, "block/grill/place_food", "block.dice_and_delish.grill.place_food");

        addSizzleLoopingSound(ModSounds.GRILL_SIZZLE, "block.dice_and_delish.grill.sizzle");
        addSizzleLoopingSound(ModSounds.SKILLET_SIZZLE, "block.dice_and_delish.skillet.sizzle_loop");

        addSound(ModSounds.SKILLET_CLANG, "item/skillet/clang", "item.dice_and_delish.skillet.clang");
    }

    private void addSound(Supplier<SoundEvent> soundEvent, String path, String subtitleKey) {
        addSound(soundEvent, path, subtitleKey, false);
    }

    @SuppressWarnings("SameParameterValue")
    private void addLoopingSound(Supplier<SoundEvent> soundEvent, String path, String subtitleKey) {
        addSound(soundEvent, path, subtitleKey, true);
    }

    private void addSizzleLoopingSound(Supplier<SoundEvent> soundEvent, String subtitleKey) {
        addLoopingSound(soundEvent, "block/cooking/sizzle", subtitleKey);
    }

    private void addSound(Supplier<SoundEvent> soundEvent, String path, String subtitleKey, boolean isLooping) {
        boolean shouldPreload = !isLooping;

        //? <1.21.4 {
        DatagenTracker.trackSound(this.existingFileHelper, path);
        //?}
        add(soundEvent, SoundDefinition.definition()
                .with(sound(id(path)).preload(shouldPreload))
                .subtitle("subtitles." + subtitleKey));
    }

    private ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(DiceAndDelish.MOD_ID, path);
    }
}
