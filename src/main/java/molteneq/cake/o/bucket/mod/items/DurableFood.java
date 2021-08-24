package molteneq.cake.o.bucket.mod.items;

import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class DurableFood extends Item {
    public DurableFood(Properties p_41383_) {

        super(p_41383_);
    }

    public static final FoodProperties BUCKET_O_CAKE = new FoodProperties.Builder() //FOOD PROPERTIES
            .nutrition(6) //should be 3 fowls
            .saturationMod(0.5f) //half full?
            .build();

    public static final int DEFAULT_DURABILITY = 128;

    /**
     * We override this to decrease durability instead of consuming item.
     * If durability reaches zero, we return a bucket
     * I didn't check if it's called each time, so maybe free buckets, yay!
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (stack.getDamageValue() >= (DEFAULT_DURABILITY)) {
            noConsumeEat(level, stack, entity);
            ItemStack itemstack = super.finishUsingItem(stack, level, entity); // Calling base class here
            return entity instanceof Player && ((Player) entity).getAbilities().instabuild ? itemstack : new ItemStack(Items.BUCKET);
        } else {
            noConsumeEat(level, stack, entity);
            return stack;
        }

    }

    /**
     * I have to go around player.eat(), since it calls LivingEntity.eat() which removes one item.
     */
    public ItemStack noConsumeEat(Level level, ItemStack stack, LivingEntity entity) {
        if (stack.isEdible()) {
            if (entity instanceof Player player) { //TODO Any better way to implement this? Why is consuming the item hardcoded :(
                player.getFoodData().eat(stack.getItem(), stack);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
                }
            }
            //What is server side and what is client side?
            level.gameEvent(entity, GameEvent.EAT, entity.eyeBlockPosition());
            level.playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), entity.getEatingSound(stack), SoundSource.NEUTRAL, 1.0F, 1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
            // entity.addEatEffect(stack, level, this); // TODO addEatEffect is private, let's skip
            Item item = stack.getItem();
            if (item.isEdible()) {
                for (Pair<MobEffectInstance, Float> pair : item.getFoodProperties().getEffects()) {
                    if (!level.isClientSide && pair.getFirst() != null && level.random.nextFloat() < pair.getSecond()) {
                        entity.addEffect(new MobEffectInstance(pair.getFirst()));
                    }
                }
            }
            entity.gameEvent(GameEvent.EAT);

            stack.hurtAndBreak(1,entity,(p_40992_) -> { //WHAT IS THIS CONSUMER?
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);}); //UMM? TODO this could be eaten in offhand
        }

        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (this.isEdible())
        {
            ItemStack itemstack = player.getItemInHand(hand);
            if (player.canEat(this.getFoodProperties().canAlwaysEat())) {
                player.startUsingItem(hand);
                if (!level.isClientSide) {
                    return InteractionResultHolder.consume(itemstack); //KEY LINE HERE!
                    // We don't want to consume the item upon use!
                }
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
        // else
        return InteractionResultHolder.pass(player.getItemInHand(hand));

    }

}



