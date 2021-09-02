package molteneq.test.mod.registration;

import molteneq.test.mod.ExampleMod;
import molteneq.test.mod.blocks.GeneratorBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
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

    // Tárgy készítése a blokkhoz.
    public static final RegistryObject<Item>  GEN_BLOCK_ITEM =  ModItemRegistry.ITEMS.register("gen_block", () -> new BlockItem(GEN_BLOCK.get(), new Item.Properties()));

    public static void Init()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
    }
}
