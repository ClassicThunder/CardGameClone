package classicthunder.combat.character

import classicthunder.actor.SpriteActor
import classicthunder.character.PlayableCharacter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

internal open class CharacterActor(
    internal open val character: PlayableCharacter,
    texture: Texture,
    location: Vector2
) : SpriteActor(
    texture,
    location,
    origin = Vector2(texture.width / 2f, 0f),
    size = Vector2(texture.width.toFloat(), texture.height.toFloat())
) {
    protected val font: BitmapFont = BitmapFont()

    override fun draw(batch: SpriteBatch) {

        super.draw(batch)

        font.draw(
            batch, "HP = " + character.characterStats.getHealth(),
            location.x, location.y
        )
        font.draw(
            batch, "Block = " + character.characterStats.getBlock(),
            location.x, location.y - 14
        )
    }
}