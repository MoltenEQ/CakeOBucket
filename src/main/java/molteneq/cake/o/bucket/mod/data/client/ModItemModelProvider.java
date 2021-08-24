package molteneq.cake.o.bucket.mod.data.client;

import molteneq.cake.o.bucket.mod.BucketOCakeMod;

import molteneq.cake.o.bucket.mod.registration.ModItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.client.model.generators.ItemModelProvider;

import net.minecraftforge.common.data.ExistingFileHelper;


public class ModItemModelProvider extends ItemModelProvider{
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BucketOCakeMod.MODID, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        //Give a single layer texture to it
        singleTexture(ModItemRegistry.BUCKET_O_CAKE.get().getRegistryName().getPath(),
                new ResourceLocation("item/generated"), //használt modell,
                "layer0", //textúra szint?
                new ResourceLocation(BucketOCakeMod.MODID, "item/bucket_o_cake")); //textúra helye

    }
}
