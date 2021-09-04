package molteneq.testmod.data;

import molteneq.testmod.ExampleMod;
import molteneq.testmod.registration.ModBlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

// 1.17 óta itt kell megjelölni, hogy hogyan lehet a blokkot bányászni, a neve valamiért CSAK tags lehet
public class Tags extends BlockTagsProvider {
    public Tags(DataGenerator dataGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, ExampleMod.MODID, existingFileHelper);
    }

    // elég egyértelműnek tűnik
    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlockRegistry.GEN_BLOCK.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlockRegistry.GEN_BLOCK.get());
    }

    @Override
    public String getName() {
        return "ExampleMod Tags";
    }
}
