package molteneq.test.mod.registration;

import molteneq.test.mod.ExampleMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static molteneq.test.mod.registration.ModItemRegistry.RegisterItemForBlock;

public class ModBlockRegistry {
    //regisztrálásra előkészítés (előbb jön létre, mint ahogy regisztráljuk), itt blokk lista
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.MODID);
    //blokk regisztrálása a listánkba (hogy jöjjön létre)
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("test_block", () -> {
        return   RegisterItemForBlock (new Block(BlockBehaviour.Properties.of(Material.STONE) //anyag típusa -> hang
                .explosionResistance(100f)
                .destroyTime( 10f )),
                "test_block",
                CreativeModeTab.TAB_BUILDING_BLOCKS);
    });

    public static void Init()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
    }
}
