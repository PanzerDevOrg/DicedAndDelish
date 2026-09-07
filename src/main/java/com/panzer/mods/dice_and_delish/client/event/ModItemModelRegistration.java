package com.panzer.mods.dice_and_delish.client.event;

//? >1.21.3 {
/*import com.panzer.mods.dice_and_delish.DiceAndDelish;
import com.panzer.mods.dice_and_delish.client.renderer.FlatItemModelUnbaked;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterItemModelsEvent;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = DiceAndDelish.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModItemModelRegistration {
    private ModItemModelRegistration() {}

    @SubscribeEvent
    public static void onRegisterItemModels(RegisterItemModelsEvent event) {
        event.register(
                ResourceLocation.fromNamespaceAndPath(DiceAndDelish.MOD_ID, "flat"),
                FlatItemModelUnbaked.MAP_CODEC
        );
    }
}
*///?}
