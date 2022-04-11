package classicthunder.combat.hand

import classicthunder.combat.card.CardActor
import classicthunder.combat.card.Position
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.math.Interpolation
import kotlin.math.abs

internal class Discarder(
    private val discardCardPosition: Position,
    private val discardFunction: (MutableList<CardActor>) -> Unit
) {

    private val cardsBeingDiscarded: MutableList<CardActor?> = ArrayList()

    fun getCardCount(): Int {
        return cardsBeingDiscarded.size
    }

    fun addCards(cards: List<CardActor?>) {
        for (card in cards) {
            card!!.reset()
            card.setRestingPosition(
                discardCardPosition.location,
                discardCardPosition.rotation,
                discardCardPosition.size
            )
        }
        cardsBeingDiscarded.addAll(cards)
    }

    fun addCard(card: CardActor) {
        card.reset()
        card.setRestingPosition(
            discardCardPosition.location,
            discardCardPosition.rotation,
            discardCardPosition.size
        )
        cardsBeingDiscarded.add(card)
    }

    fun update() {
        val settledCards: MutableList<CardActor> = ArrayList()
        for (card in cardsBeingDiscarded) {
            card!!.update()
            if (card.getActualLocation().dst(discardCardPosition.location) < 10.0f) {
                settledCards.add(card)
            }
        }
        cardsBeingDiscarded.removeAll(settledCards)
        if (settledCards.size > 0) {
            discardFunction(settledCards)
        }
    }

    fun draw(batch: PolygonSpriteBatch?) {
        for (card in cardsBeingDiscarded) {
            val location = card!!.getActualLocation()
            val dst = abs(location.dst(discardCardPosition.location))
            val alpha = if (dst < 100) {
                Interpolation.slowFast.apply(dst / 100.0f)
            } else {
                1.0f
            }
            card.draw(batch, alpha)
        }
    }
}