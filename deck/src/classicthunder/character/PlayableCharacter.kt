package classicthunder.character

import com.badlogic.gdx.graphics.Texture

open class PlayableCharacter(
    texture: Texture,
    var characterStats: CharacterStats
) {
    var texture: Texture = texture
        private set

    fun onTurnStart() {

    }

    fun onTurnEnd() {

    }
}