package molteneq.test.mod.registration;

import molteneq.test.mod.ExampleMod;
import molteneq.test.mod.items.DurableFood;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemRegistry {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);

    public static void Init() {
        //event listener beállytása (honnan jöhetnek eventek)
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
    }
    // Here we register Bucket'O'Cake
    public static final RegistryObject<DurableFood> BUCKET_O_CAKE = ITEMS.register("bucket_o_cake",() -> {
        return new DurableFood(new Item.Properties()
                .rarity(Rarity.RARE)
                .setNoRepair() // can't be repaired
                // .stacksTo(1) Durability overrides?
                .fireResistant()
                .food(DurableFood.BUCKET_O_CAKE)
                .defaultDurability(DurableFood.DEFAULT_DURABILITY) //128 uses by default
                .tab(CreativeModeTab.TAB_FOOD)); //
    });

    @Deprecated
    /**
     * create item for said block
     * @param block
     * @param name
     * @param category
     * @param <T>
     * @return
     */
    public static <T extends  Block> T RegisterItemForBlock(T block, String name, CreativeModeTab category){
        ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(category)));
        return block;
    }
}

