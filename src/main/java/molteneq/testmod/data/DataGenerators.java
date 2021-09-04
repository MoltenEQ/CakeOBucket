package molteneq.testmod.data;

import molteneq.testmod.ExampleMod;
import molteneq.testmod.data.client.ModBlockStates;
import molteneq.testmod.data.client.ModItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

//Mod event busz létrehozása?
@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
        private DataGenerators() {}

    //Akkor sül el, ha runData-val futtatunk, alkalmas adat generálásra (pl.: modellek json-ja)
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

            //Mit is csinálunk?
         DataGenerator gen = event.getGenerator();
         if (event.includeClient())
         {
             // sorrend fontos, mert a blokkitem-ek a blokkok modelljeit használják
             gen.addProvider(new ModBlockStates(gen,event.getExistingFileHelper()));
             gen.addProvider(new ModItemModelProvider(gen, event.getExistingFileHelper()));
         }

        if (event.includeServer())
        {
            gen.addProvider(new RecipeGen(gen));
            gen.addProvider(new LootTables(gen));
            gen.addProvider(new Tags(gen, event.getExistingFileHelper()));
        }
    }
}
