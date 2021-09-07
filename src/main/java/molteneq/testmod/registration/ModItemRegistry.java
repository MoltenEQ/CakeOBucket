package molteneq.testmod.registration;

import molteneq.testmod.ExampleMod;
import molteneq.testmod.items.TestItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Itt regisztráljuk azokat a tárgyakat, amelyekhez nem tartozik blokk
 *  A TÁRGYAK MINDIG A BLOKKOK UTÁN LEGYENEK REGISZTRÁLVA!
 */
public class ModItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);

    public static void Init() {
        //event listener beállytása (honnan jöhetnek eventek)
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
    }

    public static final RegistryObject<TestItem> TEST_ITEM = ITEMS.register("test_item",() -> {
        return new TestItem(new Item.Properties()
                .defaultDurability(4048)
                .tab(CreativeModeTab.TAB_TOOLS)); //
    });

    public static final RegistryObject<Item> TEST_SWORD = ITEMS.register("test_sword",() -> {
        return new SwordItem(Tiers.NETHERITE /*Tier*/,
                20 /*Attack damage*/,
                0.1f /*Attack speed*/,
                new Item.Properties().defaultDurability(1024) // tartósság
                        .tab(CreativeModeTab.TAB_COMBAT)); // hol jelenjen meg a creatív menüben
    });
    // Tárgy készítése a blokkhoz.
    public static final RegistryObject<Item>  GEN_BLOCK_ITEM =  ModItemRegistry.ITEMS.register("gen_block", () -> {
        return new BlockItem(ModBlockRegistry.GEN_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    });

//    /**
//     * create item for said block
//     * @param block
//     * @param name
//     * @param category
//     * @param <T>
//     * @return
//     */
//    public static <T extends  Block> T RegisterItemForBlock(T block, String name, CreativeModeTab category){
//        ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(category)));
//        return block;
//    }
}

