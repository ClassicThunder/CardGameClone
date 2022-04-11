package classicthunder.combat.energy

internal class Energy(private val baseAmount: Int, energyFunction: EnergyFunction)
{
    private var energy: Int
    private val energyFunction: EnergyFunction

    init {
        energy = baseAmount
        this.energyFunction = energyFunction
    }

    fun getEnergy(): Int {
        return energy
    }

    fun resetToBaseAmount() {
        energy = baseAmount
        energyFunction.onEnergyChanged(energy, baseAmount)
    }

    fun alterEnergy(amount: Int) {
        energy += amount
        energyFunction.onEnergyChanged(energy, baseAmount)
    }
}