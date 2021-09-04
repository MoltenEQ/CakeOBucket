package molteneq.testmod.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import molteneq.testmod.ExampleMod;
import molteneq.testmod.registration.ModBlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class LootTables extends LootTableProvider {

    private final DataGenerator generator;

    // Ez miez?
    private static final Gson GSON = new  GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public LootTables(DataGenerator generator) {
        super(generator);
        this.generator = generator;
    }

    @Override
    public void run(HashCache cache) {
        // Minden kincshez kell a tábla
        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        //most a blokkhoz adunk kincstáblát
        tables.put(ModBlockRegistry.GEN_BLOCK.get().getLootTable(),createStandardTable("gen_block", ModBlockRegistry.GEN_BLOCK.get()).setParamSet(LootContextParamSets.BLOCK).build());
        writeTables(cache,tables);
    }

    protected LootTable.Builder createStandardTable(String name, Block block) {
        LootPool.Builder builder = LootPool.lootPool()
                .name(name)
                // nem véletlen drop
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block)
                        .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                        //A block NBT jegyzékjei mennyenek át majd a tárgyra is
                        .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                .copy("inv","BlockEntityTag.inv",CopyNbtFunction.MergeStrategy.REPLACE)
                                .copy("energy","BlockEntityTag.energy",CopyNbtFunction.MergeStrategy.REPLACE))
                        .apply(SetContainerContents.setContents()
                                .withEntry(DynamicLoot.dynamicEntry(new ResourceLocation("minecraft", "contents"))))
                );
        return  LootTable.lootTable().withPool(builder);
    }

    //Itt írjuk ki JSON-ba a loot tablet
    private  void writeTables(HashCache cache, Map<ResourceLocation, LootTable> tables){
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key,lootTable)->{
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/"+ key.getPath() +".json");
            try {
                DataProvider.save(GSON,cache,net.minecraft.world.level.storage.loot.LootTables.serialize(lootTable),path);
            }
            catch (IOException e) {
                ExampleMod.LOGGER.error("Error while writing loot table {}",path,e);
            }
        });
    }

    @Override
    public String getName() {
        return "ExampleMod LootTables";
    }
}
