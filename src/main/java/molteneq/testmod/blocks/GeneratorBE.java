package molteneq.testmod.blocks;

import molteneq.testmod.registration.ModBERegisrty;
import molteneq.testmod.tools.CostumEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneratorBE extends BlockEntity {


    private final ItemStackHandler itemHandler = createHandler(); //A FORGE-ból, elég nyílvánvaló, mit csinál :)
    private final CostumEnergyStorage energyStorage = createEnergyStorage();

    //A belső tulajdonságokat "capabilities"-ekkel (képességekkel) tudjuk elérhetővé tenni a külvilág számára
    // Ne a getCapability-ben hozzuk őket létre!
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(()->itemHandler);
    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(()->energyStorage);

    //Hol adunk neki értéket??
    private int counter;


    public GeneratorBE( BlockPos pos, BlockState state) {
        super(ModBERegisrty.GEN_BLOCK_ENTITY.get(),pos,state);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        //fontos, hogy invalidáljuk a képességeket, ha a blokkot kiütik!
        handler.invalidate();
        energy.invalidate();
    }

    public void tickServer(BlockState state){

        //égés
        if (counter >0) {
            counter--;
            energyStorage.addEnergy(50);
            setChanged(); //Mentésre jelöli ki
        }
        if (counter <= 0) {
            ItemStack stack = itemHandler.getStackInSlot(0);
            int burnTime = ForgeHooks.getBurnTime(stack,RecipeType.SMELTING);
            if (burnTime>0) {
                itemHandler.extractItem(0,1,false);
                counter = burnTime;
                setChanged();
            }
        }

        //itt frissítjük az állapotot
        BlockState blockState = level.getBlockState(worldPosition);
        if (blockState.getValue(BlockStateProperties.POWERED) != (counter>0) ){
            level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWERED, (counter > 0)),
                    //érdekes megoldás az intek összeadása erre a célra... ezek mind prímek?
                    Constants.BlockFlags.NOTIFY_NEIGHBORS + Constants.BlockFlags.BLOCK_UPDATE);
        }

        sendOutPower();
    }

    private void sendOutPower() {
        AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored()); //Azért ilyen, hogy egy lambdában is használhassuk
        if (capacity.get() > 0) {
            for (Direction direction : Direction.values()) {
                BlockEntity te = level.getBlockEntity(worldPosition.relative(direction));
                if (te != null) {
                    boolean doContinue = te.getCapability(CapabilityEnergy.ENERGY, direction).map( handler ->{
                        if (handler.canReceive()) {
                            int recieved = handler.receiveEnergy(Math.min(capacity.get(),1000),false); //itt küldjük az energiát át, és megmondjuk, hogy mennyi sikerült
                            // szerintem ezért éppen felesleges a min, úgyis csak annyi energiát fogad el, de tőlem...

                            capacity.addAndGet(-recieved);
                            energyStorage.consumeEnergy(recieved);
                            setChanged();
                            return capacity.get() > 0;
                        } else {
                            return true;
                        }
                    }).orElse(true);

                    if ( (!doContinue)) {
                        return;
                    }
                }
            }
        }
    }

    //     * Hogyan mentsük ezeket a tulajdonságokat és töltsük vissza?

    @Override
    public void load(CompoundTag tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv")); //inv jelöléssel táruljuk a stack-et
        energyStorage.deserializeNBT(tag.getCompound("energy")); //detto

        counter = tag.getInt("counter");
        super.load(tag);
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.put("inv",itemHandler.serializeNBT()); //inv jelöléssel táruljuk a stack-et
        tag.put("energy",energyStorage.serializeNBT()); //detto

        tag.putInt("counter",counter);
        return  super.save(tag);
    }

    private ItemStackHandler createHandler(){
        // névtelen osztály
        return  new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                //Umm, ez miért is kell? Valamit meg akarunk tartani, ha a chunk kimentődik?
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                //azért így csináljuk, hogy a hardcoded üzemanyagokat lehessen bővíteni
                return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) >0;
            }

            @Nonnull
            @Override
            /**
             * Csak akkor lehessen berakni, ha odavaló,
             * lehetne isItemValiddal is ellenőrizni?
             */
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING)<=0) return stack;
                else return super.insertItem(slot,stack,simulate);
            }
        };
    }

    private  CostumEnergyStorage createEnergyStorage(){
        //Nem igazán értem miért kell felülírni ezt itt...
        return new CostumEnergyStorage(100000,0){
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }

    @Nonnull
    @Override
    /**
     * Itt tudják megkérezni, hogy van-e ilyen képességünk
     */
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return handler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY){
            return energy.cast();
        }
        return super.getCapability(cap,side);
    }
}
