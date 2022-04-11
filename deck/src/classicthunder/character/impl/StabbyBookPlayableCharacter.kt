package classicthunder.character.impl

import classicthunder.character.CharacterStats
import classicthunder.character.NonPlayableCharacter
import com.badlogic.gdx.graphics.Texture

class StabbyBookPlayableCharacter(
    texture: Texture,
    characterStats: CharacterStats
) : NonPlayableCharacter(texture, characterStats) {
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