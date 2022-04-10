package classicthunder.combat.hand

import classicthunder.combat.card.CardActor
import com.badlogic.gdx.math.Bezier
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class CardBezier(area: Rectangle, private val cardSize: Vector2) {
    private val centerX: Float
    private val paddedLeft: Float
    private val paddedRight: Float

    init {
        centerX = area.x + area.width / 2
        val width = area.width - cardSize.x
        paddedLeft = centerX - width / 2f
        paddedRight = centerX + width / 2f
    }

    fun FindRestingPosition(cardsInHand: List<CardActor?>) {
        if (cardsInHand.size == 0) {
            return
        }

        // increment should be bigger up to a point
        val increment = 1f / 11f
        if (cardsInHand.size % 2 == 0) {
            val leftIncrement = 0.5f - (cardsInHand.size - 1) * increment / 2f
            for (x in cardsInHand.indices) {
                val xIncrement = leftIncrement + x * increment
                layoutCard(cardsInHand[x], xIncrement)
            }
        } else {
            if (cardsInHand.size == 1) {
                layoutCard(cardsInHand[0], 0.5f)
            } else {
                val centerCard = (cardsInHand.size - 1) / 2
                layoutCard(cardsInHand[centerCard], 0.5f)
                for (x in 1..centerCard) {
                    val backCard = centerCard - x
                    val backPct = 0.5f - increment * x
                    layoutCard(cardsInHand[backCard], backPct)
                    val forwardCard = centerCard + x
                    val forwardPct = 0.5f + increment * x
                    layoutCard(cardsInHand[forwardCard], forwardPct)
                }
            }
        }
    }

    private fun layoutCard(card: CardActor?, pct: Float) {
        val location = Bezier.quadratic(Vector2(), pct,
                Vector2(paddedLeft, 0.0f),
                Vector2(centerX, 100f),
                Vector2(paddedRight, 0.0f),
                Vector2())
        val maxY = Bezier.quadratic(Vector2(), 0.5f,
                Vector2(paddedLeft, 0.0f),
                Vector2(centerX, 100f),
                Vector2(paddedRight, 0.0f),
                Vector2()).y
        var rotation = (maxY - location.y) * .25f
        if (location.x > centerX) {
            rotation *= -1f
        }
        card!!.SetRestingPosition(location, rotation, cardSize)
    }
}