package molteneq.testmod.tools;

import net.minecraftforge.energy.EnergyStorage;

/**
 * Saját energiatárolás, a FORGE-ból örökölt képességekkel
 */
public class CostumEnergyStorage extends EnergyStorage {
    public CostumEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    //Miért nem itt implementáljuk?
    protected void onEnergyChanged() {

    }

    public void addEnergy(int energy){
        this.energy += energy;
        if (this.energy >= getMaxEnergyStored()){
            this.energy = getEnergyStored();  // UMM EZ MI A FENE?
        }
        onEnergyChanged();
    }

    public void consumeEnergy(int energy) {
        this.energy -= energy;
        if (this.energy < 0){
            this.energy = 0;
        }
    }
}
