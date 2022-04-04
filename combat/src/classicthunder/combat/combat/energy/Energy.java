package classicthunder.combat.combat.energy;

public class Energy {

    private int baseAmount;
    private int energy;

    private final EnergyFunction energyFunction;

    public Energy(int baseAmount, EnergyFunction energyFunction) {

        this.baseAmount = baseAmount;
        this.energy = baseAmount;

        this.energyFunction = energyFunction;
    }

    public void SetBaseAmount(int energy) {

        this.energy = energy;
    }

    public int GetEnergy() {

        return energy;
    }

    public void ResetToBaseAmount() {

        energy = baseAmount;
        energyFunction.onEnergyChanged(energy, baseAmount);
    }

    public void AlterEnergy(int amount) {

        energy += amount;
        energyFunction.onEnergyChanged(energy, baseAmount);
    }
}
