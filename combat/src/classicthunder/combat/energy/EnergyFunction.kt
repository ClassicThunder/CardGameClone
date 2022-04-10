package classicthunder.combat.energy

interface EnergyFunction {
    fun onEnergyChanged(currentEnergy: Int, maxEnergy: Int)
}