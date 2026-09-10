package com.panzer.mods.dice_and_delish.compat.jei.client;

//? <1.21.2 || >1.21.3 {
import com.panzer.mods.dice_and_delish.DiceAndDelish;
import com.panzer.mods.dice_and_delish.compat.jei.JeiModPlugin;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = DiceAndDelish.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public final class JeiRecipeSyncListener {

    private JeiRecipeSyncListener() {
    }

    @SubscribeEvent
    public static void onLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        JeiModPlugin.onPlayerLoggedIn();
    }
}
//?}
