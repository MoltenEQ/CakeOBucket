package molteneq.test.mod.data.client;

import molteneq.test.mod.ExampleMod;

import molteneq.test.mod.registration.ModItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.client.model.generators.ItemModelProvider;

import net.minecraftforge.common.data.ExistingFileHelper;


public class ModItemModelProvider extends ItemModelProvider{
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ExampleMod.MODID, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        //Give a single layer texture to it
        singleTexture(ModItemRegistry.BUCKET_O_CAKE.get().getRegistryName().getPath(),
                new ResourceLocation("item/generated"), //használt modell,
                "layer0", //textúra szint?
                new ResourceLocation(ExampleMod.MODID, "item/bucket_o_cake")); //textúra helye

    }
}
