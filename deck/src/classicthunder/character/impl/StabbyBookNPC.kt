package classicthunder.character.impl

import classicthunder.character.AINPC
import classicthunder.character.CharacterStats
import com.badlogic.gdx.graphics.Texture

class StabbyBookNPC(
    texture: Texture,
    characterStats: CharacterStats
) : AINPC(texture, characterStats)
{
    private val damageRolls = sequenceOf(3, 8)

    override fun takeTurn(): Int {
        return damageRolls.shuffled().first()
    }
}