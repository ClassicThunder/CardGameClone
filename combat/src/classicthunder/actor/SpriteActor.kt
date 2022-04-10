package classicthunder.actor

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

abstract class SpriteActor(texture: Texture,
                           internal val location: Vector2 = Vector2(),
                           internal val origin: Vector2 = Vector2(),
                           internal val size: Vector2 = Vector2(),
                           internal val sprite: Sprite = Sprite(texture)
) {
    internal var offsetX = 0.0f
    internal var offsetY = 0.0f
    internal var alpha = 1.0f

    init {
        sprite.setOrigin(origin.x, origin.y)
    }

    fun reset() {
        offsetX = 0.0f
        offsetY = 0.0f
        alpha = 1.0f
    }

    fun containsMouse(mouse: Vector2): Boolean {
        return sprite.boundingRectangle.contains(mouse)
    }

    fun update() {
        sprite.setAlpha(alpha)
        sprite.setOriginBasedPosition(
            location.x + offsetX,
            location.y + offsetY)
    }

    open fun draw(batch: SpriteBatch) {
        sprite.draw(batch)
    }
}