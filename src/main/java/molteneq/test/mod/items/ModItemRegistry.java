package molteneq.test.mod.items;

import molteneq.test.mod.ExampleMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemRegistry {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);

    public static final RegistryObject<Item> EXAMPLE_SWORD = ITEMS.register("example_sword",() -> new SwordItem(Tiers.NETHERITE /*Tier*/,20 /*Attack damage*/, 0.1f /*Attack speed*/, new Item.Properties()));

    public static void Init() {
        //event listener beállytása (honnan jöhetnek eventek)
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
