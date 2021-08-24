package molteneq.test.mod.data.client;

import molteneq.test.mod.ExampleMod;
import molteneq.test.mod.data.DataGenerators;
import molteneq.test.mod.registration.ModItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import static molteneq.test.mod.data.client.ClientSetup.DISTANCE_PROPERTY;

// Itt adunk a tárgyaknak modellt? hasonlóan a blokkoknál (mármint a élerakott blokkok) is?
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
                new ResourceLocation(ExampleMod.MODID,"item/bucket_o_cake")); //textúra helye

        //item lekérdezése,
        singleTexture(ModItemRegistry.TEST_SWORD.get().getRegistryName().getPath(),
                new ResourceLocation("item/handheld"), //használt modell,
                "layer0", //textúra szint?
                new ResourceLocation(ExampleMod.MODID,"item/test_sword")); //textúra helye
                // a modLoc elméletileg megától belerakja az id-t

        //RÉGI
//        singleTexture(ModItemRegistry.TEST_ITEM.get().getRegistryName().getPath(),
//                new ResourceLocation("item/generated"),
//                "layer0",
//                new ResourceLocation(ExampleMod.MODID,"item/temp"));
        //ÚJ
        getBuilder(ModItemRegistry.TEST_ITEM.get().getRegistryName().getPath())
                .parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", "item/temp0")
                .override().predicate(DISTANCE_PROPERTY,0).model(createTestModel(0)).end() //felülírások a DISTANCE pop alapján
                .override().predicate(DISTANCE_PROPERTY,1).model(createTestModel(1)).end()
                .override().predicate(DISTANCE_PROPERTY,2).model(createTestModel(2)).end()
                .override().predicate(DISTANCE_PROPERTY,3).model(createTestModel(3)).end();

//        withExistingParent("test_block", modLoc("block/test_bock"));
//        withExistingParent( "test_sword", modLoc("block/test_sword"));
//        withExistingParent( "test_item", modLoc("block/test_test"));
//
//        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
//
//        builder(itemGenerated, "test_sword");
//        builder(itemGenerated, "test_item");
    }

    //
    private ItemModelBuilder createTestModel(int suffix){
        return getBuilder("test_item"+suffix).parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0","item/temp"+suffix);
    }

//    private void builder(ModelFile itemGenerated, String name) {
//        getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
//    }
}
