package classicthunder.components.character

class CharacterStats(
    val characterType: CharacterType,
    private var health: Int,
    private var block: Int = 0)
{
    fun AdjustHealth(delta: Int) {
        health += delta
    }

    fun GetHealth(): Int {
        return health
    }

    fun AdjustBlock(delta: Int) {
        block += delta
    }

    fun GetBlock(): Int {
        return block
    }
}