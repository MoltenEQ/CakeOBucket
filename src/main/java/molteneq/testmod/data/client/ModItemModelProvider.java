package molteneq.testmod.data.client;

import molteneq.testmod.ExampleMod;
import molteneq.testmod.registration.ModBlockRegistry;
import molteneq.testmod.registration.ModItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

// Itt adunk a tárgyaknak modellt? hasonlóan a blokkoknál (mármint a élerakott blokkok) is?
public class ModItemModelProvider extends ItemModelProvider{
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ExampleMod.MODID, existingFileHelper);
    }


    @Override
    protected void registerModels() {
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
                .override().predicate(ClientSetup.DISTANCE_PROPERTY,0).model(createTestModel(0)).end() //felülírások a DISTANCE pop alapján
                .override().predicate(ClientSetup.DISTANCE_PROPERTY,1).model(createTestModel(1)).end()
                .override().predicate(ClientSetup.DISTANCE_PROPERTY,2).model(createTestModel(2)).end()
                .override().predicate(ClientSetup.DISTANCE_PROPERTY,3).model(createTestModel(3)).end();

//        withExistingParent("test_block", modLoc("block/test_bock"));
//        withExistingParent( "test_sword", modLoc("block/test_sword"));
//        withExistingParent( "test_item", modLoc("block/test_test"));
//
//        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
//
//        builder(itemGenerated, "test_sword");
//        builder(itemGenerated, "test_item");

        // a generátor blokk tárgyi formájához kell
        withExistingParent(ModItemRegistry.GEN_BLOCK_ITEM.get().getRegistryName().getPath(),new ResourceLocation(ExampleMod.MODID, "block/generator"));
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
