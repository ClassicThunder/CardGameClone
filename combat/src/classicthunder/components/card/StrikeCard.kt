package classicthunder.components.card

import classicthunder.components.character.CharacterStats
import classicthunder.components.character.CharacterType
import com.badlogic.gdx.graphics.Texture

class StrikeCard(texture: Texture) : Card(texture, 1) {
    override fun GetEnergyCost(): Int {
        return 1
    }

    override fun CanApplyEffects(stats: CharacterStats): Boolean {
        return stats.characterType == CharacterType.ENEMY
    }

    override fun ApplyEffects(stats: CharacterStats) {
        stats.AdjustHealth(-6)
    }
}