package classicthunder.card

import classicthunder.character.CharacterStats
import classicthunder.character.CharacterType
import com.badlogic.gdx.graphics.Texture

class StrikeCard(texture: Texture) : Card(texture)
{
    override fun getEnergyCost(): Int {
        return 1
    }

    override fun canApplyEffects(stats: CharacterStats): Boolean {
        return stats.characterType == CharacterType.ENEMY
    }

    override fun applyEffects(stats: CharacterStats) {
        stats.AdjustHealth(-6)
    }
}