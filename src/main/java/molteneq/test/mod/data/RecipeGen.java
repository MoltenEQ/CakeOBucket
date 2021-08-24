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
