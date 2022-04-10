package classicthunder.combat.hand

import classicthunder.combat.card.CardActor
import classicthunder.combat.card.Position
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.math.Interpolation

class Discarder(
        private val discardCardPosition: Position?,
        private val discardFunction: (MutableList<CardActor?>) -> Unit) {

    private val cardsBeingDiscarded: MutableList<CardActor?> = ArrayList()

    fun GetCardCount(): Int {
        return cardsBeingDiscarded.size
    }

    fun ClearCards() {
        cardsBeingDiscarded.clear()
    }

    fun AddCards(cards: List<CardActor?>) {
        for (card in cards) {
            card!!.Reset()
            card.SetRestingPosition(
                    discardCardPosition!!.location,
                    discardCardPosition.rotation,
                    discardCardPosition.size)
        }
        cardsBeingDiscarded.addAll(cards)
    }

    fun AddCard(card: CardActor) {
        card.Reset()
        card.SetRestingPosition(
                discardCardPosition!!.location,
                discardCardPosition.rotation,
                discardCardPosition.size)
        cardsBeingDiscarded.add(card)
    }

    fun Update() {
        val settledCards: MutableList<CardActor?> = ArrayList()
        for (card in cardsBeingDiscarded) {
            card!!.Update()
            if (card.GetActualLocation()!!.dst(discardCardPosition!!.location) < 10.0f) {
                settledCards.add(card)
            }
        }
        cardsBeingDiscarded.removeAll(settledCards)
        if (settledCards.size > 0) {
            discardFunction(settledCards)
        }
    }

    fun Draw(batch: PolygonSpriteBatch?) {
        for (card in cardsBeingDiscarded) {
            var alpha: Float
            val location = card!!.GetActualLocation()
            val dst = Math.abs(location!!.dst(discardCardPosition!!.location))
            alpha = if (dst < 100) {
                Interpolation.slowFast.apply(dst / 100.0f)
            } else {
                1.0f
            }
            card.Draw(batch, alpha)
        }
    }
}