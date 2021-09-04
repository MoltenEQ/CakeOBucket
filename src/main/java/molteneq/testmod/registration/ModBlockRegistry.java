package molteneq.testmod.registration;

import molteneq.testmod.ExampleMod;
import molteneq.testmod.blocks.GeneratorBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Itt regisztráljuk a blokkokat és a hozzájuk tartozó tárgyakat
 */
public class ModBlockRegistry {
    //regisztrálásra előkészítés (előbb jön létre, mint ahogy regisztráljuk), itt blokk lista
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.MODID);
    //blokk regisztrálása a listánkba (hogy jöjjön létre)
    public static final RegistryObject<Block> GEN_BLOCK = BLOCKS.register("gen_block", GeneratorBlock::new); // A saját blokk konstruktorát hívja meg



    public static void Init()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
    }
}
