package classicthunder.character.impl

import classicthunder.character.CharacterStats
import classicthunder.character.NonPlayableCharacter

class StabbyBookPlayableCharacter(
    characterStats: CharacterStats
) : NonPlayableCharacter(characterStats) {
    private val damageRolls = sequenceOf(3, 8)

    private var intent = -1

    override fun resetIntent() {
        intent = -1
    }

    override fun rollIntent() {
        intent = damageRolls.shuffled().first()
    }

    override fun getIntent(): Int {
        return intent
    }
}