package classicthunder.combat.card

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

class Position {

    var location = Vector2()
        private set
        get() = field.cpy()

    var size = Vector2()
        private set
        get() = field.cpy()

    var rotation = 0f
        private set

    constructor() { }

    constructor(location: Vector2?, size: Vector2?, rotation: Float) {
        set(location, size, rotation)
    }

    fun set(location: Vector2?, size: Vector2?, rotation: Float) {
        this.location = location!!.cpy()
        this.size = size!!.cpy()
        this.rotation = rotation
    }

    fun lerpTowards(destination: Position, lerpAmount: Float) {
        location = location.lerp(destination.location, lerpAmount)
        size = size.lerp(destination.size, lerpAmount)
        rotation = MathUtils.lerp(rotation, 0f, lerpAmount)
    }
}