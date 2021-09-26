package molteneq.testmod.registration;

import molteneq.testmod.ExampleMod;
import molteneq.testmod.blocks.GeneratorContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//szerver és kliensoldali cucc
public class ModContaienrReg {

    private static  final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ExampleMod.MODID);

    public static final RegistryObject<MenuType<GeneratorContainer>> GENERATOR_CONTAINER = CONTAINERS.register("generator",() -> IForgeContainerType.create((windowId, inv, data) -> {
       // Az adat a szerverről jön
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new GeneratorContainer(windowId,world,pos,inv,inv.player);
    }));

    public static void Init(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CONTAINERS.register(modEventBus);
    }
}
