package molteneq.test.mod.blocks;

import molteneq.test.mod.ExampleMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockRegistry {
    //regisztrálásra előkészítés (előbb jön létre, mint ahogy regisztráljuk), itt blokk lista
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.MODID);
    //blokk regisztrálása a listánkba (hogy jöjjön létre)
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block( BlockBehaviour.Properties.of( new Material(MaterialColor.COLOR_BLACK,/*liqud*/false,/*solid*/true,/*blocksMotion*/false,/*solidBlocking*/true,/*flammable*/true,/*replaceable*/ false,/*pushreaction*/ PushReaction.IGNORE))));

    public static void Init()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
