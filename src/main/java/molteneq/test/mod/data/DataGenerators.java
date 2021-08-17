package molteneq.test.mod.data;

import molteneq.test.mod.ExampleMod;
import molteneq.test.mod.data.client.ModItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

//Mod event busz létrehozása?
@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
        private DataGenerators() {}

    //Milyen eseményre iratkozunk fel?
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

            //Mit is csinálunk?
         DataGenerator gen = event.getGenerator();
         ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
    }
}