package classicthunder.character

class CharacterStats(
    val characterType: CharacterType,
    private var health: Int,
    private var block: Int = 0
) {
    /** Lifecycle */
    fun reset() {
        block = 0
    }

    /**
     * Try to afflict damage taking into account all modifiers.
     */
    fun dealDamage(damage: Int) {
        val finalDamage = damage - block
        if (finalDamage > 0) {
            health -= finalDamage
        }

        val finalBlock = block - damage
        block = if (finalBlock <= 0) 0 else finalBlock
    }

    fun getHealth(): Int {
        return health
    }

    fun adjustBlock(delta: Int) {
        block += delta
    }

    fun getBlock(): Int {
        return block
    }
}