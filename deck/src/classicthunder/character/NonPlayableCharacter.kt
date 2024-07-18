package classicthunder.character

abstract class NonPlayableCharacter(
    characterStats: CharacterStats
) : PlayableCharacter(characterStats) {
    abstract fun resetIntent()
    abstract fun rollIntent()
    abstract fun getIntent(): Int
}