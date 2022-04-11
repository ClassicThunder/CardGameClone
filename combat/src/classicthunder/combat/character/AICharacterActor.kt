package classicthunder.combat.character

import classicthunder.character.NonPlayableCharacter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

internal class AICharacterActor(
    override val character: NonPlayableCharacter,
    texture: Texture,
    location: Vector2
) : CharacterActor(character, texture, location) {
    override fun draw(batch: SpriteBatch) {

        super.draw(batch)

        super.font.draw(
            batch, "Intent = " + character.getIntent(),
            location.x, location.y - (14 * 2)
        )
    }
}