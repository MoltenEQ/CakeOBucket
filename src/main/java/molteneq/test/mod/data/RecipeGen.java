package molteneq.test.mod.data;

import molteneq.test.mod.registration.ModItemRegistry;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
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
                .unlockedBy("nether_star", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHER_STAR))
                .save(consumer);

        // BUCKET_O_CAKE RECIPE
        ShapelessRecipeBuilder.shapeless(ModItemRegistry.BUCKET_O_CAKE.get())
                .requires(Items.GOLDEN_APPLE)
                .requires(Items.BUCKET)
                .requires(Items.CAKE)
                .group("food")
                .unlockedBy("golden_apple", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLDEN_APPLE))
                .save(consumer);
    }
}
