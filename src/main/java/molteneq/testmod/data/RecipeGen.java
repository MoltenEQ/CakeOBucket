package molteneq.testmod.data;

import molteneq.testmod.registration.ModBlockRegistry;
import molteneq.testmod.registration.ModItemRegistry;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class RecipeGen extends RecipeProvider {
    public RecipeGen(DataGenerator genIn) {
        super(genIn);
    }

    @Override //Eléggé magától értetődő
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItemRegistry.TEST_ITEM.get())
                .pattern("xzx")
                .pattern(" y ")
                .pattern(" s ")//mindig egyforma husszú kell legyen minden aorbN!
                .define('x', Tags.Items.BONES)
                .define('s', Items.STICK)
                .define('z', Items.NETHERITE_PICKAXE)
                .define('y', Items.NETHER_STAR)
                .group("tutorial")
                .unlockedBy("bones", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHER_STAR))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItemRegistry.GEN_BLOCK_ITEM.get())
                .pattern("iii")
                .pattern("ici")
                .pattern("igi")//mindig egyforma husszú kell legyen minden aorbN!
                .define('i', Tags.Items.INGOTS_IRON)
                .define('c', Items.COAL_BLOCK)
                .define('g', Tags.Items.INGOTS_GOLD)
                .group("tutorial")
                .unlockedBy("gold", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT))
                .save(consumer);
    }
}
