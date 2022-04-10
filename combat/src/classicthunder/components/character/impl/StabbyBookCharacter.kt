package classicthunder.components.character.impl

import classicthunder.components.character.AICharacter
import classicthunder.components.character.CharacterStats

class StabbyBookCharacter(characterStats: CharacterStats) : AICharacter(characterStats)
{
    private val damageRolls = sequenceOf(3, 8)

    override fun takeTurn(): Int {
        return damageRolls.shuffled().first()
    }
}