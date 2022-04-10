package classicthunder.components.character

abstract class AICharacter(characterStats: CharacterStats) : Character(characterStats)
{
    abstract fun takeTurn(): Int
}