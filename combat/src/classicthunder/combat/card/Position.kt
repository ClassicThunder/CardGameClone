package classicthunder.combat.card

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

internal class Position(
    location: Vector2 = Vector2(),
    size: Vector2 = Vector2(),
    rotation: Float = 0.0f)
{
    var location: Vector2 = location.cpy()
        private set
        get() = field.cpy()

    var size: Vector2 = size.cpy()
        private set
        get() = field.cpy()

    var rotation = rotation
        private set

    fun set(location: Vector2, size: Vector2, rotation: Float) {
        this.location = location.cpy()
        this.size = size.cpy()
        this.rotation = rotation
    }

    fun lerpTowards(destination: Position, lerpAmount: Float) {
        location = location.lerp(destination.location, lerpAmount)
        size = size.lerp(destination.size, lerpAmount)
        rotation = MathUtils.lerp(rotation, 0f, lerpAmount)
    }
}