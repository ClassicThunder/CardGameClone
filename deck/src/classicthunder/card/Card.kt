package classicthunder.card

import classicthunder.character.CharacterStats
import com.badlogic.gdx.graphics.Texture

abstract class Card(
    val texture: Texture
) {
    abstract fun getEnergyCost(): Int
    abstract fun canApplyEffects(stats: CharacterStats): Boolean
    abstract fun applyEffects(stats: CharacterStats)
}