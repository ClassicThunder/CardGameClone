package classicthunder.combat.layout

import classicthunder.combat.card.Position
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport

class DeckLayout(var viewport: Viewport, drawLocation: Vector2, discardLocation: Vector2) {

    private val worldWidth = 1600f
    val centerWidth = worldWidth / 2f

    private val worldHeight = 900f
    val centerHeight = worldHeight / 2f

    private val edgeBuffer = 200f
    val handWidth = worldWidth - 2 * edgeBuffer

    internal val drawPosition: Position = Position(drawLocation, Vector2(100f, 150f), 0f)
    internal val discardPosition: Position = Position(discardLocation, Vector2(100f, 150f), 0f)
}