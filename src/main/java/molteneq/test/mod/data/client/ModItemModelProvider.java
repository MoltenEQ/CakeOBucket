package molteneq.test.mod.data.client;

import molteneq.test.mod.ExampleMod;
import molteneq.test.mod.data.DataGenerators;
import net.minecraft.data.DataGenerator;
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
        withExistingParent("test_block", modLoc("block/test_bock"));
        withExistingParent( "test_sword", modLoc("block/test_sword"));

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        builder(itemGenerated, "test_sword");
    }

    private void builder(ModelFile itemGenerated, String name) {
        getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
}
