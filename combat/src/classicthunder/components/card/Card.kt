package classicthunder.components.card

import classicthunder.components.character.CharacterStats
import com.badlogic.gdx.graphics.Texture

abstract class Card(val texture: Texture, val energyCost: Int) {

    abstract fun GetEnergyCost(): Int
    abstract fun CanApplyEffects(stats: CharacterStats): Boolean
    abstract fun ApplyEffects(stats: CharacterStats)
}