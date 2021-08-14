package molteneq.test.mod.registration.items;

import molteneq.test.mod.ExampleMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemRegistry {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);

    public static final RegistryObject<Item> EXAMPLE_SWORD = ITEMS.register("example_sword",() -> {
        return new SwordItem(Tiers.NETHERITE /*Tier*/,
                20 /*Attack damage*/,
                0.1f /*Attack speed*/,
                new Item.Properties().defaultDurability(1024) // tartósság
                        .tab(CreativeModeTab.TAB_COMBAT)); // hol jelenjen meg a creatív menüben
    });

    public static void Init() {
        //event listener beállytása (honnan jöhetnek eventek)
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
    }
}

