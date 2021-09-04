/* Ötlet: torta a vödörben
   Egy olyan tárgy, ami képes a az éhség csökkentésére, de nem veszik el fogyasztás után, hanem helyette a tartósága csökken.
   Kell:
   - egy olyan tárgy, amit meg lehet enni TODO
   - van textúrája TODO
   - el lehet készíteni TODO
   - elfogyasztás után nem vész el, hanem csökken a tartóssága TODO
   - EXTRA: akár el is lehet varázsolni, hogy tartósabb legyen TODO
* */
package molteneq.testmod;

import molteneq.testmod.data.client.ClientSetup;
import molteneq.testmod.registration.ModBlockRegistry;
import molteneq.testmod.registration.ModItemRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("testmod")
public class ExampleMod
{
    public static final String MODID = "testmod";
    public static final String MODNAME = "Test Mod";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger(); //pl: hibak logolasara, ne spammeld

    /**
     * Initcializáláshoz, a konstruktor hívja meg
     */
    private void Initialization() {
        LOGGER.info(MODNAME+ " init started");
        ModBlockRegistry.Init();
        ModItemRegistry.Init();
    }

    public ExampleMod() {
        Initialization();
        // Register the setup method for modloading
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        //Saját osztály meghívása -> változó item textúra
        bus.addListener(ClientSetup::setup);
        // Register the enqueueIMC method for modloading
        bus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        bus.addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    //általában itt nincs sok teendő, már minden regisztrálva kéne legyen
    //Konfig ez előtt legyen
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        // LOGGER.info("HELLO FROM PREINIT");
        // LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void enqueueIMC(final InterModEnqueueEvent event) // modok közti kompatibilitásnál nagyon jó
    {
        // some example code to dispatch IMC to another mod
        // InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        /*
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
        */
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        // LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            //LOGGER.info("HELLO from Register Block");
        }
    }
}
