package molteneq.testmod.blocks;

import molteneq.testmod.registration.ModContaienrReg;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class GeneratorContainer extends AbstractContainerMenu {

    private BlockEntity blockEntity;
    private Player playerEntity;
    private IItemHandler playerInventory;

    public GeneratorContainer(int windowId, Level world, BlockPos pos, Inventory inv, Player player) {
        super(ModContaienrReg.GENERATOR_CONTAINER.get(), windowId);
        blockEntity = world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(inv);

        //Ha van itemhandler
        if (blockEntity !=null) {
            blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h->{
                addSlot(new SlotItemHandler(h,0,64,24)); //minden slotnak van grafikai helye
            });
            layoutPlayerInvertorySlots(10,70);

        }
    }

    private void layoutPlayerInvertorySlots(int leftCol, int topRow) {
        //Player inv
        addSlotBox(playerInventory,9,leftCol,topRow,9,18,3,18);

        //hotbar
        topRow+=58;
        addSlotRange(playerInventory,0,leftCol,topRow,9,18,);
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}
