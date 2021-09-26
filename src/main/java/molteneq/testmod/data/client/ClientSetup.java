package molteneq.testmod.data.client;

import molteneq.testmod.ExampleMod;
import molteneq.testmod.blocks.GeneratorScreen;
import molteneq.testmod.items.TestItem;
import molteneq.testmod.registration.ModContaienrReg;
import molteneq.testmod.registration.ModItemRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static final ResourceLocation DISTANCE_PROPERTY = new ResourceLocation(ExampleMod.MODID, "distance");

    // csak kliensoldali! a tárgyunk regisztrálása már kész
    public static void setup(final FMLClientSetupEvent event){
        event.enqueueWork(()-> {
            MenuScreens.register(ModContaienrReg.GENERATOR_CONTAINER.get(), GeneratorScreen::new); // ez sem szálbiztos :(
            initTestItemOverrides();
        });
        // fontos, hogy így hívjuk ezeket, mert nem biztos, hogy szálbiztosan implementáltunk!
        // ebben az esetben biztos a fő szálon fog ez futni :)
    }

    //új tulajdonság hozzáadása, kliens oldalon is megkapjuk a dist-et
    public static void initTestItemOverrides() {
        TestItem item = ModItemRegistry.TEST_ITEM.get();
        ItemProperties.register(item, DISTANCE_PROPERTY,
                (stack,level,entity,damage) -> item.getDistance(stack));
    }


}
