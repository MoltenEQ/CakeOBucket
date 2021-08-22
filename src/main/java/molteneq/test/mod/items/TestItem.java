package molteneq.test.mod.items;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.List;

public class TestItem extends PickaxeItem {

    public TestItem(Properties properties) {
        super(Tiers.NETHERITE, 1, -2.8f, properties);
    }

    //Extra Tool tip hozzáadása, fordítható
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(stack, level, list, flag);
        // kliensoldali oldal, így nem módosul a tárgy, amikor pl. megnézzük a GUI-ban
        int distance = stack.hasTag() ? stack.getTag().getInt("distance") : 0;
        list.add(new TranslatableComponent("message.test_item.tooltip", Integer.toString(distance+1))
                .withStyle(ChatFormatting.BLUE)); //Stílus beállítása
    }

    //Mire van beállítva a táv, NBT tagben tároljuk ugye
    public int getDistance(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt("distance") : 0;
    }

    /// bányászás felülírása
    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity player) {
        boolean result = super.mineBlock(stack, level, blockState, blockPos, player);
        if (result) {
            int dist = getDistance(stack);
            if (dist > 0) {
                CompoundTag tag = stack.getOrCreateTag();
                boolean mining = tag.getBoolean("mining");
                if (!mining) {
                    BlockHitResult hit = trace(level, player);
                    if (hit.getType() == HitResult.Type.BLOCK) {
                        tag.putBoolean("mining", true);
                        for (int i = 0; i < dist; i++) {
                            // ömm, ez elég durvának néz ki... A hit merőleges az eltalált blokk eltalát oldalára?
                            BlockPos relative = blockPos.relative(hit.getDirection().getOpposite(), i + 1);
                            if (!tryHarvest(stack, player, relative)) {
                                tag.putBoolean("mining", false);
                                return result;
                            }
                        }
                        tag.putBoolean("mining", false);

                    }
                }
            }

        }
        return result;
    }

    // használat felülírása, most nem kell kliens/szerver szétválasztás
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        //IMPORTANT!!! getOrCreate kell, mert nem biztos, hogy létezik hozzá tag!
        int distance = stack.getOrCreateTag().getInt("distance");
        distance++;
        //menjünk körbe
        if (distance > 3) {
            distance = 0;
        }
        stack.getTag().putInt("distance", distance);

        if (level.isClientSide()) { //kliensen üzenetküldés
            player.sendMessage(new TranslatableComponent("message.test_item.change", Integer.toString(distance+1)), Util.NIL_UUID);
        }
        return InteractionResultHolder.success(stack); // sikeres használat
        // return super.use(level, player, hand); Ha az eredeti használati módot is megvalósítanánk
    }

    private boolean tryHarvest(ItemStack stack, LivingEntity livingEntity, BlockPos relPos) {
        BlockState state = livingEntity.level.getBlockState(relPos);
        // régen canHarvestBlock
        if (isCorrectToolForDrops(stack, state)) {
            if (livingEntity instanceof ServerPlayer player) {
                return player.gameMode.destroyBlock(relPos); //Meghívja MineBlock()-ot!
            }
        }
        return false;
    }

    //Szép kis tracer bakker...
    private BlockHitResult trace(Level level, LivingEntity player) {
        double reach = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
        Vec3 eye = player.getEyePosition(1.0f); // egységvektorként??
        Vec3 view = player.getViewVector(1.0f);
        Vec3 withReach = eye.add(view.x * reach, view.y * reach, view.z * reach);
        return level.clip(new ClipContext(eye, withReach, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    }
}

