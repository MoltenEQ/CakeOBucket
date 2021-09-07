package molteneq.testmod.registration;

import molteneq.testmod.ExampleMod;
import molteneq.testmod.blocks.GeneratorBE;
import molteneq.testmod.blocks.GeneratorBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Itt regisztráljuk a blokkok entitását
 */
public class ModBERegisrty {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ExampleMod.MODID);

    public static final RegistryObject<BlockEntityType<GeneratorBE>> GEN_BLOCK_ENTITY = BLOCK_ENTITIES.register("generator",
            () -> BlockEntityType.Builder
                    .of(GeneratorBE::new, ModBlockRegistry.GEN_BLOCK.get()) //milyen típusú entitás, milyen blokkhoz
                    .build(null));



    public static void Init()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCK_ENTITIES.register(modEventBus);
    }
}
