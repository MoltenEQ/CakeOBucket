package molteneq.test.mod.data.client;

import molteneq.test.mod.ExampleMod;
import molteneq.test.mod.data.DataGenerators;
import molteneq.test.mod.registration.ModItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

// Itt adunk a tárgyaknak modellt? hasonlóan a blokkoknál (mármint a élerakott blokkok) is?
public class ModItemModelProvider extends ItemModelProvider{
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ExampleMod.MODID, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        //item lekérdezése,
        this.singleTexture(ModItemRegistry.TEST_SWORD.get().getRegistryName().getPath(),
                new ResourceLocation("item/handheld"), //használt modell,
                "layer0", //textúra szint?
                new ResourceLocation(ExampleMod.MODID,"item/test_sword")); //textúra helye
                // a modLoc elméletileg megától belerakja az id-t

//        withExistingParent("test_block", modLoc("block/test_bock"));
//        withExistingParent( "test_sword", modLoc("block/test_sword"));
//        withExistingParent( "test_item", modLoc("block/test_test"));
//
//        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
//
//        builder(itemGenerated, "test_sword");
//        builder(itemGenerated, "test_item");
    }

//    private void builder(ModelFile itemGenerated, String name) {
//        getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
//    }
}
