package classicthunder.character

import com.badlogic.gdx.graphics.Texture

abstract class NonPlayableCharacter(
    texture: Texture,
    characterStats: CharacterStats
) : PlayableCharacter(texture, characterStats) {
    abstract fun resetIntent()
    abstract fun rollIntent()
    abstract fun getIntent(): Int
}