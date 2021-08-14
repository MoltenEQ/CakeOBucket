package molteneq.test.mod.registration.blocks;

import molteneq.test.mod.ExampleMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockRegistry {
    //regisztrálásra előkészítés (előbb jön létre, mint ahogy regisztráljuk), itt blokk lista
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.MODID);
    //blokk regisztrálása a listánkba (hogy jöjjön létre)
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> {
        return new Block(BlockBehaviour.Properties.of(Material.STONE) //anyag típusa -> hang
                .harvestLevel(2) //milyen típusú csákány kell
                .harvestTool(ToolType.PICKAXE) // csákány kell
                .explosionResistance(100f)
                .destroyTime( 10f ));
    });

    public static void Init()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
    }
}
